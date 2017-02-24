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
import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.screens.GameScreen;
import io.github.teamfractal.screens.Overlay;

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
    private Array<LandPlot> plotRegister;

    /**
     * Overlay to provide a visual indication of the effect's presence and influences
     */
    private Overlay overlay;

    /**
     * Constructor that imports the parameters of the effect along with a custom block of code in which it can be used
     *
     * @param name The name of the effect
     * @param description A description of the effect
     * @param modifiers The production modifiers that the effect can impose {0: ORE | 1: ENERGY | 2: FOOD}
     * @param runnable The code to be executed when the effect is imposed through natural means
     */
    public PlotEffect(String name, String description, Float[] modifiers, Runnable runnable) {
        this.name = name;
        this.description = description;
        //Stores the effect's name and description for future reference

        super.add(modifiers);
        //Store the effect's modifiers at the base of the internal stack

        this.runnable = runnable;
        //Assign the effect to the proprietary method provided

        this.plotRegister = new Array<LandPlot>();
        //Establish the separate LandPlot stack to track affected tiles

        this.overlay = new Overlay(Color.GOLDENROD, Color.WHITE, 3);
        //Construct a visual interface through which the effect can be identified
    }

    /**
     * Overloaded constructor that imports the parameters of the effect and sets it up to be applied to a specific
     * plot in a specific way upon usage
     *
     * @param name The name of the effect
     * @param description A description of the effect
     * @param modifiers The production modifiers that the effect can impose {0: ORE | 1: ENERGY | 2: FOOD}
     * @param plot The plot which the effect is to be applied to
     */
    public PlotEffect(String name, String description, Float[] modifiers, final LandPlot plot, final int mode) {
        this(name, description, modifiers, new Runnable() {
            @Override
            public void run() {
                /*
                Intentionally empty.
                */
            }
        });

        this.runnable = new Runnable() {
            @Override
            public void run() {
                impose(plot, mode);
            }
        };
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

        Label headerLabel = new Label("PLOT EFFECT IMPOSED", new Label.LabelStyle(gameScreen.getGame().headerFontRegular.font(), Color.YELLOW));
        Label titleLabel = new Label(name, new Label.LabelStyle(gameScreen.getGame().headerFontLight.font(), Color.WHITE));
        Label descriptionLabel = new Label(description, new Label.LabelStyle(gameScreen.getGame().smallFontLight.font(), Color.WHITE));
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
                gameScreen.removeOverlay();
            }
        });

        overlay.table().add(closeButton);
        //Set up and add a [CLOSE] button to the overlay

        overlay.resize(descriptionLabel.getWidth() + 20, headerLabel.getHeight() + descriptionLabel.getHeight() + closeButton.getHeight() + 35);
        //Resize the overlay to fit around the sizes of the labels that were added to it
    }

    /**
     * Imposes the effect's modifiers on the provided plot
     * Assumes that the modifiers to be imposed at any given time will be at the head of the internal stack
     *
     * @param plot The plot to be affected
     * @param mode The mode of effect [0: ADD | 1: MULTIPLY | 2: OVERWRITE]
     */
    public void impose(LandPlot plot, int mode) {
        Float[] originalModifiers = new Float[3];
        Float[] newModifiers;
        //Declare temporary arrays to handle modifier modifications

        newModifiers = super.pop();
        //Assume that the modifiers on the top of the stack are the modifiers to be imposed

        for (int i = 0; i < 3; i++) {
            originalModifiers[i] = plot.productionModifiers[i];
            //Save each of the specified tile's original modifiers

            switch (mode) {
                case (0):
                    plot.productionModifiers[i] = plot.productionModifiers[i] + newModifiers[i];
                    //MODE 0: Add/subtract to/from the original modifiers
                    break;
                case (1):
                    plot.productionModifiers[i] = plot.productionModifiers[i] * newModifiers[i];
                    //MODE 1: Multiply the original modifiers
                    break;
                case (2):
                    plot.productionModifiers[i] = newModifiers[i];
                    //MODE 2: Replace the original modifiers
                    break;
            }
        }

        super.add(originalModifiers);
        //Add the tile's original modifiers to the stack for later access...

        super.add(newModifiers);
        //...and return the imposed modifiers to the top of the stack

        plotRegister.add(plot);
        //Push the plot that was modified on to the appropriate registration stack
    }

    /**
     * Reverts the changes made by the effect to the last plot that it was assigned to
     */
    private void revert() {
        if (plotRegister.size > 0) {
            Float[] originalModifiers;
            LandPlot lastPlot;

            swapTop();
            originalModifiers = super.pop();
            //Swap the first two modifier arrays at the head of the stack to access the array that was originally
            //bound to the last affected plot

            lastPlot = plotRegister.pop();
            //Retrieve the last plot that this effect was imposed upon

            for (int i = 0; i < 3; i++) {
                lastPlot.productionModifiers[i] = originalModifiers[i];
            }
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
}
