package main.drtn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import main.drtn.game.util.GameTimer;
import main.drtn.game.util.TTFont;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Duck Related Team Name in Big Massive Letters
 * @since Assessment 2
 * @version Assessment 2
 *
 * An executable version of the game can be found at: https://jm179796.github.io/SEPR/DRTN-Assessment2.jar
 * Our website is: https://jm179796.github.io/SEPR/
 */
public class GameTimerTest extends TesterFile {
    private boolean complete = false;
    private GameTimer TestTimer = new GameTimer(3, new TTFont(Gdx.files.internal("font/testfontbignoodle.ttf"), 120), Color.WHITE, new Runnable() {
        @Override
        public void run() {
            TimerComplete();
        }
    });


    private void TimerComplete(){
        this.complete = true;
    }

    /**
     * Tests that the GameTimer class executes it's runnable on complettion
     * @throws Exception incase sleep is interrupted
     */
    @Test
    public void testTimerCompletion() throws Exception{
        assertFalse(this.complete);
        TestTimer.start();
        Thread.sleep(3100);
        assertTrue(this.complete);
    }
}

