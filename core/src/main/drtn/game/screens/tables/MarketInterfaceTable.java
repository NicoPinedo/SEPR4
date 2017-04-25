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

    private boolean market;

    private Table navigationTable;

    private TextButton.TextButtonStyle regularButtonStyle;
    private TextButton.TextButtonStyle lightButtonStyle;

    private TextButton marketButton;
    private TextButton auctionButton;

    private TextButton buyOreButton;
    private TextButton buyEnergyButton;
    private TextButton buyFoodButton;
    private TextButton buyRoboticonButton;

    private TextButton sellOreButton;
    private TextButton sellEnergyButton;
    private TextButton sellFoodButton;

    private TextButton playerBuyOre;
    private TextButton playerBuyEnergy;
    private TextButton playerBuyFood;
    private TextButton playerSellOre;
    private TextButton playerSellEnergy;
    private TextButton playerSellFood;

    private TextButton pricePlus1;
    private TextButton pricePlus10;
    private TextButton pricePlus100;
    private TextButton priceMinus1;
    private TextButton priceMinus10;
    private TextButton priceMinus100;

    private TextButton confirmSale;

    private TextButton nextPlayerButton;
    private TextButton prevPlayerButton;

    private Label oreStockLabel;
    private Label foodStockLabel;
    private Label energyStockLabel;
    private Label roboticonStockLabel;

    private Label oreTradeAmountLabel;
    private Label energyTradeAmountLabel;
    private Label foodTradeAmountLabel;
    private Label tradePriceLabel;
    private Label playerLabel;

    private TTFont regularFont;
    private TTFont lightFont;

    private int oreTradeAmount;
    private int energyTradeAmount;
    private int foodTradeAmount;
    private int playerListPosition;
    private int tradePrice;

    private ArrayList<Player> otherPlayer;

    public MarketInterfaceTable() {
        regularFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        lightFont = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);

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

        constructMarketElements();
        constructAuctionElements();

        navigationTable = new Table();
        navigationTable.add(marketButton).width(100);
        navigationTable.add(auctionButton).width(100);
        add(navigationTable).colspan(4).padBottom(10);
        row();

        market = true;

        showMarketInterface();
    }

    public void switchInterface() {
        market = !market;

        clearChildren();

        add(navigationTable).colspan(4).padBottom(10);
        row();

        if (market) {
            marketButton.getLabel().setColor(Color.GREEN);
            marketButton.setTouchable(Touchable.disabled);

            auctionButton.getLabel().setColor(Color.WHITE);
            auctionButton.setTouchable(Touchable.enabled);

            showMarketInterface();
        } else {
            marketButton.getLabel().setColor(Color.WHITE);
            marketButton.setTouchable(Touchable.enabled);

            auctionButton.getLabel().setColor(Color.GREEN);
            auctionButton.setTouchable(Touchable.disabled);

            showAuctionInterface();
        }
    }

    private void constructMarketElements() {
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
        auctionButton.setColor(Color.WHITE);
        auctionButton.setTouchable(Touchable.enabled);
        auctionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchInterface();
            }
        });

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

    private void constructAuctionElements() {
        oreTradeAmountLabel = new Label("0", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        energyTradeAmountLabel = new Label("0", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        foodTradeAmountLabel = new Label("0", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        tradePriceLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        playerLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        playerBuyOre = new TextButton("+", lightButtonStyle);
        playerBuyEnergy = new TextButton("+", lightButtonStyle);
        playerBuyFood = new TextButton("+", lightButtonStyle);
        playerSellOre = new TextButton("-", lightButtonStyle);
        playerSellEnergy = new TextButton("-", lightButtonStyle);
        playerSellFood = new TextButton("-", lightButtonStyle);

        pricePlus1 = new TextButton("+ 1", lightButtonStyle);
        pricePlus10 = new TextButton("+ 10", lightButtonStyle);
        pricePlus100 = new TextButton("+ 100", lightButtonStyle);

        priceMinus1 = new TextButton("- 1", lightButtonStyle);
        priceMinus10 = new TextButton("- 10", lightButtonStyle);
        priceMinus100 = new TextButton("- 100", lightButtonStyle);

        confirmSale = new TextButton("Send Offer to This Player", regularButtonStyle);

        nextPlayerButton = new TextButton(">", lightButtonStyle);
        nextPlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerListPosition < otherPlayer.size() - 1) {
                    playerListPosition += 1;
                } else {
                    playerListPosition = 0;
                }
                playerLabel.setText("Player " + otherPlayer.get(playerListPosition).getPlayerNumber());

                setTradePrice(0);
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
                playerLabel.setText("Player " + otherPlayer.get(playerListPosition).getPlayerNumber());

                setTradePrice(0);
            }
        });
    }

    /**
     * Builds the market's visual interface by populating it with labels and buttons
     * Once this method has finished executing, the market can be drawn to a stage like any other actor
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

        debug();
    }

    private void showAuctionInterface() {
        playerListPosition = 0;

        setTradePrice(0);

        add(new Label("Item", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(40);
        add(new Label("#", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20);
        add(new Label("More", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20);
        add(new Label("Less", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20);
        //Visual guff

        row();
        oreTradeAmount = 0;
        add(new Label("Ore", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(oreTradeAmountLabel).left();
        add(playerBuyOre).left();
        add(playerSellOre).left();

        row();
        energyTradeAmount = 0;
        add(new Label("Energy", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(energyTradeAmountLabel).left();
        add(playerBuyEnergy).left();
        add(playerSellEnergy).left();

        row();
        add(new Label("Food", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(foodTradeAmountLabel).left();
        add(playerBuyFood).left();
        add(playerSellFood).left();

        row();
        tradePrice = 0;
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
        add(confirmSale).colspan(4);
    }

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

    public void setAuctionQuantityButtonFunction(ResourceType resource, boolean add, ChangeListener event) {
        if (add) {
            switch (resource) {
                case ORE:
                    playerBuyOre.addListener(event);
                    break;
                case ENERGY:
                    playerBuyEnergy.addListener(event);
                    break;
                case FOOD:
                    playerBuyFood.addListener(event);
                    break;
            }
        } else {
                switch (resource) {
                    case ORE:
                        playerSellOre.addListener(event);
                        break;
                    case ENERGY:
                        playerSellEnergy.addListener(event);
                        break;
                    case FOOD:
                        playerSellFood.addListener(event);
                        break;
                }
            }
        }

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

    public void setMarketButtonText(ResourceType resource, boolean buy, int price) {
        setMarketButtonText(resource, buy, String.valueOf(price));
    }

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

    public void toggleAuctionQuantityButton(ResourceType resource, boolean add, boolean enabled, Color color) {
        if (add) {
            switch (resource) {
                case ORE:
                    playerBuyOre.getLabel().setColor(color);
                    playerBuyOre.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case ENERGY:
                    playerBuyEnergy.getLabel().setColor(color);
                    playerBuyEnergy.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case FOOD:
                    playerBuyFood.getLabel().setColor(color);
                    playerBuyFood.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        } else {
            switch (resource) {
                case ORE:
                    playerSellOre.getLabel().setColor(color);
                    playerSellOre.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case ENERGY:
                    playerSellEnergy.getLabel().setColor(color);
                    playerSellEnergy.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
                case FOOD:
                    playerSellFood.getLabel().setColor(color);
                    playerSellFood.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                    break;
            }
        }
    }

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

    public void toggleAuctionConfirmationButton(boolean enabled, Color color) {
        confirmSale.getLabel().setColor(color);
        confirmSale.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
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

    public void setTradePrice(int tradePrice) {
        this.tradePrice = tradePrice;
        tradePriceLabel.setText(String.valueOf(tradePrice) + "/" + otherPlayer.get(playerListPosition).getResource(ResourceType.MONEY));
    }

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

    public void refreshPlayers(Player[] players, Player currentPlayer) {
        otherPlayer = new ArrayList<Player>();

        for (Player player : players) {
            if (player != currentPlayer && player != null) {
                otherPlayer.add(player);
            }
        }

        playerListPosition = 0;
        playerLabel.setText("Player " + otherPlayer.get(0).getPlayerNumber());

        setTradePrice(0);
    }

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

    public void setAuctionConfirmationButtonFunction(ChangeListener event) {
        confirmSale.addListener(event);
    }

    public int tradePrice() {
        return tradePrice;
    }

    public Player selectedPlayer() { return otherPlayer.get(playerListPosition); }

    public void setAuctionConfirmationButtonText(String text) {
        confirmSale.setText(text);
    }
}
