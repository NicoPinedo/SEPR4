package drtn.game.screens.tables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import drtn.game.enums.ResourceType;
import drtn.game.util.TTFont;

/**
 * Created by Joseph on 28/02/2017.
 */
public class MarketInterfaceTable extends Table {

    private boolean market;

    private TextButton marketButton;
    private TextButton auctionButton;

    private TextButton buyOreButton;
    private TextButton buyEnergyButton;
    private TextButton buyFoodButton;
    private TextButton buyRoboticonButton;

    private TextButton sellOreButton;
    private TextButton sellEnergyButton;
    private TextButton sellFoodButton;

    private Label oreStockLabel;
    private Label foodStockLabel;
    private Label energyStockLabel;
    private Label roboticonStockLabel;

    private TTFont regularFont;
    private TTFont lightFont;

    public MarketInterfaceTable() {
        regularFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        lightFont = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);

        TextButton.TextButtonStyle regularButtonStyle = new TextButton.TextButtonStyle();
        regularButtonStyle.font = regularFont.font();
        regularButtonStyle.fontColor = Color.WHITE;
        regularButtonStyle.pressedOffsetX = 1;
        regularButtonStyle.pressedOffsetY = -1;

        TextButton.TextButtonStyle lightButtonStyle = new TextButton.TextButtonStyle();
        lightButtonStyle.font = lightFont.font();
        lightButtonStyle.fontColor = Color.WHITE;
        lightButtonStyle.pressedOffsetX = 1;
        lightButtonStyle.pressedOffsetY = -1;

        marketButton = new TextButton("MARKET", regularButtonStyle);
        marketButton.setColor(Color.GRAY);
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

        market = true;

        constructMarketInterface();
    }

    public void switchInterface() {
        market = !market;

        clearChildren();

        if (market) {
            marketButton.setColor(Color.GRAY);
            marketButton.setTouchable(Touchable.disabled);

            auctionButton.setColor(Color.WHITE);
            auctionButton.setTouchable(Touchable.enabled);

            constructMarketInterface();
        } else {
            marketButton.setColor(Color.WHITE);
            marketButton.setTouchable(Touchable.enabled);

            auctionButton.setColor(Color.GRAY);
            auctionButton.setTouchable(Touchable.disabled);

            //constructAuctionInterface();
        }
    }

    /**
     * Builds the market's visual interface by populating it with labels and buttons
     * Once this method has finished executing, the market can be drawn to a stage like any other actor
     */
    private void constructMarketInterface() {
        add(marketButton);
        add(auctionButton).left();
        row();
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
        add(new Label("Roboticons", new Label.LabelStyle(regularFont.font(), Color.WHITE))).left();
        add(buyRoboticonButton).left().padBottom(15);
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
    }

    public void setButtonFunction(ResourceType resource, boolean buy, ChangeListener event) {
        if (buy) {
            if (resource == ResourceType.ORE) {
                buyOreButton.addListener(event);
            } else if (resource == ResourceType.ENERGY) {
                buyEnergyButton.addListener(event);
            } else if (resource == ResourceType.FOOD) {
                buyFoodButton.addListener(event);
            } else if (resource == ResourceType.ROBOTICON) {
                buyRoboticonButton.addListener(event);
            }
        } else {
            if (resource == ResourceType.ORE) {
                sellOreButton.addListener(event);
            } else if (resource == ResourceType.ENERGY) {
                sellEnergyButton.addListener(event);
            } else if (resource == ResourceType.FOOD) {
                sellFoodButton.addListener(event);
            }
        }
    }

    public void toggleButton(ResourceType resource, boolean buy, boolean enabled, Color color) {
        if (buy) {
            if (resource == ResourceType.ORE) {
                buyOreButton.getLabel().setColor(color);
                buyOreButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
            } else if (resource == ResourceType.ENERGY) {
                buyEnergyButton.getLabel().setColor(color);
                buyEnergyButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
            } else if (resource == ResourceType.FOOD) {
                buyFoodButton.getLabel().setColor(color);
                buyFoodButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
            } else if (resource == ResourceType.ROBOTICON) {
                buyRoboticonButton.getLabel().setColor(color);
                buyRoboticonButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
            }
        } else {
            if (resource == ResourceType.ORE) {
                sellOreButton.getLabel().setColor(color);
                sellOreButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
            } else if (resource == ResourceType.ENERGY) {
                sellEnergyButton.getLabel().setColor(color);
                sellEnergyButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
            } else if (resource == ResourceType.FOOD) {
                sellFoodButton.getLabel().setColor(color);
                sellFoodButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
            }
        }
    }
}
