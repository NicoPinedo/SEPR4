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

import com.badlogic.gdx.utils.Array;
import com.drtn.game.entity.Tile;
import com.drtn.game.enums.ResourceType;
import com.drtn.game.exceptions.InvalidResourceTypeException;


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

        this.plotRegister = new Array<Tile>();
        //Establish the separate LandPlot stack to track affected tiles



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


    public String getDescription(){
        return this.description;
    }
}
