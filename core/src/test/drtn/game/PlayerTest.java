/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game;

import com.badlogic.gdx.Game;
import drtn.game.entity.College;
import drtn.game.entity.Player;
import drtn.game.entity.Roboticon;
import drtn.game.entity.Tile;
import drtn.game.enums.ResourceType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * @author Duck Related Team Name in Big Massive Letters
 * @since Assessment 2
 * @version Assessment 2
 *
 * An executable version of the game can be found at: https://jm179796.github.io/SEPR/DRTN-Assessment2.jar
 * Our website is: https://jm179796.github.io/SEPR/
 */
public class PlayerTest extends TesterFile {

    private Game game = new Main();
    private Player TestPlayer = new Player(1);
    private Tile TestTile = new Tile(game, 0,0,0,0, null, new Runnable() {
        @Override
        public void run() {

        }
    });
    private Roboticon TestRoboticon = new Roboticon(0, TestPlayer, TestTile);
    private College TestCollege = new College (1, "I am a test.");

    @Test
    public void testAssignCollege(){
        TestPlayer.assignCollege(TestCollege);
        assertEquals(TestPlayer.getCollege(),TestCollege);
    }

    @Test
    public void testAssignTile() {
        List<Tile> TileList = new ArrayList<Tile>();
        assertEquals(TestPlayer.getTileList(),TileList);
        TestPlayer.assignTile(TestTile);
        assertNotEquals(TestPlayer.getTileList(), TileList);
    }

    @Test
    public void testVaryResource() {
        int ore = TestPlayer.getResource(ResourceType.ORE);
        int food = TestPlayer.getResource(ResourceType.FOOD);
        int energy = TestPlayer.getResource(ResourceType.ENERGY);
        int money = TestPlayer.getResource(ResourceType.MONEY);
        for (int i = 1; i < 200; i++){
            ore += i;
            food += i;
            energy += i;
            money += i;
            TestPlayer.varyResource(ResourceType.ORE, i);
            TestPlayer.varyResource(ResourceType.FOOD, i);
            TestPlayer.varyResource(ResourceType.ENERGY, i);
            TestPlayer.varyResource(ResourceType.MONEY, i);
            assertEquals(ore, TestPlayer.getResource(ResourceType.ORE));
            assertEquals(food, TestPlayer.getResource(ResourceType.FOOD));
            assertEquals(energy, TestPlayer.getResource(ResourceType.ENERGY));
            assertEquals(money, TestPlayer.getResource(ResourceType.MONEY));
        }


    }

    @Test
    public void testcalculateScore(){
        int ore = TestPlayer.getResource(ResourceType.ORE);
        int food = TestPlayer.getResource(ResourceType.FOOD);
        int energy = TestPlayer.getResource(ResourceType.ENERGY);
        int score = ore + food + energy;
        assertEquals(score, TestPlayer.calculateScore());
        //assertEquals((TestPlayer.getResource(ResourceType.ORE)() + TestPlayer.getResource(ResourceType.FOOD + TestPlayer.getResource(ResourceType.ENERGY)), TestPlayer.calculateScore());
    }

    @Test
    public void testIncreaseRoboticonInventory(){
        int count = TestPlayer.getInventoryRoboticons();
        count += 1;
        TestPlayer.increaseRoboticonInventory();
        assertEquals(count,TestPlayer.getInventoryRoboticons());
    }

    @Test
    public void testDecreaseRoboticonInventory(){
        int count = TestPlayer.getInventoryRoboticons();
        count -= 1;
        TestPlayer.decreaseRoboticonInventory();
        assertEquals(count,TestPlayer.getInventoryRoboticons());
    }




















}




