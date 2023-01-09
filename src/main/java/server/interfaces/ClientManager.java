package server.interfaces;

import java.net.Socket;

public interface ClientManager {
    /**
     * dealing the client
     *
     * @param socket connector
     * @return {@link String}
     */
    String handleClient(Socket socket);

    /**
     * close all connection
     */
    void closeAllConnections();

    /**
     * client ID show
     *
     * @param id id
     * @return {@link GameClient}
     */
    GameClient getClientById(String id);
}