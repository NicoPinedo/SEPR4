package main.drtn.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Overlay extends Stage {

    /**
     * Table providing spatial framework for the overlay
     */
    private Table table;

    /**
     * Width of the overlay region
     */
    private float regionWidth;

    /**
     * Height of the overlay region
     */
    private float regionHeight;

    /**
     * Distance between the centre of the overlay and the centre of window on the X-axis (in pixels)
     */
    private float xOffset;

    /**
     * Distance between the centre of the overlay and the centre of window on the Y-axis (in pixels)
     */
    private float yOffset;

    /**
     * Thickness of the overlay region's border
     */
    private int lineThickness;

    /**
     * The colour of the overlay's core
     */
    private Color fillColor;

    /**
     * The colour of the overlay's border
     */
    private Color lineColor;



    /**
     * Creates a stage that itself places a table of the specified parameters to an offset away from centre of the screen
     * The overlay's [draw()] method is unlike that of the standard Stage class as it also draws a bordered
     * rectangle behind the overlay region, hence rendering it a true overlay
     *
     * @param fillColor The colour of the overlay's background
     * @param lineColor The colour of the overlay's border
     * @param regionWidth The width of the overlay
     * @param regionHeight The height of the overlay
     * @param xOffset Distance between the centre of the overlay and the centre of window on the X-axis (in pixels)
     * @param yOffset Distance between the centre of the overlay and the centre of window on the Y-axis (in pixels)
     * @param lineThickness The thickness of the overlay's border
     */
    public Overlay(Color fillColor, Color lineColor, float regionWidth, float regionHeight, float xOffset, float yOffset, int lineThickness) {
        super();
        //Construct the core stage

        this.fillColor = fillColor;
        this.lineColor = lineColor;
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.lineThickness = lineThickness;
        //Import overlay size/colour variables

        table = new Table();
        table.setBounds(((Gdx.graphics.getWidth() - this.regionWidth) / 2) + xOffset, ((Gdx.graphics.getHeight() - this.regionHeight) / 2) - yOffset, regionWidth, regionHeight);
        //Instantiate and prepare the table which will provide the overlay's spatial framework
        //This table will always inhabit the centre of the screen when this object is being drawn

        this.addActor(table);
        //Bind the overlay's spatial framework to the core stage
    }

    /**
     * Creates a stage that itself places a table of the specified parameters in the centre of the screen
     * The overlay's [draw()] method is unlike that of the standard Stage class as it also draws a bordered
     * rectangle behind the overlay region, hence rendering it a true overlay
     *
     * Overloaded constructor places the overlay at the very centre of the screen
     *
     * @param fillColor The colour of the overlay's background
     * @param lineColor The colour of the overlay's border
     * @param regionWidth The width of the overlay
     * @param regionHeight The height of the overlay
     * @param lineThickness The thickness of the overlay's border
     */
    public Overlay(Color fillColor, Color lineColor, float regionWidth, float regionHeight, int lineThickness) {
        this(fillColor, lineColor, regionWidth, regionHeight, 0, 0, lineThickness);
    }

    /**
     * Creates a stage that itself places a table of the specified parameters in the centre of the screen
     * The overlay's [draw()] method is unlike that of the standard Stage class as it also draws a bordered
     * rectangle behind the overlay region, hence rendering it a true overlay
     *
     * Overloaded constructor assumes that the overlay's size is the size of the game's window at the time of the
     * overlay's declaration
     *
     * @param fillColor The colour of the overlay's background
     * @param lineColor The colour of the overlay's border
     * @param lineThickness The thickness of the overlay's border
     */
    public Overlay(Color fillColor, Color lineColor, int lineThickness) {
        this(fillColor, lineColor, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, lineThickness);
    }

    /**
     * Draws every actor on the core stage (along with a complementary bordered background) on to the next frame in
     * the game's rendering pipeline
     */
    @Override
    public void draw() {
        ShapeRenderer renderer = new ShapeRenderer();
        //Establish shape-renderer

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        //Set the renderer up to draw the overlay region's background block

        renderer.setColor(fillColor);
        //Set the colour of the overlay region's background

        renderer.rect((int) (((Gdx.graphics.getWidth() - this.regionWidth) / 2) + xOffset), (int) (((Gdx.graphics.getHeight() - this.regionHeight) / 2) - yOffset), regionWidth, regionHeight);
        //Draw the overlay region on the screen

        renderer.end();
        //Stop the renderer from drawing the overlay's background

        renderer.begin(ShapeRenderer.ShapeType.Line);
        //Set the renderer up to draw the overlay region's border

        renderer.setColor(lineColor);
        //Set the colour of the overlay region's border

        for (int i = 0; i < lineThickness; i++) {
            renderer.rect((int) (((Gdx.graphics.getWidth() - this.regionWidth) / 2) + xOffset) + i, (int) (((Gdx.graphics.getHeight() - this.regionHeight) / 2) - yOffset) + i, regionWidth - (i * 2), regionHeight - (i * 2));
        }
        //Draw the overlay region's border on the screen (by executing one draw-call for each line of pixels comprising the border)

        renderer.end();
        //Shut the renderer down after the overlay region has been drawn

        renderer.dispose();
        //Get rid of the renderer now that it isn't useful anymore

        super.draw();
        //Draw the overlay's internal contents to the screen
    }

    /**
     * Re-sizes the overlay to cover the specified parameters
     *
     * @param regionWidth The overlay's new width
     * @param regionHeight The overlay's new height
     */
    public void resize(float regionWidth, float regionHeight) {
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;

        table.setBounds(((Gdx.graphics.getWidth() - this.regionWidth) / 2) + xOffset, ((Gdx.graphics.getHeight() - this.regionHeight) / 2) - yOffset, regionWidth, regionHeight);
    }

    public void reposition(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        table.setBounds(((Gdx.graphics.getWidth() - this.regionWidth) / 2) + xOffset, ((Gdx.graphics.getHeight() - this.regionHeight) / 2) - yOffset, regionWidth, regionHeight);
    }

    /**
     * Changes the thickness of the overlay's border
     *
     * @param lineThickness The thickness of the overlay's new border (in pixels)
     */
    public void changeLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    /**
     * Changes the colour of the overlay's border
     *
     * @param lineColor The colour of the overlay's new border
     */
    public void changeLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }



    /**
     * Returns the table that serves as the overlay's spatial framework
     *
     * @return Table The overlay's internal table
     */
    public Table table(){
        return table;
    }
}