package drtn.game;

import drtn.game.entity.Player;
import drtn.game.enums.ResourceType;
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
		player1.setResource(ResourceType.ENERGY, 20);
        player1.setResource(ResourceType.ORE, 20);
        player1.setResource(ResourceType.FOOD, 20);
		player2.setResource(ResourceType.MONEY, 110);
		assert(trade.execute());
		assertEquals(player1.getResource(ResourceType.ENERGY), 10);
		assertEquals(player1.getResource(ResourceType.FOOD), 10);
		assertEquals(player1.getResource(ResourceType.ORE), 10);
		assertEquals(player2.getResource(ResourceType.MONEY), 10);
	}
	
	/**
	 * a Test for when a player does not have resources for a trade to execute, the player's money and resources
	 * remain unchanged and the execute method returns false 
	 */
	@Test
	public void notEnoughResourcesTrade(){
		player1.setResource(ResourceType.ENERGY, 5);
        player1.setResource(ResourceType.ORE, 5);
        player1.setResource(ResourceType.FOOD, 5);
		player2.setResource(ResourceType.MONEY, 110);
		assert(! trade.execute());
		assertEquals(player1.getResource(ResourceType.ENERGY), 5);
		assertEquals(player1.getResource(ResourceType.FOOD), 5);
		assertEquals(player1.getResource(ResourceType.ORE), 5);
		assertEquals(player2.getResource(ResourceType.MONEY), 110);
	}
	
	/**
	 * a Test for when a player does not have money for a trade to execute, the player's money and resources
	 * remain unchanged and the execute method returns false 
	 */
	@Test
	public void notEnoughMoneyTrade(){
		player1.setResource(ResourceType.ENERGY, 10);
        player1.setResource(ResourceType.ORE, 10);
        player1.setResource(ResourceType.FOOD, 10);
		player2.setResource(ResourceType.MONEY, 10);
		assert(! trade.execute());
		assertEquals(player1.getResource(ResourceType.ENERGY), 10);
		assertEquals(player1.getResource(ResourceType.FOOD), 10);
		assertEquals(player1.getResource(ResourceType.ORE), 10);
		assertEquals(player2.getResource(ResourceType.MONEY), 10);
	}

}
