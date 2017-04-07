package main.drtn.game.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import main.drtn.game.entity.Roboticon;
import main.drtn.game.entity.Chancellor;


public class Drawer {
    private static SpriteBatch batch;
    private static SpriteBatch textDrawBatch;
    private static ShapeRenderer renderer;
    private static Sprite roboticonSprite;
    private static Sprite chancellorSprite;
    private static TTFont defaultTTFont;
    private static BitmapFont defaultFont;
    private static BitmapFont font04b08;
    private static GlyphLayout glyphLayout;
    private static int roboCustOffsetX[] = {56, 56, 56};
    private static int roboCustOffsetY[] = {15, 28, 41};
    private static Color roboCustColours[] = {Color.BLACK, Color.WHITE, Color.WHITE};

    //new method for assessment 3
    static {
        roboticonSprite = new Sprite(new Texture("roboticon/roboticon.png"));
        chancellorSprite = new Sprite(new Texture("image/chancellor.png")); //TODO Get chancellor image path
        defaultTTFont = new TTFont(Gdx.files.internal("font/earthorbiter.ttf"),
                12, 1, Color.BLACK, false);
        defaultFont = defaultTTFont.font();

        // Load 8px bitmap retro style (that is clear to see) font.
        FreeTypeFontGenerator TTFGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/04B_08__.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter TTFStyle = new FreeTypeFontGenerator.FreeTypeFontParameter();

        TTFStyle.size = 8;
        TTFStyle.color = Color.WHITE;
        TTFStyle.borderWidth = 0;
        TTFStyle.hinting = FreeTypeFontGenerator.Hinting.None;

        font04b08 = TTFGenerator.generateFont(TTFStyle);

        try {
            batch = new SpriteBatch();
            renderer = new ShapeRenderer();
            glyphLayout = new GlyphLayout();

            // Setup camera and the projection matrix for text method.
            Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            textDrawBatch = new SpriteBatch();
            textDrawBatch.setProjectionMatrix(camera.combined);
        } catch (Exception ex) {
            System.out.println("Failed to init. drawer resources (ignore this error in test)");
            ex.printStackTrace();
        }
    }

    /**
     * Holds game-state
     */
    private Game game;

    /**
     * Class constructor
     * Stores the game's state inside the drawer class
     *
     * @param game the game object you wish to draw within
     */
    public Drawer(Game game) {
        this.game = game;
        //Import current game-state
    }

    /**
     * Draws a rectangle on the next frame to be rendered
     * Works by kickstarting a rendering pipeline and drawing a rectangle in that pipeline before disposing of it again
     *
     * @param type Defines the type of rectangle to be drawn: it can be filled or line-only
     * @param color Defines the colour of the rectangle to be drawn
     * @param x X-coordinate of the new rectangle's top-left corner
     * @param y Y-coordinate of the new rectangle's top-left corner
     * @param width Width of the new rectangle
     * @param height Height of the new rectangle
     */
    //all below has been refactored for assessment3 in this class
    private void rectangle(ShapeRenderer.ShapeType type, Color color, int x, int y, int width, int height, int thickness) {
        synchronized (renderer) {
            renderer.begin(type);
            renderer.setColor(color);

            int baseY = Gdx.graphics.getHeight() - y - height;
            for (int i = 0; i < thickness; i++) {
                renderer.rect(x + i, baseY + i, width - (i * 2), height - (i * 2));
            }

            renderer.end();
        }
    }

    /**
     * Draws a solid-coloured rectangle on the next frame to be rendered
     * Overloaded variant of the [rectangle()] method which automatically determines the resultant rectangle's
     * rendering method
     *
     * @param color Defines the colour of the rectangle to be drawn
     * @param x X-coordinate of the new rectangle's top-left corner
     * @param y Y-coordinate of the new rectangle's top-left corner
     * @param width Width of the new rectangle
     * @param height Height of the new rectangle
     */
    public void filledRectangle(Color color, int x, int y, int width, int height) {
        rectangle(ShapeRenderer.ShapeType.Filled, color, x, y, width, height, 1);
    }

