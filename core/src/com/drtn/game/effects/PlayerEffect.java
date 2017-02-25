/**
 * @author DRTN
 * Team Website with download:
 * https://misterseph.github.io/DuckRelatedFractalProject/
 *
 * This Class contains either modifications or is entirely new in Assessment 3
 *
 * If you are in any doubt a complete changelog can be found here:
 * https://github.com/NotKieran/DRTN-Fractal/compare/Fractal_Initial...development
 *
 * And a more concise report can be found in our Change3 document.
 **/

package io.github.teamfractal.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.GameScreen;
import io.github.teamfractal.screens.Overlay;


public class PlayerEffect {

    /**
     * The name of the effect
     */
    private String name;

    /**
     * A description of the effect
     */
    private String description;

    /**
     * Array holding the inventory modifiers that the effect imposes
     * These are either to be added/subtracted to/from the player's current resource counts or multiplied by them,
     * depending on the value of the [multiply] variable declared below
     */
    private float[] modifiers;

    /**
     * Determines whether the effect is to add to (or subtract from) the player's resources or to multiply them by set
     * factors
     */
    private boolean multiply;

    /**
     * Object containing a method that the effect can automatically trigger if and when it is run
     */
    private Runnable runnable;

    /**
     * Overlay to provide a visual indication of the effect's applications and influences
     */
    private Overlay overlay;

    /**
     * Constructor that imports the parameters of the effect along with a custom block of code in which it can be used
     *
     * @param name The name of the effect
     * @param description A description of the effect
     * @param oreModifier The ore-count modifier that the effect can impose
     * @param energyModifier The energy-count modifier that the effect can impose
     * @param foodModifier The food-count modifier that the effect can impose
     * @param moneyModifier The money-count modifier that the effect can impose
     * @param multiply Determines whether the effect's parameters are to be additive or multiplicative when applied to
     *                 a player's resource-counts
     * @param runnable The code to be executed when the effect is imposed through natural means
     */
    public PlayerEffect(String name, String description, float oreModifier, float energyModifier, float foodModifier, float moneyModifier, boolean multiply, Runnable runnable) {
        this.name = name;
        this.description = description;
        //Store the effect's name and description for future reference

        this.modifiers = new float[4];
        this.modifiers[0] = oreModifier;
        this.modifiers[1] = energyModifier;
        this.modifiers[2] = foodModifier;
        this.modifiers[3] = moneyModifier;
        //Import the effect's modifiers

        this.multiply = multiply;
        //Import the boolean setting to determine whether the aforementioned modifiers are to be added to (or
        //subtracted from) players' existing resource counts, or whether they are to be taken as multiplicative
        //factors instead

        this.runnable = runnable;
        //Import the code to be run whenever the effect is imposed (if any is provided at all)

        this.overlay = new Overlay(Color.OLIVE, Color.WHITE, 3);
        //Construct a visual interface through which the effect can be identified
    }

    /**
     * Method that populates the effect's associated overlay
     */
    public void constructOverlay(final GameScreen gameScreen) {
        TextButton.TextButtonStyle overlayButtonStyle = new TextButton.TextButtonStyle();
        overlayButtonStyle.font = gameScreen.getGame().headerFontRegular.font();
        overlayButtonStyle.pressedOffsetX = -1;
        overlayButtonStyle.pressedOffsetY = -1;
        overlayButtonStyle.fontColor = Color.WHITE;
        //Set the visual parameters for the [CLOSE] button on the overlay

        Label headerLabel = new Label("PLAYER EFFECT IMPOSED", new Label.LabelStyle(gameScreen.getGame().headerFontRegular.font(), Color.CHARTREUSE));
        Label titleLabel = new Label(name, new Label.LabelStyle(gameScreen.getGame().headerFontLight.font(), Color.WHITE));
        Label descriptionLabel = new Label(description, new Label.LabelStyle(gameScreen.getGame().smallFontLight.font(), Color.WHITE));
        //Construct labels to state the type, name and description of this effect

        headerLabel.setAlignment(Align.left);
        titleLabel.setAlignment(Align.right);
        descriptionLabel.setAlignment(Align.left);
        //Align the aforementioned labels against the edges of the overlay's internal table

        overlay.table().add(headerLabel).width(330).left();
        overlay.table().add(titleLabel).width(descriptionLabel.getWidth() - 330).right();
        overlay.table().row();
        overlay.table().add(descriptionLabel).left().colspan(2).padTop(5).padBottom(20);
        //...and then add them to it

        overlay.table().row().colspan(2);
        TextButton closeButton = new TextButton("CLOSE", overlayButtonStyle);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.removeOverlay();
            }
        });

        overlay.table().add(closeButton);
        //Set up and add a [CLOSE] button to the overlay

        overlay.resize(descriptionLabel.getWidth() + 20, headerLabel.getHeight() + descriptionLabel.getHeight() + closeButton.getHeight() + 35);
        //Resize the overlay to fit around the sizes of the labels that were added to it
    }

    /**
     * Imposes the effect on the player by changing the resources that they have
     * Their resources can be added to, subtracted from, multiplied or divided
     *
     * @param player The player that is to be affected
     */
    public void impose(Player player) {
        if (multiply) {
            player.setResource(ResourceType.ORE, (int) ((float) player.getOre() * modifiers[0]));
            player.setResource(ResourceType.ENERGY, (int) ((float) player.getEnergy() * modifiers[1]));
            player.setResource(ResourceType.FOOD, (int) ((float) player.getFood() * modifiers[2]));
            player.setMoney((int) ((float) player.getMoney() * modifiers[3]));
        } else {
            player.setResource(ResourceType.ORE, player.getOre() + (int) modifiers[0]);
            player.setResource(ResourceType.ENERGY, player.getEnergy() + (int) modifiers[1]);
            player.setResource(ResourceType.FOOD, player.getFood() + (int) modifiers[2]);
            player.setMoney(player.getMoney() + (int) modifiers[3]);
        }
    }

    /**
     * Getter for the overlay
     * @return The overlay of the effect
     */
    public Overlay overlay() { return overlay; }

    /**
     * Executes the runnable
     */
    public void executeRunnable() {
        runnable.run();
    }
}
