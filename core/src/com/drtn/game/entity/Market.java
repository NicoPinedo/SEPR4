package com.drtn.game.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.drtn.game.GameEngine;
import com.drtn.game.Trade;
import com.drtn.game.enums.ResourceType;
import com.drtn.game.util.Drawer;
import com.drtn.game.util.TTFont;

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
     * Object defining QOL drawing functions for rectangles and on-screen tables
     * Used in this class to simplify row creation in tables
     */
    private Drawer drawer;

    /**
     * Defines the font of the text comprising the market's interface
     */
    private TTFont tableFont;

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
     * Button in the market's interface that buys ore
     */
    private TextButton buyOre;

    /**
     * Button in the market's interface that buys food
     */
    private TextButton buyFood;

    /**
     * Button in the market's interface that buys energy
     */
    private TextButton buyEnergy;

    /**
     * Button in the market's interface that buys Roboticons
     */
    private TextButton buyRoboticon;

    /**
     * Button in the market's interface that sells the current player's ore stocks to the market
     */
    private TextButton sellOre;

    /**
     * Button in the market's interface that sells the current player's food stocks to the market
     */
    private TextButton sellFood;

    /**
     * Button in the market's interface that sells the current player's energy stocks to the market
     */
    private TextButton sellEnergy;

    /**
     * Visualises the amount of ore stocks currently held by the market
     */
    private Label oreStockLabel;

    /**
     * Visualises the amount of food stocks currently held by the market
     */
    private Label foodStockLabel;

    /**
     * Visualises the amount of energy stocks currently held by the market
     */
    private Label energyStockLabel;

    /**
     * Visualises the amount of Roboticon stocks currently held by the market
     */
    private Label roboticonStockLabel;

    /**
     * button to move from market to auction
     */
    private TextButton marketButton;

    /**
     * Button to move from market to auction
     */
    private TextButton auctionButton;

    /**
     * Button to add ore to a trade
     */
    private TextButton playerBuyOre;

    /**
     * Button to add energy to a trade
     */
    private TextButton playerBuyEnergy;

    /**
     * Button to add food to a trade
     */
    private TextButton playerBuyFood;

    /**
     * Button to remove ore from a trade
     */
    private TextButton playerSellOre;

    /**
     * Button to remove energy from a trade
     */
    private TextButton playerSellEnergy;

    /**
     * Button to remove food from a trade
     */
    private TextButton playerSellFood;

    /**
     * variable storing amount of ore to be traded
     */
    private int oreTradeAmount;

    /**
     * variable storing amount of energy to be traded
     */
    private int energyTradeAmount;

    /**
     * variable storing amount of food to be traded
     */
    private int foodTradeAmount;

    /**
     * button to confirm a trade
     */
    private TextButton confirmSale;

    /**
     * button to add 1 to the price of a trade
     */
    private TextButton pricePlus1;

    /**
     * button to add 10 to the price of a trade
     */
    private TextButton pricePlus10;

    /**
     * button to add 100 to the price of a trade
     */
    private TextButton pricePlus100;

    /**
     * button to take 1 from the price of a trade
     */
    private TextButton priceMinus1;

    /**
     * button to take 10 from the price of a trade
     */
    private TextButton priceMinus10;

    /**
     * button to take 100 from price of a trade
     */
    private TextButton priceMinus100;

    /**
     * label to show amount of ore being traded
     */
    private Label oreTradeLabel;

    /**
     * label to show amount of energy being traded
     */
    private Label energyTradeLabel;

    /**
     * label to show amount of food being traded
     */
    private Label foodTradeLabel;

    /**
     * variable storing price of trade
     */
    private int tradePrice;

    /**
     * label to show the price of a trade
     */
    private Label tradePriceLabel;

    /**
     * move forward through list of players you want to trade with in auction
     */
    private TextButton nextPlayerButton;

    /**
     * move backwardward through list of players you want to trade with in auction
     */
    private TextButton prevPlayerButton;

    /**
     * Label to show which player a trade is going to be sent to
     */
    private Label playerLabel;

    /**
     * a list of players  that the current player can trade with
     */
    private Array<Player> otherPlayer;

    /**
     * current position in other player list in auction
     */
    private int playerListPosition;


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

        drawer = new Drawer(this.game);
        //QOL class that uses the game's state to directly access and control the game's renderer
        //Used by this class to construct the market's visual interface

        tableFont = new TTFont(Gdx.files.internal("font/testfontbignoodle.ttf"), 24);
        //Establish the font in which the market interface's text is to be pronted
        try {
            OreBuyPrice = calculateNewCost(OreStock, "buy");
            FoodBuyPrice = calculateNewCost(FoodStock, "buy");
            EnergyBuyPrice = calculateNewCost(EnergyStock, "buy");
            OreSellPrice = calculateNewCost(OreStock, "sell");
            FoodSellPrice = calculateNewCost(FoodStock, "sell");
            EnergySellPrice = calculateNewCost(EnergyStock, "sell");
            //Calculate the market's buying/selling prices
        } catch (Exception e) {
            e.printStackTrace();
        }

        constructButtons();
        //Build the purchase/sale buttons to populate the market's interface with

        constructInterface();
        //Build the market's visual interface using the buttons declared and prepared earlier
    }

    /**
     * Instantiates the purchase/sale buttons to be placed in the market and sets their on-click functions
     * Obviously, these buttons enable players to buy and sell resources during certain game-phases
     */
    private void constructButtons() {
        TextButton.TextButtonStyle tableButtonStyle = new TextButton.TextButtonStyle();
        tableButtonStyle.font = tableFont.font();
        tableButtonStyle.fontColor = Color.WHITE;
        tableButtonStyle.pressedOffsetX = 1;
        tableButtonStyle.pressedOffsetY = -1;
        //Set the visual parameters for the rest of the market's labels and buttons


        marketButton = new TextButton("Market", tableButtonStyle);
        marketButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clearChildren();
                constructInterface();
            }
        });

        auctionButton = new TextButton("Auction", tableButtonStyle);
        auctionButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clearChildren();
                constructAuctionInterface();
                refreshAuction();
            }
        });
        /**
         * Button that attempts to buy a Roboticon for the current player when clicked on
         */
        buyRoboticon = new TextButton("-" + getRoboticonBuyPrice(), tableButtonStyle);

        buyRoboticon.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    engine.updateCurrentPlayer(buy("roboticon", 1, engine.currentPlayer()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButtonAvailability();

            }
        });
        //Set the button for purchasing Roboticons to do just that (but only when the game is in phase 2)

        /**
         * Button that attempts to buy a unit of ore for the current player when clicked on
         */
        buyOre = new TextButton("-" + getOreBuyPrice(), tableButtonStyle);

        buyOre.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    engine.updateCurrentPlayer(buy("ore", 1, engine.currentPlayer()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButtonAvailability();
            }
        });
        //Set the button for purchasing ore to do just that (but only when the game is in phase 5)

        /**
         * Button that attempts to buy a unit of food for the current player when clicked on
         */
        buyFood = new TextButton("-" + getFoodBuyPrice(), tableButtonStyle);
        buyFood.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    engine.updateCurrentPlayer(buy("food", 1, engine.currentPlayer()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButtonAvailability();
            }
        });
        //Set the button for purchasing food to do just that (but only when the game is in phase 5)

        /**
         * Button that attempts to buy a unit of energy for the current player when clicked on
         */
        buyEnergy = new TextButton("-" + getEnergyBuyPrice(), tableButtonStyle);
        buyEnergy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    engine.updateCurrentPlayer(buy("energy", 1, engine.currentPlayer()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButtonAvailability();
            }
        });
        //Set the button for purchasing energy to do just that (but only when the game is in phase 5)

        /**
         * Button that attempts to take a unit of energy from the player's inventory and sell it back to the market
         * when clicked on
         */
        sellEnergy = new TextButton("+" + getEnergySellPrice(), tableButtonStyle);
        sellEnergy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

                try {
                    engine.updateCurrentPlayer(sell("energy", 1, engine.currentPlayer()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButtonAvailability();
            }
        });
        //Set the button for selling energy to do just that (but only when the game is in phase 5)

        /**
         * Button that attempts to take a unit of ore from the player's inventory and sell it back to the market
         * when clicked on
         */
        sellOre = new TextButton("+" + getOreSellPrice(), tableButtonStyle);
        sellOre.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    engine.updateCurrentPlayer(sell("ore", 1, engine.currentPlayer()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButtonAvailability();
            }
        });
        //Set the button for selling ore to do just that (but only when the game is in phase 5)

        /**
         * Button that attempts to take a unit of food from the player's inventory and sell it back to the market
         * when clicked on
         */
        sellFood = new TextButton("+" + getFoodSellPrice(), tableButtonStyle);
        sellFood.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    engine.updateCurrentPlayer(sell("food", 1, engine.currentPlayer()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButtonAvailability();
            }
        });
        //Set the button for selling food to do just that (but only when the game is in phase 5)

        //rest of method is new for assessment 3

        playerBuyOre = new TextButton("+", tableButtonStyle);
        oreTradeLabel = new Label("Ore:        " + oreTradeAmount,
                new Label.LabelStyle(tableFont.font(), Color.WHITE));
        playerBuyOre.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                oreTradeAmount += 1;
                refreshAuction();

            }
        });

        playerBuyEnergy = new TextButton("+", tableButtonStyle);
        energyTradeLabel = new Label("Energy:   " + energyTradeAmount,
                new Label.LabelStyle(tableFont.font(), Color.WHITE));
        playerBuyEnergy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                energyTradeAmount += 1;
                refreshAuction();

            }
        });

        playerBuyFood = new TextButton("+", tableButtonStyle);
        foodTradeLabel = new Label("Food:      " + foodTradeAmount,
                new Label.LabelStyle(tableFont.font(), Color.WHITE));
        playerBuyFood.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                foodTradeAmount += 1;
                refreshAuction();

            }
        });

        playerSellOre = new TextButton("-", tableButtonStyle);
        playerSellOre.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                oreTradeAmount -= 1;
                refreshAuction();

            }
        });

        playerSellEnergy = new TextButton("-", tableButtonStyle);
        playerSellEnergy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                energyTradeAmount -= 1;
                refreshAuction();

            }
        });

        playerSellFood = new TextButton("-", tableButtonStyle);
        playerSellFood.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                foodTradeAmount -= 1;
                refreshAuction();

            }
        });

        tradePriceLabel = new Label("" + tradePrice, new Label.LabelStyle(tableFont.font(), Color.WHITE));
        pricePlus1 = new TextButton("+ 1", tableButtonStyle);
        pricePlus1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tradePrice += 1;
                refreshAuction();

            }
        });

        pricePlus10 = new TextButton("+ 10", tableButtonStyle);
        pricePlus10.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tradePrice += 10;
                refreshAuction();

            }
        });

        pricePlus100 = new TextButton("+ 100", tableButtonStyle);
        pricePlus100.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tradePrice += 100;
                refreshAuction();

            }
        });


        priceMinus1 = new TextButton("- 1", tableButtonStyle);
        priceMinus1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tradePrice -= 1;
                refreshAuction();

            }
        });

        priceMinus10 = new TextButton("- 10", tableButtonStyle);
        priceMinus10.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tradePrice -= 10;
                refreshAuction();

            }
        });

        priceMinus100 = new TextButton("- 100", tableButtonStyle);
        priceMinus100.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                tradePrice -= 100;
                refreshAuction();

            }
        });

        confirmSale = new TextButton("confirm", tableButtonStyle);
        confirmSale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Trade trade = new Trade(oreTradeAmount, energyTradeAmount, foodTradeAmount,
                        tradePrice, engine.currentPlayer(), otherPlayer.get(playerListPosition));
                engine.currentPlayer().setTrade(trade);
                engine.addTrade(trade);
                oreTradeAmount = 0;
                energyTradeAmount = 0;
                foodTradeAmount = 0;
                tradePrice = 0;
                refreshAuction();

            }
        });

        playerListPosition = 0;
        playerLabel = new Label("", new Label.LabelStyle(tableFont.font(), Color.WHITE));
        nextPlayerButton = new TextButton(">", tableButtonStyle);
        nextPlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                playerListPosition += 1;
                playerLabel.setText("Player " + otherPlayer.get(playerListPosition).getPlayerNumber());
                refreshAuction();
            }
        });
        prevPlayerButton = new TextButton("<", tableButtonStyle);
        prevPlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                playerListPosition -= 1;
                playerLabel.setText("Player " + otherPlayer.get(playerListPosition).getPlayerNumber());
                refreshAuction();
            }
        });
        refreshButtonAvailability();
        refreshPlayers();
        //Ensure that these buttons are disabled at the beginning of the game

    }

    /**
     * Builds the market's visual interface by populating it with labels and buttons
     * Once this method has finished executing, the market can be drawn to a stage like any other actor
     */
    private void constructInterface() {

        tableFont.setSize(36);
        drawer.toggleButton(marketButton, false, Color.GRAY);
        drawer.toggleButton(auctionButton, true, Color.WHITE);
        drawer.addTableRow(this, marketButton);
        this.add(auctionButton).left();
        //Add a heading to the market interface

        tableFont.setSize(24);

        this.row();
        this.add(new Label("Item", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padRight(90);
        this.add(new Label("Buy", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padRight(40);
        this.add(new Label("Sell", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padRight(20);
        //Visual guff

        this.row();
        this.add(new Label("Ore", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        this.add(buyOre).left();
        this.add(sellOre).left();
        //Add buttons for buying and selling ore to the market's visual framework
        //Note that the strings encoded by these TextButtons represent the market's current buying/selling prices for ore

        this.row();
        this.add(new Label("Food", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        this.add(buyFood).left();
        this.add(sellFood).left();
        //Add buttons for buying and selling food to the market's visual framework
        //Note that the strings encoded by these TextButtons represent the market's current buying/selling prices for food

        this.row();
        this.add(new Label("Energy", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        this.add(buyEnergy).left();
        this.add(sellEnergy).left();
        //Add buttons for buying and selling energy to the market's visual framework
        //Note that the strings encoded by these TextButtons represent the market's current buying/selling prices for energy

        this.row();
        this.add(new Label("Roboticons", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padBottom(15);
        this.add(buyRoboticon).left().padBottom(15);
        //Add button for buying Roboticons to the market's visual framework
        //Note that the string encoded by this TextButton represents the market's current buying price for Roboticons

        oreStockLabel = new Label("" + getOreStock(), new Label.LabelStyle(tableFont.font(), Color.WHITE));
        foodStockLabel = new Label("" + getFoodStock(), new Label.LabelStyle(tableFont.font(), Color.WHITE));
        energyStockLabel = new Label("" + getEnergyStock(), new Label.LabelStyle(tableFont.font(), Color.WHITE));
        roboticonStockLabel = new Label("" + getRoboticonStock(), new Label.LabelStyle(tableFont.font(), Color.WHITE));
        //Prepare new labels to encode resources' stock levels within the market
        //These will NOT be interactive, unlike the buttons declared earlier on

        this.row();
        this.add(new Label("Item", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padRight(90);
        this.add(new Label("Stock", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        //More visual guff!

        this.row();
        this.add(new Label("Ore", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        this.add(oreStockLabel).left();
        //Add label to encode current ore stocks to the market's visual framework

        this.row();
        this.add(new Label("Food", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        this.add(foodStockLabel).left();
        //Add label to encode current food stocks to the market's visual framework

        this.row();
        this.add(new Label("Energy", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        this.add(energyStockLabel).left();
        //Add label to encode current energy stocks to the market's visual framework

        this.row();
        this.add(new Label("Roboticons", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();
        this.add(roboticonStockLabel).left();
        //Add label to encode current Roboticon stocks to the market's visual framework
    }


    /**
     * Builds the auction's visual interface by populating it with labels and buttons
     * Once this method has finished executing, the market can be drawn to a stage like any other actor
     */
    //new for assessment 3
    private void constructAuctionInterface() {
        tableFont.setSize(36);
        drawer.toggleButton(auctionButton, false, Color.GRAY);
        drawer.toggleButton(marketButton, true, Color.WHITE);
        drawer.addTableRow(this, marketButton);
        this.add(auctionButton).left();
        //Add a heading to the market/ auction interface

        tableFont.setSize(24);

        this.row();
        this.add(new Label("Item", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padRight(90);
        this.add(new Label("More", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padRight(30);
        this.add(new Label("Less", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left().padRight(20);
        //Visual guff

        this.row();
        oreTradeAmount = 0;
        this.add(oreTradeLabel).left();
        this.add(playerBuyOre).left().padLeft(10);
        this.add(playerSellOre).left().padLeft(10);

        this.row();
        energyTradeAmount = 0;
        this.add(energyTradeLabel).left();
        this.add(playerBuyEnergy).left().padLeft(10);
        this.add(playerSellEnergy).left().padLeft(10);

        this.row();
        foodTradeAmount = 0;
        this.add(foodTradeLabel).left().padBottom(15);
        this.add(playerBuyFood).left().padLeft(10).padBottom(15);
        this.add(playerSellFood).left().padLeft(10).padBottom(15);

        this.row();
        tradePrice = 0;
        this.add(new Label("Price:", new Label.LabelStyle(tableFont.font(), Color.WHITE))).left();


        this.row();

        this.add();
        this.add(pricePlus1).left();
        this.add(priceMinus1).left();

        this.row();

        this.add(tradePriceLabel);
        this.add(pricePlus10).left();
        this.add(priceMinus10).left();

        this.row();

        this.add();
        this.add(pricePlus100).left();
        this.add(priceMinus100).left();

        this.row();
        this.add(prevPlayerButton);
        this.add(playerLabel);
        this.add(nextPlayerButton);
        this.row();
        this.add(confirmSale).left();
    }

    /**
     * Returns the number of Roboticons currently held in the market
     *
     * @return int The number of Roboticons currently held in the market
     */
    public int getRoboticonStock() {
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
        roboticonStockLabel.setText("" + getRoboticonStock());
    }

    /**
     * Getter for RoboticonBuyPrice
     *
     * @return this.RoboticonBuyPrice is integer roboticon buy price value
     */
    public int getRoboticonBuyPrice() {
        return this.RoboticonBuyPrice;
    }

    /**
     * Setter for RoboticonBuyPrice
     *
     * @param NewRoboticonBuyPrice integer value that RoboticonBuyPrice is assigned to.
     */
    public void setRoboticonBuyPrice(int NewRoboticonBuyPrice) {
        this.RoboticonBuyPrice = NewRoboticonBuyPrice;
        buyRoboticon.setText("" + getRoboticonBuyPrice());
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
        sellOre.setText("" + getOreSellPrice());
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
        buyOre.setText("" + getOreBuyPrice());
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
        sellFood.setText("" + getFoodSellPrice());
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
        this.FoodBuyPrice = NewFoodBuyPrice;
        buyFood.setText("" + getFoodBuyPrice());
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
        sellEnergy.setText("" + getEnergySellPrice());
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
        buyEnergy.setText("" + getEnergyBuyPrice());
    }


    /**
     * A method that allows buying resources from the market.
     * <p>
     * Depending on what type of resources is passed ("ore", "food" or "energy") method checks whether it is sufficient
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
    public Player buy(String Stock_Type, int Quantity, Player Player) throws Exception {
        if ("ore".equals(Stock_Type)) {
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
                    oreStockLabel.setText("" + getOreStock());
                    buyOre.setText("-" + getOreBuyPrice());
                } else {
                    throw new Exception("Insufficient money");
                }
            } else {
                throw new Exception("Insufficient resources");
            }
        } else if ("food".equals(Stock_Type)) {
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
                    foodStockLabel.setText("" + getFoodStock());
                    buyFood.setText("-" + getFoodBuyPrice());
                } else {
                    throw new Exception("Insufficient money");
                }

            } else {
                throw new Exception("Insufficient resources");
            }
        } else if ("energy".equals(Stock_Type)) {
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
                    energyStockLabel.setText("" + getEnergyStock());
                    buyEnergy.setText("-" + getEnergyBuyPrice());
                } else {
                    throw new Exception("Insufficient money");
                }
            } else {
                throw new Exception("Insufficient resources");
            }
        } else if ("roboticon".equals(Stock_Type)) {
            if (RoboticonStock > 0) {
                if (Player.getResource(ResourceType.MONEY) >= RoboticonBuyPrice) {
                    RoboticonStock -= 1;
                    Player.setResource(ResourceType.MONEY, Player.getResource(ResourceType.MONEY) - RoboticonBuyPrice);
                    RoboticonBuyPrice += 5;
                    Player.increaseRoboticonInventory();

                    roboticonStockLabel.setText("" + this.getRoboticonStock());
                    buyRoboticon.setText("-" + getRoboticonBuyPrice());
                } else {
                    throw new Exception("Insufficient money");
                }
            } else {
                throw new Exception("No available Roboticons");
            }
        } else {

            throw new Exception("Wrong Stock_Type passed");
        }
        return Player;


    }


    /**
     * A method that allows selling resources to the market.
     * <p>
     * Depending on what type of resources it is passed ("ore", "food" or "energy") method checks whether the Player has
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
        if ("ore".equals(Stock_Type)) {
            int playersOre = Player.getResource(ResourceType.ORE);
            if (playersOre >= Quantity) {
                OreStock += Quantity;
                playersMoney += Quantity * OreSellPrice;
                Player.setResource(ResourceType.MONEY, playersMoney);
                playersOre -= Quantity;
                Player.setResource(ResourceType.ORE, playersOre);

                OreBuyPrice = calculateNewCost(OreStock, "buy");
                OreSellPrice = calculateNewCost(OreStock, "sell");
                oreStockLabel.setText("" + getOreStock());
                sellOre.setText("+" + getOreSellPrice());
            } else {
                throw new Exception("Insufficient resources");

            }
        } else if ("food".equals(Stock_Type)) {
            int playersFood = Player.getResource(ResourceType.FOOD);
            if (playersFood >= Quantity) {

                FoodStock += Quantity;
                playersMoney += Quantity * FoodSellPrice;
                Player.setResource(ResourceType.MONEY, playersMoney);
                playersFood -= Quantity;
                Player.setResource(ResourceType.FOOD, playersFood);
                FoodBuyPrice = calculateNewCost(FoodStock, "buy");
                FoodSellPrice = calculateNewCost(FoodStock, "sell");
                foodStockLabel.setText("" + getFoodStock());
                sellFood.setText("+" + getFoodSellPrice());
            } else {
                throw new Exception("Insufficient resources");
            }
        } else if ("energy".equals(Stock_Type)) {
            int playersEnergy = Player.getResource(ResourceType.ENERGY);
            if (playersEnergy >= Quantity) {
                EnergyStock += Quantity;
                playersMoney += Quantity * EnergySellPrice;
                Player.setResource(ResourceType.MONEY, playersMoney);
                playersEnergy -= Quantity;
                Player.setResource(ResourceType.ENERGY, playersEnergy);
                EnergyBuyPrice = calculateNewCost(EnergyStock, "buy");
                EnergySellPrice = calculateNewCost(EnergyStock, "sell");
                energyStockLabel.setText("" + getEnergyStock());
                sellEnergy.setText("+" + getEnergySellPrice());
            } else {
                throw new Exception("Insufficient resources");
            }

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
     * Enables/disables the market's purchase/sale buttons and updates their colours to reflect the player's current
     * amount of money, the game's current phase and the player's inventory
     * <p>
     * GRAY: Cannot buy/sell resource on the current phase
     * GREEN: Can buy/sell resource
     * RED: Cannot buy/sell resource due to a lack of money or stock
     */
    public void refreshButtonAvailability() {
        if (engine.phase() == 2) {
            if (engine.currentPlayer().getResource(ResourceType.MONEY) >= RoboticonBuyPrice && RoboticonStock > 0) {
                drawer.toggleButton(buyRoboticon, true, Color.GREEN);
            } else {
                drawer.toggleButton(buyRoboticon, false, Color.RED);
            }
            //If the game is in phase 2, enable the roboticon purchase button ONLY (and only if the current player can
            //afford one while one is in stock)

            drawer.toggleButton(buyOre, false, Color.GRAY);
            drawer.toggleButton(buyFood, false, Color.GRAY);
            drawer.toggleButton(buyEnergy, false, Color.GRAY);
            //Disable all of the market's other functions in phase 2
        } else if (engine.phase() == 5) {
            if (engine.currentPlayer().getResource(ResourceType.MONEY) >= OreBuyPrice && OreStock > 0) {
                drawer.toggleButton(buyOre, true, Color.GREEN);
            } else {
                drawer.toggleButton(buyOre, false, Color.RED);
            }
            //Conditionally enable the ore purchase button

            if (engine.currentPlayer().getResource(ResourceType.MONEY) >= EnergyBuyPrice && EnergyStock > 0) {
                drawer.toggleButton(buyEnergy, true, Color.GREEN);
            } else {
                drawer.toggleButton(buyEnergy, false, Color.RED);
            }
            //Conditionally enable the energy purchase button

            if (engine.currentPlayer().getResource(ResourceType.MONEY) >= FoodBuyPrice && FoodStock > 0) {
                drawer.toggleButton(buyFood, true, Color.GREEN);
            } else {
                drawer.toggleButton(buyFood, false, Color.RED);
            }
            //Conditionally enable the food purchase button

            drawer.toggleButton(buyRoboticon, false, Color.GRAY);
            //Disable the roboticon purchase button in round 5

            if (engine.currentPlayer().getResource(ResourceType.ORE) > 0) {
                drawer.toggleButton(sellOre, true, Color.GREEN);
            } else {
                drawer.toggleButton(sellOre, false, Color.RED);
            }
            //Conditionally enable the ore sale button

            if (engine.currentPlayer().getResource(ResourceType.ENERGY) > 0) {
                drawer.toggleButton(sellEnergy, true, Color.GREEN);
            } else {
                drawer.toggleButton(sellEnergy, false, Color.RED);
            }
            //Conditionally enable the energy sale button

            if (engine.currentPlayer().getResource(ResourceType.FOOD) > 0) {
                drawer.toggleButton(sellFood, true, Color.GREEN);
            } else {
                drawer.toggleButton(sellFood, false, Color.RED);
            }
            //Conditionally enable the food sale button
        } else {
            drawer.toggleButton(buyOre, false, Color.GRAY);
            drawer.toggleButton(buyFood, false, Color.GRAY);
            drawer.toggleButton(buyEnergy, false, Color.GRAY);

            drawer.toggleButton(buyRoboticon, false, Color.GRAY);

            drawer.toggleButton(sellOre, false, Color.GRAY);
            drawer.toggleButton(sellFood, false, Color.GRAY);
            drawer.toggleButton(sellEnergy, false, Color.GRAY);
            //Disable the entire market if the game is in one of phases 1, 3 and 4
        }
    }

    /**
     * Enables and disables various buttons in the auction based on the players inventory and the value
     * of the current resources and price of the current trade
     */
    //new for assessment 3
    public void refreshAuction() {
        drawer.toggleButton(playerBuyOre, false, Color.GRAY);
        drawer.toggleButton(playerSellOre, false, Color.GRAY);
        drawer.toggleButton(playerBuyFood, false, Color.GRAY);
        drawer.toggleButton(playerSellFood, false, Color.GRAY);
        drawer.toggleButton(playerBuyEnergy, false, Color.GRAY);
        drawer.toggleButton(playerSellEnergy, false, Color.GRAY);
        drawer.toggleButton(pricePlus1, false, Color.GRAY);
        drawer.toggleButton(pricePlus10, false, Color.GRAY);
        drawer.toggleButton(pricePlus100, false, Color.GRAY);
        drawer.toggleButton(priceMinus1, false, Color.GRAY);
        drawer.toggleButton(priceMinus10, false, Color.GRAY);
        drawer.toggleButton(priceMinus100, false, Color.GRAY);
        drawer.toggleButton(confirmSale, false, Color.GRAY);
        drawer.toggleButton(nextPlayerButton, false, Color.GRAY);
        drawer.toggleButton(prevPlayerButton, false, Color.GRAY);
        tradePriceLabel.setText("" + tradePrice);
        oreTradeLabel.setText("Ore:        " + oreTradeAmount);
        foodTradeLabel.setText("Food:      " + foodTradeAmount);
        energyTradeLabel.setText("Energy:   " + energyTradeAmount);
        if (engine.phase() == 5) {
            drawer.toggleButton(pricePlus1, true, Color.WHITE);
            drawer.toggleButton(pricePlus10, true, Color.WHITE);
            drawer.toggleButton(pricePlus100, true, Color.WHITE);
            drawer.toggleButton(confirmSale, true, Color.RED);
            if (tradePrice >= 100) {
                drawer.toggleButton(priceMinus1, true, Color.WHITE);
                drawer.toggleButton(priceMinus10, true, Color.WHITE);
                drawer.toggleButton(priceMinus100, true, Color.WHITE);
            } else if (tradePrice >= 10) {
                drawer.toggleButton(priceMinus1, true, Color.WHITE);
                drawer.toggleButton(priceMinus10, true, Color.WHITE);
            } else if (tradePrice >= 1) {
                drawer.toggleButton(priceMinus1, true, Color.WHITE);
            }

            if (engine.currentPlayer().getResource(ResourceType.ORE) > oreTradeAmount) {
                drawer.toggleButton(playerBuyOre, true, Color.WHITE);
            }
            if (engine.currentPlayer().getResource(ResourceType.ENERGY) > energyTradeAmount) {
                drawer.toggleButton(playerBuyEnergy, true, Color.WHITE);
            }
            if (engine.currentPlayer().getResource(ResourceType.FOOD) > foodTradeAmount) {
                drawer.toggleButton(playerBuyFood, true, Color.WHITE);
            }
            if (otherPlayer.size - 1 > playerListPosition) {
                drawer.toggleButton(nextPlayerButton, true, Color.WHITE);
            }
            if (playerListPosition > 0) {
                drawer.toggleButton(prevPlayerButton, true, Color.WHITE);
            }
            if (oreTradeAmount > 0) drawer.toggleButton(playerSellOre, true, Color.WHITE);
            if (energyTradeAmount > 0) drawer.toggleButton(playerSellEnergy, true, Color.WHITE);
            if (foodTradeAmount > 0) drawer.toggleButton(playerSellFood, true, Color.WHITE);
            if (oreTradeAmount > 0 || energyTradeAmount > 0 || foodTradeAmount > 0 || tradePrice > 0)
                drawer.toggleButton(confirmSale, true, Color.GREEN);

        }
    }

    /**
     * updates the other player list so all players except the current player are in it
     */
    //all below is new for assessment 3
    public void refreshPlayers() {
        otherPlayer = new Array<Player>();
        for (Player player : engine.players()) {
            if (player != null && engine.currentPlayer() != player) {
                otherPlayer.add(player);
            }
        }
        playerLabel.setText("Player " + otherPlayer.get(0).getPlayerNumber());
    }


    public void setPlayerListPosition(int i) {
        playerListPosition = i;

    }

    /**
     * allows the market to get it's inventory of roboticons up to 10 so long as it has
     * at least 10 ore, each roboticon costs 3 ore
     */
    public void produceRoboticon() {
        while (this.OreStock > 10 && RoboticonStock < 10) {
            OreStock -= 3;
            oreStockLabel.setText("" + getOreStock());
            RoboticonStock += 1;
            roboticonStockLabel.setText("" + RoboticonStock);
            refreshButtonAvailability();

        }
    }
}