    /**
     * Draws a line-only rectangle on the next frame to be rendered
     * Overloaded variant of the [rectangle()] method which automatically determines the resultant rectangle's
     * rendering method
     *
     * @param color Defines the colour of the rectangle to be drawn
     * @param x X-coordinate of the new rectangle's top-left corner
     * @param y Y-coordinate of the new rectangle's top-left corner
     * @param width Width of the new rectangle
     * @param height Height of the new rectangle
     */
    public void lineRectangle(Color color, int x, int y, int width, int height, int thickness) {
        rectangle(ShapeRenderer.ShapeType.Line, color, x, y, width, height, thickness);
    }

    /**
     * Draws a bordered, solid-coloured rectangle on the next frame to be rendered
     * This basically draws a filled rectangle and a line-only rectangle of equal dimensions in the same place
     *
     * @param fillColor Defines the fill-colour of the rectangle to be drawn
     * @param lineColor Defines the line-colour of the rectangle to be drawn
     * @param x X-coordinate of the new rectangle's top-left corner
     * @param y Y-coordinate of the new rectangle's top-left corner
     * @param width Width of the new rectangle
     * @param height Height of the new rectangle
     */
    public void borderedRectangle(Color fillColor, Color lineColor, int x, int y, int width, int height, int thickness) {
        rectangle(ShapeRenderer.ShapeType.Filled, fillColor, x, y, width, height, 1);
        rectangle(ShapeRenderer.ShapeType.Line, lineColor, x, y, width, height, thickness);
    }

