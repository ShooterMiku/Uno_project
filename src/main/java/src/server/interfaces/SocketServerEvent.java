package src.server.interfaces;

public interface SocketServerEvent {    /**
 * connection
 *
 * @param client
 */
void onConnect(GameClient client);

    /**
     * message recall
     *
     * @param client
     * @param message
     */
    void onMessage(GameClient client, String message);

    /**
     * offline
     *
     * @param clientId client id
     * @param reason   reason
     */
    void onDisconnect(String clientId, String reason);

    /**
     * 发生错误
     *
     * @param client ID
     * @param error  Error
     */
    void onError(GameClient client, String error);
}
