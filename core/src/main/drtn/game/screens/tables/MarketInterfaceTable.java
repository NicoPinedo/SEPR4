package drtn.game.screens.tables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

    private Label oreTradeLabel;
    private Label energyTradeLabel;
    private Label foodTradeLabel;
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
        add(navigationTable).colspan(4);
        row();

        market = true;

        showMarketInterface();
    }

    public void switchInterface() {
        market = !market;

        clearChildren();

        add(navigationTable).colspan(4);
        row();

        if (market) {
            marketButton.getLabel().setColor(Color.GRAY);
            marketButton.setTouchable(Touchable.disabled);

            auctionButton.getLabel().setColor(Color.WHITE);
            auctionButton.setTouchable(Touchable.enabled);

            showMarketInterface();
        } else {
            marketButton.getLabel().setColor(Color.WHITE);
            marketButton.setTouchable(Touchable.enabled);

            auctionButton.getLabel().setColor(Color.GRAY);
            auctionButton.setTouchable(Touchable.disabled);

            showAuctionInterface();
        }
    }

    private void constructMarketElements() {
        marketButton = new TextButton("MARKET", regularButtonStyle);
        marketButton.getLabel().setColor(Color.GRAY);
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
        oreTradeLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        energyTradeLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));
        foodTradeLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        tradePriceLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        playerLabel = new Label("", new Label.LabelStyle(lightFont.font(), Color.WHITE));

        playerBuyOre = new TextButton("+", lightButtonStyle);
        playerBuyOre.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradeAmount(ResourceType.ORE, oreTradeAmount + 1);
            }
        });

        playerBuyEnergy = new TextButton("+", lightButtonStyle);
        playerBuyEnergy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradeAmount(ResourceType.ENERGY, energyTradeAmount + 1);
            }
        });

        playerBuyFood = new TextButton("+", lightButtonStyle);
        playerBuyFood.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradeAmount(ResourceType.FOOD, foodTradeAmount + 1);
            }
        });

        playerSellOre = new TextButton("-", lightButtonStyle);
        playerSellOre.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradeAmount(ResourceType.ORE, oreTradeAmount - 1);
            }
        });

        playerSellEnergy = new TextButton("-", lightButtonStyle);
        playerSellEnergy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradeAmount(ResourceType.ENERGY, energyTradeAmount - 1);
            }
        });

        playerSellFood = new TextButton("-", lightButtonStyle);
        playerSellFood.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradeAmount(ResourceType.FOOD, foodTradeAmount - 1);
            }
        });

        pricePlus1 = new TextButton("+ 1", lightButtonStyle);
        pricePlus1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradePrice(tradePrice + 1);
            }
        });

        pricePlus10 = new TextButton("+ 10", lightButtonStyle);
        pricePlus10.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradePrice(tradePrice + 10);
            }
        });

        pricePlus100 = new TextButton("+ 100", lightButtonStyle);
        pricePlus100.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradePrice(tradePrice + 100);
            }
        });

        priceMinus1 = new TextButton("- 1", lightButtonStyle);
        priceMinus1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradePrice(tradePrice - 1);
            }
        });

        priceMinus10 = new TextButton("- 10", lightButtonStyle);
        priceMinus10.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradePrice(tradePrice - 10);
            }
        });

        priceMinus100 = new TextButton("- 100", lightButtonStyle);
        priceMinus100.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTradePrice(tradePrice - 100);
            }
        });

        confirmSale = new TextButton("Confirm", lightButtonStyle);

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
            }
        });
    }

    /**
     * Builds the market's visual interface by populating it with labels and buttons
     * Once this method has finished executing, the market can be drawn to a stage like any other actor
     */
    private void showMarketInterface() {
        add(new Label("Item", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().width(90);
        add(new Label("Buy", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().width(40);
        add(new Label("Sell", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().width(20);
        row();
        add(new Label("Ore", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(buyOreButton).left();
        add(sellOreButton).left();
        row();
        add(new Label("Energy", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(buyEnergyButton).left();
        add(sellEnergyButton).left();
        row();
        add(new Label("Food", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(buyFoodButton).left();
        add(sellFoodButton).left();
        row();
        add(new Label("Roboticons", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().top().padBottom(10);
        add(buyRoboticonButton).left().top().padBottom(10);
        row();
        add(new Label("Item", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(new Label("Stock", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        row();
        add(new Label("Ore", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(oreStockLabel).left();
        row();
        add(new Label("Energy", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(energyStockLabel).left();
        row();
        add(new Label("Food", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(foodStockLabel).left();
        row();
        add(new Label("Roboticons", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(roboticonStockLabel).left();

        debug();
    }

    private void showAuctionInterface() {
        playerListPosition = 0;

        setTradePrice(0);

        add(new Label("Item", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(90);
        add(new Label("More", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(30);
        add(new Label("Less", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left().padRight(20);
        //Visual guff

        row();
        oreTradeAmount = 0;
        add(oreTradeLabel).left();
        add(playerBuyOre).left().padLeft(10);
        add(playerSellOre).left().padLeft(10);

        row();
        energyTradeAmount = 0;
        add(energyTradeLabel).left();
        add(playerBuyEnergy).left().padLeft(10);
        add(playerSellEnergy).left().padLeft(10);

        row();
        foodTradeAmount = 0;
        add(foodTradeLabel).left().padBottom(15);
        add(playerBuyFood).left().padLeft(10).padBottom(15);
        add(playerSellFood).left().padLeft(10).padBottom(15);

        row();
        tradePrice = 0;
        add(new Label("Price:", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();

        row();

        add();
        add(pricePlus1).left();
        add(priceMinus1).left();

        row();

        add(tradePriceLabel);
        add(pricePlus10).left();
        add(priceMinus10).left();

        row();

        add();
        add(pricePlus100).left();
        add(priceMinus100).left();

        row();
        add(prevPlayerButton);
        add(playerLabel);
        add(nextPlayerButton);
        row();
        add(confirmSale).left();
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

    public void toggleButton(ResourceType resource, boolean buy, boolean enabled, Color color) {
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

    private void setTradePrice(int tradePrice) {
        this.tradePrice = tradePrice;
        tradePriceLabel.setText(String.valueOf(tradePrice));
    }

    public void setTradeAmount(ResourceType resource, int value) {
        switch (resource) {
            case ORE:
                oreTradeAmount = value;
                oreTradeLabel.setText("Ore: " + oreTradeAmount);
                break;
            case ENERGY:
                energyTradeAmount = value;
                energyTradeLabel.setText("Energy: " + energyTradeAmount);
                break;
            case FOOD:
                foodTradeAmount = value;
                foodTradeLabel.setText("Food: " + foodTradeAmount);
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
    }
}
