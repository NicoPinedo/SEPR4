/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

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

public class TradeOverlay extends Overlay {

    private static TTFont headerFontRegular;
    private static TTFont smallFontRegular;
    private static TTFont smallFontLight;

    static {
        headerFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 24);
        smallFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        smallFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);
    }

    /**
     * Label encoding the identity of the impending trade request's sender
     */
    private Label senderLabel;

    /**
     * Label encoding the amounts of resources offered to the recipient of the impending trade request
     */
    private Label offeredResourcesLabel;

    /**
     * Label encoding the price-tag put on the impending trade request
     */
    private Label priceLabel;

    /**
     * Button allowing players to accept incoming trade requests
     */
    private TextButton acceptButton;

    /**
     * Button allowing players to reject incoming trade requests
     */
    private TextButton rejectButton;

    /**
     * Constructs the overlay that will describe incoming trade requests (referencing their senders, their offerings
     * and their prices) to their recipients and present such recipients with options to accept or reject those requests
     */
    public TradeOverlay() {
        super(Color.GRAY, Color.WHITE, 460, 190, 3);
        //Create an overlay of the specified parameters

        senderLabel = new Label("", new Label.LabelStyle(smallFontRegular.font(), Color.WHITE));
        senderLabel.setAlignment(Align.left);
        offeredResourcesLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        offeredResourcesLabel.setAlignment(Align.left);
        priceLabel = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));
        //Construct all of the labels that will populate the interface

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = smallFontRegular.font();
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        //Define the visual parameters of the buttons in the interface that will accept or reject incoming trade requests

        acceptButton = new TextButton("ACCEPT", textButtonStyle);
        rejectButton = new TextButton("REJECT", textButtonStyle);
        //Construct the buttons that will accept or reject incoming trade requests

        Table responsePanel = new Table();
        responsePanel.add(acceptButton).padRight(20);
        responsePanel.add(rejectButton).padLeft(20);
        //Add those buttons to a sub-table to ensure that they are spaced correctly

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

    /**
     * Sets the function of the button that was made to allow players to accept incoming trade requests
     *
     * @param event The function to be assigned to the overlay's internal acceptButton object
     */
    public void setAcceptButtonFunction(ChangeListener event) {
        acceptButton.addListener(event);
    }

    /**
     * Sets the function of the button that was made to allow players to decline incoming trade requests
     *
     * @param event The function to be assigned to the overlay's internal rejectButton object
     */
    public void setRejectButtonFunction(ChangeListener event) {
        rejectButton.addListener(event);
    }

    /**
     * Sets the name to be encoded by the label made to identify incoming trade requests' senders
     * Assume that players' "names" are those of their colleges for the sake of clarity
     *
     * @param name The name of the incoming trade request's sender
     */
    public void setSenderLabelText(String name) {
        senderLabel.setText(name + " is offering...");
    }

    /**
     * Sets the list of resources to encoded by the label made to identify incoming trade requests' offerings
     *
     * @param text The bounty to be offered through the incoming trade request
     */
    public void setOfferedResourcesLabelText(String text) {
        offeredResourcesLabel.setText(text);
    }

    /**
     * Sets the sum of money to encoded by the label made to identify incoming trade requests' prices
     *
     * @param text The price-tag attached the incoming trade request
     */
    public void setPriceLabeltext(String text) {
        priceLabel.setText(text);
    }

    /**
     * Prepares the overlay to present the terms of an incoming trade request
     *
     * @param name The name of the incoming trade request's sender
     * @param offeredResources The bounty to be offered through the incoming trade request
     * @param price The price-tag attached the incoming trade request
     */
    public void setOffer(String name, String offeredResources, String price) {
        senderLabel.setText(name + " is offering...");
        offeredResourcesLabel.setText(offeredResources);
        priceLabel.setText(price);
    }
}