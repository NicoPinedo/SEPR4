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

    private Image collegeImage;

    private Label playerLabel;
    private Label collegeLabel;

    private Label oreCounterLabel;
    private Label foodCounterLabel;
    private Label energyCounterLabel;
    private Label moneyCounterLabel;
    private Label roboticonCounterLabel;

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

    private Label prepareLabel(BitmapFont font) {
        Label label = new Label("", new Label.LabelStyle(font, Color.WHITE));
        label.setAlignment(Align.left);

        return label;
    }

    private Label prepareLabel(String text, BitmapFont font) {
        Label label = new Label(text, new Label.LabelStyle(font, Color.WHITE));
        label.setAlignment(Align.left);

        return label;
    }

    public void showPlayerInfo(Player player) {
        collegeImage.setDrawable(player.getCollege().getLogo().getDrawable());

        playerLabel.setText("PLAYER " + player.getPlayerNumber());
        collegeLabel.setText(player.getCollege().getName().toUpperCase());

        showPlayerInventory(player);
    }

    public void showPlayerInventory(Player player) {
        oreCounterLabel.setText(String.valueOf(player.getResource(ResourceType.ORE)));
        foodCounterLabel.setText(String.valueOf(player.getResource(ResourceType.FOOD)));
        energyCounterLabel.setText(String.valueOf(player.getResource(ResourceType.ENERGY)));
        moneyCounterLabel.setText(String.valueOf(player.getResource(ResourceType.MONEY)));
        roboticonCounterLabel.setText(String.valueOf(player.getResource(ResourceType.ROBOTICON)));
    }

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
