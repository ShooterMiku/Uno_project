package src.server;

public enum ErrorCodeEnum {
    E1("E1","you don’t have the card"),
    E2("E2","invalid move"),
    E3("E3","not your turn"),
    E4("E4","you don’t have the card");

    private String tag;
    private String msg;
    ErrorCodeEnum(String tag,String msg){
        this.tag = tag;
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }
}
