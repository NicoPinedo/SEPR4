import com.drtn.game.Trade;
import com.drtn.game.entity.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TradeTest {
	
	private Player player1;
	private Player player2;
	private Trade trade;
	
	@Before
	public void setup(){
		player1 = new Player(0);
		player2 = new Player(1);
		trade = new Trade(10, 10, 10, 100, player1, player2);
	}
	/**
	 * Test for a trade in which both sender and target have enough money/resources
	 */
	@Test
	public void successfulTrade(){
		player1.setEnergyCount(20);
		player1.setFoodCount(20);
		player1.setOreCount(20);
		player2.setMoney(110);
		assert(trade.execute());
		assertEquals(player1.getEnergyCount(), 10);
		assertEquals(player1.getFoodCount(), 10);
		assertEquals(player1.getOreCount(), 10);
		assertEquals(player2.getMoney(), 10);
	}
	
	/**
	 * a Test for when a player does not have resources for a trade to execute, the player's money and resources
	 * remain unchanged and the execute method returns false 
	 */
	@Test
	public void notEnoughResourcesTrade(){
		player1.setEnergyCount(5);
		player1.setFoodCount(5);
		player1.setOreCount(5);
		player2.setMoney(110);
		assert(! trade.execute());
		assertEquals(player1.getEnergyCount(), 5);
		assertEquals(player1.getFoodCount(), 5);
		assertEquals(player1.getOreCount(), 5);
		assertEquals(player2.getMoney(), 110);
	}
	
	/**
	 * a Test for when a player does not have money for a trade to execute, the player's money and resources
	 * remain unchanged and the execute method returns false 
	 */
	@Test
	public void notEnoughMoneyTrade(){
		player1.setEnergyCount(10);
		player1.setFoodCount(10);
		player1.setOreCount(10);
		player2.setMoney(10);
		assert(! trade.execute());
		assertEquals(player1.getEnergyCount(), 10);
		assertEquals(player1.getFoodCount(), 10);
		assertEquals(player1.getOreCount(), 10);
		assertEquals(player2.getMoney(), 10);
	}

}
