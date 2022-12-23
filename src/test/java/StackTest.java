import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.Generated;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    @DisplayName("Generating New Stack")
    void isStackEmpty() {
        Stack stack = new Stack();
        assertFalse(stack.isStackEmpty());
    }

    @Test
    @DisplayName("Card turn out")
    void getCard() {
        Stack stack = new Stack();
        Card card = stack.getCard();
        assertEquals(107,stack.stack.size());
    }
}