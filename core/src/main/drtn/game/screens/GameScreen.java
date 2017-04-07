package main.drtn.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import main.drtn.game.GameEngine;
import main.drtn.game.Trade;
import main.drtn.game.entity.Chancellor;
import main.drtn.game.entity.Tile;
import main.drtn.game.enums.ResourceType;
import main.drtn.game.util.Drawer;
import main.drtn.game.util.LabelledElement;
import main.drtn.game.util.Overlay;
import main.drtn.game.util.TTFont;
import main.teamfractal.util.animation.AnimationPlayerWin;
import main.teamfractal.util.animation.AnimationTileFlash;
import main.teamfractal.util.animation.IAnimation;
import main.teamfractal.util.screens.AbstractAnimationScreen;


public class GameScreen extends AbstractAnimationScreen implements Screen {
    private final static int tileXOffset = 256;
    /**
     * Font which will be adopted by the game-screen's main interface
     */
    private static TTFont gameFont;
    /**
     * Establish visual parameters for in-game buttons
     */
    private static TextButton.TextButtonStyle gameButtonStyle;

    static {
        gameFont = new TTFont(Gdx.files.internal("font/testfontbignoodle.ttf"), 36);

        gameButtonStyle = new TextButton.TextButtonStyle();
        gameButtonStyle.font = gameFont.font();
        gameButtonStyle.fontColor = Color.WHITE;
        gameButtonStyle.pressedOffsetX = 1;
        gameButtonStyle.pressedOffsetY = -1;
    }

    private boolean shown = false;
    private IAnimation lastTileClickedFlash;
    private IAnimation playerWin;
    /**
     * Stores current game-state, enabling transitions between screens and external QOL drawing functions
     */
    private Game game;
    /**
     * Engine class that handles all of the game's logical processing
     */
    private GameEngine engine;
    /**
     * On-screen stage which can be populated with actors
     */
    private Stage gameStage;
    /**
     * Establishes a secondary stage which will appear when the game is paused
     */
    private Stage pauseStage;
    /**
     * Holds the image of the in-game map
     */
    private Image map;
    /**
     * Establishes the grid of tiles to be laid over the map
     */
    private Table tileGrid;
    /**
     * Label identifying the current phase number
     */
    private Label phaseLabel;
    /**
     * Label counter providing a visual representation of the current player's food stocks
     */
    private Label foodCounter;
    /**
     * Label counter providing a visual representation of the current player's energy stocks
     */
    private Label energyCounter;
    /**
     * Label counter providing a visual representation of the current player's ore stocks
     */
    private Label oreCounter;
    /**
     * Label counter providing a visual representation of the current player's roboticon stocks
     */
    private Label roboticonCounter;
    /**
     * Label counter providing a visual representation of the current player's money
     */
    private Label moneyCounter;
    /**
     * Label stating the getID of the currently-selected tile
     */
    private Label selectedTileLabel;
    /**
     * Object defining QOL drawing functions for rectangles and on-screen tables
     * Used in this class to render tooltip regions
     */
    private Drawer drawer;
    /**
     * Button that, when clicked, ends the current turn for the current player prematurely
     */
    private TextButton endTurnButton;
    /**
     * Button which can be clicked on to pause the game
     */
    private TextButton pauseButton;
    /**
     * Button which can be clicked on to go to the mini game
     */

    private TextButton miniGameButton;
    /**
     * Button which allows players to claim selected tiles
     */
    private TextButton claimTileButton;
    /**
     * Button which allows players to deploy owned roboticons onto selected tiles
     */
    private TextButton deployRoboticonButton;
    /**
     * Button allowing players to upgrade roboticons' food-production capabilities
     */
    private TextButton foodUpgradeButton;
    /**
     * Button allowing players to upgrade roboticons' ore-production capabilities
     */
    private TextButton oreUpgradeButton;
    /**
     * Button allowing players to upgrade roboticons' energy-production capabilities
     */
    private TextButton energyUpgradeButton;
    /**
     * Button allowing players to escape from the upgrade overlay if they decide against upgrading roboticons
     */
    private TextButton closeUpgradeOverlayButton;
    /**
     * Icon representing the currently-active player's chosen college
     */
    private Image currentPlayerIcon;
    /**
     * Icon representing the player who owns the currently-selected tile
     */
    private Image selectedTileOwnerIcon;
    /**
     * Icon representing the roboticon occupying the currently-selected tile
     */
    private Image selectedTileRoboticonIcon;
    /**
     * Customised stage that shows up to offer roboticon upgrade choices
     */
    private Overlay upgradeOverlay;
    private Overlay tradeOverlay;
    private Overlay eventMessageOverlay;
    private Label eventMessage;
    private TextButton closeEventMessageButton;
    /**
     * Determines whether the aforementioned roboticon upgrade overlay is to be drawn to the screen
     */

    private boolean upgradeOverlayVisible;
    private boolean tradeOverlayVisible;
    private Batch batch;
    private int height;
    private int width;
    private Label currentPlayerLabel;
    private boolean drawRoboticonIcon;
    private Tile selectedTile;
    private Table tableRight;
    private boolean eventMessageOverlayVisible;
	private TextButton confirmTradeButton;
	private TextButton cancelTradeButton;
	private Overlay tooExpensiveOverlay;
	private boolean tooExpensiveOverlayVisible;
	private TextButton closePriceOverlayButton;
    private boolean activeTrade;
    private Trade currentTrade;
    /**
     * The game-screen's initial constructor
     *
     * @param game Variable storing the game's state for rendering purposes
     */
    public GameScreen(Game game, boolean vsPlayer) {
        this.game = game;
        //Import current game-state to access the game's renderer


        //Start game engine up

        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gameStage = new Stage();
    }

