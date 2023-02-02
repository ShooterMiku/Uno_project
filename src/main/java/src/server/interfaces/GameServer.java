package src.server.interfaces;

public interface GameServer extends SocketServerEvent {
    /**
     * start
     */
    void start();

    /**
     * end
     */
    void stop();
}
