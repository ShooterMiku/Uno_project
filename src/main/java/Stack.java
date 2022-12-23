import java.util.ArrayList;
import java.util.Collections;

public class Stack {
    public ArrayList<Card> stack;

    public Stack() {
        stack = new ArrayList<>();

        stack.add(new Card(Type.NUMBER, Color.BLUE,0));
        stack.add(new Card(Type.NUMBER, Color.BLUE,1));
        stack.add(new Card(Type.NUMBER, Color.BLUE,1));
        stack.add(new Card(Type.NUMBER, Color.BLUE,2));
        stack.add(new Card(Type.NUMBER, Color.BLUE,2));
        stack.add(new Card(Type.NUMBER, Color.BLUE,3));
        stack.add(new Card(Type.NUMBER, Color.BLUE,3));
        stack.add(new Card(Type.NUMBER, Color.BLUE,4));
        stack.add(new Card(Type.NUMBER, Color.BLUE,4));
        stack.add(new Card(Type.NUMBER, Color.BLUE,5));
        stack.add(new Card(Type.NUMBER, Color.BLUE,5));
        stack.add(new Card(Type.NUMBER, Color.BLUE,6));
        stack.add(new Card(Type.NUMBER, Color.BLUE,6));
        stack.add(new Card(Type.NUMBER, Color.BLUE,7));
        stack.add(new Card(Type.NUMBER, Color.BLUE,7));
        stack.add(new Card(Type.NUMBER, Color.BLUE,8));
        stack.add(new Card(Type.NUMBER, Color.BLUE,8));
        stack.add(new Card(Type.NUMBER, Color.BLUE,9));
        stack.add(new Card(Type.NUMBER, Color.BLUE,9));
        stack.add(new Card(Type.BLOCK, Color.BLUE,-1));
        stack.add(new Card(Type.BLOCK, Color.BLUE,-1));
        stack.add(new Card(Type.PLUS2, Color.BLUE,-1));
        stack.add(new Card(Type.PLUS2, Color.BLUE,-1));
        stack.add(new Card(Type.TURN, Color.BLUE,-1));
        stack.add(new Card(Type.TURN, Color.BLUE,-1));

        stack.add(new Card(Type.NUMBER, Color.RED,0));
        stack.add(new Card(Type.NUMBER, Color.RED,1));
        stack.add(new Card(Type.NUMBER, Color.RED,1));
        stack.add(new Card(Type.NUMBER, Color.RED,2));
        stack.add(new Card(Type.NUMBER, Color.RED,2));
        stack.add(new Card(Type.NUMBER, Color.RED,3));
        stack.add(new Card(Type.NUMBER, Color.RED,3));
        stack.add(new Card(Type.NUMBER, Color.RED,4));
        stack.add(new Card(Type.NUMBER, Color.RED,4));
        stack.add(new Card(Type.NUMBER, Color.RED,5));
        stack.add(new Card(Type.NUMBER, Color.RED,5));
        stack.add(new Card(Type.NUMBER, Color.RED,6));
        stack.add(new Card(Type.NUMBER, Color.RED,6));
        stack.add(new Card(Type.NUMBER, Color.RED,7));
        stack.add(new Card(Type.NUMBER, Color.RED,7));
        stack.add(new Card(Type.NUMBER, Color.RED,8));
        stack.add(new Card(Type.NUMBER, Color.RED,8));
        stack.add(new Card(Type.NUMBER, Color.RED,9));
        stack.add(new Card(Type.NUMBER, Color.RED,9));
        stack.add(new Card(Type.BLOCK, Color.RED,-1));
        stack.add(new Card(Type.BLOCK, Color.RED,-1));
        stack.add(new Card(Type.PLUS2, Color.RED,-1));
        stack.add(new Card(Type.PLUS2, Color.RED,-1));
        stack.add(new Card(Type.TURN, Color.RED,-1));
        stack.add(new Card(Type.TURN, Color.RED,-1));

        stack.add(new Card(Type.NUMBER, Color.GREEN,0));
        stack.add(new Card(Type.NUMBER, Color.GREEN,1));
        stack.add(new Card(Type.NUMBER, Color.GREEN,1));
        stack.add(new Card(Type.NUMBER, Color.GREEN,2));
        stack.add(new Card(Type.NUMBER, Color.GREEN,2));
        stack.add(new Card(Type.NUMBER, Color.GREEN,3));
        stack.add(new Card(Type.NUMBER, Color.GREEN,3));
        stack.add(new Card(Type.NUMBER, Color.GREEN,4));
        stack.add(new Card(Type.NUMBER, Color.GREEN,4));
        stack.add(new Card(Type.NUMBER, Color.GREEN,5));
        stack.add(new Card(Type.NUMBER, Color.GREEN,5));
        stack.add(new Card(Type.NUMBER, Color.GREEN,6));
        stack.add(new Card(Type.NUMBER, Color.GREEN,6));
        stack.add(new Card(Type.NUMBER, Color.GREEN,7));
        stack.add(new Card(Type.NUMBER, Color.GREEN,7));
        stack.add(new Card(Type.NUMBER, Color.GREEN,8));
        stack.add(new Card(Type.NUMBER, Color.GREEN,8));
        stack.add(new Card(Type.NUMBER, Color.GREEN,9));
        stack.add(new Card(Type.NUMBER, Color.GREEN,9));
        stack.add(new Card(Type.BLOCK, Color.GREEN,-1));
        stack.add(new Card(Type.BLOCK, Color.GREEN,-1));
        stack.add(new Card(Type.PLUS2, Color.GREEN,-1));
        stack.add(new Card(Type.PLUS2, Color.GREEN,-1));
        stack.add(new Card(Type.TURN, Color.GREEN,-1));
        stack.add(new Card(Type.TURN, Color.GREEN,-1));

        stack.add(new Card(Type.NUMBER, Color.YELLOW,0));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,1));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,1));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,2));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,2));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,3));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,3));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,4));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,4));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,5));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,5));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,6));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,6));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,7));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,7));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,8));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,8));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,9));
        stack.add(new Card(Type.NUMBER, Color.YELLOW,9));
        stack.add(new Card(Type.BLOCK, Color.YELLOW,-1));
        stack.add(new Card(Type.BLOCK, Color.YELLOW,-1));
        stack.add(new Card(Type.PLUS2, Color.YELLOW,-1));
        stack.add(new Card(Type.PLUS2, Color.YELLOW,-1));
        stack.add(new Card(Type.TURN, Color.YELLOW,-1));
        stack.add(new Card(Type.TURN, Color.YELLOW,-1));

        stack.add(new Card(Type.CHANGER, Color.ALL,-1));
        stack.add(new Card(Type.CHANGER, Color.ALL,-1));
        stack.add(new Card(Type.CHANGER, Color.ALL,-1));
        stack.add(new Card(Type.CHANGER, Color.ALL,-1));

        stack.add(new Card(Type.PLUS4, Color.ALL,-1));
        stack.add(new Card(Type.PLUS4, Color.ALL,-1));
        stack.add(new Card(Type.PLUS4, Color.ALL,-1));
        stack.add(new Card(Type.PLUS4, Color.ALL,-1));

        Collections.shuffle(stack);
    }

    public boolean isStackEmpty(){
        return this.stack.size() == 0;
    }

    public Card getCard(){
        Card result = this.stack.get(0);
        this.stack.remove(0);
        return result;
    }

}
