import server.Card;
import server.Stack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    @DisplayName("Generating New Server.Stack")
    void isStackEmpty() {
        Stack stack = new Stack();
        assertFalse(stack.isStackEmpty());
    }

    @Test
    @DisplayName("Server.Card turn out")
    void getCard() {
        Stack stack = new Stack();
        Card card = stack.getCard();
        assertEquals(107,stack.stack.size());
    }
}