/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import drtn.game.GameEngine;
import drtn.game.Trade;
import drtn.game.entity.Tile;
import drtn.game.enums.ResourceType;
import drtn.game.screens.tables.MarketInterfaceTable;
import drtn.game.screens.tables.PhaseInfoTable;
import drtn.game.screens.tables.PlayerInfoTable;
import drtn.game.screens.tables.SelectedTileInfoTable;
import drtn.game.util.Drawer;
import drtn.game.util.Overlay;
import drtn.game.util.TTFont;
import teamfractal.util.animation.AnimationPlayerWin;
import teamfractal.util.animation.AnimationTileFlash;
import teamfractal.util.animation.IAnimation;
import teamfractal.util.screens.AbstractAnimationScreen;

public class GameScreen extends AbstractAnimationScreen implements Screen {
    private final static int tileXOffset = 256;

    private static TextButton.TextButtonStyle largeButtonStyle;
    private static TextButton.TextButtonStyle smallButtonStyle;

    private static TTFont headerFontRegular;
    private static TTFont smallFontRegular;
    private static TTFont smallFontLight;

    static {
        headerFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 24);

        smallFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
        smallFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 16);

        largeButtonStyle = new TextButton.TextButtonStyle();
        largeButtonStyle.font = headerFontRegular.font();
        largeButtonStyle.fontColor = Color.WHITE;
        largeButtonStyle.pressedOffsetX = 1;
        largeButtonStyle.pressedOffsetY = -1;

        smallButtonStyle = new TextButton.TextButtonStyle();
        smallButtonStyle.font = smallFontRegular.font();
        smallButtonStyle.fontColor = Color.WHITE;
        smallButtonStyle.pressedOffsetX = 1;
        smallButtonStyle.pressedOffsetY = -1;
    }

    /**
     * Table configured to identify the active player (and what they own) at any point in the game's run-time
     */
    public PlayerInfoTable playerInfoTable;

    /**
     * Table configured to identify the active phase (and what should be done in it) at any point in the game's run-time
     */
    public PhaseInfoTable phaseInfoTable;

    /**
     * Table configured to identify the most recently-selected tile; who owns it, and whether or not it harbours a
     * roboticon
     */
    public SelectedTileInfoTable selectedTileInfoTable;

    /**
     * Table configured to act as an interface for the game's market
     */
    public MarketInterfaceTable marketInterfaceTable;

    /**
     * Overlay made to simplify the process of upgrading roboticons deployed on selected tiles
     */
    public UpgradeOverlay upgradeOverlay;

    /**
     * Overlay intended to appear whenever players receive trade offers and describe the terms of those offers
     */
    private TradeOverlay tradeOverlay;

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

    private Overlay eventMessageOverlay;
    private Label eventMessage;
    private TextButton closeEventMessageButton;

    /**
     * Determines whether the roboticon upgrade overlay (upgradeOverlay) is to be drawn to the screen
     */
    private boolean upgradeOverlayVisible;

    /**
     * Determines whether the trade request overlay (tradeOverlay) is to be drawn to the screen
     */
    private boolean tradeOverlayVisible;

    /**
     * Serves as a rendering pipeline for visual objects that need to be drawn directly to the screen
     */
    private Batch batch;
    private int height;
    private int width;
    private Table tableRight;
    private boolean eventMessageOverlayVisible;

    /**
     * The pending trade to be described in the next trade overlay that shows up
     */
    private Trade currentTrade;

    /**
     * The game-screen's initial constructor
     *
     * @param game Variable storing the game's state for rendering purposes
     * @param vsPlayer [TBC]
     */
    public GameScreen(Game game, boolean vsPlayer) {
        this.game = game;
        //Import current game-state to access the game's renderer

        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gameStage = new Stage();
    }

    public GameScreen(Game game) {
        this(game, true);
    }

    static TextButton.TextButtonStyle getLargeButtonStyle() {
        return largeButtonStyle;
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
        if (shown) return;

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

        constructEventMessageOverlay();

        constructMarketInterface();
        //Construct the interface that will allow players to interact with the game's market

        System.out.println("GameScreen.show");

        engine.nextPhase();
        //Kick-start the game's core state-machine
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

        Stage inputProcessor = gameStage;
        //Initialise the Stage object holding a reference to the stage that will process inputs on the current frame

        if (engine.state() == GameEngine.State.RUN) {
            drawRectangles();
            //Draw window-dressing

            gameStage.act(delta);
            gameStage.draw();
            //Draw the stage onto the screen

            // Draw owned tile's border
            for (Tile tile : engine.tiles()) {
                tile.drawBorder();
            }

            if (engine.chancellor().getisActive()){
                updateChancellor();
            }

            // Draw animation.
            renderAnimation(delta, IAnimation.AnimationType.Tile);

            // Draw
            if (!upgradeOverlayVisible && !eventMessageOverlayVisible && !tradeOverlayVisible) {
                for (Tile tile : engine.tiles()) {
                    tile.drawTooltip();
                    //If any of the tiles' tooltips are deemed "active", render them to the screen too
                }
            }
            else {
                if (upgradeOverlayVisible) {
                    upgradeOverlay.act(delta);
                    upgradeOverlay.draw();
                    inputProcessor = upgradeOverlay;
                    //Draw the roboticon upgrade overlay to the screen if the "upgrade" button has been selected
                }

                if (eventMessageOverlayVisible) {
                    eventMessageOverlay.act(delta);
                    eventMessageOverlay.draw();
                    inputProcessor = eventMessageOverlay;
                }

                if (tradeOverlayVisible) {
                    tradeOverlay.act(delta);
                    tradeOverlay.draw();
                    inputProcessor = tradeOverlay;
                    //Draw the trading overlay to the screen if the current player has received an impending trade offer
                }
            }

            Gdx.input.setInputProcessor(inputProcessor);
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
     * Draws some of the visual makeup that helps to make the screen's appearance look rather nice
     * Also responsible for drawing some button backgrounds, thereby indicating that certain buttons are clickable
     */
    public void drawRectangles() {
        drawer.lineRectangle(Color.WHITE, 256, 0, 513, 512, 1);
        //Border around map

        drawer.borderedRectangle(Color.WHITE, Color.GRAY, 45, 165, 165, 37, 1);
        //"Next Phase" button background

        drawer.borderedRectangle(Color.WHITE, Color.GRAY, 30, 471, 195, 37, 1);
        //Pause button background

        drawer.borderedRectangle(Color.WHITE, Color.GRAY, 794, 471, 205, 37, 1);
        //Pause button background

        drawer.borderedRectangle(Color.WHITE, Color.GRAY, 806, 132, 63, 21, 1);
        //Claim button background

        drawer.borderedRectangle(Color.WHITE, Color.GRAY, 911, 132, 91, 21, 1);
        //Deploy/upgrade button background

        drawer.borderedRectangle(Color.LIGHT_GRAY, Color.GRAY, 809, 174, 75, 21, 1);
        //Market interface selection button background

        drawer.borderedRectangle(Color.LIGHT_GRAY, Color.GRAY, 905, 174, 83, 21, 1);
        //Auction interface selection button background

        drawer.borderedRectangle(Color.WHITE, Color.WHITE, 769, 163, 255, 1, 1);
        //Line separating SelectedTileInfoTable from MarketInterfaceTable

        drawer.lineRectangle(Color.WHITE, 803, 35, 66, 66, 1);
        drawer.lineRectangle(Color.WHITE, 923, 35, 66, 66, 1);
        //Borders around College and Roboticon icons in SelectedTileInfoTable
    }

    /**
     * Sets up the buttons to be placed onto the interface later by defining their visual representations and their
     * on-click functions together
     */
    //Extended for Assessment 3 with new buttons
    private void constructButtons() {
        //Button that, when clicked, ends the current turn for the current player prematurely
        endTurnButton = new TextButton("NEXT PHASE", largeButtonStyle);
        endTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.nextPhase();
            }
        });
        drawer.toggleButton(endTurnButton, false, Color.GRAY);
        //Turn off the "END TURN" button right away to force players into selecting tiles

        //Button which can be clicked on to pause the game
        pauseButton = new TextButton("II | Pause Game", largeButtonStyle);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.pauseGame();

                Gdx.input.setInputProcessor(pauseStage);
            }
        });
        drawer.toggleButton(pauseButton, true, Color.BLACK);

        //Button which can be clicked on to go to the mini game
        miniGameButton = new TextButton("Mini Game ($20)", largeButtonStyle);
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
        drawer.toggleButton(miniGameButton, true, Color.BLACK);

        closeEventMessageButton = new TextButton("CLOSE MESSAGE", smallButtonStyle);
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

        phaseInfoTable = new PhaseInfoTable();
        phaseInfoTable.timer.setTerminalMethod(() -> engine.nextPhase());
        phaseInfoTable.updateLabels(engine.phase());
        tableLeft.add(phaseInfoTable).colspan(2).align(Align.center);
        //Add the timer to the table

        tableLeft.row();
        tableLeft.add(endTurnButton).padTop(15).padBottom(30);
        //Add the "End Phase" button to the table

        tableLeft.row();
        tableLeft.add(new Label("CURRENT PLAYER", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE))).colspan(2);
        //Window-dressing: adds "CURRENT PLAYER" label

        playerInfoTable = new PlayerInfoTable();
        playerInfoTable.showPlayerInfo(engine.currentPlayer());
        tableLeft.row();
        tableLeft.add(playerInfoTable).padTop(5);

        tableLeft.row();
        tableLeft.add(pauseButton).padTop(98).colspan(2);
        //Prepare and add the pause button to the bottom of the table

        gameStage.addActor(tableLeft);
        //Add left-hand table to the stage

        phaseInfoTable.updateLabels(engine.phase());
        playerInfoTable.showPlayerInfo(engine.currentPlayer());
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

        selectedTileInfoTable = new SelectedTileInfoTable();
        selectedTileInfoTable.setClaimTileButtonFunction(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.claimTile();
            }
        });

        selectedTileInfoTable.setDeployRoboticonButtonFunction(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!engine.selectedTile().hasRoboticon()) {
                    engine.deployRoboticon();

                    selectTile(engine.selectedTile(), false);
                    //Re-select the current tile to update the UI
                } else {
                    engine.refreshUpgradeOverlay();
                    //Refresh the upgrade options shown on the roboticon upgrade overlay

                    upgradeOverlayVisible = true;
                    //Set the renderer to show the upgrade overlay if this button is clicked after a tile with a
                    //roboticon was clicked on
                }
            }
        });

        tableRight.add(selectedTileInfoTable).padBottom(20);

        marketInterfaceTable = new MarketInterfaceTable();
        marketInterfaceTable.refreshPlayers(engine.players(), engine.currentPlayer());
        marketInterfaceTable.align(Align.top);
        tableRight.row();
        tableRight.add(marketInterfaceTable).colspan(2).height(299);
        //Establish market and add market interface to right-hand table

        tableRight.row();
        tableRight.add(miniGameButton);

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

        pauseTable.row();
        pauseTable.add(new Label("Sabbaticoup", new Label.LabelStyle(titleFont.font(), Color.BLACK))).padBottom(30);
        //Add the game's logo to the pause menu

        TextButton.TextButtonStyle menuButtonStyle = new TextButton.TextButtonStyle();
        menuButtonStyle.font = menuFont.font();
        menuButtonStyle.fontColor = Color.BLACK;
        menuButtonStyle.pressedOffsetX = 1;
        menuButtonStyle.pressedOffsetY = -1;
        //Establish the visual style of the pause menu's buttons

        TextButton resume = new TextButton("Resume", menuButtonStyle);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.resumeGame();
            }
        });
        //Establish and prepare a button to resume the game from the pause menu

        pauseTable.row();
        pauseTable.add(resume);
        //Add the resume button to the pause menu's interface

        pauseStage.addActor(pauseTable);
        //Prepare the pause menu's interface to be shown on the screen when the game is paused
    }

    /**
     * Set up an overlay which shows up when the current player opts to upgrade their roboticon
     * This will allow them to upgrade its food, energy or ore production stats
     */
    private void constructUpgradeOverlay() {
        upgradeOverlay = new UpgradeOverlay();
        //Establish the upgrade overlay

        engine.setUpgradeOverlayButtonFunctions();

        upgradeOverlayVisible = false;
        //Stop the GameScreen's renderer from rendering the overlay right away
    }

    public boolean getUpgradeOverlayVisible() {
        return upgradeOverlayVisible;
    }

    //new for assessment 3
    private void constructEventMessageOverlay() {
        eventMessageOverlay = new Overlay(Color.GRAY, Color.WHITE, 900, 200, 3);
        eventMessage = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));

        eventMessageOverlayVisible = false;

        eventMessageOverlay.table().add(new Label("RANDOM EVENT!", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE)));
        eventMessageOverlay.table().row();
        eventMessageOverlay.table().add(eventMessage);

        eventMessageOverlay.table().row();
        eventMessageOverlay.table().add(closeEventMessageButton);
    }

    //new for assessment 3
    public void showEventMessage(String message) {
        eventMessage.setText(message);
        eventMessageOverlayVisible = true;
    }

  //new for assessment 3
  public void hideEventMessage() {
        eventMessageOverlayVisible = false;
    }

    /**
     * Constructs (or reconstructs) and prepares an overlay to describe an impending trade offer and give its recipient
     * a choice to accept or reject that offer
     *
     * @param trade The trade offer to be presented to the active player
     */
    private void constructTradeOverlay(Trade trade){
    	tradeOverlay = new TradeOverlay();
        //Build (or rebuild) the overlay's structure

        String offeredResources = "";
        offeredResources += trade.oreAmount + " Ore\n";
        offeredResources += trade.energyAmount + " Energy\n";
        offeredResources += trade.foodAmount + " Food";
        //Compile the proposition's offerings into a multi-line string

        tradeOverlay.setOffer(trade.getSender().getCollege().getName() + " College", offeredResources, trade.getPrice() + " Money");
        //Write the name of the college representing the player who sent the offer; the resources offered in the
        //proposition and the monetary amount demanded in the proposition to the overlay itself

        tradeOverlay.setAcceptButtonFunction(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeTradeOverlay();
                if (currentTrade.execute()){
                    playerInfoTable.showPlayerInventory(engine.currentPlayer());
                    engine.testTrade();
                    //If the active player chooses to accept their offer, update their on-screen inventory to reflect
                    //what they gained and what they lost

                    marketInterfaceTable.setTradePrice(marketInterfaceTable.tradePrice());
                    //Reset the pricing section of the auction-house interface to acknowledge the active player's
                    //loss of money in the trade that they just accepted

                    if (engine.phase() == 2) {
                        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.market().getRoboticonBuyPrice()) {
                            marketInterfaceTable.toggleMarketButton(ResourceType.ROBOTICON, true, true, Color.GREEN);
                        } else {
                            marketInterfaceTable.toggleMarketButton(ResourceType.ROBOTICON, true, false, Color.RED);
                        }
                    } else if (engine.phase() == 5) {
                        engine.refreshMarketButtonAvailability();
                    }
                    //Update the options available to the player in the market to acknowledge the active player's
                    //loss of money in the trade that they just accepted
                }
            }
        });

        tradeOverlay.setRejectButtonFunction(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeTradeOverlay();
                //If the active player rejects the trade offer that they receive, simply close the trade overlay

                engine.testTrade();
            }
        });

    	tradeOverlayVisible = false;
    }

    /**
     * Prepares the market's in-game interface (which is displayed on this screen) for later use
     */
    private void constructMarketInterface() {
        engine.setMarketButtonFunctions();
        engine.setAuctionButtonFunctions();

        marketInterfaceTable.setMarketButtonText(ResourceType.ORE, true, "-" + engine.market().getOreBuyPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ORE, false, "+" + engine.market().getOreSellPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ENERGY, true, "-" + engine.market().getEnergyBuyPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ENERGY, false, "+" + engine.market().getEnergySellPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.FOOD, true, "-" + engine.market().getFoodBuyPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.FOOD, false, "+" + engine.market().getFoodSellPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ROBOTICON, true, "-" + engine.market().getRoboticonBuyPrice());
        //Set the labels of the market's purchase/sale buttons to state the amounts of money that the active player
        //will lose or gain in their respective transactions

        marketInterfaceTable.setMarketStockText(ResourceType.ORE, engine.market().getOreStock());
        marketInterfaceTable.setMarketStockText(ResourceType.ENERGY, engine.market().getEnergyStock());
        marketInterfaceTable.setMarketStockText(ResourceType.FOOD, engine.market().getFoodStock());
        marketInterfaceTable.setMarketStockText(ResourceType.ROBOTICON, engine.market().getRoboticonStock());
        //Update the market's internal stock labels

        engine.resetAuctionInterface();
        //Prepare the auction-house section of the interface for later use

        engine.closeMarketInterface();
        //Stop transactions from occurring in the market at the beginning of the game
    }

    /**
     * Returns the button used to allow for players to prematurely end their turns
     * This method is required to allow for the GameEngine class to turn the button off during phase 1
     *
     * @return TextButton The in-game "End Turn" button
     */
    public TextButton endTurnButton() {
        return endTurnButton;
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
        if (lastTileClickedFlash != null) {
            lastTileClickedFlash.cancelAnimation();
            lastTileClickedFlash = null;
        }

        if (showAnimation) {
            lastTileClickedFlash = new AnimationTileFlash(tileXOffset + tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
            addAnimation(lastTileClickedFlash);
        }

        selectedTileInfoTable.showTileInfo(tile);

        if (tile.isOwned()) {
            selectedTileInfoTable.toggleClaimTileButton(false);
            //Disable the button for claiming the tile if it's already been claimed

            if (tile.hasRoboticon()) {
                if (engine.phase() == 3 && tile.getOwner() == engine.currentPlayer()) {
                    selectedTileInfoTable.toggleDeployRoboticonButton(true);
                    //Allow for the upgrading of roboticons that have been planted on owned tiles
                } else {
                    selectedTileInfoTable.toggleDeployRoboticonButton(false);
                    //Ensure that players can't upgrade roboticons that aren't theirs or upgrade their own outside
                    //of phase 3
                }
            } else {
                if (engine.phase() == 3 && tile.getOwner() == engine.currentPlayer() && engine.currentPlayer().getRoboticonInventory() > 0) {
                    selectedTileInfoTable.toggleDeployRoboticonButton(true);
                    //If the currently-selected tile is owned by the active player; that player owns at least one
                    //roboticon and the tile itself doesn't harbour one, provide the player with the option to
                    //deploy one of their spare roboticons on the tile if the game is currently in phase 3
                } else {
                    selectedTileInfoTable.toggleDeployRoboticonButton(false);
                }
            }
        } else {
            if (engine.phase() == 1) {
                selectedTileInfoTable.toggleClaimTileButton(true);
                //Allow unclaimed tiles to be claimed by players during phase 1
            } else {
                selectedTileInfoTable.toggleClaimTileButton(false);
            }

            selectedTileInfoTable.toggleDeployRoboticonButton(false);
            //Stop players from trying to deploy roboticons on tiles that they don't own
        }
    }

    /**
     * Closes the upgrade overlay and restores the functionality of the game's main stage
     */
    //new for assessment 3
    public void closeUpgradeOverlay() {
        upgradeOverlayVisible = false;
        //Hide the upgrade overlay again
        
        engine.testTrade();
    }

    /**
     * Moves the chancellor's visual representation around the map during the "capture the chancellor" minigame
     * that can take place in phase 3
     */
    public void updateChancellor() {
        //Phase Timer less than 16 seconds?
        if (phaseInfoTable.timer.seconds() <= 15) {
            drawer.drawChancellor(engine.chancellor().getCoordX(), engine.chancellor().getCoordY());

            //Has chancellor been captured?
            if (engine.selectedTile() == engine.chancellor().getTile()) {
                engine.chancellor().captured();
                playerInfoTable.updateResource(engine.currentPlayer(), ResourceType.MONEY);
            }
            //Deselect latest tile
            engine.deselectTile();
            if (lastTileClickedFlash != null) {
                lastTileClickedFlash.cancelAnimation();
                lastTileClickedFlash = null;
            }
        }
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

    private void openTradeOverlay() {
        tradeOverlayVisible = true;
    }
    
    public void closeTradeOverlay(){
    	tradeOverlayVisible = false;

        engine.removeTrade(currentTrade);
    }

	public boolean TradeOverlayVisible() {
		return tradeOverlayVisible;
		
	}

    /**
     * Casts a trade-offer as the most important one to be dealt with
     *
     * @param trade The trade-offer to be presented ASAP
     */
	public void activeTrade(Trade trade) {
		constructTradeOverlay(trade);
		currentTrade = trade;
		openTradeOverlay();

	}

    void assignEngine(GameEngine engine) {
        this.engine = engine;
    }

    public Game getGame(){
        return this.game;
    }
}
