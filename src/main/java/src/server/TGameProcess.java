package src.server;

import src.CommandsEnum;
import src.server.interfaces.GameClient;
import src.server.interfaces.GameProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TGameProcess implements GameProcess {


    private HashMap<String, MyGameClient> clients = new HashMap<>();

    private Stack stack;

    private HashMap<String,Collection<Card>> playerStacks = new HashMap<>();
    private ArrayList<String> rotaryTable =new ArrayList<>();

    private HashMap<String,Integer> playerScore = new HashMap<>();

    public TGameProcess(int num){
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
        return state==1;
    }

    @Override
    public void addPlayers(GameClient client) {
        clients.put(client.getClientId(), (MyGameClient) client);
        playerStacks.put(client.getClientId(),new ArrayList<>());
        playerScore.put(client.getClientId(),0);

    }

    @Override
    public void onMessage(GameClient client, String message) {

        CommandsEnum command = CommandsParser.getCommand(message);
        if (command==null) {
            // TODO: 2023/2/3 无法识别命令
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
            } case MOVE:{
                String action = CommandsParser.splitCommand(message)[1];
                //玩家选择抽牌的话
                if (action.equals("DRAW")) {
                    //给玩家发牌
                    String curCards = genPlayerCurrentCards(client,1);
                    client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(),curCards));
                }else {//玩家出牌
                    moveCard(client,action);
                }
                return;
                //发送消息
            } case CHAT:{
                String msg = CommandsParser.splitCommand(message)[1];
                sendChatMessage(client,msg);
                return;
            }case TAKE:{
                //没用到
                return;
            }case CHANGE:{
                String targetPlayerName = CommandsParser.splitCommand(message)[1];
                changeCards(client,targetPlayerName);
                return;
            }
            default:
        }
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
    public void sendChatMessage(GameClient client,String msg){
        for (MyGameClient player : clients.values()) {
            //不发给自己
            if (player!=client) {
                player.send(CommandsParser.merge("FROM",client.getClientId(),msg));
            }
        }
    }
    public void handCard(GameClient client){

        String collect = playerStacks.get(client.getClientId()).stream().map(Card::getCarStr).collect(Collectors.joining(","));
        client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(),collect));
    }

    public void errorMsg(GameClient client,ErrorCodeEnum error){
        client.send(CommandsParser.merge(CommandsEnum.ERROR.getCommand(),error.getTag()));
    }

    /**
     * 出牌
     */
    public void moveCard(GameClient client,String action){
        Collection<Card> cards = playerStacks.get(client.getClientId());
        Card card = null;

        for (Card item : cards) {
            //识别的奥当前牌的信息
            if (item.getCarStr().equals(action)) {
                card = item;

                //减少玩家的牌
                cards.remove(card);
                //加玩家的分
                playerScorePlus(client,1);

                break;
            }
        }
        // TODO: 2023/2/4  没有牌这张牌的异常
        if(card==null){

        }


        //牌出完了宣布胜利
        if (cards.size()==0) {
            gameOver(client);
        }

    }
    public void gameOver(GameClient winner){
        state = 2;

        String players = String.join(",", playerScore.keySet());

        for (MyGameClient client : clients.values()) {
            String collect = playerScore.values().stream().map(String::valueOf).collect(Collectors.joining(","));
            //
            client.send(CommandsParser.merge("WIN",players,collect));
            // TODO: 2023/2/3 后续
//            client.send(CommandsParser.merge("END",players,curCards));
        }
    }

    /**
     * 开始游戏线程
     */
    public void startGameProcess(){
        //开启转盘
        rotaryTable.addAll(clients.keySet());
        currentPosition = 0;

        String players = String.join(",", clients.keySet());


        //给当前游戏玩家发送消息
        for (MyGameClient client : clients.values()) {
            //每个玩家发五张牌
            String curCards = genPlayerCurrentCards(client,5);
            //
            client.send(CommandsParser.merge("START",players,curCards));
        }

    }
    public void notifyPlayersOperating(){
        // 告诉玩家开始出牌
        GameClient client = clients.get(rotaryTable.get(currentPosition));
        client.send("");
        if (clockwise) {
            currentPosition++;
            if(currentPosition==rotaryTable.size()){
                currentPosition = 0;
            }
        }else {
            currentPosition--;
            if(currentPosition<0){
                currentPosition = rotaryTable.size()-1;
            }
        }
    }

    /**
     * 创玩家当前的卡片
     *
     * @param client 客户端
     * @return {@link String}
     */
    public String genPlayerCurrentCards(GameClient client,int cardNum){
        Collection<Card> cards = playerStacks.get(client.getClientId());
        while (cardNum--!=0){
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
    public void playerScorePlus(GameClient player,int score){
        String clientId = player.getClientId();
        Integer integer = playerScore.get(clientId);
        playerScore.put(clientId,integer+score);
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
