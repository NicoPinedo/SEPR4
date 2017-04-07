package drtn.game.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import drtn.game.GameEngine;
import drtn.game.Trade;
import drtn.game.enums.ResourceType;
import drtn.game.util.Drawer;
import drtn.game.util.TTFont;
import drtn.game.GameEngine;
import drtn.game.enums.ResourceType;

import java.util.Random;


public class Market extends Table {
    /**
     * Holds game-state
     */
    private Game game;

    /**
     * Engine class that handles all of the game's logical processing
     */
    private GameEngine engine;

    /**
     * Variable holding current Ore resource amount as an int, initialises at 0 as stated in the brief.
     */
    private int OreStock = 0;

    /**
     * Variable holding current Food resource amount as an int, initialises at 16 as stated in the brief.
     */
    private int FoodStock = 16;

    /**
     * Variable holding current Energy resource amount as an int, initialises at 16 as stated in the brief.
     */
    private int EnergyStock = 16;

    /**
     * Variable holding current amount of Roboticons as an int, initialises at 12 as stated in the brief.
     */
    private int RoboticonStock = 12;

    /**
     * Variable holding ore resource selling price.
     */
    private int OreSellPrice = 14;

    /**
     * Variable holding food resource selling price.
     */
    private int FoodSellPrice = 14;

    /**
     * Variable holding energy resource selling price.
     */
    private int EnergySellPrice = 14;

    /**
     * Variable holding ore resource buying price.
     */
    private int OreBuyPrice = 15;

    /**
     * Variable holding food resource buying price.
     */
    private int FoodBuyPrice = 15;

    /**
     * Variable holding energy resource buying price.
     */
    private int EnergyBuyPrice = 15;

    /**
     * Variable holding roboticon buying price.
     */
    private int RoboticonBuyPrice = 20;


    /**
     * Constructs the market by calculating buying/selling costs and arranging the associated visual interface
     * Imports the game's state (for direct renderer access) and the engine which controls it, before setting up
     * the functions and visual features of its internal purchase/sale buttons and populating a drawable visual
     * framework with them and some other stock/identification labels
     *
     * @param game   The game's state, which is used in this context to operate the game's renderer via the Drawer class
     * @param engine The game's engine, which directly controls the availability and prices of market resources
     */
    public Market(Game game, GameEngine engine) {
        super();
        //Run the constructor for the Table object that forms this market's visual interface

        this.game = game;
        //Import the game's current state

        this.engine = engine;
        //Link the market to the game's engine
        //This is required to access phase information, as certain market sectors open and close based on it
    }

    /**
     * Returns the number of Roboticons currently held in the market
     *
     * @return int The number of Roboticons currently held in the market
     */
    int getRoboticonStock() {
        return this.RoboticonStock;
    }

    /**
     * Sets the market's Roboticon stock level
     * Also updates the appropriate stock label to reflect the new quantity given
     *
     * @param NewRoboticonStock The new number of Roboticons to be held in the market
     */
    public void setRoboticonStock(int NewRoboticonStock) {
        this.RoboticonStock = NewRoboticonStock;
    }

    /**
     * Getter for RoboticonBuyPrice
     *
     * @return this.RoboticonBuyPrice is integer roboticon buy price value
     */
    int getRoboticonBuyPrice() {
        return this.RoboticonBuyPrice;
    }

    /**
     * Setter for RoboticonBuyPrice
     *
     * @param NewRoboticonBuyPrice integer value that RoboticonBuyPrice is assigned to.
     */
    public void setRoboticonBuyPrice(int NewRoboticonBuyPrice) {
        this.RoboticonBuyPrice = NewRoboticonBuyPrice;
    }

    /**
     * Getter for OreStock
     *
     * @return this.OresStock is  integer ore stock value of a Market.
     */
    public int getOreStock() {
        return this.OreStock;
    }

    /**
     * Setter for OreStock.
     *
     * @param NewOreStock integer value that OreStock is assigned to.
     */
    public void setOreStock(int NewOreStock) {
        this.OreStock = NewOreStock;
    }

    /**
     * Getter for OreSellPrice
     *
     * @return this.OreSellPrice returns ore selling price value as an integer.
     */
    public int getOreSellPrice() {
        return this.OreSellPrice;
    }

    /**
     * Setter for OreSellPrice.
     *
     * @param NewOreSellPrice integer value that OreSellPrice is set to.
     */
    public void setOreSellPrice(int NewOreSellPrice) {
        this.OreSellPrice = NewOreSellPrice;
    }