    public GameScreen(Game game) {
        this(game, true);
    }

    static TextButton.TextButtonStyle getGameButtonStyle() {
        return gameButtonStyle;
    }

    static TTFont getGameFont() {
        return gameFont;
    }

    /**
     * Executes when the game-screen is loaded up, typically from the point of another screen
     * Serves as an extension of the screen's constructor that primarily builds visual elements
     *
     * Currently instantiates Drawer object, the main stage, the font used to render on-screen text and the image of
     * the game's map before constructing the three primary tables that make up the in-game interface (along with the
     * auxiliary pause menu)
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(gameStage);
        if (shown) return ;

        shown = true;
        drawer = new Drawer(game);
        //Import QOL drawing functions

        map = new Image(new Texture("image/TestMap.png"));
        map.setPosition((Gdx.graphics.getWidth() / 2) - (map.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (map.getHeight() / 2));
        gameStage.addActor(map);
        //Initialise and deploy map texture

        constructTileGrid();

        constructButtons();
        //Set up the buttons to be placed down onto the interface

        constructLeftTable();
        constructRightTable();
        //Construct and deploy side-hand tables

        constructPauseMenu();
        //Construct pause-menu (and hide it for the moment)

        constructUpgradeOverlay();
        //Construct roboticon upgrade overlay (and, again, hide it for the moment)
        constructTooExpensiveOverlay();
        constructEventMessageOverlay();
        //drawer.debug(gameStage);
        //Call this to draw temporary debug lines around all of the actors on the stage

        System.out.println("GameScreen.show");
        engine.nextPhase();
    }

    /**
     * Renders all visual elements (set up in the [show()] subroutine and all of its subsiduaries) to the window
     * This is called to prepare each and every frame that the game deploys
     *
     * @param delta offset to render by
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //OpenGL nonsense
        //First instruction sets background colour

        if (engine.state() == GameEngine.State.RUN) {
            drawRectangles();

            gameStage.act(delta);
            gameStage.draw();
            //Draw the stage onto the screen

            // Draw owned tile's border
            for (Tile tile : engine.tiles()) {
                tile.drawBorder();
            }

            // Draw animation.
            renderAnimation(delta, IAnimation.AnimationType.Tile);

            // Draw
            if (!upgradeOverlayVisible) {
                for (Tile tile : engine.tiles()) {
                    tile.drawTooltip();
                    //If any of the tiles' tooltips are deemed "active", render them to the screen too
                }
            }
            else {
                upgradeOverlay.act(delta);
                upgradeOverlay.draw();
            }

            if (drawRoboticonIcon) {
                drawer.drawRoboticon(selectedTile.getRoboticonStored(),
                        tableRight.getX() + selectedTileRoboticonIcon.getX(),
                        selectedTileRoboticonIcon.getY()
                );
            }

            if (engine.chancellor().getisActive() == Boolean.TRUE && engine.timer().seconds() <= 15){
                updateChancellor(256, 0); //TODO Get actual x,y
            }

            if (eventMessageOverlayVisible) {
                eventMessageOverlay.act(delta);
                eventMessageOverlay.draw();
            }

            if (tradeOverlayVisible) {
                tradeOverlay.act(delta);
                tradeOverlay.draw();
            }

            if (tooExpensiveOverlayVisible) {
            	tooExpensiveOverlay.act(delta);
            	tooExpensiveOverlay.draw();
            }
            //Draw the roboticon upgrade overlay to the screen if the "upgrade" button has been selected
        } else if (engine.state() == GameEngine.State.PAUSE) {
            drawer.filledRectangle(Color.WHITE, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            //If the game is paused, render a white background...

            pauseStage.act(delta);
            pauseStage.draw();
            //...followed by the menu itself
        }

        renderAnimation(delta, IAnimation.AnimationType.Overlay);
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Disposes of all visual data used to construct previous frames
     * This is called after each frame is rendered, and remains necessary to prevent memory leaks
     */
    @Override
    public void dispose() {
        gameStage.dispose();
        //Dispose of the stage
    }

