package drtn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import drtn.game.enums.ResourceType;
import drtn.game.util.Overlay;
import drtn.game.util.TTFont;

/**
 * Created by Joseph on 30/04/2017.
 */
public class UpgradeOverlay extends Overlay {

    private static TTFont headerFontRegular;
    private static TTFont smallFontRegular;
    private static TTFont smallFontLight;

    static {
        headerFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 24);
        smallFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        smallFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);
    }

    private Label oreYieldLabel;
    private Label energyYieldLabel;
    private Label foodYieldLabel;

    private Label oreLevelLabel;
    private Label energyLevelLabel;
    private Label foodLevelLabel;

    /**
     * Button allowing players to upgrade roboticons' ore-production capabilities
     */
    private TextButton oreUpgradeButton;

    /**
     * Button allowing players to upgrade roboticons' energy-production capabilities
     */
    private TextButton energyUpgradeButton;

    /**
     * Button allowing players to upgrade roboticons' food-production capabilities
     */
    private TextButton foodUpgradeButton;

    /**
     * Button allowing players to escape from the upgrade overlay if they decide against upgrading roboticons
     */
    private TextButton closeUpgradeOverlayButton;

    /**
     * Customised stage that shows up to offer roboticon upgrade choices
     */

    public UpgradeOverlay() {
        super(Color.GRAY, Color.WHITE, 460, 190, 3);

        Label oreLabel = new Label("Ore", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        oreLabel.setAlignment(Align.left);
        Label energyLabel = new Label("Energy", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        energyLabel.setAlignment(Align.left);
        Label foodLabel = new Label("Food", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        foodLabel.setAlignment(Align.left);

        oreYieldLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        oreYieldLabel.setAlignment(Align.left);
        energyYieldLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        energyYieldLabel.setAlignment(Align.left);
        foodYieldLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        foodYieldLabel.setAlignment(Align.left);

        oreLevelLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        oreLevelLabel.setAlignment(Align.left);
        energyLevelLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        energyLevelLabel.setAlignment(Align.left);
        foodLevelLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        foodLevelLabel.setAlignment(Align.left);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = smallFontLight.font();
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;

        oreUpgradeButton = new TextButton("", textButtonStyle);
        oreUpgradeButton.getLabel().setAlignment(Align.left);
        energyUpgradeButton = new TextButton("", textButtonStyle);
        energyUpgradeButton.getLabel().setAlignment(Align.left);
        foodUpgradeButton = new TextButton("", textButtonStyle);
        foodUpgradeButton.getLabel().setAlignment(Align.left);

        closeUpgradeOverlayButton = new TextButton("CLOSE", textButtonStyle);

        table().add(new Label("UPGRADE ROBOTICON", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE))).colspan(4).padBottom(20);
        //Visual guff

        table().row();
        table().add(new Label("Resource", new Label.LabelStyle(smallFontRegular.font(), Color.WHITE))).padRight(20);
        table().add(new Label("Base Tile Yield", new Label.LabelStyle(smallFontRegular.font(), Color.WHITE))).padRight(20);
        table().add(new Label("Rob. Level", new Label.LabelStyle(smallFontRegular.font(), Color.WHITE))).padRight(20);
        table().add(new Label("Upgrade Cost", new Label.LabelStyle(smallFontRegular.font(), Color.WHITE)));
        table().row();
        table().add(oreLabel).left();
        table().add(oreYieldLabel).left();
        table().add(oreLevelLabel).left();
        table().add(oreUpgradeButton).left();
        table().row();
        table().add(energyLabel).left();
        table().add(energyYieldLabel).left();
        table().add(energyLevelLabel).left();
        table().add(energyUpgradeButton).left();
        table().row();
        table().add(foodLabel).left();
        table().add(foodYieldLabel).left();
        table().add(foodLevelLabel).left();
        table().add(foodUpgradeButton).left();
        table().row();
        table().add(closeUpgradeOverlayButton).colspan(4).padTop(20);
        //Add buttons for upgrading roboticons to the overlay
        //Like in the market, each button's label is the monetary price of the upgrade that it performs
    }

    public void setYieldLabelText(ResourceType resource, int yield) {
        switch (resource) {
            case ORE:
                oreYieldLabel.setText(String.valueOf(yield));
                break;
            case ENERGY:
                energyYieldLabel.setText(String.valueOf(yield));
                break;
            case FOOD:
                foodYieldLabel.setText(String.valueOf(yield));
                break;
        }
    }

    public void setRoboticonLevelLabelText(ResourceType resource, int level) {
        switch (resource) {
            case ORE:
                oreLevelLabel.setText(String.valueOf(level));
                break;
            case ENERGY:
                energyLevelLabel.setText(String.valueOf(level));
                break;
            case FOOD:
                foodLevelLabel.setText(String.valueOf(level));
                break;
        }
    }

    public void setUpgradeButtonFunction(ResourceType resource, ChangeListener event) {
        switch (resource) {
            case ORE:
                oreUpgradeButton.addListener(event);
                break;
            case ENERGY:
                energyUpgradeButton.addListener(event);
                break;
            case FOOD:
                foodUpgradeButton.addListener(event);
                break;
        }
    }

    public void setUpgradeButtonLabelText(ResourceType resource, String text) {
        switch (resource) {
            case ORE:
                oreUpgradeButton.getLabel().setText(text);
                break;
            case ENERGY:
                energyUpgradeButton.getLabel().setText(text);
                break;
            case FOOD:
                foodUpgradeButton.getLabel().setText(text);
                break;
        }
    }

    public void toggleUpgradeButton(ResourceType resource, boolean enabled, Color color) {
        switch (resource) {
            case ORE:
                oreUpgradeButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                oreUpgradeButton.getLabel().setColor(color);
                break;
            case ENERGY:
                energyUpgradeButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                energyUpgradeButton.getLabel().setColor(color);
                break;
            case FOOD:
                foodUpgradeButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
                foodUpgradeButton.getLabel().setColor(color);
                break;
        }
    }

    public void setCloseUpgradeOverlayButtonFunction(ChangeListener event) {
        closeUpgradeOverlayButton.addListener(event);
    }
}