    /**
     * Getter for OreBuyPrice.
     *
     * @return this.OreBuyPrice returns ore buying price as an integer.
     */
    public int getOreBuyPrice() {
        return this.OreBuyPrice;
    }

    /**
     * Setter for OreBuyPrice.
     *
     * @param NewOreBuyPrice integer value that OreBuyPrice is set to.
     */
    public void setOreBuyPrice(int NewOreBuyPrice) {
        this.OreBuyPrice = NewOreBuyPrice;
    }

    /**
     * Getter  for FoodStock
     *
     * @return this.FoodStock is  integer food stock value of a Market.
     */
    public int getFoodStock() {
        return this.FoodStock;
    }

    /**
     * Setter for FoodStock.
     *
     * @param NewFoodStock integer value that FoodStock is assigned to.
     */
    public void setFoodStock(int NewFoodStock) {
        this.FoodStock = NewFoodStock;
    }

    /**
     * Getter for FoodSellPrice
     *
     * @return this.FoodSellPrice returns food selling price value as an integer.
     */
    public int getFoodSellPrice() {
        return this.FoodSellPrice;
    }

    /**
     * Setter for FoodSellPrice.
     *
     * @param NewFoodSellPrice integer value that FoodSellPrice is set to.
     */
    public void setFoodSellPrice(int NewFoodSellPrice) {
        this.FoodSellPrice = NewFoodSellPrice;
    }

    /**
     * Getter for FoodBuyPrice.
     *
     * @return this.FoodBuyPrice returns food buying price as an integer.
     */
    public int getFoodBuyPrice() {
        return this.FoodBuyPrice;
    }

    /**
     * Setter for FoodBuyPrice.
     *
     * @param NewFoodBuyPrice integer value that FoodBuyPrice is set to.
     */
    public void setFoodBuyPrice(int NewFoodBuyPrice) {
    }

    /**
     * Getter  for EnergyStock
     *
     * @return this.EnergyStock is  integer energy stock value of a Market.
     */
    public int getEnergyStock() {
        return this.EnergyStock;
    }

    /**
     * Setter for EnergyStock.
     *
     * @param NewEnergyStock integer value that EnergyStock is assigned to.
     */
    public void setEnergyStock(int NewEnergyStock) {
        this.EnergyStock = NewEnergyStock;
    }

    /**
     * Getter for EnergySellPrice
     *
     * @return this.EnergySellPrice returns energy selling price value as an integer.
     */
    public int getEnergySellPrice() {
        return this.EnergySellPrice;
    }

    /**
     * Setter for EnergySellPrice.
     *
     * @param NewEnergySellPrice integer value that EnergySellPrice is set to.
     */
    public void setEnergySellPrice(int NewEnergySellPrice) {
        this.EnergySellPrice = NewEnergySellPrice;
    }

    /**
     * Getter for EnergyBuyPrice.
     *
     * @return this.EnergyBuyPrice returns energy buying price as an integer.
     */
    public int getEnergyBuyPrice() {
        return this.EnergyBuyPrice;
    }

    /**
     * Setter for EnergyBuyPrice.
     *
     * @param NewEnergyBuyPrice integer value that EnergyBuyPrice is set to.
     */
    public void setEnergyBuyPrice(int NewEnergyBuyPrice) {
        this.EnergyBuyPrice = NewEnergyBuyPrice;
    }