    /**
     * Sets up the buttons to be placed onto the interface later by defining their visual representations and their
     * on-click functions together
     */
    //Extended for Assessment 3 with new buttons
    private void constructButtons() {
        //Button that, when clicked, ends the current turn for the current player prematurely

        endTurnButton = new TextButton("NEXT PHASE", gameButtonStyle);
        endTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.nextPhase();
            }
        });
        drawer.toggleButton(endTurnButton, false, Color.GRAY);
        //Turn off the "END TURN" button right away to force players into selecting tiles

        //Button which can be clicked on to pause the game

        pauseButton = new TextButton("Pause Game", gameButtonStyle);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.pauseGame();
            }
        });


        //Button which can be clicked on to go to the mini game
        miniGameButton = new TextButton("Mini Game ($20)", gameButtonStyle);
        miniGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (engine.currentPlayer().getResource(ResourceType.MONEY) >= 20) {
                    engine.miniGame();
                }
                else{
                    System.out.println("not enough money");
                }
            }
        });

        gameFont.setSize(30);
        gameButtonStyle.font = gameFont.font();

        //Button which allows players to claim selected tiles

        claimTileButton = new TextButton("CLAIM", gameButtonStyle);
        claimTileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.claimTile();

                selectTile(engine.selectedTile(), false);
                //Refresh tile information and tile management UI
            }
        });

        //Button which allows players to deploy Roboticons onto selected tiles
        deployRoboticonButton = new TextButton("DEPLOY", gameButtonStyle);
        deployRoboticonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!engine.selectedTile().hasRoboticon()) {
                    engine.deployRoboticon();

                    selectTile(engine.selectedTile(), false);
                    //Re-select the current tile to update the UI
                } else {
                    updateUpgradeOptions();
                    //Refresh the upgrade options shown on the roboticon upgrade overlay

                    upgradeOverlayVisible = true;
                    //Set the renderer to show the upgrade overlay if this button is clicked after a tile with a
                    //roboticon was clicked on

                    Gdx.input.setInputProcessor(upgradeOverlay);
                    //Direct user inputs towards the roboticon upgrade overlay
                }
            }
        });

        gameFont.setSize(24);
        gameButtonStyle.font = gameFont.font();

        //Button allowing players to upgrade roboticons' food-production capabilities

        foodUpgradeButton = new TextButton("PRICE", gameButtonStyle);
        foodUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.upgradeRoboticon(2);

                updateInventoryLabels();
                updateUpgradeOptions();
            }
        });

        //Button allowing players to upgrade roboticons' ore-production capabilities
        oreUpgradeButton = new TextButton("PRICE", gameButtonStyle);
        oreUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.upgradeRoboticon(0);

                updateInventoryLabels();
                updateUpgradeOptions();
            }
        });

        /*
         * Button allowing players to upgrade roboticons' energy-production capabilities
         */
        energyUpgradeButton = new TextButton("PRICE", gameButtonStyle);
        energyUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.upgradeRoboticon(1);

                updateInventoryLabels();
                updateUpgradeOptions();
            }
        });

        //Button allowing players to escape from the upgrade overlay if they decide against upgrading roboticons

        closeUpgradeOverlayButton = new TextButton("CLOSE", gameButtonStyle);
        closeUpgradeOverlayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeUpgradeOverlay();
            }
        });

        confirmTradeButton = new TextButton("Confirm", gameButtonStyle);
        confirmTradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               closeTradeOverlay();
               if (! currentTrade.execute()){
            	   openTooExpensiveOverlay();
               }
               else{
            	   updateInventoryLabels();
            	   engine.testTrade();
               }

            }
        });

        cancelTradeButton = new TextButton("Cancel", gameButtonStyle);
        cancelTradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               closeTradeOverlay();
               engine.testTrade();
            }
        });

        closePriceOverlayButton = new TextButton("close", gameButtonStyle);
        closePriceOverlayButton.addListener(new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		closeTooExpensiveOverlay();
        	}
        });

        closeEventMessageButton = new TextButton("CLOSE MESSAGE", gameButtonStyle);
        closeEventMessageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hideEventMessage();
            }
        });
    }

    /**
     * Set up the form and contents of the left-hand table so that they can be rendered later
     * Specifically defines the left-hand panel's spatial framework (in the form of a table) before populating it with
     * the game's timer, a phase label, a button for ending turns, the logo of the current player's associated college
     * and counters visualising the numbers of ore, energy, food and Roboticon stocks (and the money) that the
     * current player holds
     */
    private void constructLeftTable() {
        /*
         * Establishes the metadata for the interface's left-hand table
         */
        Table tableLeft = new Table();
        //Construct left-hand table

        tableLeft.setBounds(0, 0, 256, Gdx.graphics.getHeight());
        //Set boundaries of left-hand table

        tableLeft.center().top();
        //Shift the table towards the top of the screen

        tableLeft.add(engine.timer()).colspan(2);
        //Add the timer to the table

        gameFont.setSize(22);
        Table phaseTable = new Table();
        phaseLabel = new Label("", new Label.LabelStyle(gameFont.font(), Color.WHITE));
        phaseLabel.setAlignment(Align.center);
        phaseTable.add(phaseLabel).width(120);
        phaseTable.add().width(5);
        phaseTable.add(endTurnButton);
        drawer.addTableRow(tableLeft, phaseTable, 0, 0, 15, 0, 2);
        //Prepare and add the "End Phase" button to the table

        gameFont.setSize(36);
        drawer.addTableRow(tableLeft, new Label("CURRENT PLAYER", new Label.LabelStyle(gameFont.font(), Color.BLACK)), 0, 0, 10, 0, 2);
        //Window-dressing: adds "CURRENT PLAYER" label



        gameFont.setSize(24);
        Table collegeInfo = new Table();
        currentPlayerIcon = new Image();
        currentPlayerLabel = new Label("", new Label.LabelStyle(gameFont.font(), Color.WHITE));
        drawer.addTableRow(collegeInfo, currentPlayerIcon, 64, 64);
        drawer.addTableRow(collegeInfo, new Label("COLLEGE", new Label.LabelStyle(gameFont.font(), Color.WHITE)));
        drawer.addTableRow(collegeInfo, currentPlayerLabel);
        drawer.addTableRow(tableLeft, collegeInfo, 5, 0, 0, 0);
        //Prepare icon region to show the icon of the college which is currently active



        Table resourceCounters = new Table();
        foodCounter = new Label("" + engine.currentPlayer().getResource(ResourceType.FOOD), new Label.LabelStyle(gameFont.font(), Color.WHITE));
        foodCounter.setAlignment(Align.right);
        energyCounter = new Label("" + engine.currentPlayer().getResource(ResourceType.ENERGY), new Label.LabelStyle(gameFont.font(), Color.WHITE));
        energyCounter.setAlignment(Align.right);
        oreCounter = new Label("" + engine.currentPlayer().getResource(ResourceType.ORE), new Label.LabelStyle(gameFont.font(), Color.WHITE));
        oreCounter.setAlignment(Align.right);
        roboticonCounter = new Label("" + engine.currentPlayer().getRoboticonInventory(), new Label.LabelStyle(gameFont.font(), Color.WHITE));
        roboticonCounter.setAlignment(Align.right);
        moneyCounter = new Label("" + engine.currentPlayer().getResource(ResourceType.MONEY), new Label.LabelStyle(gameFont.font(), Color.WHITE));
        moneyCounter.setAlignment(Align.right);

        drawer.addTableRow(resourceCounters, new LabelledElement("Food", gameFont, Color.WHITE, foodCounter, 100, 40));
        drawer.addTableRow(resourceCounters, new LabelledElement("Energy", gameFont, Color.WHITE, energyCounter, 100, 40));
        drawer.addTableRow(resourceCounters, new LabelledElement("Ore", gameFont, Color.WHITE, oreCounter, 100, 40));
        drawer.addTableRow(resourceCounters, new LabelledElement("Roboticons", gameFont, Color.WHITE, roboticonCounter, 100, 40));
        drawer.addTableRow(resourceCounters, new LabelledElement("Money", gameFont, Color.WHITE, moneyCounter, 100, 40));
        tableLeft.add(resourceCounters).size(150, 120).align(Align.right);
        //Add resource-counters to the table
        //These will show the current resource stocks for the current player

        drawer.addTableRow(tableLeft, miniGameButton, 105-40, 0, 0, 0, 2);


        drawer.addTableRow(tableLeft, pauseButton, 0, 0, 0, 0, 2);
        //Prepare and add the pause button to the bottom of the table

        gameStage.addActor(tableLeft);
        //Add left-hand table to the stage


        updatePhaseLabel();
        updatePlayerName();
    }

    /**
     * Set up the form and contents of the right-hand table so that they can be rendered later
     * Specifically defines the right-hand panel's spatial framework (in the form of a table) before populating it with
     * the selected tile's name-label and college/roboticon icons, along with buttons to claim and deploy/upgrade
     * Roboticons on the selected tile and the whole interface for the game's market
     */
    private void constructRightTable() {
        /*
         * Establishes the metadata for the interface's right-hand table
         */
        tableRight = new Table();

        tableRight.setBounds((Gdx.graphics.getWidth() / 2) + (map.getWidth() / 2), 0, 256, Gdx.graphics.getHeight());
        //Set boundaries of right-hand table

        tableRight.center().top();
        //Shift the table towards the top of the screen

        gameFont.setSize(40);
        selectedTileLabel = new Label("NO TILE SELECTED", new Label.LabelStyle(gameFont.font(), Color.WHITE));
        selectedTileLabel.setAlignment(Align.center);
        drawer.addTableRow(tableRight, selectedTileLabel, 240, 43, 0, 0, 10, 0, 2);
        //Set up and deploy label to identify tiles when they're clicked on

        selectedTileOwnerIcon = new Image();
        selectedTileOwnerIcon.setVisible(false);
        selectedTileOwnerIcon.setScaling(Scaling.fit);
        selectedTileOwnerIcon.setAlign(Align.center);
        selectedTileRoboticonIcon = new Image();
        selectedTileRoboticonIcon.setVisible(false);
        selectedTileRoboticonIcon.setScaling(Scaling.fit);
        selectedTileRoboticonIcon.setAlign(Align.center);
        tableRight.row();
        tableRight.add(selectedTileOwnerIcon).size(64, 64).center();
        tableRight.add(selectedTileRoboticonIcon).size(64, 64).center();
        //Instantiate and deploy icons to represent tiles' owners and Roboticons

        gameFont.setSize(20);
        Label collegeFootLabel = new Label("COLLEGE", new Label.LabelStyle(gameFont.font(), Color.WHITE));
        Label roboticonFootLabel = new Label("ROBOTICON", new Label.LabelStyle(gameFont.font(), Color.WHITE));
        collegeFootLabel.setAlignment(Align.center);
        roboticonFootLabel.setAlignment(Align.center);
        tableRight.row();
        tableRight.add(collegeFootLabel).padBottom(10).width(120);
        tableRight.add(roboticonFootLabel).padBottom(10).width(120);
        //Even more window-dressing

        drawer.toggleButton(claimTileButton, false, Color.GRAY);
        drawer.toggleButton(deployRoboticonButton, false, Color.GRAY);
        //Disable the claim and deploy button until a tile is selected under the appropriate conditions

        drawer.addTableRow(tableRight, claimTileButton, 0, 0, 15, 0);
        tableRight.add(deployRoboticonButton).padBottom(15);
        //Add tile claim/deploy buttons to interface

        drawer.addTableRow(tableRight, engine.market(), 2);
        //Establish market and add market interface to right-hand table

        gameStage.addActor(tableRight);
        //Add right-hand table to the stage
    }

    /**
     * Set up the game-screen's central tile-grid so that it can be interacted with
     * The tiles on this grid take the form of invisible buttons that are directly laid over the map image at the
     * centre of this screen
     */
    private void constructTileGrid(){
        tileGrid = new Table();
        //Initialise tile-grid

        tileGrid.setBounds((Gdx.graphics.getWidth() / 2) - (map.getWidth() / 2), 0, map.getWidth(), map.getHeight());
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                tileGrid.add(engine.tiles()[(y * 4) + x]).width(map.getWidth() / 4).height(map.getHeight() / 4);
            }
            tileGrid.row();
        }

        gameStage.addActor(tileGrid);
    }

    /**
     * Set up the pause menu to allow for the game to be successfully paused
     */
    private void constructPauseMenu() {

        /*
         * Establishes the visual framework for the pause screen
         * Specifically instantiates a new stage and spatial framework table for the pause menu before populating it
         * with the game's logo and a "Resume" button to unpause the game with
         * This screen's [render()] function is initially configured to avoid rendering this stage, so it won't
         * appear when the GameScreen loads: it will instead appear when the Pause button on the main stage is used
         * to shift the game's engine into a "Paused" state
         */
        Table pauseTable = new Table();
        pauseStage = new Stage();
        //Establish stage and interface for pause menu

        TTFont titleFont = new TTFont(Gdx.files.internal("font/earthorbiterxtrabold.ttf"), 72);
        TTFont menuFont = new TTFont(Gdx.files.internal("font/enterthegrid.ttf"), 36);
        //Prepare fonts for the logo and the options on the pause menu

        pauseTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Centre the pause menu's contents

        drawer.addTableRow(pauseTable, new Label("Sabbaticoup", new Label.LabelStyle(titleFont.font(), Color.BLACK)), 0, 0, 30, 0);
        //Add the game's logo to the pause menu

        TextButton.TextButtonStyle menuButtonStyle = new TextButton.TextButtonStyle();
        menuButtonStyle.font = menuFont.font();
        menuButtonStyle.fontColor = Color.BLACK;
        menuButtonStyle.pressedOffsetX = 1;
        menuButtonStyle.pressedOffsetY = -1;
        //Establish the visual style of the pause menu's buttons

        /*
         * Button which allows players to resume the game from the pause menu
         */
        TextButton resume = new TextButton("Resume", menuButtonStyle);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.resumeGame();
            }
        });
        //Establish and prepare a button to resume the game from the pause menu

        drawer.addTableRow(pauseTable, resume);
        //Add the resume button to the pause menu's interface

        pauseStage.addActor(pauseTable);
        //Prepare the pause menu's interface to be shown on the screen when the game is paused
    }

    /**
     * Set up an overlay which shows up when the current player opts to upgrade their roboticon
     * This will allow them to upgrade its food, energy or ore production stats
     */
    private void constructUpgradeOverlay() {
        upgradeOverlay = new Overlay(this.game, Color.GRAY, Color.WHITE, 250, 200, 3);
        //Establish the upgrade overlay

        upgradeOverlayVisible = false;
        //Stop the GameScreen's renderer from rendering the overlay right away

        gameFont.setSize(36);
        upgradeOverlay.table().add(new Label("UPGRADE ROBOTICON", new Label.LabelStyle(gameFont.font(), Color.WHITE))).padBottom(20);
        //Visual guff

        gameFont.setSize(24);
        upgradeOverlay.table().row();
        upgradeOverlay.table().add(new LabelledElement("FOOD", gameFont, Color.WHITE, foodUpgradeButton, 175, 0)).left();
        upgradeOverlay.table().row();
        upgradeOverlay.table().add(new LabelledElement("ENERGY", gameFont, Color.WHITE, energyUpgradeButton, 175, 0)).left();
        upgradeOverlay.table().row();
        upgradeOverlay.table().add(new LabelledElement("ORE", gameFont, Color.WHITE, oreUpgradeButton, 175, 0)).left().padBottom(20);
        //Add buttons for upgrading roboticons to the overlay
        //Like in the market, each button's label is the monetary price of the upgrade that it performs

        drawer.addTableRow(upgradeOverlay.table(), closeUpgradeOverlayButton);
        //Add a final button for closing the overlay
    }

    public boolean getUpgradeOverlayVisible() {
        return upgradeOverlayVisible;
    }

    //new for assessment 3
    private void constructEventMessageOverlay() {
        eventMessageOverlay = new Overlay(this.game, Color.GRAY, Color.WHITE, 900, 200, 3);
        eventMessage = new Label("", new Label.LabelStyle(gameFont.font(), Color.WHITE));

        eventMessageOverlayVisible = false;

        gameFont.setSize(26);

        eventMessageOverlay.table().add(new Label("RANDOM EVENT!", new Label.LabelStyle(gameFont.font(), Color.WHITE)));

        gameFont.setSize(20);
        eventMessageOverlay.table().row();
        eventMessageOverlay.table().add(eventMessage);

        drawer.addTableRow(eventMessageOverlay.table(), closeEventMessageButton);
    }

    //new for assessment 3
    public void showEventMessage(String message) {
        eventMessage.setText(message);
        eventMessageOverlayVisible = true;
        Gdx.input.setInputProcessor(eventMessageOverlay);
    }

  //new for assessment 3
  public void hideEventMessage() {
        eventMessageOverlayVisible = false;
        if (upgradeOverlayVisible) {
            Gdx.input.setInputProcessor(upgradeOverlay);
        }
        else {
            Gdx.input.setInputProcessor(gameStage);
        }
    }

    //new for assessment 3
    private void constructTradeOverlay(Trade trade){
    	tradeOverlay = new Overlay(this.game, Color.GRAY, Color.WHITE, 250, 300, 3);
    	tradeOverlayVisible = false;
    	gameFont.setSize(36);
        tradeOverlay.table().add(new Label("INCOMING TRADE", new Label.LabelStyle(gameFont.font(), Color.WHITE))).padBottom(20);

        gameFont.setSize(24);
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("From: Player " + trade.getSender().getPlayerNumber(), new Label.LabelStyle(gameFont.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("ORE: " + trade.oreAmount, new Label.LabelStyle(gameFont.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("ENERGY: " + trade.energyAmount, new Label.LabelStyle(gameFont.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("FOOD " + trade.foodAmount, new Label.LabelStyle(gameFont.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("PRICE: " + trade.getPrice(), new Label.LabelStyle(gameFont.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(confirmTradeButton);
        tradeOverlay.table().add(cancelTradeButton);
    }

    //new for assessment 3
    private void constructTooExpensiveOverlay(){
    	tooExpensiveOverlay = new Overlay(this.game, Color.GRAY, Color.WHITE, 250, 300, 3);
    	tooExpensiveOverlayVisible = false;
    	gameFont.setSize(36);
    	tooExpensiveOverlay.table().add(new Label("NOT ENOUGH MONEY!", new Label.LabelStyle(gameFont.font(), Color.WHITE))).padBottom(20);
    	tooExpensiveOverlay.table().row();
    	gameFont.setSize(24);
    	tooExpensiveOverlay.table().add(closePriceOverlayButton);


    }

    /**
     * Draw auxiliary rectangles to provide window-dressing for the interface
     */
    private void drawRectangles() {
        drawer.lineRectangle(Color.WHITE, (int) map.getX(), (int) map.getY(), (int) map.getWidth() + 1, (int) map.getHeight(), 1);
        //Draw border around the map

        drawer.filledRectangle(Color.WHITE, 0, (int) engine.timer().getHeight(), 256, 1);
        drawer.filledRectangle(Color.WHITE, 0, (int) (engine.timer().getHeight() + phaseLabel.getHeight()), 256, 1);
        drawer.borderedRectangle(Color.GRAY, Color.WHITE, 19, (int) (engine.timer().getHeight() + phaseLabel.getHeight()) + 15, 219, 40, 1);
        //drawer.lineRectangle(Color.WHITE, ((int) (Gdx.graphics.getWidth() * 0.125)) - 110, 240, 66, 66);
        drawer.filledRectangle(Color.WHITE, 0, Gdx.graphics.getHeight() - 46, 256, 1);
        //Draw lines and rectangles in left-hand table

        drawer.lineRectangle(Color.WHITE, ((int) (Gdx.graphics.getWidth() * 0.875)) - 93, 52, 66, 66, 1);
        drawer.lineRectangle(Color.WHITE, ((int) (Gdx.graphics.getWidth() * 0.875)) + 27, 52, 66, 66, 1);
        drawer.filledRectangle(Color.WHITE, Gdx.graphics.getWidth() - 256, 190, 256, 1);
        //Draw lines in right-hand table
    }

    /**
     * Sets the value represented by the food-counter rendered within the main in-game interface
     *
     * @param value The new value to be visualised by the in-game food counter
     */
    private void setFoodCounterValue(int value) {
        foodCounter.setText(String.valueOf(value));
    }

    /**
     * Sets the value represented by the energy-counter rendered within the main in-game interface
     *
     * @param value The new value to be visualised by the in-game energy counter
     */
    private void setEnergyCounterValue(int value) {
        energyCounter.setText(String.valueOf(value));
    }

    /**
     * Sets the value represented by the ore-counter rendered within the main in-game interface
     *
     * @param value The new value to be visualised by the in-game ore counter
     */
    private void setOreCounterValue(int value) {
        oreCounter.setText(String.valueOf(value));
    }

    /**
     * Sets the value represented by the Roboticon-counter rendered within the main in-game interface
     *
     * @param value The new value to be visualised by the in-game Roboticon counter
     */
    private void setRoboticonCounterValue(int value) {
        roboticonCounter.setText(String.valueOf(value));
    }

    /**
     * Sets the value represented by the money-counter rendered within the main in-game interface
     *
     * @param value The new value to be visualised by the in-game money counter
     */
    private void setMoneyCounterValue(int value) {
        moneyCounter.setText(String.valueOf(value));
    }

    /**
     * Updates the label on the right-hand side of the in-game interface to visualise the identity of the selected tile
     *
     * @param value The ID value of the tile to be described on the interface
     */
    private void updateSelectedTileLabel(int value) {
        if (value < 1 || value > 16) {
            selectedTileLabel.setText("NO TILE SELECTED");
        } else {
            selectedTileLabel.setText("TILE " + value);
        }
    }

    /**
     * Updates the label on the right-hand side of the in-game interface to visualise the identity of the selected tile
     * Alternative method that takes an ID value from a provided tile rather than an ID value directly
     *
     * @param tile The tile to be described on the interface
     */
    public void updateSelectedTileLabel(Tile tile) {
        selectedTileLabel.setText("TILE " + tile.getID());
    }

    /**
     * Updates the label on the left-hand side of the in-game interface to visualise and describe the game's current
     * phase
     *
     * @param description The phase description to be displayed by the same label
     */
    private void updatePhaseLabel(String description) {
        phaseLabel.setText("PHASE " + engine.phase() + "\n" + description);
    }

    public void updatePhaseLabel () {
        int phase = engine.phase();
        String description;
        switch (phase) {
            case 1:
                description = "ACQUISITION";
                break;

            case 2:
                description = "BUY ROBOTICONS";
                break;

            case 3:
                description = "PLACE ROBOTICONS";
                break;

            case 4:
                description = "PRODUCTION";
                break;

            case 5:
                description = "MARKET OPEN";
                break;

            default:
                description = "UNKNOWN";
                break;
        }

        updatePhaseLabel(description);
    }

    /**
     * Returns the button used to allow for players to prematurely end their turns
     * This method is required to allow for the GameEngine class to turn the button off during phase 1
     *
     *
     * @return TextButton The in-game "End Turn" button
     */
    public TextButton endTurnButton() {
        return endTurnButton;
    }

    /**
     * Returns the icon object used to visualise the logo of the current player's associated college
     * The GameEngine class uses this method to update said logo when it changes the active player
     *
     *
     * @return Image The image object visualising the current player's associated college
     */
    public Image currentPlayerIcon() {
        return currentPlayerIcon;
    }

    /**
     * Prepares the renderer to render the main stage again (after pausing) by setting it up to accept inputs again
     */
    public void openGameStage() {
        Gdx.input.setInputProcessor(gameStage);
    }

    /**
     * Prepares the renderer to render the pause-screen stage again by setting it up to accept inputs again
     */
    public void openPauseStage() {
        Gdx.input.setInputProcessor(pauseStage);
    }

    /**
     * The code to be run whenever a particular tile is clicked on
     * Specifically updates the label identifying the selected tile, the college icon linked to the player who owns
     * it, the icon representing the Roboticon planted on it and the available options for the tile in the main
     * in-game interface
     *
     * @param tile The tile being clicked on
     */
    public void selectTile(Tile tile, boolean showAnimation) {
        selectedTile = tile;
        if (lastTileClickedFlash != null) {
            lastTileClickedFlash.cancelAnimation();
            lastTileClickedFlash = null;
        }

        if (showAnimation) {
            lastTileClickedFlash = new AnimationTileFlash(tileXOffset + tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
            addAnimation(lastTileClickedFlash);
        }

        selectedTileLabel.setText("TILE " + tile.getID());

        drawRoboticonIcon = false;
        if (tile.isOwned()) {
            selectedTileOwnerIcon.setVisible(true);
            selectedTileOwnerIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(tile.getOwner().getCollege().getLogoTexture())));
            selectedTileOwnerIcon.setSize(64, 64);
            //Update tile owner college icon

            drawer.toggleButton(claimTileButton, false, Color.GRAY);
            //Disable the button for claiming the tile if it's already been claimed

            if (tile.hasRoboticon()) {
                deployRoboticonButton.setText("UPGRADE");

                drawRoboticonIcon = true;
                // selectedTileRoboticonIcon.setVisible(true);
                selectedTileRoboticonIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(tile.getRoboticonStored().getIconTexture())));
                selectedTileOwnerIcon.setSize(64, 64);

                if (engine.phase() == 3 && tile.getOwner() == engine.currentPlayer()) {
                    drawer.toggleButton(deployRoboticonButton, true, Color.WHITE);
                } else {
                    drawer.toggleButton(deployRoboticonButton, false, Color.GRAY);
                }
                //If the tile already has a Roboticon, offer an upgrade button if the Roboticon upgrade conditions are met
                //Also show an icon representing the Roboticon inhabiting the tile
                //This will only happen if the game is in phase 3
            } else {
                deployRoboticonButton.setText("DEPLOY");

                selectedTileRoboticonIcon.setVisible(false);

                if (engine.phase() == 3 && tile.getOwner() == engine.currentPlayer() && engine.currentPlayer().getRoboticonInventory() > 0) {
                    drawer.toggleButton(deployRoboticonButton, true, Color.WHITE);
                } else {
                    drawer.toggleButton(deployRoboticonButton, false, Color.GRAY);
                }
                //If the tile doesn't have a Robotion, offer a deployment button if the current player owns at least 1 Roboticon
                //This will only happen if the game is in phase 3
            }
        } else {
            // tile.getX()

            selectedTileOwnerIcon.setVisible(false);
            selectedTileRoboticonIcon.setVisible(false);

            if (engine.phase() == 1) {
                drawer.toggleButton(claimTileButton, true, Color.WHITE);
            }

            deployRoboticonButton.setText("DEPLOY");
            drawer.toggleButton(deployRoboticonButton, false, Color.GRAY);
        }
        //If the tile isn't yet owned by anyone, allow the current player to claim it if the game is in phase 1
    }

    /**
     * Run this to deselect the currently selected tile
     * Specifically resets the labels and icons that identify the selected tile and disables the buttons for
     * manipulating said tile (as no tile can be deemed as being "selected" after this is run)
     */
    public void deselectTile() {
        drawer.toggleButton(claimTileButton, false, Color.GRAY);
        drawer.toggleButton(deployRoboticonButton, false, Color.GRAY);

        deployRoboticonButton.setText("DEPLOY");

        selectedTileOwnerIcon.setVisible(false);
        selectedTileRoboticonIcon.setVisible(false);

        updateSelectedTileLabel(0);
    }

    /**
     * Closes the upgrade overlay and restores the functionality of the game's main stage
     */
    //new for assessment 3
    public void closeUpgradeOverlay() {
        upgradeOverlayVisible = false;
        
        //Hide the upgrade overlay again

        Gdx.input.setInputProcessor(gameStage);
        //Direct user inputs back towards the main stage
        
        engine.testTrade();
    }

    /**
     * Updates the on-screen counters for food, energy, ore, money and Roboticons
     * This is typically called when the active player switches or when a market transaction is made
     */
    public void updateInventoryLabels(){
        setFoodCounterValue(engine.currentPlayer().getResource(ResourceType.FOOD));
        setEnergyCounterValue(engine.currentPlayer().getResource(ResourceType.ENERGY));
        setOreCounterValue(engine.currentPlayer().getResource(ResourceType.ORE));
        setMoneyCounterValue(engine.currentPlayer().getResource(ResourceType.MONEY));
        setRoboticonCounterValue(engine.currentPlayer().getRoboticonInventory());
    }

    /**
     * Updates the options available to the current player on the roboticon upgrade screen based on their money count
     */
    private void updateUpgradeOptions() {
        oreUpgradeButton.setText(String.valueOf(engine.selectedTile().getRoboticonStored().getOreUpgradeCost()));
        energyUpgradeButton.setText(String.valueOf(engine.selectedTile().getRoboticonStored().getEnergyUpgradeCost()));
        foodUpgradeButton.setText(String.valueOf(engine.selectedTile().getRoboticonStored().getFoodUpgradeCost()));
        //Refresh prices shown on upgrade screen

        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.selectedTile().getRoboticonStored().getOreUpgradeCost()) {
            drawer.toggleButton(oreUpgradeButton, true, Color.GREEN);
        } else {
            drawer.toggleButton(oreUpgradeButton, false, Color.RED);
        }
        //Conditionally enable ore upgrade button

        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.selectedTile().getRoboticonStored().getEnergyUpgradeCost()) {
            drawer.toggleButton(energyUpgradeButton, true, Color.GREEN);
        } else {
            drawer.toggleButton(energyUpgradeButton, false, Color.RED);
        }
        //Conditionally enable energy upgrade button

        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.selectedTile().getRoboticonStored().getFoodUpgradeCost()) {
            drawer.toggleButton(foodUpgradeButton, true, Color.GREEN);
        } else {
            drawer.toggleButton(foodUpgradeButton, false, Color.RED);
        }
        //Conditionally enable food upgrade button
    }

    public void updateChancellor(float x, float y){
            drawer.drawChancellor(x, y);
    }

    @Override
    protected Batch getBatch() {
        return batch;
    }

    @Override
    public Size getScreenSize() {
        Size s = new Size();
        s.Height = height;
        s.Width = width;
        return s;
    }

  //all below is new for assessment 3
    public void showPlayerWin(int playerId){
        playerWin = new AnimationPlayerWin(playerId + 1);
        addAnimation(playerWin);
    }

    public void updatePlayerName() {
        currentPlayerLabel.setText("Player " + engine.currentPlayer().getPlayerNumber());
        currentPlayerIcon.setDrawable(engine.currentPlayer().getCollege().getLogo().getDrawable());
    }

    private void openTradeOverlay() {
        tradeOverlayVisible = true;
        Gdx.input.setInputProcessor(tradeOverlay);
    }
    
    public void closeTradeOverlay(){
    	tradeOverlayVisible = false;

        Gdx.input.setInputProcessor(gameStage);
        //Direct user inputs back towards the main stage

    }

	public boolean TradeOverlayVisible() {
		return tradeOverlayVisible;
		
	}

	public void activeTrade(Trade trade) {
		constructTradeOverlay(trade);
		currentTrade = trade;
		openTradeOverlay();
		
	}

    private void openTooExpensiveOverlay() {
        tooExpensiveOverlayVisible = true;
        Gdx.input.setInputProcessor(tooExpensiveOverlay);
    }

    private void closeTooExpensiveOverlay() {
        tooExpensiveOverlayVisible = false;

        Gdx.input.setInputProcessor(gameStage);
        //Direct user inputs back towards the main stage

    }

    void assignEngine(GameEngine engine) {
        this.engine = engine;
    }

    public Game getGame(){
        return this.game;
    }
	
    
}