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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import drtn.game.entity.Player;
import drtn.game.enums.ResourceType;
import drtn.game.util.TTFont;

/**
 * Created by Joseph on 28/02/2017.
 */
public class PlayerInfoTable extends Table {

    /**
     * Image object intended to hold visual representations of players' associated colleges
     */
    private Image collegeImage;

    /**
     * Label identifying the current player's ID
     */
    private Label playerLabel;

    /**
     * Label identifying the current player's associated college
     */
    private Label collegeLabel;

    /**
     * Label encoding the amount of ore that the active player currently owns
     */
    private Label oreCounterLabel;

    /**
     * Label encoding the amount of food that the active player currently owns
     */
    private Label foodCounterLabel;

    /**
     * Label encoding the amount of energy that the active player currently owns
     */
    private Label energyCounterLabel;

    /**
     * Label encoding the amount of money that the active player currently owns
     */
    private Label moneyCounterLabel;

    /**
     * Label encoding the amount of roboticons that the active player currently owns
     */
    private Label roboticonCounterLabel;

    /**
     * Constructs the table which will identify active players' associated colleges and describe their inventories
     */
    public PlayerInfoTable() {
        final int textSize = 16;
        final int labelWidth = 80;
        final int counterWidth = 40;

        TTFont regularFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), textSize);
        TTFont lightFont = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), textSize);

        collegeImage = new Image();
        collegeImage.setSize(60, 60);

        playerLabel = prepareLabel(regularFont.font());
        collegeLabel = prepareLabel(lightFont.font());

        Table collegeTable = new Table();
        collegeTable.add(collegeImage);
        collegeTable.row();
        collegeTable.add(playerLabel);
        collegeTable.row();
        collegeTable.add(collegeLabel);

        oreCounterLabel = prepareLabel(lightFont.font());
        foodCounterLabel = prepareLabel(lightFont.font());
        energyCounterLabel = prepareLabel(lightFont.font());
        moneyCounterLabel = prepareLabel(lightFont.font());
        roboticonCounterLabel = prepareLabel(lightFont.font());

        Table inventoryTable = new Table();
        inventoryTable.add(prepareLabel("ORE", regularFont.font())).width(labelWidth).left();
        inventoryTable.add(oreCounterLabel).width(counterWidth).left();
        inventoryTable.row();
        inventoryTable.add(prepareLabel("FOOD", regularFont.font())).width(labelWidth).left();
        inventoryTable.add(foodCounterLabel).width(counterWidth).left();
        inventoryTable.row();
        inventoryTable.add(prepareLabel("ENERGY", regularFont.font())).width(labelWidth).left();
        inventoryTable.add(energyCounterLabel).width(counterWidth).left();
        inventoryTable.row();
        inventoryTable.add(prepareLabel("MONEY", regularFont.font())).width(labelWidth).left();
        inventoryTable.add(moneyCounterLabel).width(counterWidth).left();
        inventoryTable.row();
        inventoryTable.add(prepareLabel("RBTICNS", regularFont.font())).width(labelWidth).left();
        inventoryTable.add(roboticonCounterLabel).width(counterWidth).left();

        add(collegeTable).padLeft(10).padRight(20).center();
        add(inventoryTable).center();
    }

    /**
     * Creates a label to render text in a specific style and aligns it left-wise
     *
     * @param font The font that will encode the label's textual contents
     * @return A label encoded with the provided font and aligned left-wise
     */
    private Label prepareLabel(BitmapFont font) {
        Label label = new Label("", new Label.LabelStyle(font, Color.WHITE));
        label.setAlignment(Align.left);

        return label;
    }

    /**
     * Creates a label to render text in a specific style and aligns it left-wise
     *
     * @param text The text to be encoded in the label
     * @param font The font that will encode the label's textual contents
     * @return A label encoded with the provided font and aligned left-wise
     */
    private Label prepareLabel(String text, BitmapFont font) {
        Label label = new Label(text, new Label.LabelStyle(font, Color.WHITE));
        label.setAlignment(Align.left);

        return label;
    }

    /**
     * Configures the table's contents to represent a specific player and display their inventory
     *
     * @param player The player to be represented
     */
    public void showPlayerInfo(Player player) {
        collegeImage.setDrawable(player.getCollege().getLogo().getDrawable());

        playerLabel.setText("PLAYER " + player.getPlayerNumber());
        collegeLabel.setText(player.getCollege().getName().toUpperCase());

        showPlayerInventory(player);
    }

    /**
     * Configures the table's contents to display a specific player's inventory
     *
     * @param player The player owning the inventory to be shown
     */
    public void showPlayerInventory(Player player) {
        oreCounterLabel.setText(String.valueOf(player.getResource(ResourceType.ORE)));
        foodCounterLabel.setText(String.valueOf(player.getResource(ResourceType.FOOD)));
        energyCounterLabel.setText(String.valueOf(player.getResource(ResourceType.ENERGY)));
        moneyCounterLabel.setText(String.valueOf(player.getResource(ResourceType.MONEY)));
        roboticonCounterLabel.setText(String.valueOf(player.getResource(ResourceType.ROBOTICON)));
    }

    /**
     * Updates a specific component of the table's inventory section
     *
     * @param player The player owning the goods to be represented
     * @param resource The resource-type corresponding to the internal counter that is to be refreshed/updated
     */
    public void updateResource(Player player, ResourceType resource) {
        if (resource == ResourceType.ORE) {
            oreCounterLabel.setText(String.valueOf(player.getResource(resource)));
        } else if (resource == ResourceType.FOOD) {
            foodCounterLabel.setText(String.valueOf(player.getResource(resource)));
        } else if (resource == ResourceType.ENERGY) {
            energyCounterLabel.setText(String.valueOf(player.getResource(resource)));
        } else if (resource == ResourceType.MONEY) {
            moneyCounterLabel.setText(String.valueOf(player.getResource(resource)));
        } else if (resource == ResourceType.ROBOTICON) {
            roboticonCounterLabel.setText(String.valueOf(player.getResource(resource)));
        }
    }
}
