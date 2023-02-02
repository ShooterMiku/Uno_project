package src.server;

import src.CommandsEnum;
import src.server.interfaces.GameClient;
import src.server.interfaces.GameProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TGameProcess implements GameProcess {


    private HashMap<String, MyGameClient> clients = new HashMap<>();

    private Stack stack;

    private HashMap<String,Collection<Card>> playerStacks = new HashMap<>();
    public TGameProcess(int num){
        this.state = 0;
        this.num = num;
        stack = new Stack();
    }
    /**
     * 状态：  0 队列 1 开始 2 结束
     */
    private int state;
    private int num;

    @Override
    public boolean isGaming(GameClient client) {
        return state==1;
    }

    @Override
    public void addPlayers(GameClient client) {
        clients.put(client.getClientId(), (MyGameClient) client);
        playerStacks.put(client.getClientId(),new ArrayList<>());
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
                String action = CommandsParser.splitCommand(message)[0];
                if (action.equals("CARD")) {

                }else {

                }
                return;
                //举手
            } case HAND:{

                return;
            }
            default:
        }
    }
    public void startGameProcess(){
        String players = String.join(",", clients.keySet());;
        Collection<MyGameClient> values = clients.values();

        //给当前游戏玩家发送消息
        for (MyGameClient client : values) {
            //每个玩家发五张牌
            String curCards = genPlayerCurrentCards(client,5);
            //
            client.send(CommandsParser.merge("START",players,curCards));
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
    @Override
    public int getState() {
        return state;
    }

    @Override
    public int getPlayerNum() {
        return num;
    }
}
