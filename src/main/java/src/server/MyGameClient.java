package src.server;


import src.server.interfaces.GameClient;
import src.server.interfaces.SocketServerEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class MyGameClient implements GameClient {
    private boolean state;
    private Socket socket;
    private String clientId;
    private Thread currentThread;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private SocketServerEvent socketServerEvent;

    public MyGameClient(String clientId, Socket socket) {
        this.socket = socket;
        this.clientId = clientId;
        try {
            this.socket.setKeepAlive(true);
        } catch(SocketException ex) { }
    }

    @Override
    public void listen(SocketServerEvent socketServerEvent) {
        this.socketServerEvent = socketServerEvent;
        this.socketServerEvent.onConnect(this);

        try {
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
            this.inputStream = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
        } catch(Exception ex) {
            socketServerEvent.onError(this, "Could open data streams");
            close("Error occured");
        }

        currentThread = new Thread(this);
        currentThread.start();
    }

    @Override
    public void close(String reason) {
        try {
            this.currentThread.interrupt();
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
            socketServerEvent.onDisconnect(clientId, reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String message) {
        outputStream.println(message);
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = inputStream.readLine()) != null) {
                socketServerEvent.onMessage(this, line);
            }
        } catch(Exception ex) {
            send("ERROR&" + ex.getMessage()+ "|");
            socketServerEvent.onError(this, "Error while reading data from the socket");
        } finally {
            close("Socket disconnected on purpose");
        }
    }

    public String getClientId() {
        return clientId;
    }
    @Override
    public void setClientId(String clientId){
        this.clientId = clientId;
        this.state = true;
    }
    public boolean isSignIn(){
        return state;
    }
}
