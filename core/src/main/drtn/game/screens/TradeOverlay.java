package drtn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import drtn.game.util.Overlay;
import drtn.game.util.TTFont;

/**
 * Created by Joseph on 30/04/2017.
 */
public class TradeOverlay extends Overlay {

    private static TTFont headerFontRegular;
    private static TTFont smallFontRegular;
    private static TTFont smallFontLight;

    static {
        headerFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 24);
        smallFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        smallFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);
    }

    private Label senderLabel;
    private Label offeredResourcesLabel;
    private Label priceLabel;

    private TextButton acceptButton;
    private TextButton rejectButton;

    public TradeOverlay() {
        super(Color.GRAY, Color.WHITE, 460, 190, 3);

        senderLabel = new Label("", new Label.LabelStyle(smallFontRegular.font(), Color.WHITE));
        senderLabel.setAlignment(Align.left);
        offeredResourcesLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        offeredResourcesLabel.setAlignment(Align.left);
        priceLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = smallFontRegular.font();
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;

        acceptButton = new TextButton("ACCEPT", textButtonStyle);
        rejectButton = new TextButton("REJECT", textButtonStyle);

        Table responsePanel = new Table();
        responsePanel.add(acceptButton).padRight(20);
        responsePanel.add(rejectButton).padLeft(20);

        table().add(new Label("TRADE OFFER RECEIVED", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE))).colspan(2).padBottom(20);
        table().row();
        table().add(senderLabel).padRight(20);
        table().add(new Label("...for this price.", new Label.LabelStyle(smallFontRegular.font(), Color.WHITE)));
        table().row();
        table().add(offeredResourcesLabel);
        table().add(priceLabel);
        table().row();
        table().add(responsePanel).colspan(2).padTop(20);
    }

    public void setAcceptButtonFunction(ChangeListener event) {
        acceptButton.addListener(event);
    }

    public void setRejectButtonFunction(ChangeListener event) {
        rejectButton.addListener(event);
    }

    public void setSenderLabelText(String name) {
        senderLabel.setText(name + " is offering...");
    }

    public void setOfferedResourcesLabelText(String text) {
        offeredResourcesLabel.setText(text);
    }

    public void setPriceLabeltext(String text) {
        priceLabel.setText(text);
    }

    public void setOffer(String name, String offeredResources, String price) {
        senderLabel.setText(name + " is offering...");
        offeredResourcesLabel.setText(offeredResources);
        priceLabel.setText(price);
    }
}