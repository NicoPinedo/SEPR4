package com.drtn.game;

import com.drtn.game.entity.Player;

/**
 * Class to facilitate trades for assessment 3
 * @author jc1850
 *
 */
//new class for assessment 3
public class Trade {
	public final int oreAmount;
	public final int energyAmount;
	public final int foodAmount;
	private int price;
	private Player sender;
	private Player targetPlayer;

	/**
	 * constructor for a trade
	 * @param oreAmount - amount of ore for sale
	 * @param energyAmount - amount of energy for sale
	 * @param foodAmount - amount of food for sale
	 * @param price - price of the sale
	 * @param sender - player sending the trade
	 * @param targetPlayer - player receiving the trade
	 */
	public Trade(int oreAmount, int energyAmount, int foodAmount, int price,
			Player sender, Player targetPlayer){
		this.oreAmount = oreAmount;
		this.energyAmount = energyAmount;
		this.foodAmount = foodAmount;
		this.price = price;
		this.targetPlayer = targetPlayer;
		this.sender = sender;
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public void setPrice(int newPrice){
		this.price = newPrice;
	}

	Player getTargetPlayer() {
		return targetPlayer;
	}
	
	public Player getSender(){
		return this.sender;
	}
	
	/**
	 * execute will first test if the players have enough resources and money to execute the trade
	 * if they do it will vary the resources and money in their inventories by the appropriate amount
	 * @return true if trade is successful, false if it fails
	 */
	public boolean execute(){
		if (sender.getEnergyCount()> energyAmount && sender.getFoodCount() > foodAmount 
				&& sender.getOreCount()> oreAmount){
			if (targetPlayer.getMoney() > getPrice()){
				targetPlayer.varyResource("Ore", this.oreAmount);
				targetPlayer.varyResource("Energy", this.energyAmount);
				targetPlayer.varyResource("Food", this.foodAmount);
				targetPlayer.varyResource("Money", -this.price);
				sender.varyResource("Ore", -this.oreAmount);
				sender.varyResource("Energy", -this.energyAmount);
				sender.varyResource("Food", -this.foodAmount);
				sender.varyResource("Money", this.price);
				return true;
			}
			else return false;
		}
		else return false;
	}
	
}
