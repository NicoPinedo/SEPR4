package drtn.game.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import drtn.game.enums.ResourceType;
import drtn.game.exceptions.InvalidResourceTypeException;
import drtn.game.util.Drawer;
import drtn.game.util.TTFont;


public class Tile extends Button {

    private final static int tileXOffset = 256;
    /**
     * Defines width of the tile's tooltip
     */
    private final int tooltipWidth;
    /**
     * Defines height of the tile's tooltip
     */
    private final int tooltipHeight;
    /**
     * Defines distance (in pixels) from cursor point to the lower-right (or upper-right) corner of the tile's tooltip
     * (on both axes)
     */
    private final int tooltipCursorSpace;
    /**
     * Defines internal padding within the tile's tooltip (in pixels)
     */
    private final int tooltipTextSpace;
    /**
     * Defines the fill-colour for the tile's tooltip
     */
    private final Color tooltipFillColor;
    /**
     * Defines the line-colour for the tile's tooltip
     */
    private final Color tooltipLineColor;
    /**
     * Defines the font of the text inside the tile's tooltip
     */
    private final TTFont tooltipFont;
    /**
     * Holds game-state for the purpose of accessing the game's renderer
     */
    private Game game;
    /**
     * Uniquely identifies the tile
     */
    private int ID;
    /**
     * A modifier influencing how much energy is produced.
     */
    private int EnergyCount;
    /**
     * A modifier influencing how much food is produced.
     */
    private int FoodCount;
    /**
     * A modifier influencing how much ore is produced.
     */
    private int OreCount;
    /**
     * A modifier influencing how much ore is produced.
     */
    private boolean landmark;
    /**
     * The player that owns the tile, if it has one.
     */
    private Player owner;
    /**
     * The roboticon that has been placed on the tile.
     */
    private Roboticon roboticonStored;
    /**
     * Object holding executable method that can be assigned to the tile
     */
    private Runnable runnable;
    /**
     * Object defining QOL drawing functions for rectangles and on-screen tables
     * Used in this class to render tooltip regions
     */
    private Drawer drawer;
    /**
     * Boolean variable that's true whenever the tile's tooltip is visible and false otherwise
     */
    private boolean tooltipActive;
    /**
     * Holds the colour of the border to be drawn over the tile when it is claimed
     */
    private Color tileBorderColor;
    /**
     * Determines the thickness of the tile's border (in pixels)
     */
    private int tileBorderThickness;

    /**
     * Construct's the tile's visual interface and logical underpinnings. Sets the sizes of the tile's associated
     * tooltip and border before setting its resource yields and implementing Listeners to detect when the tile is
     * clicked on (for individual tile selection) and hovered over (to determine when the tile's tooltip should be
     * drawn).
     *
     * @param game Variable storing the game's state
     * @param ID   The tile's distictive getID value
     * @param EnergyCount The multiplier for the production of energy
     * @param OreCount    The multiplier for the production of ore
     * @param FoodCount The multiplier for the production of food
     * @param landmark    A boolean to signify if the tile is to be a landmark or not
     * @param runnable    An object encapsulating a method that can be executed when the tile is clicked on
     */
    public Tile(Game game, int ID, int EnergyCount, int OreCount, int FoodCount, boolean landmark, final Runnable runnable) {
        super(new ButtonStyle());
        //Execute the constructor for the class' parent Button class using default visual parameters

        this.game = game;
        //Import and save the game's state

        this.drawer = new Drawer(this.game);
        //Use the game's state to establish a new Drawer class that can directly interface with (and modify) it

        this.ID = ID;
        //Import and save the tile's assigned getID

        tooltipWidth = 122;
        tooltipHeight = 35;
        tooltipCursorSpace = 3;
        tooltipTextSpace = 5;

        tooltipFillColor = Color.GRAY;
        tooltipLineColor = Color.BLACK;

        tooltipFont = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 36);
        //Visual parameters of the tile's tooltip

        tooltipActive = false;
        //Initialise boolean variable to track when the tile's tooltip is on-screen

        tileBorderColor = Color.BLACK;
        tileBorderThickness = 3;
        //Initialise the tile's border to default visual parameters

        this.EnergyCount = EnergyCount;
        this.FoodCount = FoodCount;
        this.OreCount = OreCount;
        //Import and save the tile's determined resource yields

