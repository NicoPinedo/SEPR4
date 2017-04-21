package drtn.game;

import com.badlogic.gdx.Gdx;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * @author Duck Related Team Name in Big Massive Letters
 * @since Assessment 2
 * @version Assessment 2
 *
 * An executable version of the game can be found at: https://jm179796.github.io/SEPR/DRTN-Assessment2.jar
 * Our website is: https://jm179796.github.io/SEPR/
 */
public class ResourceAccessTest extends TesterFile{

    @Test
    public void TestsHaveAssets(){
        assertTrue(Gdx.files.internal("image/Roboticon111.png").exists());
    }
}
