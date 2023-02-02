package src.server.interfaces;

public interface GameClient extends Runnable {
    /**
     * listener
     *
     * @param listener
     */
    void listen(SocketServerEvent listener);

    /**
     * send
     *
     * @param message
     */
    void send(String message);

    /**
     * discon
     *
     * @param reason
     */
    void close(String reason);

    /**
     * get ID
     *
     * @return {@link String}
     */
    String getClientId();
    void setClientId(String clientId);

    boolean isSignIn();
}
