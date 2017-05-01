/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game.screens.tables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import drtn.game.entity.Player;
import drtn.game.enums.ResourceType;
import drtn.game.util.TTFont;

import java.util.ArrayList;

/**
 * Created by Joseph on 28/02/2017.
 */
public class MarketInterfaceTable extends Table {

    /**
     * Determines whether the interface is accessing the game's market or auction house
     */
    private boolean market;

    /**
     * Table holding the buttons facilitating switches between the market's interface and the auction house's interface
     */
    private Table navigationTable;

    private TextButton.TextButtonStyle regularButtonStyle;
    private TextButton.TextButtonStyle lightButtonStyle;

    /**
     * Opens the market's interface when clicked on
     */
    private TextButton marketButton;

    /**
     * Opens the auction house's interface when clicked on
     */
    private TextButton auctionButton;

    /**
     * Attempts to purchase one unit of ore for the current player when clicked on
     */
    private TextButton buyOreButton;

    /**
     * Attempts to purchase one unit of energy for the current player when clicked on
     */
    private TextButton buyEnergyButton;

    /**
     * Attempts to purchase one unit of food for the current player when clicked on
     */
    private TextButton buyFoodButton;

    /**
     * Attempts to purchase one roboticon for the current player when clicked on
     */
    private TextButton buyRoboticonButton;

    /**
     * Attempts to sell one of the active player's ore stocks to the market when clicked on
     */
    private TextButton sellOreButton;

    /**
     * Attempts to sell one of the active player's energy stocks to the market when clicked on
     */
    private TextButton sellEnergyButton;

    /**
     * Attempts to sell one of the active player's food stocks to the market when clicked on
     */
    private TextButton sellFoodButton;

    /**
     * Increases the amount of ore to be offered in the pending trade request
     */
    private TextButton auctionAddOre;

    /**
     * Increases the amount of energy to be offered in the pending trade request
     */
    private TextButton auctionAddEnergy;

    /**
     * Increases the amount of food to be offered in the pending trade request
     */
    private TextButton auctionAddFood;

    /**
     * Decreases the amount of ore to be offered in the pending trade request
     */
    private TextButton auctionSellOre;

    /**
     * Decreases the amount of energy to be offered in the pending trade request
     */
    private TextButton auctionSellEnergy;

    /**
     * Decreases the amount of food to be offered in the pending trade request
     */
    private TextButton auctionSellFood;

    /**
     * Adds 1 unit of currency to the price of the pending trade request
     */
    private TextButton pricePlus1;

    /**
     * Adds 10 units of currency to the price of the pending trade request
     */
    private TextButton pricePlus10;

    /**
     * Adds 100 units of currency to the price of the pending trade request
     */
    private TextButton pricePlus100;

    /**
     * Subtracts 1 unit of currency from the price of the pending trade request
     */
    private TextButton priceMinus1;

    /**
     * Subtracts 10 units of currency from the price of the pending trade request
     */
    private TextButton priceMinus10;

    /**
     * Subtracts 100 units of currency from the price of the pending trade request
     */
    private TextButton priceMinus100;

    /**
     * Confirms and sends the pending trade offer to the offer's intended recipient
     */
    private TextButton confirmOffer;

    /**
     * Advances through the list of possible recipients for the pending trade request
     */
    private TextButton nextPlayerButton;

    /**
     * Retreats through the list of possible recipients for the pending trade request
     */
    private TextButton prevPlayerButton;

    /**
     * Label encoding the amount of ore stocks currently held in the market
     */
    private Label oreStockLabel;

    /**
     * Label encoding the amount of food stocks currently held in the market
     */
    private Label foodStockLabel;

    /**
     * Label encoding the amount of energy stocks currently held in the market
     */
    private Label energyStockLabel;

    /**
     * Label encoding the amount of roboticons currently held in the market
     */
    private Label roboticonStockLabel;

    /**
     * Label encoding the amount of ore to be offered in the pending trade request
     */
    private Label oreTradeAmountLabel;

