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

package com.drtn.game.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.drtn.game.entity.Tile;
import com.drtn.game.enums.ResourceType;
import com.drtn.game.exceptions.InvalidResourceTypeException;
import com.drtn.game.screens.GameScreen;
import com.drtn.game.util.Overlay;
import com.drtn.game.util.TTFont;


public class PlotEffect extends Array<Float[]> {

    /**
     * The name of the effect
     */
    private String name;

    /**
     * A description of the effect
     */
    private String description;

    /**
     * Object containing a method that the effect can automatically trigger if and when it is run
     */
    private Runnable runnable;

    /**
     * Array storing all of the plots to have been affected by this effect (in the order by which they were affected)
     */
    private Array<Tile> plotRegister;

    /**
     * Overlay to provide a visual indication of the effect's presence and influences
     */
    private Overlay overlay;
    private GameScreen gameScreen;

    /**
     * Constructor that imports the parameters of the effect along with a custom block of code in which it can be used
     *
     * @param name The name of the effect
     * @param description A description of the effect
     * @param modifiers The production modifiers that the effect can impose {0: ORE | 1: ENERGY | 2: FOOD}
     * @param runnable The code to be executed when the effect is imposed through natural means
     */
    public PlotEffect(String name, String description, Float[] modifiers, Runnable runnable,GameScreen gameScreen) {
        this.name = name;
        this.description = description;
        //Stores the effect's name and description for future reference

        super.add(modifiers);
        //Store the effect's modifiers at the base of the internal stack

        this.runnable = runnable;
        this.gameScreen = gameScreen;
        //Assign the effect to the proprietary method provided

        this.plotRegister = new Array<Tile>();
        //Establish the separate LandPlot stack to track affected tiles

        this.overlay = new Overlay(gameScreen.getGame(), Color.GRAY, Color.WHITE, 900, 200, 3);
        //Construct a visual interface through which the effect can be identified
    }



