package server;


import CommandsEnum;
import server.interfaces.GameClient;
import server.interfaces.GameProcess;

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
        for (GameProcess gameProcess : gameProcessList) {
            if (gameProcess.isGaming(client)) {
                gameProcess.onMessage(message);
                return;
            }
        }
        CommandsEnum command = CommandsParser.getCommand(message);
        //Command to enter the server
        switch (command) {
            case HELLO:{
                String name = CommandsParser.splitCommand(message)[1];
                client.setClientId(name);
                client.send(CommandsParser.merge(new String[]{CommandsParser.STR_WELCOME,name}) );
                return;
            }case PLAY:{
                String name = CommandsParser.splitCommand(message)[1];
                System.out.println("开启多人游戏队列");
                client.setClientId(name);
                client.send(CommandsParser.merge(new String[]{CommandsParser.STR_WELCOME,name}) );
                return;
            } default:
//                return;

        }

        System.out.println("[Server]: client " + client.getClientId() + " messaged: " + message);
//        client.send(message);
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
