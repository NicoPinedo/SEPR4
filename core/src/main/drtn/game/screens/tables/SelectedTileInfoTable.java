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

    private Label selectedTileLabel;

    private Image selectedTileOwnerIcon;
    private Image selectedTileRoboticonIcon;

    private TextButton claimTileButton;
    private TextButton deployRoboticonButton;

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

    public void toggleClaimTileButton(boolean enabled) {
        if (enabled) {
            claimTileButton.getLabel().setColor(Color.WHITE);
        } else {
            claimTileButton.getLabel().setColor(Color.GRAY);
        }

        claimTileButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
    }

    public void toggleDeployRoboticonButton(boolean enabled) {
        if (enabled) {
            deployRoboticonButton.getLabel().setColor(Color.WHITE);
        } else {
            deployRoboticonButton.getLabel().setColor(Color.GRAY);
        }

        deployRoboticonButton.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
    }

    public void setClaimTileButtonFunction(ChangeListener event) {
        claimTileButton.addListener(event);
    }

    public void setDeployRoboticonButtonFunction(ChangeListener event) {
        deployRoboticonButton.addListener(event);
    }

    public void showTileInfo(Tile tile) {
        selectedTileLabel.setText("TILE " + tile.getID());

        if (tile.isOwned()) {
            selectedTileOwnerIcon.setVisible(true);
            selectedTileOwnerIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(tile.getOwner().getCollege().getLogoTexture())));
            selectedTileOwnerIcon.setSize(64, 64);

            if (tile.hasRoboticon()) {
                deployRoboticonButton.setText("UPGRADE");

                selectedTileRoboticonIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(tile.getRoboticonStored().getIconTexture())));
                selectedTileRoboticonIcon.setSize(64, 64);

                selectedTileRoboticonIcon.setVisible(true);
            } else {
                deployRoboticonButton.setText("DEPLOY");

                selectedTileRoboticonIcon.setVisible(false);
            }
        } else {
            deployRoboticonButton.setText("DEPLOY");

            selectedTileOwnerIcon.setVisible(false);
            selectedTileRoboticonIcon.setVisible(false);
        }
    }

    public void hideTileInfo() {
        claimTileButton.setTouchable(Touchable.disabled);
        claimTileButton.setColor(Color.GRAY);

        deployRoboticonButton.setTouchable(Touchable.disabled);
        deployRoboticonButton.setColor(Color.GRAY);

        deployRoboticonButton.setText("DEPLOY");

        selectedTileOwnerIcon.setVisible(false);
        selectedTileRoboticonIcon.setVisible(false);

        selectedTileLabel.setText("NO TILE SELECTED");
    }
}
