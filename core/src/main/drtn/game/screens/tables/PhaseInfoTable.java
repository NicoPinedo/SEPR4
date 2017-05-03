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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import drtn.game.util.GameTimer;
import drtn.game.util.TTFont;

/**
 * Created by Joseph on 28/02/2017.
 */
public class PhaseInfoTable extends Table {

    /**
     * The in-game timer object that helps to accelerate the game's flow
     * This combines a standard Timer-type object with a visual interface that can be drawn directly to the screen
     */
    public GameTimer timer;

    /**
     * Label encoding the number of the game's current phase
     */
    private Label phaseNumberLabel;

    /**
     * Label encoding the description of the game's current phase
     */
    private Label phaseDescriptionLabel;

    /**
     * Constructs the framework enclosing the game's internal timer and labels identifying its different phases
     */
    public PhaseInfoTable() {
        final int width = 240;
        //Determines the width of the framework

        final int textSize = 20;
        //Determines the consistent size of the text to be displayed in this table

        TTFont timerFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 74);
        TTFont labelFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), textSize);
        TTFont labelFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), textSize);
        //Setup the fonts which will encode the text to be displayed in this table

        timer = new GameTimer(0, timerFont, Color.WHITE);
        //Set up the game's internal timer

        phaseNumberLabel = new Label("", new Label.LabelStyle(labelFontRegular.font(), Color.WHITE));
        phaseNumberLabel.setAlignment(Align.center);
        //Set up the label that identifies the number of the game's current phase

        phaseDescriptionLabel = new Label("", new Label.LabelStyle(labelFontLight.font(), Color.WHITE));
        phaseDescriptionLabel.setAlignment(Align.center);
        //Set up the label that describes game's current phase

        add(timer).height(100);
        row();
        add(phaseNumberLabel).width(width).center();
        row();
        add(phaseDescriptionLabel).width(width).center();
    }

    /**
     * Method allowing other classes to update the text displayed in the phaseNumberLabel and phaseDescriptionLabel
     * objects
     *
     * @param phase The game's current phase
     */
    public void updateLabels(int phase) {
        phaseNumberLabel.setText("PHASE " + phase);

        switch (phase) {
            case (1):
                phaseDescriptionLabel.setText("ACQUIRE A TILE");
                break;
            case (2):
                phaseDescriptionLabel.setText("BUY ROBOTICONS");
                break;
            case (3):
                phaseDescriptionLabel.setText("PLACE ROBOTICONS");
                break;
            case (4):
                phaseDescriptionLabel.setText("PRODUCTION");
                break;
            case (5):
                phaseDescriptionLabel.setText("BUY/SELL RESOURCES");
                break;
            default:
                phaseDescriptionLabel.setText("UNKNOWN");
                break;
        }
    }
}
