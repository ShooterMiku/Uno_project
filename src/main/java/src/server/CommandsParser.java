package src.server;

import src.CommandsEnum;

public class CommandsParser {

    public static final String STR_WELCOME = "WELCOME";
    public static final String SEPARATOR = "&";
    public static final String END = "|";
    public static CommandsEnum getCommand(String message) {
        if (!isValidCommand(message)) {
            return null;
        }
        String s = splitCommand(message)[0];
        return CommandsEnum.valueOf(s);
    }

    /**
     * String nmuber
     *
     * @return int
     */
    public static int getStringCount(String message){
        return splitCommand(message).length;
    }
    public static String[] splitCommand(String message){
        return message.substring(0,message.length()-1).split(SEPARATOR);
    }

    /**
     * effect order
     *
     * @param message 
     * @return boolean
     */
    public static boolean isValidCommand(String message){
        if (message==null||message.isBlank()) {
            return false;
        }
        return message.endsWith(END);
    }

    public static String merge(String ...strings) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if(i >0){
                stringBuffer.append(SEPARATOR);
            }
            stringBuffer.append(strings[i]);
        }
        stringBuffer.append(END);
        return stringBuffer.toString();
    }

}
