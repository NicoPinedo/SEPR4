package main.drtn.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class LabelledElement extends Table {

    /**
     * Constructor for the class, which pairs an actor to a complementary label around a spatial framework that can
     * be rendered to the screen directly as an independent actor itself
     * Specifically creates a table, a new label of the provided parameters and a single table row containing
     * the aforementioned label in one cell and a specified actor in the other
     *
     * @param labelText The text visualised by the complementary label
     * @param labelFont The complementary label's font
     * @param labelColor The complementary label's colour
     * @param labelRight When this is true, the label will be set to the right of the element that this class pairs
     *                   with it
     * @param actor The actor to be paired with the complementary label in the object's spatial framework
     * @param labelSpacing The total width taken up by the object's label
     * @param actorSpacing The total width taken up by the object's actor
     */
    private LabelledElement(String labelText, TTFont labelFont, Color labelColor, boolean labelRight, Actor actor, float labelSpacing, float actorSpacing) {
        super();
        //Set up table format

        if (!labelRight) {
            if (labelSpacing == 0) {
                this.add(new Label(labelText, new Label.LabelStyle(labelFont.font(), labelColor)));
            } else {
                this.add(new Label(labelText, new Label.LabelStyle(labelFont.font(), labelColor))).left().width(labelSpacing);
            }

            if (actorSpacing == 0) {
                this.add(actor);
            } else
            {
                this.add(actor).width(actorSpacing);
            }
        } else {
            if (actorSpacing == 0) {
                this.add(actor);
            } else {
                this.add(actor).left().width(actorSpacing);
            }

            if (labelSpacing == 0) {
                this.add(new Label(labelText, new Label.LabelStyle(labelFont.font(), labelColor)));
            } else {
                this.add(new Label(labelText, new Label.LabelStyle(labelFont.font(), labelColor))).width(labelSpacing);
            }
        }
        //Add label and actor to the table in whatever order the user specifies
    }

    /**
     * Constructor for the class, which pairs an actor to a complementary label around a spatial framework that can
     * be rendered to the screen directly as an independent actor itself
     * Specifically creates a table, a new label of the provided parameters and a single table row containing
     * the aforementioned label in one cell and a specified actor in the other
     * Overloaded constructor that automatically sets the complementary label to the left of the paired actor
     *
     * @param labelText The text visualised by the complementary label
     * @param labelFont The complementary label's font
     * @param labelColor The complementary label's colour
     * @param actor The actor to be paired with the complementary label in the object's spatial framework
     * @param labelSpacing The total width taken up by the object's label
     * @param actorSpacing The total width taken up by the object's actor
     */
    public LabelledElement(String labelText, TTFont labelFont, Color labelColor, Actor actor, float labelSpacing, float actorSpacing) {
        this(labelText, labelFont, labelColor, false, actor, labelSpacing, actorSpacing);
    }
}
