package server.interfaces;

public interface GameProcess {
    /**
     * undergoing
     *
     * @return boolean
     */
    boolean isGaming(GameClient client);


    void onMessage(String message);
}