    /**
     * Prints text directly on to the next frame, foregoing the need to generate any labels or scenes
     * Works by orthographically projecting the bitmaps in the provided TTFont object's internal BitmapFont during
     * a rendering pipeline which only exists while the method itself exists
     *
     * @param text The text to be printed
     * @param font The font of the text to be printed
     * @param x The X-coordinate of the text's location
     * @param y The Y-coordinate of the text's location
     */
    public void text(String text, TTFont font, float x, float y) {
        textDrawBatch.begin();
        //Start the rendering batch

        font.font().draw(textDrawBatch, text, x - (Gdx.graphics.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - y);
        //Print the provided text to the screen...
        //...specifically by simulating the printing process through the orthographic projection of a 3D textual object

        textDrawBatch.end();
    }

    /**
     * Draws debug lines around all actors on the specified stage
     *
     * @param stage The stage holding the actors around which to draw debug lines
     */
    public void debug(Stage stage) {
        for (Actor a : stage.getActors()) {
            a.debug();
        }
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     * @param width The width of the cell in which the provided actor is to be placed
     * @param height The height of the cell in which the provided actor is to be placed
     * @param padTop The top-most padding of the cell in which the provided actor is to be placed
     * @param padLeft The left-most padding of the cell in which the provided actor is to be placed
     * @param padBottom The bottom-most padding of the cell in which the provided actor is to be placed
     * @param padRight The right-most padding of the cell in which the provided actor is to be placed
     * @param colSpan The number of columns over which the new cell will span
     */
    public void addTableRow(Table table, Actor actor, float width, float height, float padTop, float padLeft, float padBottom, float padRight, int colSpan) {
        if (width == 0 && height == 0) {
            table.row().colspan(colSpan);
        } else {
            table.row().colspan(colSpan).size(width, height);
        }

        table.add(actor).pad(padTop, padLeft, padBottom, padRight);
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     * Assumes that the new cell to be created has a width of 1 column
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     * @param width The width of the cell in which the provided actor is to be placed
     * @param height The height of the cell in which the provided actor is to be placed
     * @param padTop The top-most padding of the cell in which the provided actor is to be placed
     * @param padLeft The left-most padding of the cell in which the provided actor is to be placed
     * @param padBottom The bottom-most padding of the cell in which the provided actor is to be placed
     * @param padRight The right-most padding of the cell in which the provided actor is to be placed
     */
    public void addTableRow(Table table, Actor actor, float width, float height, float padTop, float padLeft, float padBottom, float padRight) {
        addTableRow(table, actor, width, height, padTop, padLeft, padBottom, padRight, 1);
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     * Fits the dimensions of the new cell around the contents of the surrounding cells and the provided actor's size
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     * @param padTop The top-most padding of the cell in which the provided actor is to be placed
     * @param padLeft The left-most padding of the cell in which the provided actor is to be placed
     * @param padBottom The bottom-most padding of the cell in which the provided actor is to be placed
     * @param padRight The right-most padding of the cell in which the provided actor is to be placed
     * @param colSpan The number of columns over which the new cell will span
     */
    public void addTableRow(Table table, Actor actor, float padTop, float padLeft, float padBottom, float padRight, int colSpan) {
        addTableRow(table, actor, 0, 0, padTop, padLeft, padBottom, padRight, colSpan);
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     * Assumes that the new cell to be created has a width of 1 column
     * Fits the dimensions of the new cell around the contents of the surrounding cells and the provided actor's size
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     * @param padTop The top-most padding of the cell in which the provided actor is to be placed
     * @param padLeft The left-most padding of the cell in which the provided actor is to be placed
     * @param padBottom The bottom-most padding of the cell in which the provided actor is to be placed
     * @param padRight The right-most padding of the cell in which the provided actor is to be placed
     */
    public void addTableRow(Table table, Actor actor, float padTop, float padLeft, float padBottom, float padRight) {
        addTableRow(table, actor, 0, 0, padTop, padLeft, padBottom, padRight, 1);
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     * Does not pad the new cell
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     * @param width The width of the cell in which the provided actor is to be placed
     * @param height The height of the cell in which the provided actor is to be placed
     * @param colSpan The number of columns over which the new cell will span
     */
    public void addTableRow(Table table, Actor actor, float width, float height, int colSpan) {
        addTableRow(table, actor, width, height, 0, 0, 0, 0, colSpan);
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     * Assumes that the new cell to be created has a width of 1 column
     * Does not pad the new cell
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     * @param width The width of the cell in which the provided actor is to be placed
     * @param height The height of the cell in which the provided actor is to be placed
     */
    public void addTableRow(Table table, Actor actor, float width, float height) {
        addTableRow(table, actor, width, height, 0, 0, 0, 0, 1);
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     * Fits the dimensions of the new cell around the contents of the surrounding cells and the provided actor's size
     * Does not pad the new cell
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     * @param colSpan The number of columns over which the new cell will span
     */
    public void addTableRow(Table table, Actor actor, int colSpan) {
        addTableRow(table, actor, 0, 0, 0, 0, 0, 0, colSpan);
    }

    /**
     * Automatically adds an actor on to a new row within a specified table
     * Exists primarily to cut down on statement quantities
     * Assumes that the new cell to be created has a width of 1 column
     * Fits the dimensions of the new cell around the contents of the surrounding cells and the provided actor's size
     * Does not pad the new cell
     *
     * @param table The table to be expanded
     * @param actor The actor to add to the provided table
     */
    public void addTableRow(Table table, Actor actor) {
        addTableRow(table, actor, 0, 0, 0, 0, 0, 0, 1);
    }

    /**
     * Stretches the last row in the provided table to span across the maximum number of columns in that table
     *
     * @param table The table containing the cell to be stretched
     */
    public void stretchCurrentCell(Table table) {
        table.getCells().items[table.getRows()].fillX();
    }

    /**
     * Simplifies simultaneously changing TextButtons' colours and enabling/disabling them on the fly
     *
     * @param button The button to be enabled/disabled
     * @param enabled The button's new status
     * @param buttonColor The new colour that the button should assume
     */
    public void toggleButton(TextButton button, boolean enabled, Color buttonColor) {
        button.getLabel().setColor(buttonColor);
        //Assign a new colour to the specified label

        button.setTouchable(enabled ? Touchable.enabled : Touchable.disabled);
    }

    public void drawRoboticon(Roboticon roboticon, float x, float y) {
        batch.begin();

        batch.draw(roboticonSprite, x, y, 64, 64);

        int[] levels;
        if (roboticon == null) {
            levels = new int[]{0, 0, 0};
        } else {
            levels = roboticon.getLevel();
        }

        for(int i = 0; i < 3; i++) {
            glyphLayout.setText(font04b08, "" + levels[i], roboCustColours[i],
                    0, Align.center, false);
            font04b08.draw(batch, glyphLayout, x + roboCustOffsetX[i], y + roboCustOffsetY[i]);

        }
        batch.end();
    }

    public void drawChancellor(float x, float y){
        batch.begin();
        batch.draw(chancellorSprite, x, y, 32, 44); //TODO get Width/Height
        batch.end();
    }
}
