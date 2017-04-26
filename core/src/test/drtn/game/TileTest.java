/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import drtn.game.entity.Player;
import drtn.game.entity.Roboticon;
import drtn.game.entity.Tile;
import drtn.game.enums.ResourceType;
import drtn.game.exceptions.InvalidResourceTypeException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Duck Related Team Name in Big Massive Letters
 * @since Assessment 2
 * @version Assessment 4
 *
 * This test class gets Tile to 65% for methods and 66% for lines. This is acceptable coverage as the rest of the methods implement UI.
 */

public class TileTest extends TesterFile {
    private Game game = new Main();
    private Player TestPlayer = new Player(1);
    private Tile TestTile = new Tile(game, 0, 5, 5, 5, null, new Runnable() {
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
        assertEquals(TestRoboticon, TestTile.getRoboticonStored());
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

    @Test
    public void testSetColor() {
        TestTile.setTileBorderColor(Color.BLUE);
        assertEquals(Color.BLUE, TestTile.tileBorderColor());
    }

    @Test
    public void testgetters() {
        TestTile.setOwner(TestPlayer);
        assertEquals(TestTile.getID(), 0);
        assertEquals(TestTile.getOwner(), TestPlayer);
    }

    @Test
    public void testSetandGetResource() throws InvalidResourceTypeException {
        assertEquals(5, TestTile.getResource(ResourceType.ORE));
        assertEquals(5, TestTile.getResource(ResourceType.FOOD));
        assertEquals(5, TestTile.getResource(ResourceType.ENERGY));
        try {
            assertEquals(5, TestTile.getResource(ResourceType.MONEY));
            fail("Expected to throw exception");
        } catch (InvalidResourceTypeException ignored) {
        }
        TestTile.setResource(ResourceType.ORE, 1);
        TestTile.setResource(ResourceType.FOOD, 1);
        TestTile.setResource(ResourceType.ENERGY, 1);
        assertEquals(1, TestTile.getResource(ResourceType.ORE));
        assertEquals(1, TestTile.getResource(ResourceType.FOOD));
        assertEquals(1, TestTile.getResource(ResourceType.ENERGY));
    }

}
