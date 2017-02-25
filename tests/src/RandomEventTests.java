import com.badlogic.gdx.Game;
import com.drtn.game.GameEngine;
import com.drtn.game.Main;
import com.drtn.game.effects.Earthquake;
import com.drtn.game.effects.Malfunction;
import com.drtn.game.entity.Roboticon;
import com.drtn.game.entity.Tile;
import com.drtn.game.enums.ResourceType;
import com.drtn.game.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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

    @Test
    public void testEarthquakeCutsProduction() throws Exception {
        Earthquake testEarthquake = new Earthquake(gameEngine, gameScreen);
        ArrayList<Tile> tilesDamagedBeforeQuake = testEarthquake.getTilesDamaged();
        int tileDamageValue = testEarthquake.getTileDamageValue();
        try {
            testEarthquake.eventHappen(true);
        } catch (Exception ignored) {

        }
        ArrayList<Tile> tileDamagedAfterQuake = testEarthquake.getTilesDamaged();


        for (int tile = 0; tile < tilesDamagedBeforeQuake.size(); tile++) {
            assertEquals(tilesDamagedBeforeQuake.get(tile).getResource(ResourceType.ORE) / tileDamageValue, tileDamagedAfterQuake.get(tile).getResource(ResourceType.ORE));
            assertEquals(tilesDamagedBeforeQuake.get(tile).getResource(ResourceType.ENERGY) / tileDamageValue, tileDamagedAfterQuake.get(tile).getResource(ResourceType.ENERGY));
            assertEquals(tilesDamagedBeforeQuake.get(tile).getResource(ResourceType.FOOD) / tileDamageValue, tileDamagedAfterQuake.get(tile).getResource(ResourceType.FOOD));
        }
    }

    @Test
    public void testEarthquakeResetsValuesAfterDuration() throws Exception {
        Earthquake testEarthquake = new Earthquake(gameEngine, gameScreen);
        ArrayList<Tile> tilesDamagedBeforeQuake = testEarthquake.getTilesDamaged();
        try {
            testEarthquake.eventHappen(true);
        } catch (Exception ignored) {
        }
        try {
            testEarthquake.eventHappen(false);
        } catch (Exception ignored) {
        }
        ArrayList<Tile> tilesDamagedAferRepair = testEarthquake.getTilesDamaged();

        for (int tile = 0; tile < tilesDamagedBeforeQuake.size(); tile++) {
            assertEquals(tilesDamagedBeforeQuake.get(tile).getResource(ResourceType.ORE), tilesDamagedAferRepair.get(tile).getResource(ResourceType.ORE));
            assertEquals(tilesDamagedBeforeQuake.get(tile).getResource(ResourceType.ENERGY), tilesDamagedAferRepair.get(tile).getResource(ResourceType.ENERGY));
            assertEquals(tilesDamagedBeforeQuake.get(tile).getResource(ResourceType.FOOD), tilesDamagedAferRepair.get(tile).getResource(ResourceType.FOOD));
        }
    }

    @Test
    public void testRoboticonMalfunctionsSuccessfully() throws Exception {
        Tile testTile = new Tile(game, 0,0,0,0, true, new Runnable() {
            @Override
            public void run() {
            }
        });

        gameEngine.players()[0].assignTile(testTile);
        Roboticon testRoboticon = new Roboticon(1, gameEngine.players()[0], gameEngine.players()[0].getTileList().get(0));
        Malfunction testMalfunction = new Malfunction(gameEngine, gameScreen, 0);
        try {
            testMalfunction.eventHappen(true);
        } catch (Exception ignored) {
        }

        int[] roboticonLevelsAfterMalfunction = testMalfunction.getRoboticonToMalfunction().getLevel();
        int[] expectedRoboticonLevels = {0, 0, 0};
        assertArrayEquals(expectedRoboticonLevels, roboticonLevelsAfterMalfunction);
    }

    @Test
    public void testRoboticonRevertsAfterMalfunction() {
        Tile testTile = new Tile(game, 0,0,0,0, true, new Runnable() {
            @Override
            public void run() {
            }
        });

        gameEngine.players()[0].assignTile(testTile);
        Roboticon testRoboticon = new Roboticon(1, gameEngine.players()[0], gameEngine.players()[0].getTileList().get(0));
        Malfunction testMalfunction = new Malfunction(gameEngine, gameScreen, 0);
        int[] startingRoboticonLevels = testMalfunction.getStartingRoboticonLevels();
        try {
            testMalfunction.eventHappen(true);
        } catch (Exception ignored) {
        }
        try {
            testMalfunction.eventHappen(false);
        } catch (Exception ignored) {
        }

        int[] roboticonLevelsAfterFix = testMalfunction.getRoboticonToMalfunction().getLevel();
        assertArrayEquals(startingRoboticonLevels, roboticonLevelsAfterFix);

    }

}
