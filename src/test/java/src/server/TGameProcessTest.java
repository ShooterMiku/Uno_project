package src.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.mockito.Mockito.*;

class TGameProcessTest {
    @Mock
    HashMap<String, MyGameClient> clients;
    @Mock
    Stack stack;
    @Mock
    HashMap<String, Collection<Card>> playerStacks;
    @Mock
    ArrayList<Card> placedCards;
    @Mock
    ArrayList<String> rotaryTable;
    @Mock
    HashMap<String, Integer> playerScore;
    @InjectMocks
    TGameProcess tGameProcess;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIsGaming() {
        boolean result = tGameProcess.isGaming(new MyGameClient("clientId", null));
        Assertions.assertEquals(true, result);
    }

    @Test
    void testAddPlayers() {
        tGameProcess.addPlayers(new MyGameClient("clientId", null));
    }

    @Test
    void testOnMessage() {
        when(stack.getCard()).thenReturn(new Card(Type.NUMBER, Color.RED, 0));

        tGameProcess.onMessage(new MyGameClient("clientId", null), "message");
    }

    @Test
    void testMoveValid() {
        boolean result = tGameProcess.moveValid(new Card(Type.NUMBER, Color.RED, 0));
        Assertions.assertEquals(true, result);
    }

    @Test
    void testChangeCards() {
        tGameProcess.changeCards(new MyGameClient("clientId", null), "targetPlayerName");
    }

    @Test
    void testSendChatMessage() {
        tGameProcess.sendChatMessage(new MyGameClient("clientId", null), "msg");
    }

    @Test
    void testHandCard() {
        tGameProcess.handCard(new MyGameClient("clientId", null));
    }

    @Test
    void testErrorMsg() {
        tGameProcess.errorMsg(new MyGameClient("clientId", null), ErrorCodeEnum.E1);
    }

    @Test
    void testMoveCard() {
        tGameProcess.moveCard(new MyGameClient("clientId", null), "action");
    }

    @Test
    void testUpdateCard() {
        tGameProcess.updateCard(new MyGameClient("clientId", null), "action", new Card(Type.NUMBER, Color.RED, 0));
    }

    @Test
    void testGameOver() {
        tGameProcess.gameOver(new MyGameClient("clientId", null));
    }

    @Test
    void testStartGameProcess() {
        when(stack.getCard()).thenReturn(new Card(Type.NUMBER, Color.RED, 0));

        tGameProcess.startGameProcess();
    }

    @Test
    void testNotifyPlayersOperating() {
        tGameProcess.notifyPlayersOperating();
    }

    @Test
    void testGenPlayerCurrentCards() {
        when(stack.getCard()).thenReturn(new Card(Type.NUMBER, Color.RED, 0));

        String result = tGameProcess.genPlayerCurrentCards(new MyGameClient("clientId", null), 0);
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testPlayerScorePlus() {
        tGameProcess.playerScorePlus(new MyGameClient("clientId", null), 0);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme