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

    public GameTimer timer;

    private Label phaseNumberLabel;
    private Label phaseDescriptionLabel;

    public PhaseInfoTable() {
        final int width = 240;
        final int textSize = 20;

        TTFont timerFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 74);
        TTFont labelFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), textSize);
        TTFont labelFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), textSize);

        timer = new GameTimer(0, timerFont, Color.WHITE);

        phaseNumberLabel = new Label("", new Label.LabelStyle(labelFontRegular.font(), Color.WHITE));
        phaseNumberLabel.setAlignment(Align.center);

        phaseDescriptionLabel = new Label("", new Label.LabelStyle(labelFontLight.font(), Color.WHITE));
        phaseDescriptionLabel.setAlignment(Align.center);

        add(timer).height(100);
        row();
        add(phaseNumberLabel).width(width).center();
        row();
        add(phaseDescriptionLabel).width(width).center();
    }

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