    /**
     * Method that populates the effect's associated overlay
     */
    public void constructOverlay(final GameScreen gameScreen) {
        TextButton.TextButtonStyle overlayButtonStyle = new TextButton.TextButtonStyle();
        TTFont gameFont = new TTFont(Gdx.files.internal("font/testfontbignoodle.ttf"), 36);
        overlayButtonStyle.font = gameFont.font();
        overlayButtonStyle.pressedOffsetX = -1;
        overlayButtonStyle.pressedOffsetY = -1;
        overlayButtonStyle.fontColor = Color.WHITE;
        //Set the visual parameters for the [CLOSE] button on the overlay

        Label headerLabel = new Label("PLOT EFFECT IMPOSED", new Label.LabelStyle(gameFont.font(), Color.YELLOW));
        Label titleLabel = new Label(name, new Label.LabelStyle(gameFont.font(), Color.WHITE));
        Label descriptionLabel = new Label(description, new Label.LabelStyle(gameFont.font(), Color.WHITE));
        //Construct labels to state the type, name and description of this effect

        headerLabel.setAlignment(Align.left);
        titleLabel.setAlignment(Align.right);
        descriptionLabel.setAlignment(Align.left);
        //Align the aforementioned labels against the edges of the overlay's internal table...

        overlay.table().add(headerLabel).width(300).left();
        overlay.table().add(titleLabel).width(descriptionLabel.getWidth() - 300).right();
        overlay.table().row();
        overlay.table().add(descriptionLabel).left().colspan(2).padTop(5).padBottom(20);
        //...and then add them to it

        overlay.table().row().colspan(2);
        TextButton closeButton = new TextButton("CLOSE", overlayButtonStyle);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.hideEventMessage();
            }
        });

        overlay.table().add(closeButton);
        //Set up and add a [CLOSE] button to the overlay


        //Resize the overlay to fit around the sizes of the labels that were added to it
    }

    /**
     * Imposes the effect's modifiers on the provided plot
     * Assumes that the modifiers to be imposed at any given time will be at the head of the internal stack
     *
     * @param tile The plot to be affected
     * @param mode The mode of effect [0: ADD | 1: MULTIPLY | 2: OVERWRITE]
     */
    public void impose(Tile tile, int mode) throws InvalidResourceTypeException {
        Float[] originalModifiers = new Float[3];
        Float[] newModifiers;
        //Declare temporary arrays to handle modifier modifications

        newModifiers = super.pop();
        //Assume that the modifiers on the top of the stack are the modifiers to be imposed
        originalModifiers[0] = Float.valueOf(tile.getResource(ResourceType.ORE));
        originalModifiers[1] = Float.valueOf(tile.getResource(ResourceType.ENERGY));
        originalModifiers[2] = Float.valueOf(tile.getResource(ResourceType.FOOD));


            //Save each of the specified tile's original modifiers

            switch (mode) {
                case (0):
                    tile.setResource(ResourceType.ORE, (int) (tile.getResource(ResourceType.ORE) + newModifiers[0]));
                    tile.setResource(ResourceType.ENERGY, (int) (tile.getResource(ResourceType.ENERGY) + newModifiers[1]));
                    tile.setResource(ResourceType.FOOD, (int) (tile.getResource(ResourceType.FOOD) + newModifiers[2]));
                    //MODE 0: Add/subtract to/from the original modifiers
                    break;
                case (1):
                    tile.setResource(ResourceType.ORE, (int) (tile.getResource(ResourceType.ORE) * newModifiers[0]));
                    tile.setResource(ResourceType.ENERGY, (int) (tile.getResource(ResourceType.ENERGY) * newModifiers[1]));
                    tile.setResource(ResourceType.FOOD, (int) (tile.getResource(ResourceType.FOOD) * newModifiers[2]));
                    //MODE 1: Multiply the original modifiers
                    break;
                case (2):
                    tile.setResource(ResourceType.ORE, Math.round(newModifiers[0]));
                    tile.setResource(ResourceType.ENERGY,Math.round(newModifiers[1]));
                    tile.setResource(ResourceType.FOOD,Math.round(newModifiers[2]));
                    //MODE 2: Replace the original modifiers
                    break;
            }


        super.add(originalModifiers);
        //Add the tile's original modifiers to the stack for later access...

        super.add(newModifiers);
        //...and return the imposed modifiers to the top of the stack

        plotRegister.add(tile);
        //Push the plot that was modified on to the appropriate registration stack
    }

    /**
     * Reverts the changes made by the effect to the last plot that it was assigned to
     */
    private void revert() {
        if (plotRegister.size > 0) {
            Float[] originalModifiers;
            Tile lastPlot;

            swapTop();
            originalModifiers = super.pop();
            //Swap the first two modifier arrays at the head of the stack to access the array that was originally
            //bound to the last affected plot

            lastPlot = plotRegister.pop();
            //Retrieve the last plot that this effect was imposed upon


            lastPlot.setResource(ResourceType.ORE, Math.round( originalModifiers[0]));
            lastPlot.setResource(ResourceType.ENERGY,Math.round( originalModifiers[1]));
            lastPlot.setResource(ResourceType.FOOD,Math.round( originalModifiers[2]));
            //Restore the original production modifiers of the aforementioned plot
        }
    }
    /**
     * Reverts all affected tiles back to their original states
     */
    public void revertAll() {
        while (plotRegister.size > 0) {
            revert();
        }
    }
    /**
     * Swaps the positions of the first two values within the internal stack
     */
    private void swapTop() {
        if (super.size > 1) {
            Float[] i = super.pop();
            Float[] j = super.pop();

            super.add(i);
            super.add(j);
        }
    }

    /**
     * Executes the runnable
     */
    public void executeRunnable() {
        runnable.run();
    }

    /**
     * Getter for the overlay
     * @return The overlay of the effect
     */
    public Overlay overlay() { return overlay; }

    public String getDescription(){
        return this.description;
    }
}