    /**
     * Label encoding the amount of energy to be offered in the pending trade request
     */
    private Label energyTradeAmountLabel;

    /**
     * Label encoding the amount of food to be offered in the pending trade request
     */
    private Label foodTradeAmountLabel;

    /**
     * Label encoding the amount of money to be demanded in the pending trade request
     */
    private Label tradePriceLabel;

    /**
     * Label representing the intended recipient of the pending trade request
     */
    private Label playerLabel;

    private TTFont regularFont;
    private TTFont lightFont;

    /**
     * Holds the amount of ore to be offered in the pending trade request
     */
    private int oreTradeAmount;

    /**
     * Holds the amount of energy to be offered in the pending trade request
     */
    private int energyTradeAmount;

    /**
     * Holds the amount of food to be offered in the pending trade request
     */
    private int foodTradeAmount;

    /**
     * Tracks the currently-selected position in the list of potential trade offer recipients
     */
    private int playerListPosition;

    /**
     * Holds the amount of money to be demanded in the pending trade request
     */
    private int tradePrice;

    /**
     * Array holding all of the valid potential recipients of the pending trade request
     */
    private ArrayList<Player> otherPlayer;

    /**
     * Constructs the tabular interfaces of the game's market and auction house
     * Displays the market's interface first by default
     */
    public MarketInterfaceTable() {
        regularFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        lightFont = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);
        //Import the fonts that will encode the text to be displayed in both interfaces

        regularButtonStyle = new TextButton.TextButtonStyle();
        regularButtonStyle.font = regularFont.font();
        regularButtonStyle.fontColor = Color.WHITE;
        regularButtonStyle.pressedOffsetX = 1;
        regularButtonStyle.pressedOffsetY = -1;

        lightButtonStyle = new TextButton.TextButtonStyle();
        lightButtonStyle.font = lightFont.font();
        lightButtonStyle.fontColor = Color.WHITE;
        lightButtonStyle.pressedOffsetX = 1;
        lightButtonStyle.pressedOffsetY = -1;
        //Define the visual parameters of the interfaces' buttons

