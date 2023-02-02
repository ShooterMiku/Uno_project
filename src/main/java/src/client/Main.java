package src.client;

import src.client.interfaces.SocketClientEvent;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        GameServerConnector connector = new GameServerConnector("localhost", 8500);
        connector.listen(new SocketClientEvent() {
            @Override
            public void onConnect() {
                System.out.println("[Client]: onConnect");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("[Client]: onMessage:" + message);
            }

            @Override
            public void onDisconnect(String reason) {
                System.out.println("[Client]: onDisconnect" + reason);
            }
        });

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if ("exit".equals(line)){
                return;
            }
            connector.send(line);
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (connector) {
                    connector.send("ffffff");
                }
            }
        }, 0, 3 * 1000);
    }
}