    /**
     * A method that allows buying resources from the market.
     * <p>
     * Depending on what type of resources is passed (ResourceType.ORE, ResourceType.FOOD or ResourceType.ENERGY) method checks whether it is sufficient
     * amount of that resource in market stock. Then it is checked whether does Player has enough money to buy required
     * amount. Market stock value (e.g. OreStock) is reduced by the quantity that has been bought(Quantity). Players money
     * (playersMoney) are reduced by the amount that was spent on the purchase. The value of Players resource stock is
     * updated. New selling and buying prices, for that chosen resource, are calculated (calculateNewCost()).
     * </p>
     *
     * @param Stock_Type Type of resources (ore, energy or food) that is stored in the market.
     * @param Quantity   The amount of resources that Player wants to buy.
     * @param Player     A Player object.
     */
    public Player buy(ResourceType Stock_Type, int Quantity, Player Player) throws Exception {
        switch (Stock_Type) {

            case ORE:
                if (Quantity <= OreStock) {
                    int OrePrice = OreBuyPrice * Quantity;
                    if (Player.getResource(ResourceType.MONEY) >= OrePrice) {
                        OreStock -= Quantity;
                        Player.setResource(ResourceType.MONEY, Player.getResource(ResourceType.MONEY) - OrePrice);
                        int playersOre = Player.getResource(ResourceType.ORE);
                        playersOre += Quantity;
                        Player.setResource(ResourceType.ORE, playersOre);
                        OreBuyPrice = calculateNewCost(OreStock, "buy");
                        OreSellPrice = calculateNewCost(OreStock, "sell");
                    } else {
                        throw new Exception("Insufficient money");
                    }
                } else {
                    throw new Exception("Insufficient resources");
                }
                break;

            case FOOD:
                if (Quantity <= FoodStock) {
                    int FoodPrice = FoodBuyPrice * Quantity;
                    if (Player.getResource(ResourceType.MONEY) >= FoodPrice) {
                        FoodStock -= Quantity;
                        Player.setResource(ResourceType.MONEY, Player.getResource(ResourceType.MONEY) - FoodPrice);
                        int playersFood = Player.getResource(ResourceType.FOOD);
                        playersFood += Quantity;
                        Player.setResource(ResourceType.FOOD, playersFood);
                        FoodBuyPrice = calculateNewCost(FoodStock, "buy");
                        FoodSellPrice = calculateNewCost(FoodStock, "sell");
                    } else {
                        throw new Exception("Insufficient money");
                    }

                } else {
                    throw new Exception("Insufficient resources");
                }
                break;

            case ENERGY:
                if (Quantity <= EnergyStock) {
                    int EnergyPrice = EnergyBuyPrice * Quantity;
                    if (Player.getResource(ResourceType.MONEY) >= EnergyPrice) {
                        EnergyStock -= Quantity;
                        Player.setResource(ResourceType.MONEY, Player.getResource(ResourceType.MONEY) - EnergyPrice);
                        int playersEnergy = Player.getResource(ResourceType.ENERGY);
                        playersEnergy += Quantity;
                        Player.setResource(ResourceType.ENERGY, playersEnergy);
                        EnergyBuyPrice = calculateNewCost(EnergyStock, "buy");
                        EnergySellPrice = calculateNewCost(EnergyStock, "sell");
                    } else {
                        throw new Exception("Insufficient money");
                    }
                } else {
                    throw new Exception("Insufficient resources");
                }
                break;

            case ROBOTICON:
                if (RoboticonStock > 0) {
                    if (Player.getResource(ResourceType.MONEY) >= RoboticonBuyPrice) {
                        RoboticonStock -= 1;
                        Player.setResource(ResourceType.MONEY, Player.getResource(ResourceType.MONEY) - RoboticonBuyPrice);
                        RoboticonBuyPrice += 5;
                        Player.increaseRoboticonInventory();
                    } else {
                        throw new Exception("Insufficient money");
                    }
                } else {
                    throw new Exception("No available Roboticons");
                }
                break;
            default:

                throw new Exception("Wrong Stock_Type passed");
        }
        return Player;


    }


