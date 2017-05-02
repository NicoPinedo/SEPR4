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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import drtn.game.entity.Tile;
import drtn.game.util.TTFont;

/**
 * Created by Joseph on 28/02/2017.
 */
public class SelectedTileInfoTable extends Table {

    /**
     * Label encoding the most recently-selected tile's ID
     */
    private Label selectedTileLabel;

    /**
     * Image object intended to hold the logo of the college that owns the most recently-selected tile
     */
    private Image selectedTileOwnerIcon;

    /**
     * Image object intended to hold a representation of the roboticon working on the most recently-selected tile
     */
    private Image selectedTileRoboticonIcon;

    /**
     * Button enabling players to claim unoccupied tiles right after selecting them
     */
    private TextButton claimTileButton;

    /**
     * Button enabling players to deploy roboticons on occupied tiles right after selecting them
     */
    private TextButton deployRoboticonButton;

    /**
     * Constructs the table that will identify selected tiles; their owners and their resident roboticons
     * It will also prepare and deploy buttons to allow for players to claim those tiles or deploy/upgrade roboticons
     * on them
     */
    public SelectedTileInfoTable() {
        TTFont headerFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 20);
        TTFont smallFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        TTFont smallFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = smallFontRegular.font();
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.pressedOffsetX = 1;
        buttonStyle.pressedOffsetY = -1;

        claimTileButton = new TextButton("CLAIM", buttonStyle);
        deployRoboticonButton = new TextButton("DEPLOY", buttonStyle);
        claimTileButton.center();
        deployRoboticonButton.center();
        toggleClaimTileButton(false);
        toggleDeployRoboticonButton(false);

        selectedTileLabel = new Label("NO TILE SELECTED", new Label.LabelStyle(headerFont.font(), Color.WHITE));
        selectedTileLabel.setAlignment(Align.center);

        selectedTileOwnerIcon = new Image();
        selectedTileOwnerIcon.setVisible(false);
        selectedTileOwnerIcon.setScaling(Scaling.fit);
        selectedTileOwnerIcon.setAlign(Align.center);

        selectedTileRoboticonIcon = new Image();
        selectedTileRoboticonIcon.setVisible(false);
        selectedTileRoboticonIcon.setScaling(Scaling.fit);
        selectedTileRoboticonIcon.setAlign(Align.center);

        add(selectedTileLabel).width(240).colspan(2).padBottom(10);
        row();
        add(selectedTileOwnerIcon).size(64, 64).center();
        add(selectedTileRoboticonIcon).size(64, 64).center();
        row();
        add(new Label("COLLEGE", new Label.LabelStyle(smallFontLight.font(), Color.WHITE))).padBottom(10).center();
        add(new Label("ROBOTICON", new Label.LabelStyle(smallFontLight.font(), Color.WHITE))).padBottom(10).center();
        row();
        add(claimTileButton).width(120).center();
        add(deployRoboticonButton).width(120).center();
    }

    /**
     * Enables/disables the button in the table that allows players to claim unoccupied tiles
     *
     * @param enabled Determines whether the claimTileButton object is to be enabled or disabled
     */
    public void toggleClaimTileButton(boolean enabled) {
        if (enabled) {
            claimTileButton.getLabel().setColor(Color.BLACK);
        } else {
            claimTileButton.getLabel().setColor(Color.GRAY);
        }

        claimTileButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
    }

    /**
     * Enables/disables the button in the table that allows players to deploy and/or upgrade roboticons on occupied
     * tiles
     *
     * @param enabled Determines whether the deployRoboticonButton object is to be enabled or disabled
     */
    public void toggleDeployRoboticonButton(boolean enabled) {
        if (enabled) {
            deployRoboticonButton.getLabel().setColor(Color.BLACK);
        } else {
            deployRoboticonButton.getLabel().setColor(Color.GRAY);
        }

        deployRoboticonButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
    }

    /**
     * Sets the function of the button that was originally made to facilitate tile reservations
     *
     * @param event Object containing the method to be executed when the claimTileButton object is clicked on
     */
    public void setClaimTileButtonFunction(ChangeListener event) {
        claimTileButton.addListener(event);
    }

    /**
     * Sets the function of the button that was originally made to facilitate roboticon deployments and upgrades
     *
     * @param event Object containing the method to be executed when the deployRoboticonButton object is clicked on
     */
    public void setDeployRoboticonButtonFunction(ChangeListener event) {
        deployRoboticonButton.addListener(event);
    }

    /**
     * Configures the table's contents to display a specific tile's properties and present the options that may be
     * available for interacting with it
     *
     * @param tile The tile to be represented
     */
    public void showTileInfo(Tile tile) {
        selectedTileLabel.setText("TILE " + tile.getID());
        //Update the label that identifies the specified tile

        if (tile.isOwned()) {
            selectedTileOwnerIcon.setVisible(true);
            selectedTileOwnerIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(tile.getOwner().getCollege().getLogoTexture())));
            selectedTileOwnerIcon.setSize(64, 64);
            //If the specified tile is already owned, display the logo of the college that owns it

            if (tile.hasRoboticon()) {
                deployRoboticonButton.setText("UPGRADE");
                //Ensure that tiles which already accommodate roboticons can have their roboticons be upgraded

                selectedTileRoboticonIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(tile.getRoboticonStored().getIconTexture())));
                selectedTileRoboticonIcon.setSize(64, 64);
                //If the specified tile already accommodate a roboticon, draw a visual representation of that roboticon

                selectedTileRoboticonIcon.setVisible(true);
            } else {
                deployRoboticonButton.setText("DEPLOY");
                //If the specified tile does not accommodate a roboticon, allow for one to be deployed on to it

                selectedTileRoboticonIcon.setVisible(false);
            }
        } else {
            deployRoboticonButton.setText("DEPLOY");

            selectedTileOwnerIcon.setVisible(false);
            selectedTileRoboticonIcon.setVisible(false);
        }
    }

    /**
     * Configures the table's contents to disassociate them from any tile
     */
    public void hideTileInfo() {
        claimTileButton.setTouchable(Touchable.disabled);
        claimTileButton.getLabel().setColor(Color.GRAY);
        //Prevent players from attempting to claim unspecified tiles

        deployRoboticonButton.setTouchable(Touchable.disabled);
        deployRoboticonButton.getLabel().setColor(Color.GRAY);
        //Prevent players from attempting to deploy roboticons on unspecified tiles

        deployRoboticonButton.setText("DEPLOY");

        selectedTileOwnerIcon.setVisible(false);
        selectedTileRoboticonIcon.setVisible(false);

        selectedTileLabel.setText("NO TILE SELECTED");
    }
}
