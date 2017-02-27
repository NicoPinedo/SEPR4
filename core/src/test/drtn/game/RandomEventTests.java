package drtn.game;

import com.badlogic.gdx.Game;
import drtn.game.GameEngine;
import drtn.game.Main;
import drtn.game.screens.GameScreen;
import org.junit.Before;

/**
 * @author Team Fractal
 *
 * New for Assessment 3
 */

public class RandomEventTests extends TesterFile {

    private Game game;
    private GameScreen gameScreen;
    private GameEngine gameEngine;


    @Before
    public void setup() {
        game = new Main();
        gameEngine = new GameEngine(game, gameScreen);
        gameEngine.initialisePlayers(1, 1);
    }



}
