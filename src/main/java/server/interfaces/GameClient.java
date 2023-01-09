package server.interfaces;

public interface GameClient {
    /**
     * Listener
     *
     * @param listener
     */
    void listen(SocketServerEvent listener);

    /**
     * send massage
     *
     * @param message
     */
    void send(String message);

    /**
     * disconnect
     *
     * @param reason
     */
    void close(String reason);

    /**
     * get client ID
     *
     * @return {@link String}
     */
    String getClientId();
    void setClientId(String clientId);

    boolean isSignIn();
}
