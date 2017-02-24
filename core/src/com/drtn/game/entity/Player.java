package com.drtn.game.entity;

import com.drtn.game.Trade;

import java.util.ArrayList;
import java.util.List;


public class Player {
    /**
     * Unique numerical identifier of the player.
     */
    private int playerID;
    /**
     * An integer storing the amount of ore the player owns.
     */
    private int OreCount = 0;
    /**
     * An integer storing the amount of food a player owns.
     */
    private int FoodCount = 5;
    /**
     * An integer storing the amount of energy a player owns.
     */
    private int EnergyCount = 5;
    /**
     * An integer storing the amount of money a player owns.
     */
    private int money = 50;
    /**
     * A variable determining if the player is currently active. True means that they are active.
     */
    private Boolean Active = false;
    /**
     * The college that the player is playing as.
     */
    private com.drtn.game.entity.College College;
    /**
     * A list of the tiles that the player owns.
     */
    private List<Tile> TileList = new ArrayList<Tile>();
    /**
     * The number of Roboticons that the player owns
     */
    private int inventoryRoboticons = 0;
    private Trade currentTrade;

    /**
     * The constructor of the class
     *
     * @param playerID The id of the player that is being created. Should be an integer greater than 0.
     */
    public Player(int playerID) {
        this.playerID = playerID;
    }

    public boolean isAi() {
        return false;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getPlayerNumber() {
        return playerID + 1;
    }

    /**
     * Getter for the tile list of the Player
     *
     * @return TileList A list of the tile objects that the player owns.
     */
    public List<Tile> getTileList() {
        return this.TileList;
    }

    /**
     * Getter for the money attribute of the player
     *
     * @return Current player money amount.
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Setter for the money attribute
     *
     * @param newMoney int value corresponding to the new money value desired
     */
    public void setMoney(int newMoney) {
        if (newMoney >= 0) {
            this.money = newMoney;
        }
    }

    /**
     * Getter for OreCount
     *
     * @return this.OreCount the orecount of the player as an integer
     */
    public int getOreCount() {
        return this.OreCount;
    }

    /**
     * Setter for Orecount
     *
     * @param Newcount int value that the Orecount is set to
     */
    public void setOreCount(int Newcount) {
        this.OreCount = Newcount;
    }

    /**
     * Getter for EnergyCount
     *
     * @return this.EnergyCount the Energycount of the player as an integer
     */
    public int getEnergyCount() {
        return this.EnergyCount;
    }

    /**
     * Setter for Energycount
     *
     * @param Newcount int value that the Energycount is set to
     */
    public void setEnergyCount(int Newcount) {
        this.EnergyCount = Newcount;
    }

    /**
     * Getter for FoodCount
     *
     * @return this.FoodCount the Foodcount of the player as an integer
     */
    public int getFoodCount() {
        return this.FoodCount;
    }

    /**
     * Setter for Foodcount
     *
     * @param Newcount int value that the Foodcount is set to
     */
    public void setFoodCount(int Newcount) {
        this.FoodCount = Newcount;
    }

    /**
     * Toggles the 'active' attribute of the player from True to False or False to True.
     */
    public void toggleActive() {
        this.Active = !this.Active;
    }

    /**
     * Changes the college of the player to the one that is specified.
     *
     * @param College The college that the player is being assigned to.
     */
    public void assignCollege(College College) {
        this.College = College;
    }

    /**
     * Adds the specified tile to the players list of tiles.
     *
     * @param Tile The tile that is to be added to the player's tile list.
     */

    public void assignTile(Tile Tile) {
        TileList.add(Tile);
    }

    /**
     * Increases/decreases the specified resource of the player by the specified amount
     *
     * @param resource The resource that is to be modified.
     * @param amount   The amount that the player's resource is to change by. Negative value for a decrease, positive for an increase.
     */

    public void varyResource(String resource, int amount) {
        if (resource.equals("Ore")) {
            this.OreCount += amount;
        } else if (resource.equals("Energy")) {
            this.EnergyCount += amount;
        } else if (resource.equals("Food")) {
            this.FoodCount += amount;
        } else if (resource.equals("Money")) {
            this.money += amount;
        }

    }

    /**
     * Calculates the score of the player based on the resources that they own.
     *
     * @return int The player's current score
     */
    public int calculateScore() {
        return this.EnergyCount + this.FoodCount + this.OreCount;

    }

    /**
     * Increments the number of Roboticons owned by the player
     */
    public void increaseRoboticonInventory() {
        this.inventoryRoboticons += 1;
    }

    /**
     * Decrements the number of Roboticons owned by the player
     */
    public void decreaseRoboticonInventory() {
        this.inventoryRoboticons -= 1;
    }

    /**
     * Getter for Inventory Roboticon Count
     * @return int value for roboticons in inventory.
     */
    public int getInventoryRoboticons(){
        return this.inventoryRoboticons;
    }

    /**
     * Returns the number of Roboticons owned by the player
     *
     * @return int The number of Roboticons owned by the player
     */
    public int getRoboticonInventory() {
        return this.inventoryRoboticons;
    }

    /**
     * Returns the college assigned to the player
     *
     * @return College The player's associated college
     */
    public College getCollege() {
        return this.College;
    }

    public Trade getTrade() {
        return this.currentTrade;
    }
    
    //two methods below are new for assessment3
    public void setTrade(Trade trade){
    	this.currentTrade = trade;
    }
}