        this.landmark = landmark;
        //Import and save the tile's landmark status

        this.runnable = runnable;
        this.owner = null;
        //Establish the function that the tile should execute when interacted with
        //Currently, "interacting" with the tile means clicking on it

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runnable.run();
            }
        });
        //Set up the tile to run the provided Runnable function when it's interacted with

        addListener(new ClickListener() {
            /**
             * Determines whether or not the cursor is hovering over the tile at the current time
             */
            Boolean mouseOver = false;
            //Initialise the tile's hover status

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                mouseOver = true;
                //When a cursor hovers over the tile, register its presence by switching this boolean variable

                /**
                 * Temporary timer measuring the time since the cursor previously started hovering over the tile
                 */
                Timer timer = new Timer();
                //Establish a temporary timer to measure how long the mouse stays over the tile for

                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        if (mouseOver == true) {
                            tooltipActive = true;
                        }
                    }
                }, (float) 0.5);
                //If the cursor stays over the tile for one half of a second, allow for the tile's tooltip to be
                //drawn by the drawTooltip() function

                timer.start();
                //Start the tile's hovering timer as soon as the cursor enters its domain
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                mouseOver = false;

                tooltipActive = false;
                //Once the cursor leaves the tile's domain, prevent the render from drawing its tooltip
            }
        });
        //Set up mouse-hovering detection over the tiles
        //This is used to trigger the appearance of tooltips after the cursor hovers over the tile for a short period
    }

    /**
     * Calculates how many resources are produced based on the amount of roboticons present and adds them to the player.
     */
    public void produce() {
        if (roboticonStored != null && owner != null) {

            if ((owner.getResource(ResourceType.ENERGY) >= 3) && (owner.getResource(ResourceType.FOOD) >= 2)) {
                //TODO: why are these negative? - Kieran
                owner.varyResource(ResourceType.ENERGY, -3);
                owner.varyResource(ResourceType.FOOD, -2);

            int[] modifiers = this.roboticonStored.productionModifier();

            int OreProduce = modifiers[0] * this.OreCount;
                owner.varyResource(ResourceType.ORE, OreProduce);
            //Add the tile's ore yields to the owner's resource-counters
                System.out.println("Ore produced: " + OreProduce);

            int EnergyProduce = modifiers[1] * this.EnergyCount;
                owner.varyResource(ResourceType.ENERGY, EnergyProduce);
            //Add the tile's energy yields to the owner's resource-counters
                System.out.println("Energy produced: " + EnergyProduce);

            int FoodProduce = modifiers[2] * this.FoodCount;
                owner.varyResource(ResourceType.FOOD, FoodProduce);
            //Add the tile's food yields to the owner's resource-counters
                System.out.println("Food produced: " + FoodProduce);
            }
        }
    }

    /**
     * Unified Getter for resource amounts
     *
     * @param type resource requested
     * @return int value representing the amount of type the player currently have
     */
    public int getResource(ResourceType type) throws InvalidResourceTypeException {
        switch (type) {
            case ENERGY:
                return this.EnergyCount;
            case FOOD:
                return this.FoodCount;
            case ORE:
                return this.OreCount;
            default:
                throw new InvalidResourceTypeException();
        }
    }

    /**
     * Unified Setter for resource amounts
     *
     * @param type     resource being set
     * @param newCount int value the count should be updated to
     */
    public void setResource(ResourceType type, int newCount) {
        if (!(newCount < 0)) {
            switch (type) {
                case ENERGY:
                    this.EnergyCount = newCount;
                    break;
                case FOOD:
                    this.FoodCount = newCount;
                    break;
                case ORE:
                    this.OreCount = newCount;
                    break;
                default:
            }
        }
    }

    /**
     * Returns the class of the player who owns the tile
     *
     * @return Player The tile's owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Changes the owner of the tile to the one specified
     *
     * @param Owner The new owner.
     */
    public void setOwner(Player Owner) {
        this.owner = Owner;
    }


    /**
     * Adds a Roboticon to the roboticon list.
     * @param roboticon The roboticon to be added to the list.
     */
    public void assignRoboticon( Roboticon roboticon) {
        this.roboticonStored = roboticon;
    }

    /**
     * Removes the first instance of the roboticon from the list.
     *
     * @param Roboticon The roboticon to be removed.
     */
    public void unassignRoboticon(Roboticon Roboticon) {
        roboticonStored = null;
    }

    /**
     * Returns the tile's associated function
     *
     * @return Runnable The function that the tile executes when interacted with
     */
    public Runnable getFunction() {
        return runnable;
    }

    /**
     * Runs the tile's associated function
     */
    public void runFunction() {
        runnable.run();
    }

    /**
     * Draws the tile's tooltip on the game's stage
     * Specifically draws the tooltip region in the space to the top- or bottom-left of the cursor's position
     * (depending on how high up the cursor is in the game's window) before drawing textual and visual contents inside
     * that region
     *
     * This must be called during the construction of each frame in which the tooltip is to be shown
     */
    public void drawTooltip() {
        if (tooltipActive == true) {
            if (Gdx.input.getY() < tooltipHeight) {
                drawer.borderedRectangle(tooltipFillColor, tooltipLineColor, Gdx.input.getX() - tooltipWidth - tooltipCursorSpace, Gdx.input.getY() + tooltipCursorSpace, tooltipWidth, tooltipHeight, 1);
                //Draw the tooltip's main space onto the screen in the region to the top-left of the cursor
                drawer.text("Tile " + this.ID, tooltipFont, Gdx.input.getX() - tooltipWidth - tooltipCursorSpace + tooltipTextSpace, Gdx.input.getY() + tooltipCursorSpace + tooltipTextSpace);
                //Draw an identification label in that space
            } else {
                drawer.borderedRectangle(tooltipFillColor, tooltipLineColor, Gdx.input.getX() - tooltipWidth - tooltipCursorSpace, Gdx.input.getY() - tooltipHeight - tooltipCursorSpace, tooltipWidth, tooltipHeight, 1);
                drawer.text("Tile " + this.ID, tooltipFont, Gdx.input.getX() - tooltipWidth - tooltipCursorSpace + tooltipTextSpace, Gdx.input.getY() - tooltipHeight - tooltipCursorSpace + tooltipTextSpace);
                //Do the same thing, but in the region to the bottom-left of the cursor if the cursor is near the
                //top of the game's window
            }
        }
    }

    /**
     * Draws the tile's coloured border on the game's stage
     * This must be called during the construction of each frame in which the border is to be shown
     * Note that the border must only be shown if the tile is owned by someone
     */
    public void drawBorder() {
        if (isOwned()) {
            /*
            * Basically, the old drawer cord system and the new cord system have a different Y direction.
            *
            * For the old version, flip the Y cords are required for it to draw properly.
            *
            ***********************************************************************************************************/
            float tileX = tileXOffset + getX();
            float tileY = Gdx.graphics.getHeight() - getHeight() - getY();

            drawer.lineRectangle(tileBorderColor,
                    (int)tileX + 1, (int)tileY + 1,
                    (int)(this.getWidth() - 1),
                    (int)(this.getHeight() - 1),
                    tileBorderThickness);

            // TODO: draw owner


            if (hasRoboticon()) {
                drawer.drawRoboticon(roboticonStored,
                        tileX + this.getWidth() - 64 - 10,
                        getY() + 10);
            }
        }
    }

    /**
     * Returns the tile's associated getID value
     *
     * @return int The tile's associated getID value
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Returns a boolean value that's true if/when the tile is owned by a player and false otherwise
     *
     * @return Boolean The ownership status of the tile
     */
    public boolean isOwned() {
        return owner != null;
    }

    /**
     * Sets the colour of the tile's border
     * This must only be called if and when a player acquires the tile
     *
     * @param color The new colour of the tile's border
     */
    public void setTileBorderColor(Color color) {
        tileBorderColor = color;
    }

    /**
     * Returns the colour of the tile's border
     *
     * @return Color The current colour of the tile's border
     */
    public Color tileBorderColor() {
        return tileBorderColor;
    }

    /**
     * Returns a value that's true if a Roboticon is assigned to the tile, and false otherwise
     *
     * @return Boolean The presence of a Roboticon on the tile
     */
    public boolean hasRoboticon(){
        return this.roboticonStored != null;

    }

    /**
     * Retuns the Roboticon assigned to this tile
     *
     * @return Roboticon The Roboticon assigned to this tile
     */
    public Roboticon getRoboticonStored(){
        return this.roboticonStored;
    }

}
