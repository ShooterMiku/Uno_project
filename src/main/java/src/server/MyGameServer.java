package src.server;


import src.CommandsEnum;
import src.client.AiPlayer;
import src.server.interfaces.GameClient;
import src.server.interfaces.GameProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyGameServer extends AbstractGameServer {


    private List<GameProcess> gameProcessList = new ArrayList<>();

    public MyGameServer(int port) throws IOException {
        super(port);
    }

    @Override
    public void onConnect(GameClient client) {
        System.out.println("[Server]: client " + client.getClientId() + " connected");
    }

    @Override
    public void onMessage(GameClient client, String message) {

        CommandsEnum command = CommandsParser.getCommand(message);
        //未知命令
        if(command==null){
            client.send(CommandsParser.merge(CommandsEnum.ERROR.getCommand(),ErrorCodeEnum.E4.getTag()));
            return;
        }

        for (GameProcess gameProcess : gameProcessList) {
            if (gameProcess.isGaming(client)) {
                gameProcess.onMessage(client,message);
                return;
            }
        }

        //Command to enter the server
        switch (command) {
            case HELLO:{
                String name = CommandsParser.splitCommand(message)[1];
                client.setClientId(name);
                client.send(CommandsParser.merge(CommandsParser.STR_WELCOME,name) );
                return;
            }case PLAY:{
                String numStr = CommandsParser.splitCommand(message)[1];
                int num = Integer.parseInt(numStr);
                //人数是否合理
                if(num>10||num<1){
                    client.send(CommandsParser.merge(CommandsEnum.ERROR.getCommand(),ErrorCodeEnum.E1.getTag()) );
                    return;
                }
                //加入AI
                if(num==1){
                    new Thread(new AiPlayer()).start();
                }

                GameProcess join = getJoin(num);
                join.addPlayers(client);


                System.out.println("开启多人游戏队列");
                join.onMessage(client,message);

                return;
            } default:
//                return;

        }

        System.out.println("[Server]: client " + client.getClientId() + " messaged: " + message);
        client.send(CommandsParser.merge(CommandsEnum.ERROR.getCommand(),ErrorCodeEnum.E2.getTag()));
//        client.send(message);
    }
    public GameProcess getJoin(int num){
        //有创建过的则加入
        GameProcess gameProcess = null;
        for (GameProcess ech : gameProcessList) {
            if (ech.getState()==0&&ech.getPlayerNum()==num) {
                gameProcess = ech;
                break;
            }
        }
        //无则创建个新游戏线程
        if(gameProcess==null){
            gameProcess = new TGameProcess(num);
            gameProcessList.add(gameProcess);

        }
        return gameProcess;
    }
    @Override
    public void onDisconnect(String clientId, String reason) {
        System.out.println("[Server]: client " + clientId + " disconnect; reason:" + reason);
    }

    @Override
    public void onError(GameClient client, String error) {
        System.out.println("[Server]: error occured with client" + client.getClientId() + ": " + error);
    }
}
