package src.server;

import src.BroadcastsEnum;
import src.CommandsEnum;
import src.server.interfaces.GameClient;
import src.server.interfaces.GameProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TGameProcess implements GameProcess {


    private HashMap<String, MyGameClient> clients = new HashMap<>();

    private Stack stack;

    private HashMap<String, Collection<Card>> playerStacks = new HashMap<>();

    private ArrayList<Card> placedCards = new ArrayList<>();
    private ArrayList<String> rotaryTable = new ArrayList<>();

    private HashMap<String, Integer> playerScore = new HashMap<>();

    public TGameProcess(int num) {
        this.state = 0;
        this.num = num;
        stack = new Stack();
    }

    /**
     * 状态：  0 队列 1 开始 2 结束
     */
    private int state;


    private int currentPosition;
    private boolean clockwise;

    private int num;

    @Override
    public boolean isGaming(GameClient client) {
        return clients.values().contains(client) && state == 1;
    }

    @Override
    public void addPlayers(GameClient client) {
        clients.put(client.getClientId(), (MyGameClient) client);
        playerStacks.put(client.getClientId(), new ArrayList<>());
        playerScore.put(client.getClientId(), 0);

    }

    @Override
    public synchronized void onMessage(GameClient client, String message) {

        CommandsEnum command = CommandsParser.getCommand(message);
        if (command == null) {
            //  无法识别命令
            errorMsg(client, ErrorCodeEnum.E3);
            return;
        }
        switch (command) {
            case PLAY: {
                String players = String.join(",", clients.keySet());
                client.send(CommandsParser.merge(CommandsEnum.QUEUE.getCommand(), players));
                //如果当前玩家数量足够,就开启游戏了
                if (clients.size() == num) {
                    startGameProcess();
                }

                return;
                //操作
            }
            case MOVE: {
                String action = CommandsParser.splitCommand(message)[1];
                //当前发言用户
                String curPlayer = rotaryTable.get(currentPosition);
                //轮不到你
                if (!curPlayer.equals(client.getClientId())) {
                    errorMsg(client, ErrorCodeEnum.E3);
                    return;
                }
                //玩家选择抽牌的话
                if (action.equals("DRAW")) {
                    //给玩家发牌
                    String curCards = genPlayerCurrentCards(client, 1);
                    client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(), curCards));
                } else {//玩家出牌
                    moveCard(client, action);
                    String collect = playerStacks.get(client.getClientId()).stream().map(Card::getCarStr).collect(Collectors.joining(","));
                    client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(), collect));
                }
                notifyPlayersOperating();
                return;
            }
            //发送消息
            case CHAT: {
                String msg = CommandsParser.splitCommand(message)[1];
                sendChatMessage(client, msg);
                return;
            }
            case TAKE: {
                //没用到
                return;
            }
            case CHANGE: {
                String targetPlayerName = CommandsParser.splitCommand(message)[1];
                changeCards(client, targetPlayerName);
                return;
            }
            default:
        }
    }

    public boolean moveValid(Card nowCard) {
        if (placedCards.size()<1) {
            return true;
        }
        Card oldCard = placedCards.get(placedCards.size());
        if (oldCard.getColor() == nowCard.getColor()
                || (oldCard.getType() == Type.NUMBER &&
                oldCard.getNumber() == nowCard.getNumber())) {
            return true;
        }
        return false;
    }

    /**
     * 交换卡片
     *
     * @param client           客户端
     * @param targetPlayerName 目标玩家名称
     */
    public void changeCards(GameClient client, String targetPlayerName) {
        Collection<Card> cards = playerStacks.get(targetPlayerName);
        ArrayList<Card> cards1 = new ArrayList<>(cards);
        cards.clear();
        Collection<Card> cards2 = playerStacks.get(client.getClientId());
        cards.addAll(cards2);
        cards2.clear();
        cards2.addAll(cards1);
    }

    /**
     * 发送聊天消息
     *
     * @param client 客户端
     * @param msg    消息
     */
    public void sendChatMessage(GameClient client, String msg) {
        for (MyGameClient player : clients.values()) {
            //不发给自己
            if (player != client) {
                player.send(CommandsParser.merge("FROM", client.getClientId(), msg));
            }
        }
    }

    public void handCard(GameClient client) {

        String collect = playerStacks.get(client.getClientId()).stream().map(Card::getCarStr).collect(Collectors.joining(","));
        client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(), collect));
    }

    public void errorMsg(GameClient client, ErrorCodeEnum error) {
        client.send(CommandsParser.merge(CommandsEnum.ERROR.getCommand(), error.getTag()));
    }

    /**
     * 出牌
     */
    public void moveCard(GameClient client, String action) {
        Collection<Card> cards = playerStacks.get(client.getClientId());
        Card card = null;

        for (Card item : cards) {
            //识别的奥当前牌的信息
            if (item.getCarStr().equals(action)) {
                card = item;
                break;
            }
        }
        //  没有牌这张牌的异常
        if (card == null) {
            errorMsg(client, ErrorCodeEnum.E1);
            return;
        }
        // 有效
        if(!moveValid(card)){
            errorMsg(client, ErrorCodeEnum.E2);
            return;
        }
        //放置
        placedCards.add(new Card(card.getType(), card.getColor(), card.getNumber()));
        //告诉大家
        updateCard(client, action, card);
        //减少玩家的牌
        cards.remove(card);
        //加玩家的分
        playerScorePlus(client, 1);


        //牌出完了宣布胜利
        if (cards.size() == 0) {
            gameOver(client);
        }

    }

    public void updateCard(GameClient client, String action, Card card) {
        for (GameClient player : clients.values()) {
            //不发给自己
            if (player != client) {
                player.send(CommandsParser.merge(BroadcastsEnum.UPDATE.getTag(), client.getClientId(), action));
            }
        }
    }

    public void gameOver(GameClient winner) {
        state = 2;

        String players = String.join(",", playerScore.keySet());

        for (MyGameClient client : clients.values()) {
            String collect = playerScore.values().stream().map(String::valueOf).collect(Collectors.joining(","));
            //
            client.send(CommandsParser.merge("WIN", players, collect));
            // TODO: 2023/2/3 后续
//            client.send(CommandsParser.merge("END",players,curCards));
        }
    }

    /**
     * 开始游戏线程
     */
    public void startGameProcess() {
        //开启转盘
        rotaryTable.addAll(clients.keySet());
        currentPosition = 0;
        state = 1;

        String players = String.join(",", clients.keySet());


        //给当前游戏玩家发送消息
        for (MyGameClient client : clients.values()) {
            //每个玩家发五张牌
            String curCards = genPlayerCurrentCards(client, 5);
            //
            client.send(CommandsParser.merge("START", players, curCards));
        }

    }

    /**
     * next 操作
     */
    public void notifyPlayersOperating() {
        // 告诉玩家开始出牌
        GameClient client = clients.get(rotaryTable.get(currentPosition));
//        client.send("");
        if (clockwise) {
            currentPosition++;
            if (currentPosition == rotaryTable.size()) {
                currentPosition = 0;
            }
        } else {
            currentPosition--;
            if (currentPosition < 0) {
                currentPosition = rotaryTable.size() - 1;
            }
        }
    }

    /**
     * 创玩家当前的卡片
     *
     * @param client 客户端
     * @return {@link String}
     */
    public String genPlayerCurrentCards(GameClient client, int cardNum) {
        Collection<Card> cards = playerStacks.get(client.getClientId());
        while (cardNum-- != 0) {
            Card card = stack.getCard();
            cards.add(card);
        }
//        Collection<String> cardsStr = new ArrayList<>();
        String collect = cards.stream().map(Card::getCarStr).collect(Collectors.joining(","));
        //String.join(",",cardsStr)
        return collect;
    }

    /**
     * 玩家得分+
     *
     * @param player 球员
     * @param score  分数
     */
    public void playerScorePlus(GameClient player, int score) {
        String clientId = player.getClientId();
        Integer integer = playerScore.get(clientId);
        playerScore.put(clientId, integer + score);
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public int getPlayerNum() {
        return num;
    }
}
