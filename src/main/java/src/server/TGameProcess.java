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

    /**
     * Move card
     *
     * @param client is a player
     */
    @Override
    public boolean isGaming(GameClient client) {
        return clients.values().contains(client) && state == 1;
    }

    /**
     * Move card
     *
     * @param client is a player
     */
    @Override
    public void addPlayers(GameClient client) {
        clients.put(client.getClientId(), (MyGameClient) client);
        playerStacks.put(client.getClientId(), new ArrayList<>());
        playerScore.put(client.getClientId(), 0);

    }

    /**
     * Move card
     *
     * @param client is a player
     * @param message is a String
     */
    @Override
    public synchronized void onMessage(GameClient client, String message) {

        CommandsEnum command = CommandsParser.getCommand(message);
        if (command == null) {
            //  error
            errorMsg(client, ErrorCodeEnum.E3);
            return;
        }
        switch (command) {
            case PLAY: {
                String players = String.join(",", clients.keySet());
                client.send(CommandsParser.merge(CommandsEnum.QUEUE.getCommand(), players));
                //enough player to start
                if (clients.size() == num) {
                    startGameProcess();
                }

                return;
                //operation
            }
            case MOVE: {
                String action = CommandsParser.splitCommand(message)[1];

                String curPlayer = rotaryTable.get(currentPosition);
                //not u turn
                if (!curPlayer.equals(client.getClientId())) {
                    errorMsg(client, ErrorCodeEnum.E3);
                    return;
                }
                //
                if (action.equals("DRAW")) {
                    //draw card
                    String curCards = genPlayerCurrentCards(client, 1);
                    client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(), curCards));
                } else {//place card
                    moveCard(client, action);
                    String collect = playerStacks.get(client.getClientId()).stream().map(Card::getCarStr).collect(Collectors.joining(","));
                    client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(), collect));
                }
                notifyPlayersOperating();
                return;
            }
            //send message
            case CHAT: {
                String msg = CommandsParser.splitCommand(message)[1];
                sendChatMessage(client, msg);
                return;
            }
            case TAKE: {
                //did not use
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
    /**
     * Move card
     *
     * @param nowCard is a card from player
     */
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
     * exchange card
     *
     * @param client
     * @param targetPlayerName
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
     * Send message
     *
     * @param client is a player
     * @param msg is a string
     */
    public void sendChatMessage(GameClient client, String msg) {
        for (MyGameClient player : clients.values()) {
            //
            if (player != client) {
                player.send(CommandsParser.merge("FROM", client.getClientId(), msg));
            }
        }
    }
    /**
     * Move card
     *
     * @param client is a player
     */
    public void handCard(GameClient client) {

        String collect = playerStacks.get(client.getClientId()).stream().map(Card::getCarStr).collect(Collectors.joining(","));
        client.send(CommandsParser.merge(CommandsEnum.HAND.getCommand(), collect));
    }
    /**
     * Move card
     *
     * @param client is a player
     * @param error is listed
     */
    public void errorMsg(GameClient client, ErrorCodeEnum error) {
        client.send(CommandsParser.merge(CommandsEnum.ERROR.getCommand(), error.getTag()));
    }

    /**
     * Move card
     *
     * @param client is a player
     * @param action is a action
     */
    public void moveCard(GameClient client, String action) {
        Collection<Card> cards = playerStacks.get(client.getClientId());
        Card card = null;

        for (Card item : cards) {
            //current card number
            if (item.getCarStr().equals(action)) {
                card = item;
                break;
            }
        }
        //
        if (card == null) {
            errorMsg(client, ErrorCodeEnum.E1);
            return;
        }
        // effective
        if(!moveValid(card)){
            errorMsg(client, ErrorCodeEnum.E2);
            return;
        }
        //place
        placedCards.add(new Card(card.getType(), card.getColor(), card.getNumber()));
        //boardcast
        updateCard(client, action, card);
        //remove from hand
        cards.remove(card);
        //adding score
        playerScorePlus(client, 1);


        //Winner Decalar
        if (cards.size() == 0) {
            gameOver(client);
        }

    }
    /**
     * Update card on hand
     *
     * @param client is a player
     * @param action is at list
     * @param card is a card
     */
    public void updateCard(GameClient client, String action, Card card) {
        for (GameClient player : clients.values()) {

            if (player != client) {
                player.send(CommandsParser.merge(BroadcastsEnum.UPDATE.getTag(), client.getClientId(), action));
            }
        }
    }
    /**
     * end game
     *
     * @param winner is a player
     */
    public void gameOver(GameClient winner) {
        state = 2;

        String players = String.join(",", playerScore.keySet());

        for (MyGameClient client : clients.values()) {
            String collect = playerScore.values().stream().map(String::valueOf).collect(Collectors.joining(","));
            //
            client.send(CommandsParser.merge("WIN", players, collect));
            // TODO:
//            client.send(CommandsParser.merge("END",players,curCards));
        }
    }

    /**
     * start game thread
     */
    public void startGameProcess() {
        //
        rotaryTable.addAll(clients.keySet());
        currentPosition = 0;
        state = 1;

        String players = String.join(",", clients.keySet());


        //sending message
        for (MyGameClient client : clients.values()) {
            //sending five cards to each player
            String curCards = genPlayerCurrentCards(client, 5);
            //
            client.send(CommandsParser.merge("START", players, curCards));
        }

    }

    /**
     * next operation
     */
    public void notifyPlayersOperating() {
        // tell player to place card
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
     * create beginning cards
     *
     * @param client is a player
     * @return {@link String} stanard card string
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
     * Adding score to player
     *
     * @param player is a client
     * @param score  is a int
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
