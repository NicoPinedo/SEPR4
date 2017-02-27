package drtn.game.entity;

import drtn.game.Trade;
import drtn.game.enums.ResourceType;
import drtn.game.Trade;
import drtn.game.enums.ResourceType;

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
    private drtn.game.entity.College College;
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
     * Unified Getter for resource amounts
     *
     * @param type resource requested
     * @return int value representing the amount of type the player currently have
     */
    public int getResource(ResourceType type) {
        switch (type) {
            case ENERGY:
                return this.EnergyCount;
            case FOOD:
                return this.FoodCount;
            case ORE:
                return this.OreCount;
            case MONEY:
                return this.money;
            default:
                //Unable to reach this state as a ResourceType must be passed.
                //TODO: Improve this - Kieran
                return 0;
        }
    }

    /**
     * Unified Setter for resource amounts
     *
     * @param type     resource being set
     * @param newCount int value the count should be updated to
     */
    public void setResource(ResourceType type, int newCount) {
        if (!(newCount < 0)) {
            switch (type) {
                case ENERGY:
                    this.EnergyCount = newCount;
                    break;
                case FOOD:
                    this.FoodCount = newCount;
                    break;
                case ORE:
                    this.OreCount = newCount;
                    break;
                case MONEY:
                    this.money = newCount;
                default:
            }
        }
    }

    /**
     * Unified Setter for resource amounts
     *
     * @param type  resource being set
     * @param delta difference to be applied negative for decrease, positive for increase
     */
    //TODO: add check to prevent going below 0
    public void varyResource(ResourceType type, int delta) {
        switch (type) {
            case ENERGY:
                this.EnergyCount += delta;
                break;
            case FOOD:
                this.FoodCount += delta;
                break;
            case ORE:
                this.OreCount += delta;
                break;
            case MONEY:
                this.money += delta;
            default:

        }
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
     *
     * @return int value for roboticons in inventory.
     */
    public int getInventoryRoboticons() {
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
    public void setTrade(Trade trade) {
        this.currentTrade = trade;
    }
}
