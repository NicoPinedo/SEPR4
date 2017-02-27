import com.badlogic.gdx.Game;
import com.drtn.game.GameEngine;
import com.drtn.game.Main;
import com.drtn.game.screens.GameScreen;
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
