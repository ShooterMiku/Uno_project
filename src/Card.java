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

    public Card(){
        type = Type.NUMBER;
        color = Color.ALL;
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
}

class NumberdCard extends Card {
    private int number;

    public NumberdCard(){
        super();
        number = 0;
    }

    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }
}