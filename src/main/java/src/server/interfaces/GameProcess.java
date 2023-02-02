package src.server.interfaces;

public interface GameProcess {
    /**
     * Undergoing
     *
     * @return boolean
     */
    boolean isGaming(GameClient client);

    void addPlayers(GameClient client);

    void onMessage(GameClient client, String message);

    /**
     * status
     *
     * @return boolean
     */
    int getState();

    /**
     * player number
     *
     * @return int
     */
    int getPlayerNum();

}