    /**
     * A method that allows selling resources to the market.
     * <p>
     * Depending on what type of resources it is passed (ResourceType.ORE, ResourceType.FOOD or ResourceType.ENERGY) method checks whether the Player has
     * sufficient amount (Quantity) of resource that he is willing to sell. Market stock value (e.g. OreStock) is
     * increased by the quantity that has been sold by Player. Players money(playersMoney) are increased by the amount
     * that was gotten by selling resources. The value of Player's resource is decreased by the amount that has been sold.
     * New selling and buying prices, for that chosen resource, are calculated (calculateNewCost()).
     * </p>
     *
     * @param Stock_Type Type of resources (ore, energy or food) that is stored in the market.
     * @param Quantity   The amount of resources that Player wants to buy.
     * @param Player     A Player object.
     */
    public Player sell(ResourceType Stock_Type, int Quantity, Player Player) throws Exception {
        int playersMoney = Player.getResource(ResourceType.MONEY);

        switch (Stock_Type) {
            case ORE:
                int playersOre = Player.getResource(ResourceType.ORE);
                if (playersOre >= Quantity) {
                    OreStock += Quantity;
                    playersMoney += Quantity * OreSellPrice;
                    Player.setResource(ResourceType.MONEY, playersMoney);
                    playersOre -= Quantity;
                    Player.setResource(ResourceType.ORE, playersOre);

                    OreBuyPrice = calculateNewCost(OreStock, "buy");
                    OreSellPrice = calculateNewCost(OreStock, "sell");
                } else {
                    throw new Exception("Insufficient resources");

                }
                break;
            case FOOD:
                int playersFood = Player.getResource(ResourceType.FOOD);
                if (playersFood >= Quantity) {

                    FoodStock += Quantity;
                    playersMoney += Quantity * FoodSellPrice;
                    Player.setResource(ResourceType.MONEY, playersMoney);
                    playersFood -= Quantity;
                    Player.setResource(ResourceType.FOOD, playersFood);
                    FoodBuyPrice = calculateNewCost(FoodStock, "buy");
                    FoodSellPrice = calculateNewCost(FoodStock, "sell");
                } else {
                    throw new Exception("Insufficient resources");
                }
                break;
            case ENERGY:
                int playersEnergy = Player.getResource(ResourceType.ENERGY);
                if (playersEnergy >= Quantity) {
                    EnergyStock += Quantity;
                    playersMoney += Quantity * EnergySellPrice;
                    Player.setResource(ResourceType.MONEY, playersMoney);
                    playersEnergy -= Quantity;
                    Player.setResource(ResourceType.ENERGY, playersEnergy);
                    EnergyBuyPrice = calculateNewCost(EnergyStock, "buy");
                    EnergySellPrice = calculateNewCost(EnergyStock, "sell");
                } else {
                    throw new Exception("Insufficient resources");
                }
                break;
            default:
                //Roboticon or Money passed, both invalid here

        }
        return Player;

    }


    /**
     * A method that allows gambling as specified in the brief.
     * <p>
     * First it is checked whether a chosen amount of money is not higher than the total amount of Player's money.
     * Number generator generates whether 0 or 1. If 0 is generated - Player loses and his money is reduced by the
     * "amountToGamble". If 1 is generated - Player wins and his money is increased by the "amountToGamble".
     * </p>
     *
     * @param amountToGamble The amount of money that is meant to be spent for gambling.
     * @param Player         A Player object.
     * @return Returns True if Player has won, False if he lost and null if Player has less money than
     * chosen amount of money to gamble with.
     */
    public Boolean gamble(int amountToGamble, Player Player) {
        int playersMoney = Player.getResource(ResourceType.MONEY);
        if (amountToGamble <= playersMoney) {
            Random rand = new Random();
            int result = rand.nextInt(2);
            if (result == 0) {
                playersMoney -= amountToGamble;
                Player.setResource(ResourceType.MONEY, playersMoney);
                return false;
            } else {
                playersMoney += amountToGamble;
                Player.setResource(ResourceType.MONEY, playersMoney);
                return true;
            }

        } else {
            return null; //throw an error or prevent them clicking it in the first place
        }

    }


    /**
     * A method that calculates cost of market selling and buying prices.
     * <p>
     * When Stock value equals 0 and wanted operation is "buy", costOfResource value is set to 0. When Stock value is
     * equal to 0 and wanted operation is "sell", costOfResource value is set to 200.
     * If wanted operation is "buy", new buying price is calculated. If wanted operation is "sell", then new selling
     * price is calculated.
     * </p>
     *
     * @param Stock int values of market resources.
     * @param oper  String value representing operations "buy" and "sell".
     * @return costofresources int value of the resource's new cost
     * @throws Exception Thrown if there's a wrong operator used with the function
     */
    private int calculateNewCost(int Stock, String oper) throws Exception {
        double cost;
        int costOfResources;
        if (Stock == 0 && oper.equals("buy")) {
            costOfResources = 0;
        } else if (Stock == 0 && oper.equals("sell")) {
            costOfResources = 200;

        } else if (oper.equals("buy")) {
            cost = 160 / Stock + 2;
            costOfResources = (int) Math.round(cost);

        } else if (oper.equals("sell")) {
            cost = 160 / Stock;
            int costInt = (int) Math.round(cost);
            if (costInt < 1) {
                costOfResources = 1;
            } else {
                costOfResources = costInt;
            }
        } else {
            throw new Exception("Wrong operator");
        }
        return costOfResources;
    }

    /**
     * allows the market to get it's inventory of roboticons up to 10 so long as it has
     * at least 10 ore, each roboticon costs 3 ore
     */
    public void produceRoboticon() {
        while (this.OreStock > 10 && RoboticonStock < 10) {
            OreStock -= 3;
            RoboticonStock += 1;
        }
    }
}




