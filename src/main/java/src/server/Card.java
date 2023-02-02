package src.server;

enum Type {
    NUMBER,
    CHANGER,
    PLUS2,
    PLUS4,
    TURN,
    BLOCK
}

enum Color {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    ALL
}


public class Card {
    private Type type;
    private Color color;
    private int number;

    public Card(Type type, Color color, int number){
        this.number = number;
        this.type = type;
        this.color = color;
    }

    public Type getType(){
        return this.type;
    }

    public void setType(Type type){
        this.type = type;
    }

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }
    public String getCarStr(){
        StringBuffer str = new StringBuffer();
        switch (color) {
            case RED:
                str.append("R");
                break;
            case BLUE:
                str.append("B");
                break;
            case YELLOW:
                str.append("Y");
                break;
            default:
                str.append("?");
                break;
        }
        switch (type){
            case NUMBER:
                str.append(number);
                break;
            case TURN:
                str.append("turn");
                break;
            case BLOCK:
                str.append("block");
                break;
            case PLUS2:
                str.append("plus2");
                break;
            case PLUS4:
                str.append("plus4");
                break;
            case CHANGER:
                str.append("changer");
                break;
        }

        return str.toString();
    }
}
