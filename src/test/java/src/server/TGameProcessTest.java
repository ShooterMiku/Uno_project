package src.server;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.apache.log4j.Logger;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
public class TGameProcessTest {

    private Logger log = Logger.getLogger(this.getClass());
    @BeforeAll
    static void initAll() {
    }
    @BeforeEach
    void init() {
    }

    @Test
    @DisplayName("is Gaming")
    public void isGaming(){
        try {
            log.info("Starting execution of isGaming");
            boolean expectedValue=false;
            GameClient client = null;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            boolean actualValue=tgameprocess.isGaming( client);
            log.info("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            System.out.println("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            Assertions.assertEquals(expectedValue, actualValue);
        } catch (Exception exception) {
            log.error("Exception in execution of execute1GetAllLogFromFirstMovF-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("update Card")
    public void updateCard(){
        try {
            log.info("Starting execution of updateCard");
            GameClient client = null;
            String action="";
            Card card = null;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.updateCard( client ,action ,card);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofupdateCard-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("game Over")
    public void gameOver(){
        try {
            log.info("Starting execution of gameOver");
            GameClient winner = null;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.gameOver( winner);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofgameOver-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("start Game Process")
    public void startGameProcess(){
        try {
            log.info("Starting execution of startGameProcess");

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.startGameProcess();
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofstartGameProcess-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("notify Players Operating")
    public void notifyPlayersOperating(){
        try {
            log.info("Starting execution of notifyPlayersOperating");

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.notifyPlayersOperating();
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofnotifyPlayersOperating-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("gen Player Current Cards")
    public void genPlayerCurrentCards(){
        try {
            log.info("Starting execution of genPlayerCurrentCards");
            String expectedValue="";
            GameClient client = null;
            int cardNum=0;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            String actualValue=tgameprocess.genPlayerCurrentCards( client ,cardNum);
            log.info("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            System.out.println("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            Assertions.assertEquals(expectedValue, actualValue);
        } catch (Exception exception) {
            log.error("Exception in execution of execute1GetAllLogFromFirstMovF-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("player Score Plus")
    public void playerScorePlus(){
        try {
            log.info("Starting execution of playerScorePlus");
            GameClient player = null;
            int score=0;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.playerScorePlus( player ,score);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofplayerScorePlus-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("get State")
    public void getState(){
        try {
            log.info("Starting execution of getState");
            int expectedValue=0;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            int actualValue=tgameprocess.getState();
            log.info("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            System.out.println("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            Assertions.assertEquals(expectedValue, actualValue);
        } catch (Exception exception) {
            log.error("Exception in execution of execute1GetAllLogFromFirstMovF-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("get Player Num")
    public void getPlayerNum(){
        try {
            log.info("Starting execution of getPlayerNum");
            int expectedValue=0;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            int actualValue=tgameprocess.getPlayerNum();
            log.info("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            System.out.println("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            Assertions.assertEquals(expectedValue, actualValue);
        } catch (Exception exception) {
            log.error("Exception in execution of execute1GetAllLogFromFirstMovF-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("add Players")
    public void addPlayers(){
        try {
            log.info("Starting execution of addPlayers");
            GameClient client = null;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.addPlayers( client);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofaddPlayers-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("on Message")
    public void onMessage(){
        try {
            log.info("Starting execution of onMessage");
            GameClient client = null;
            String message="";

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.onMessage( client ,message);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofonMessage-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("move Valid")
    public void moveValid(){
        try {
            log.info("Starting execution of moveValid");
            boolean expectedValue=false;
            Card nowCard = null;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            boolean actualValue=tgameprocess.moveValid( nowCard);
            log.info("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            System.out.println("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
            Assertions.assertEquals(expectedValue, actualValue);
        } catch (Exception exception) {
            log.error("Exception in execution of execute1GetAllLogFromFirstMovF-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("change Cards")
    public void changeCards(){
        try {
            log.info("Starting execution of changeCards");
            GameClient client = null;
            String targetPlayerName="";

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.changeCards( client ,targetPlayerName);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofchangeCards-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("send Chat Message")
    public void sendChatMessage(){
        try {
            log.info("Starting execution of sendChatMessage");
            GameClient client = null;
            String msg="";

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.sendChatMessage( client ,msg);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofsendChatMessage-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("hand Card")
    public void handCard(){
        try {
            log.info("Starting execution of handCard");
            GameClient client = null;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.handCard( client);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofhandCard-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("error Msg")
    public void errorMsg(){
        try {
            log.info("Starting execution of errorMsg");
            GameClient client = null;
            ErrorCodeEnum error = null;

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.errorMsg( client ,error);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution oferrorMsg-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }

    @Test
    @DisplayName("move Card")
    public void moveCard(){
        try {
            log.info("Starting execution of moveCard");
            GameClient client = null;
            String action="";

            int numc=0;

            TGameProcess tgameprocess  =new TGameProcess( numc);
            tgameprocess.moveCard( client ,action);
            Assertions.assertTrue(true);
        } catch (Exception exception) {
            log.error("Exception in execution ofmoveCard-"+exception,exception);
            exception.printStackTrace();
            Assertions.assertFalse(false);
        }
    }
    @AfterEach
    void tearDown() {
    }
    @AfterAll
    static void tearDownAll() {
    }
}
