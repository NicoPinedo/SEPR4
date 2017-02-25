import com.badlogic.gdx.Game;
import com.drtn.game.Main;
import com.drtn.game.entity.Player;
import com.drtn.game.entity.Roboticon;
import com.drtn.game.entity.Tile;
import com.drtn.game.enums.ResourceType;
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

public class TileTest extends TesterFile {
    private Game game = new Main();
    private Player TestPlayer = new Player(1);
    private Tile TestTile = new Tile(game, 0, 5, 5, 5, true, new Runnable() {
        @Override
        public void run() {

        }
    });
    private Roboticon TestRoboticon = new Roboticon(0, TestPlayer, TestTile);


    /**
     * Test confirming that the Player's resources are updated with roboticon production modifiers after tile.produce has completed
     */
    @Test
    public void testProduce() {
        int TestValues[] = {TestPlayer.getResource(ResourceType.ENERGY), TestPlayer.getResource(ResourceType.FOOD), TestPlayer.getResource(ResourceType.ORE)};

        TestTile.setOwner(TestPlayer);
        TestTile.produce();

        assertTrue(TestPlayer.getResource(ResourceType.ENERGY) > TestValues[0]);
        assertTrue(TestPlayer.getResource(ResourceType.FOOD) > TestValues[1]);
        assertTrue(TestPlayer.getResource(ResourceType.ORE) > TestValues[2]);

    }

    @Test
    public void testAssignRoboticon(){
        TestTile.assignRoboticon(TestRoboticon);
        assertTrue(TestTile.hasRoboticon());
    }
    @Test
    public void testUnassignRoboticon(){
        TestTile.assignRoboticon(TestRoboticon);
        TestTile.unassignRoboticon(TestRoboticon);
        assertFalse(TestTile.hasRoboticon());
    }

    @Test
    public void testisOwned(){
        assertFalse(TestTile.isOwned());
        TestTile.setOwner(TestPlayer);
        assertTrue(TestTile.isOwned());

    }
    @Test
    public void testhasRoboticon(){
        TestTile.unassignRoboticon(TestRoboticon);
        assertFalse(TestTile.hasRoboticon());
        TestTile.assignRoboticon(TestRoboticon);
        assertTrue(TestTile.hasRoboticon());

    }

}
