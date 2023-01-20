package server;
//import CommandsEnum;

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
     * 得到消息中字符串数量
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
     * 是有效命令
     *
     * @param message 消息
     * @return boolean
     */
    public static boolean isValidCommand(String message){
        if (message==null||message.isBlank()) {
            return false;
        }
        return message.endsWith(END);
    }

    public static String merge(String[] strings) {
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

