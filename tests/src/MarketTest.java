import com.badlogic.gdx.Game;
import com.drtn.game.GameEngine;
import com.drtn.game.entity.Market;
import com.drtn.game.entity.Player;
import com.drtn.game.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Duck Related Team Name in Big Massive Letters
 * @since Assessment 2
 * @version Assessment 2
 *
 * An executable version of the game can be found at: https://jm179796.github.io/SEPR/DRTN-Assessment2.jar
 * Our website is: https://jm179796.github.io/SEPR/
 */
public class MarketTest extends TesterFile{
    private Player TestPlayer = new Player(0);
    private Game testGame;
    private GameScreen testScreen;
    private GameEngine testGameEngine;
    private Market testMarket;

    @Before
    public void setup() {
        testGameEngine = new GameEngine(testGame,testScreen);
        testGameEngine.initialisePlayers(0, 2);
        testMarket = new Market(testGame,testGameEngine);
    }

    /**
     * Tests Valid buy conditions for all resources.
     * <p>
     *     Market resources are set to 10, and the prices are set to be valid for this test.
     *     Initial values of OreCount, OreSellPrice, OreBuyPrice are set to 10 and Money is set to 100.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 100.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 100.
     * </p>
     * @throws Exception Thrown when an invalid transaction is attempted.
     */
    @Test
    public void testBuy() throws Exception{

        //Energy
        TestPlayer.setEnergyCount(10);
        TestPlayer.setMoney(100);
        testMarket.setEnergySellPrice(10);
        testMarket.setEnergyBuyPrice(10);
        testMarket.setEnergyStock(10);

        try {
            testMarket.buy("energy", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        int TestEnergyCount = 20;
        assertEquals(TestEnergyCount, TestPlayer.getEnergyCount());
        int TestMoney = 0;
        assertEquals(TestMoney, TestPlayer.getMoney());
        int TestSellPrice = 200;
        assertEquals(TestSellPrice, testMarket.getEnergySellPrice());
        int TestBuyPrice = 0;
        assertEquals(TestBuyPrice, testMarket.getEnergyBuyPrice());
        int TestFoodStock = 0;
        assertEquals(TestFoodStock, testMarket.getEnergyStock());

        //Ore
        TestPlayer.setOreCount(10);
        TestPlayer.setMoney(100);
        testMarket.setOreSellPrice(10);
        testMarket.setOreBuyPrice(10);
        testMarket.setOreStock(10);

        try {
            testMarket.buy("ore", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        int TestOreCount = 20;
        assertEquals(TestOreCount, TestPlayer.getOreCount());
        TestMoney = 0;
        assertEquals(TestMoney, TestPlayer.getMoney());
        TestSellPrice = 200;
        assertEquals(TestSellPrice, testMarket.getOreSellPrice());
        TestBuyPrice = 0;
        assertEquals(TestBuyPrice, testMarket.getOreBuyPrice());
        int TestOreStock = 0;
        assertEquals(TestOreStock, testMarket.getOreStock());

        //Food
        TestPlayer.setFoodCount(10);
        TestPlayer.setMoney(100);
        testMarket.setFoodSellPrice(10);
        testMarket.setFoodBuyPrice(10);
        testMarket.setFoodStock(10);

        try {
            testMarket.buy("food", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        int TestFoodCount = 20;
        assertEquals(TestFoodCount, TestPlayer.getFoodCount());
        TestMoney = 0;
        assertEquals(TestMoney, TestPlayer.getMoney());
        TestSellPrice = 200;
        assertEquals(TestSellPrice, testMarket.getFoodSellPrice());
        TestBuyPrice = 0;
        assertEquals(TestBuyPrice, testMarket.getFoodBuyPrice());
        TestFoodStock = 0;
        assertEquals(TestFoodStock, testMarket.getFoodStock());

        //Roboticon
        TestPlayer.setMoney(1000);
        testMarket.setRoboticonBuyPrice(10);
        testMarket.setRoboticonStock(10);

        try {
            testMarket.buy("roboticon", 1, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

    }

    /**
     * Tests Invalid buy conditions for all resources.
     * <p>
     *     Market resources are set to 10, and the prices are set to be valid for this test.
     *     Initial values of OreCount, OreSellPrice, OreBuyPrice are set to 10 and Money is set to 100.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 100.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 100.
     *
     *     However the player attempts to buy 100 of the resource, triggering the exception
     * </p>
     * @throws Exception Thrown when an invalid transaction is attempted.
     */
    @Test
    public void testBuyExceptions() throws Exception {

        //Energy
        TestPlayer.setEnergyCount(10);
        TestPlayer.setMoney(100);
        testMarket.setEnergySellPrice(10);
        testMarket.setEnergyBuyPrice(10);
        testMarket.setEnergyStock(100);

        try {
            testMarket.buy("energy", 100, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Insufficient money");
        }

        int TestEnergyCount = 10;
        assertEquals(TestEnergyCount, TestPlayer.getEnergyCount());
        int TestMoney = 100;
        assertEquals(TestMoney, TestPlayer.getMoney());
        int TestSellPrice = 10;
        assertEquals(TestSellPrice, testMarket.getEnergySellPrice());
        int TestBuyPrice = 10;
        assertEquals(TestBuyPrice, testMarket.getEnergyBuyPrice());
        int TestEnergyStock = 100;
        assertEquals(TestEnergyStock, testMarket.getEnergyStock());

        //Ore
        TestPlayer.setOreCount(10);
        TestPlayer.setMoney(100);
        testMarket.setOreSellPrice(10);
        testMarket.setOreBuyPrice(10);
        testMarket.setOreStock(100);

        try {
            testMarket.buy("ore", 100, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Insufficient money");
        }

        int TestOreCount = 10;
        assertEquals(TestOreCount, TestPlayer.getOreCount());
        TestMoney = 100;
        assertEquals(TestMoney, TestPlayer.getMoney());
        TestSellPrice = 10;
        assertEquals(TestSellPrice, testMarket.getOreSellPrice());
        TestBuyPrice = 10;
        assertEquals(TestBuyPrice, testMarket.getOreBuyPrice());
        int TestOreStock = 100;
        assertEquals(TestOreStock, testMarket.getOreStock());

        //Food
        TestPlayer.setFoodCount(10);
        TestPlayer.setMoney(100);
        testMarket.setFoodSellPrice(10);
        testMarket.setFoodBuyPrice(10);
        testMarket.setFoodStock(100);

        try {
            testMarket.buy("food", 100, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Insufficient money");
        }

        int TestFoodCount = 10;
        assertEquals(TestFoodCount, TestPlayer.getFoodCount());
        TestMoney = 100;
        assertEquals(TestMoney, TestPlayer.getMoney());
        TestSellPrice = 10;
        assertEquals(TestSellPrice, testMarket.getFoodSellPrice());
        TestBuyPrice = 10;
        assertEquals(TestBuyPrice, testMarket.getFoodBuyPrice());
        int TestFoodStock = 100;
        assertEquals(TestFoodStock, testMarket.getFoodStock());

        //Roboticon
        TestPlayer.setMoney(1);
        testMarket.setRoboticonBuyPrice(10);
        testMarket.setRoboticonStock(10);

        try {
            testMarket.buy("roboticon", 10, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Insufficient money");
        }

        TestPlayer.setMoney(100);
        testMarket.setRoboticonBuyPrice(10);
        testMarket.setRoboticonStock(0);

        try {
            testMarket.buy("roboticon", 10, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "No available Roboticons");
        }
    }

    /**
     * Tests Valid sell conditions for all resources.
     * <p>
     *     Market resources are set to 10, and the prices are set to be valid for this test.
     *     Initial values of OreCount, OreSellPrice, OreBuyPrice are set to 10 and Money is set to 10.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 10.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 10.
     * </p>
     * @throws Exception Thrown when an invalid transaction is attempted.
     */
    @Test
    public void testSell() throws Exception{
        //ore
        TestPlayer.setOreCount(10);
        TestPlayer.setMoney(10);
        testMarket.setOreSellPrice(10);
        testMarket.setOreBuyPrice(10);
        testMarket.setOreStock(10);

        try {
            testMarket.sell("ore", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        //food
        TestPlayer.setFoodCount(10);
        TestPlayer.setMoney(10);
        testMarket.setFoodSellPrice(10);
        testMarket.setFoodBuyPrice(10);
        testMarket.setFoodStock(10);

        try {
            testMarket.sell("food", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        //energy
        TestPlayer.setEnergyCount(10);
        TestPlayer.setMoney(10);
        testMarket.setEnergySellPrice(10);
        testMarket.setEnergyBuyPrice(10);
        testMarket.setEnergyStock(10);
        try {
            testMarket.sell("energy", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }
    }

    /**
     * Tests Invalid sell conditions for all resources.
     * <p>
     *     Market resources are set to 10, and the prices are set to be valid for this test.
     *     Initial values of OreCount, OreSellPrice, OreBuyPrice are set to 10 and Money is set to 10.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 10.
     *     Initial values of FoodCount, FoodSellPrice, FoodBuyPrice are set to 10 and Money is set to 10.
     *
     *     However the player attempts to sell 100 of the resource, triggering the exception
     * </p>
     * @throws Exception Thrown when an invalid transaction is attempted.
     */
    @Test
    public void testSellExceptions() throws Exception{
        //ore
        TestPlayer.setOreCount(10);
        TestPlayer.setMoney(10);
        testMarket.setOreSellPrice(10);
        testMarket.setOreBuyPrice(10);
        testMarket.setOreStock(10);

        try {
            testMarket.sell("ore", 100, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Insufficient resources");
        }

        //food
        TestPlayer.setFoodCount(10);
        TestPlayer.setMoney(10);
        testMarket.setFoodSellPrice(10);
        testMarket.setFoodBuyPrice(10);
        testMarket.setFoodStock(10);

        try {
            testMarket.sell("food", 100, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Insufficient resources");
        }

        //energy
        TestPlayer.setEnergyCount(10);
        TestPlayer.setMoney(10);
        testMarket.setEnergySellPrice(10);
        testMarket.setEnergyBuyPrice(10);
        testMarket.setEnergyStock(10);
        try {
            testMarket.sell("energy", 100, TestPlayer);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Insufficient resources");
        }

    }

    @Test
    public void testGamble(){
        TestPlayer.setMoney(49);

        for (int j = 0; j < 100; j++) {
            if (TestPlayer.getMoney() < 50) {
                assertNull(testMarket.gamble(100, TestPlayer));
            } else if (TestPlayer.getMoney() >= 50) {
                Boolean current = testMarket.gamble(50, TestPlayer);
                assertTrue(((current == Boolean.TRUE) || (current == Boolean.FALSE)));
            }
        }
    }

    @Test
    public void testcalculatenewcost(){


        TestPlayer.setEnergyCount(10);
        TestPlayer.setMoney(1000 );
        testMarket.setEnergySellPrice(2);
        testMarket.setEnergyBuyPrice(2);
        testMarket.setEnergyStock(100);
        int initialbuyprice = testMarket.getEnergyBuyPrice();
        int initialsellprice = testMarket.getEnergySellPrice();

        try {
            testMarket.buy("energy", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        assertTrue(initialbuyprice < testMarket.getEnergyBuyPrice());
        assertTrue(initialsellprice > testMarket.getEnergySellPrice());

        TestPlayer.setOreCount(10);
        TestPlayer.setMoney(1000 );
        testMarket.setOreSellPrice(2);
        testMarket.setOreBuyPrice(2);
        testMarket.setOreStock(100);
        initialbuyprice = testMarket.getOreBuyPrice();
        initialsellprice = testMarket.getOreSellPrice();

        try {
            testMarket.buy("ore", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        assertTrue(initialbuyprice < testMarket.getOreBuyPrice());
        assertTrue(initialsellprice > testMarket.getOreSellPrice());

        TestPlayer.setFoodCount(10);
        TestPlayer.setMoney(1000 );
        testMarket.setFoodSellPrice(2);
        testMarket.setFoodBuyPrice(2);
        testMarket.setFoodStock(100);
        initialbuyprice = testMarket.getFoodBuyPrice();
        initialsellprice = testMarket.getFoodSellPrice();

        try {
            testMarket.buy("food", 10, TestPlayer);
        } catch (Exception e) {
            fail("Expected to pass");
        }

        assertTrue(initialbuyprice < testMarket.getFoodBuyPrice());
        assertTrue(initialsellprice > testMarket.getFoodSellPrice());
    }


}
