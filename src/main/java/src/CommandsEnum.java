package src;

public enum CommandsEnum {
    HELLO("HELLO"),
    WELCOME("WELCOME"),
    PLAY("PLAY"),
    QUEUE("QUEUE"),
    MOVE("MOVE"),
    HAND("HAND"),
    CHAT("CHAT"),
    ERROR("ERROR"),
    TAKE("TAKE"),
    CHANGE("CHANGE");

    private String command;

    CommandsEnum(String command){
        this.command = command;
    }
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
