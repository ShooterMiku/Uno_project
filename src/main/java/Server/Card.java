package Server;

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
}
