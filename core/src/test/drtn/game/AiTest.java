package main.drtn.game;
import main.drtn.game.entity.AiPlayer;
import main.drtn.game.entity.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AiTest extends TesterFile {
    private AiPlayer aiPlayer;
    private Player humanPlayer;

    @Before
    public void aiInit() {
        aiPlayer = new AiPlayer(0);
        humanPlayer = new Player(1);
    }

    @Test
    public void aiShouldIdentifyThem (){
        assertTrue("AI think they are human.", aiPlayer.isAi());
        assertFalse("Human think they are AI.", humanPlayer.isAi());

    }

    @Test
    public void aiShouldDecideChanceOfAcceptOffer() {
        assertEquals("Ai has failed to judge the money.", 0, aiPlayer.calculateLikelihood(0, 100), 0.01);
        assertEquals("Ai should have 2/3 chance of accept.", 0.66, aiPlayer.calculateLikelihood(50, 50), 0.01);
        assertEquals("Ai should definitely accept this offer.", 1, aiPlayer.calculateLikelihood(100, 50), 0.01);
    }
}