        marketButton = new TextButton("MARKET", regularButtonStyle);
        marketButton.getLabel().setColor(Color.GREEN);
        marketButton.setTouchable(Touchable.disabled);
        marketButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchInterface();
            }
        });

        auctionButton = new TextButton("AUCTION", regularButtonStyle);
        auctionButton.getLabel().setColor(Color.BLACK);
        auctionButton.setTouchable(Touchable.enabled);
        auctionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchInterface();
            }
        });
        //Set up the buttons allowing players to switch between viewing the market's interface and the auction house's
        //interface

        constructMarketElements();
        constructAuctionElements();
        //Construct both interfaces

        navigationTable = new Table();
        navigationTable.add(marketButton).width(100);
        navigationTable.add(auctionButton).width(100);
        add(navigationTable).colspan(4).padBottom(10);
        row();
        //Add buttons to switch between the market and auction interfaces

        market = true;
        //Note that the table is currently populated with the elements of the market's interface

        showMarketInterface();
        //Populate the table with the components of the market's interface
    }

    /**
     * Switches between showing the market's interface and showing the auction house's interface upon being called
     */
    public void switchInterface() {
        market = !market;
        //Make a note of the switch

        clearChildren();
        //Clear the entire table

        add(navigationTable).colspan(4).padBottom(10);
        row();
        //Re-integrate the buttons allowing players to switch between the market's interface and the auction house's interface

        if (market) {
            marketButton.getLabel().setColor(Color.GREEN);
            marketButton.setTouchable(Touchable.disabled);

            auctionButton.getLabel().setColor(Color.BLACK);
            auctionButton.setTouchable(Touchable.enabled);

            showMarketInterface();
        } else {
            marketButton.getLabel().setColor(Color.BLACK);
            marketButton.setTouchable(Touchable.enabled);

            auctionButton.getLabel().setColor(Color.GREEN);
            auctionButton.setTouchable(Touchable.disabled);

            showAuctionInterface();
        }
        //Populate the rest of the table with the required components based on what was previously present inside it
    }

    /**
     * Constructs the components of the market's interface
     */
    private void constructMarketElements() {
        buyOreButton = new TextButton("", lightButtonStyle);
        buyEnergyButton = new TextButton("", lightButtonStyle);
        buyFoodButton = new TextButton("", lightButtonStyle);
        buyRoboticonButton = new TextButton("", lightButtonStyle);
        sellOreButton = new TextButton("", lightButtonStyle);
        sellEnergyButton = new TextButton("", lightButtonStyle);
        sellFoodButton = new TextButton("", lightButtonStyle);

        oreStockLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        energyStockLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        foodStockLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        roboticonStockLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));
    }

    /**
     * Constructs the components of the auction house's interface
     */
    private void constructAuctionElements() {
        oreTradeAmountLabel = new Label("0", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        energyTradeAmountLabel = new Label("0", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        foodTradeAmountLabel = new Label("0", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        tradePriceLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        playerLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        auctionAddOre = new TextButton("+", lightButtonStyle);
        auctionAddEnergy = new TextButton("+", lightButtonStyle);
        auctionAddFood = new TextButton("+", lightButtonStyle);
        auctionSellOre = new TextButton("-", lightButtonStyle);
        auctionSellEnergy = new TextButton("-", lightButtonStyle);
        auctionSellFood = new TextButton("-", lightButtonStyle);

        pricePlus1 = new TextButton("+ 1", lightButtonStyle);
        pricePlus10 = new TextButton("+ 10", lightButtonStyle);
        pricePlus100 = new TextButton("+ 100", lightButtonStyle);

        priceMinus1 = new TextButton("- 1", lightButtonStyle);
        priceMinus10 = new TextButton("- 10", lightButtonStyle);
        priceMinus100 = new TextButton("- 100", lightButtonStyle);

        confirmOffer = new TextButton("Send Offer to This Player", regularButtonStyle);

        nextPlayerButton = new TextButton(">", lightButtonStyle);
        nextPlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerListPosition < otherPlayer.size() - 1) {
                    playerListPosition += 1;
                } else {
                    playerListPosition = 0;
                }
                playerLabel.setText(otherPlayer.get(playerListPosition).getCollege().getName());
                //Set the next player in the otherPlayer array to be the pending trade request's intended recipient

                setTradeAmount(ResourceType.ORE, 0);
                setTradeAmount(ResourceType.ENERGY, 0);
                setTradeAmount(ResourceType.FOOD, 0);
                setTradePrice(0);
                //Reset the auction house's interface to acknowledge the newly-selected recipient's funds

                toggleAuctionConfirmationButton(false, Color.RED);
            }
        });

        prevPlayerButton = new TextButton("<", lightButtonStyle);
        prevPlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerListPosition > 0) {
                    playerListPosition -= 1;
                } else {
                    playerListPosition = otherPlayer.size() - 1;
                }
                playerLabel.setText(otherPlayer.get(playerListPosition).getCollege().getName());
                //Set the previous player in the otherPlayer array to be the pending trade request's intended recipient

                setTradeAmount(ResourceType.ORE, 0);
                setTradeAmount(ResourceType.ENERGY, 0);
                setTradeAmount(ResourceType.FOOD, 0);
                setTradePrice(0);
                //Reset the auction house's interface to acknowledge the newly-selected recipient's funds

                toggleAuctionConfirmationButton(false, Color.RED);
            }
        });
    }

    /**
     * Builds the market's visual interface by populating it with labels and buttons
     * Once this method has finished executing, the market's interface can be drawn to a stage like any other
     * set of actors
     */
    private void showMarketInterface() {
        add(new Label("Item", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().width(120);
        add(new Label("Buy", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().width(75);
        add(new Label("Sell", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().width(35);
        row();
        add(new Label("Ore", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left();
        add(buyOreButton).left();
        add(sellOreButton).left();
        row();
        add(new Label("Energy", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left();
        add(buyEnergyButton).left();
        add(sellEnergyButton).left();
        row();
        add(new Label("Food", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left();
        add(buyFoodButton).left();
        add(sellFoodButton).left();
        row();
        add(new Label("Roboticons", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left().top().padBottom(10);
        add(buyRoboticonButton).left().top().padBottom(10);
        row();
        add(new Label("Item", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(new Label("Stock", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        row();
        add(new Label("Ore", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left();
        add(oreStockLabel).left();
        row();
        add(new Label("Energy", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left();
        add(energyStockLabel).left();
        row();
        add(new Label("Food", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left();
        add(foodStockLabel).left();
        row();
        add(new Label("Roboticons", new Label.LabelStyle(lightFont.font(), Color.WHITE))).left();
        add(roboticonStockLabel).left();
    }

    /**
     * Builds the auction house's visual interface by populating it with labels and buttons
     * Once this method has finished executing, the market's interface can be drawn to a stage like any other
     * set of actors
     */
    private void showAuctionInterface() {
        add(new Label("Item", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(40);
        add(new Label("#", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20);
        add(new Label("More", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20);
        add(new Label("Less", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20);

        row();
        add(new Label("Ore", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(oreTradeAmountLabel).left();
        add(auctionAddOre).left();
        add(auctionSellOre).left();

        row();
        add(new Label("Energy", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(energyTradeAmountLabel).left();
        add(auctionAddEnergy).left();
        add(auctionSellEnergy).left();

        row();
        add(new Label("Food", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(foodTradeAmountLabel).left();
        add(auctionAddFood).left();
        add(auctionSellFood).left();

        row();
        add(new Label("Price", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().colspan(2).padTop(15);
        add(new Label("More", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20).padTop(15);
        add(new Label("Less", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20).padTop(15);

        row();

        add().colspan(2);
        add(pricePlus1).left();
        add(priceMinus1).left();

        row();

        add(tradePriceLabel).colspan(2);
        add(pricePlus10).left();
        add(priceMinus10).left();

        row();

        add().colspan(2);
        add(pricePlus100).left();
        add(priceMinus100).left();

        row();
        Table tradeTargetSelectionTable = new Table();
        tradeTargetSelectionTable.add(prevPlayerButton);
        playerLabel.setAlignment(Align.center);
        tradeTargetSelectionTable.add(playerLabel).width(100);
        tradeTargetSelectionTable.add(nextPlayerButton);
        add(tradeTargetSelectionTable).colspan(4).padTop(15);
        row();
        add(confirmOffer).colspan(4);
    }

    /**
     * Method allowing other classes to set the functions of the market interface's various purchasing/selling buttons
     *
     * @param resource The type of resource that is to be bought or sold when clicking on the targeted button
     * @param buy Denotes whether to target the button that buys a particular resource for the active player or the
     *            one that would sell it for them instead
     * @param event Object containing the method to be executed when clicking on the targeted button
     */
    public void setMarketButtonFunction(ResourceType resource, boolean buy, ChangeListener event) {
        if (buy) {
            switch (resource) {
                case ORE:
                    buyOreButton.addListener(event);
                    break;
                case ENERGY:
                    buyEnergyButton.addListener(event);
                    break;
                case FOOD:
                    buyFoodButton.addListener(event);
                    break;
                case ROBOTICON:
                    buyRoboticonButton.addListener(event);
                    break;
            }
        } else {
            switch (resource) {
                case ORE:
                    sellOreButton.addListener(event);
                    break;
                case ENERGY:
                    sellEnergyButton.addListener(event);
                    break;
                case FOOD:
                    sellFoodButton.addListener(event);
                    break;
            }
        }
    }

    /**
     * Method allowing other classes to set the functions of the auction-house interface's various quantity-adjustment
     * buttons
     *
     * @param resource The type of resource that is to be varied in the pending trade request through the targeted
     *                 button
     * @param add Denotes whether to target the button that reduces the amount of a particular resource to be offered
     *            or the one that increases it instead
     * @param event Object containing the method to be executed when clicking on the targeted button
     */
    public void setAuctionQuantityButtonFunction(ResourceType resource, boolean add, ChangeListener event) {
        if (add) {
            switch (resource) {
                case ORE:
                    auctionAddOre.addListener(event);
                    break;
                case ENERGY:
                    auctionAddEnergy.addListener(event);
                    break;
                case FOOD:
                    auctionAddFood.addListener(event);
                    break;
            }
        } else {
                switch (resource) {
                    case ORE:
                        auctionSellOre.addListener(event);
                        break;
                    case ENERGY:
                        auctionSellEnergy.addListener(event);
                        break;
                    case FOOD:
                        auctionSellFood.addListener(event);
                        break;
                }
            }
        }

    /**
     * Method allowing other classes to set the functions of the auction-house interface's various price-adjustment
     * buttons
     *
     * @param figures The number of digits in the amount of money that the targeted button should vary the pending
     *                trade offer's price by [2 denotes values of +/- 10; 3 denotes values of +/- 100; etc.]
     * @param add Denotes whether to target a button that reduces the amount of money to be offered or one that
     *            increases it instead
     * @param event Object containing the method to be executed when clicking on the targeted button
     */
    public void setAuctionPriceButtonFunction(int figures, boolean add, ChangeListener event) {
        if (add) {
            switch (figures) {
                case 1:
                    pricePlus1.addListener(event);
                    break;
                case 2:
                    pricePlus10.addListener(event);
                    break;
                case 3:
                    pricePlus100.addListener(event);
                    break;
            }
        } else {
            switch (figures) {
                case 1:
                    priceMinus1.addListener(event);
                    break;
                case 2:
                    priceMinus10.addListener(event);
                    break;
                case 3:
                    priceMinus100.addListener(event);
                    break;
            }
        }
    }

    /**
     * Method allowing other classes to set the labels of the market interface's various price-adjustment buttons
     *
     * @param resource The type of resource that is to be bought or sold when clicking on the targeted button
     * @param buy Denotes whether to target the button that buys a particular resource for the active player or the
     *            one that would sell it for them instead
     * @param text The text to be displayed on the surface of the targeted button
     */
    public void setMarketButtonText(ResourceType resource, boolean buy, String text) {
        if (buy) {
            switch (resource) {
                case ORE:
                    buyOreButton.setText(text);
                    break;
                case ENERGY:
                    buyEnergyButton.setText(text);
                    break;
                case FOOD:
                    buyFoodButton.setText(text);
                    break;
                case ROBOTICON:
                    buyRoboticonButton.setText(text);
                    break;
            }
        } else {
            switch (resource) {
                case ORE:
                    sellOreButton.setText(text);
                    break;
                case ENERGY:
                    sellEnergyButton.setText(text);
                    break;
                case FOOD:
                    sellFoodButton.setText(text);
                    break;
            }
        }
    }

    /**
     * Method allowing other classes to set the labels of the market interface's various price-adjustment buttons
     *
     * @param resource The type of resource that is to be bought or sold when clicking on the targeted button
     * @param buy Denotes whether to target the button that buys a particular resource for the active player or the
     *            one that would sell it for them instead
     * @param price The purchasing/selling cost to be displayed on the surface of the targeted button
     */
    public void setMarketButtonText(ResourceType resource, boolean buy, int price) {
        setMarketButtonText(resource, buy, String.valueOf(price));
    }

    /**
     * Method allowing other classes to enable or disable the market interface's various price-adjustment buttons
     *
     * @param resource The type of resource that is to be bought or sold when clicking on the targeted button
     * @param buy Denotes whether to target the button that buys a particular resource for the active player or the
     *            one that would sell it for them instead
     * @param enabled Determines whether the targeted button is to be enabled or disabled
     * @param color Sets the new colour of the targeted button's surface-level label
     */
    public void toggleMarketButton(ResourceType resource, boolean buy, boolean enabled, Color color) {
        if (buy) {
            switch (resource) {
                case ORE:
                    buyOreButton.getLabel().setColor(color);
                    buyOreButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case ENERGY:
                    buyEnergyButton.getLabel().setColor(color);
                    buyEnergyButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case FOOD:
                    buyFoodButton.getLabel().setColor(color);
                    buyFoodButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case ROBOTICON:
                    buyRoboticonButton.getLabel().setColor(color);
                    buyRoboticonButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        } else {
            switch (resource) {
                case ORE:
                    sellOreButton.getLabel().setColor(color);
                    sellOreButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case ENERGY:
                    sellEnergyButton.getLabel().setColor(color);
                    sellEnergyButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case FOOD:
                    sellFoodButton.getLabel().setColor(color);
                    sellFoodButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        }
    }

    /**
     * Method allowing other classes to enable or disable the auction-house interface's various quantity-adjustment
     * buttons
     *
     * @param resource The type of resource that is to be varied in the pending trade request through the targeted
     *                 button
     * @param add Denotes whether to target the button that reduces the amount of a particular resource to be offered
     *            or the one that increases it instead
     * @param enabled Determines whether the targeted button is to be enabled or disabled
     * @param color Sets the new colour of the targeted button's surface-level label
     */
    public void toggleAuctionQuantityButton(ResourceType resource, boolean add, boolean enabled, Color color) {
        if (add) {
            switch (resource) {
                case ORE:
                    auctionAddOre.getLabel().setColor(color);
                    auctionAddOre.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case ENERGY:
                    auctionAddEnergy.getLabel().setColor(color);
                    auctionAddEnergy.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case FOOD:
                    auctionAddFood.getLabel().setColor(color);
                    auctionAddFood.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        } else {
            switch (resource) {
                case ORE:
                    auctionSellOre.getLabel().setColor(color);
                    auctionSellOre.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case ENERGY:
                    auctionSellEnergy.getLabel().setColor(color);
                    auctionSellEnergy.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case FOOD:
                    auctionSellFood.getLabel().setColor(color);
                    auctionSellFood.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        }
    }

    /**
     * Method allowing other classes to enable or disable the auction-house interface's various price-adjustment
     * buttons
     *
     * @param figures The number of digits in the amount of money that the targeted button should vary the pending
     *                trade offer's price by [2 denotes values of +/- 10; 3 denotes values of +/- 100; etc.]
     * @param add Denotes whether to target a button that reduces the amount of money to be offered or one that
     *            increases it instead
     * @param enabled Determines whether the targeted button is to be enabled or disabled
     * @param color Sets the new colour of the targeted button's surface-level label
     */
    public void toggleAuctionPriceButton(int figures, boolean add, boolean enabled, Color color) {
        if (add) {
            switch (figures) {
                case 1:
                    pricePlus1.getLabel().setColor(color);
                    pricePlus1.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case 2:
                    pricePlus10.getLabel().setColor(color);
                    pricePlus10.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case 3:
                    pricePlus100.getLabel().setColor(color);
                    pricePlus100.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        } else {
            switch (figures) {
                case 1:
                    priceMinus1.getLabel().setColor(color);
                    priceMinus1.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case 2:
                    priceMinus10.getLabel().setColor(color);
                    priceMinus10.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case 3:
                    priceMinus100.getLabel().setColor(color);
                    priceMinus100.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        }
    }

    /**
     * Method allowing other classes to enable or disable the button in the auction-house's interface that sends pending
     * trade requests
     *
     * @param enabled Determines whether the targeted button is to be enabled or disabled
     * @param color Sets the new colour of the targeted button's surface-level label
     */
    public void toggleAuctionConfirmationButton(boolean enabled, Color color) {
        confirmOffer.getLabel().setColor(color);
        confirmOffer.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
    }

    public void setMarketStockText(ResourceType resource, int quantity) {
        switch (resource) {
            case ORE:
                oreStockLabel.setText(String.valueOf(quantity));
                break;
            case ENERGY:
                energyStockLabel.setText(String.valueOf(quantity));
                break;
            case FOOD:
                foodStockLabel.setText(String.valueOf(quantity));
                break;
            case ROBOTICON:
                roboticonStockLabel.setText(String.valueOf(quantity));
                break;
        }
    }

    /**
     * Method allowing other classes to set the amounts of money demanded in pending trade requests
     *
     * @param tradePrice The amount of money to be demanded in the pending trade request
     */
    public void setTradePrice(int tradePrice) {
        this.tradePrice = tradePrice;
        tradePriceLabel.setText(String.valueOf(tradePrice) + "/" + otherPlayer.get(playerListPosition).getResource(ResourceType.MONEY));
    }

    /**
     * Method allowing other classes to set the amounts of resources offered in pending trade requests
     *
     * @param resource The resource to be offered in the pending trade request
     * @param value The amount of that resource to be offered
     */
    public void setTradeAmount(ResourceType resource, int value) {
        switch (resource) {
            case ORE:
                oreTradeAmount = value;
                oreTradeAmountLabel.setText(String.valueOf(oreTradeAmount));
                break;
            case ENERGY:
                energyTradeAmount = value;
                energyTradeAmountLabel.setText(String.valueOf(energyTradeAmount));
                break;
            case FOOD:
                foodTradeAmount = value;
                foodTradeAmountLabel.setText(String.valueOf(foodTradeAmount));
                break;
        }
    }

    /**
     * Refreshes the list of potential trade-offer recipients that can be examined in the auction-house's interface
     *
     * @param players The list of players who are currently playing the game
     * @param currentPlayer The player that is currently undertaking their turn
     */
    public void refreshPlayers(Player[] players, Player currentPlayer) {
        otherPlayer = new ArrayList<Player>();

        for (Player player : players) {
            if (player != currentPlayer && player != null) {
                otherPlayer.add(player);
            }
        }

        playerListPosition = 0;
        playerLabel.setText(otherPlayer.get(0).getCollege().getName());

        setTradePrice(0);
    }

    /**
     * Returns the amount of a particular resource to be offered in the pending trade request
     *
     * @param resource The type of resource to inquire into
     * @return The amount of that resource to be offered in the pending trade request
     */
    public int tradeAmount(ResourceType resource)
    {
        switch (resource) {
            case ORE:
                return oreTradeAmount;
            case ENERGY:
                return energyTradeAmount;
            case FOOD:
                return foodTradeAmount;
            default:
                return 0;
        }
    }

    /**
     * Allows or revokes access to the auction-house's interface
     *
     * @param enabled Determines whether or not access to the auction-house's interface is to be permitted
     */
    public void toggleAuctionAccess(boolean enabled) {
        if (enabled) {
            auctionButton.setTouchable(Touchable.enabled);
            auctionButton.getLabel().setColor(Color.BLACK);
        } else {
            if (!market) {
                switchInterface();
            }

            auctionButton.setTouchable(Touchable.disabled);
            auctionButton.getLabel().setColor(Color.RED);
        }
    }

    /**
     * Method allowing other classes to set the function of the button made to confirm and send pending trade requests
     *
     * @param event Object containing the method to be executed through this button
     */
    public void setAuctionConfirmationButtonFunction(ChangeListener event) {
        confirmOffer.addListener(event);
    }

    /**
     * Returns the current price attached to the pending trade-offer
     *
     * @return The price of the pending trade-offer
     */
    public int tradePrice() {
        return tradePrice;
    }

    /**
     * Returns the player who is currently intended to receive the pending trade-offer
     *
     * @return The pending trade-offer's intended recipient
     */
    public Player selectedPlayer() { return otherPlayer.get(playerListPosition); }

    /**
     * Sets the text to be displayed on the surface of the auction-house interface's trade-offer confirmation button
     *
     * @param text The text to be displayed on the button that confirms and sends pending trade-offers
     */
    public void setAuctionConfirmationButtonText(String text) {
        confirmOffer.setText(text);
    }
}
