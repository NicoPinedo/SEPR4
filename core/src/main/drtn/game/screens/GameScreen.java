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
import drtn.game.GameEngine;
import drtn.game.Trade;
import drtn.game.entity.Tile;
import drtn.game.enums.ResourceType;
import drtn.game.screens.tables.MarketInterfaceTable;
import drtn.game.screens.tables.PhaseInfoTable;
import drtn.game.screens.tables.PlayerInfoTable;
import drtn.game.screens.tables.SelectedTileInfoTable;
import drtn.game.util.Drawer;
import drtn.game.util.LabelledElement;
import drtn.game.util.Overlay;
import drtn.game.util.TTFont;
import teamfractal.util.animation.AnimationPlayerWin;
import teamfractal.util.animation.AnimationTileFlash;
import teamfractal.util.animation.IAnimation;
import teamfractal.util.screens.AbstractAnimationScreen;

public class GameScreen extends AbstractAnimationScreen implements Screen {
    private final static int tileXOffset = 256;

    /**
     * Establish visual parameters for in-game buttons
     */
    private static TextButton.TextButtonStyle largeButtonStyle;

    private static TextButton.TextButtonStyle smallButtonStyle;

    private static TTFont headerFontRegular;
    private static TTFont headerFontLight;
    private static TTFont smallFontRegular;
    private static TTFont smallFontLight;

    static {
        headerFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 24);
        headerFontLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"), 24);

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

    public PlayerInfoTable playerInfoTable;
    public PhaseInfoTable phaseInfoTable;
    public SelectedTileInfoTable selectedTileInfoTable;
    public MarketInterfaceTable marketInterfaceTable;

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
        Gdx.input.setInputProcessor(gameStage);
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
        constructTooExpensiveOverlay();
        constructEventMessageOverlay();

        drawer.debug(gameStage);
        //Call this to draw temporary debug lines around all of the actors on the stage

        constructMarketInterface();

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
                        tableRight.getX() + engine.selectedTile().getRoboticonStored().getIcon().getX(),
                        engine.selectedTile().getRoboticonStored().getIcon().getY()
                );
            }

            if (engine.chancellor().getisActive()){
                updateChancellor();
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
        pauseButton = new TextButton("Pause Game", largeButtonStyle);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.pauseGame();
            }
        });

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

        //Button allowing players to upgrade roboticons' food-production capabilities
        foodUpgradeButton = new TextButton("PRICE", smallButtonStyle);
        foodUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.upgradeRoboticon(2);

                playerInfoTable.updateResource(engine.currentPlayer(), ResourceType.FOOD);
                updateUpgradeOptions();
            }
        });

        //Button allowing players to upgrade roboticons' ore-production capabilities
        oreUpgradeButton = new TextButton("PRICE", smallButtonStyle);
        oreUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.upgradeRoboticon(0);

                playerInfoTable.updateResource(engine.currentPlayer(), ResourceType.ORE);
                updateUpgradeOptions();
            }
        });

        /*
         * Button allowing players to upgrade roboticons' energy-production capabilities
         */
        energyUpgradeButton = new TextButton("PRICE", smallButtonStyle);
        energyUpgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                engine.upgradeRoboticon(1);

                playerInfoTable.updateResource(engine.currentPlayer(), ResourceType.ENERGY);
                updateUpgradeOptions();
            }
        });

        //Button allowing players to escape from the upgrade overlay if they decide against upgrading roboticons

        closeUpgradeOverlayButton = new TextButton("CLOSE", smallButtonStyle);
        closeUpgradeOverlayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeUpgradeOverlay();
            }
        });

        confirmTradeButton = new TextButton("Confirm", smallButtonStyle);
        confirmTradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               closeTradeOverlay();
               if (! currentTrade.execute()){
            	   openTooExpensiveOverlay();
               }
               else{
                   playerInfoTable.showPlayerInventory(engine.currentPlayer());
            	   engine.testTrade();
               }

            }
        });

        cancelTradeButton = new TextButton("Cancel", smallButtonStyle);
        cancelTradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               closeTradeOverlay();
               engine.testTrade();
            }
        });

        closePriceOverlayButton = new TextButton("close", smallButtonStyle);
        closePriceOverlayButton.addListener(new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		closeTooExpensiveOverlay();
        	}
        });

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
        tableLeft.add(endTurnButton).padTop(15).padBottom(15);
        //Add the "End Phase" button to the table

        drawer.addTableRow(tableLeft, new Label("CURRENT PLAYER", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE)), 0, 0, 5, 0, 2);
        //Window-dressing: adds "CURRENT PLAYER" label

        playerInfoTable = new PlayerInfoTable();
        playerInfoTable.showPlayerInfo(engine.currentPlayer());
        tableLeft.row();
        tableLeft.add(playerInfoTable).padTop(5);

        drawer.addTableRow(tableLeft, miniGameButton, 105-40, 0, 0, 0, 2);

        drawer.addTableRow(tableLeft, pauseButton, 0, 0, 0, 0, 2);
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

                selectTile(engine.selectedTile(), false);
                //Refresh tile information and tile management UI
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

        tableRight.add(selectedTileInfoTable).padBottom(20);

        marketInterfaceTable = new MarketInterfaceTable();
        drawer.addTableRow(tableRight, marketInterfaceTable, 2);
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
        upgradeOverlay = new Overlay(Color.GRAY, Color.WHITE, 250, 200, 3);
        //Establish the upgrade overlay

        upgradeOverlayVisible = false;
        //Stop the GameScreen's renderer from rendering the overlay right away

        upgradeOverlay.table().add(new Label("UPGRADE ROBOTICON", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE))).padBottom(20);
        //Visual guff

        upgradeOverlay.table().row();
        upgradeOverlay.table().add(new LabelledElement("FOOD", smallFontRegular, Color.WHITE, foodUpgradeButton, 175, 0)).left();
        upgradeOverlay.table().row();
        upgradeOverlay.table().add(new LabelledElement("ENERGY", smallFontRegular, Color.WHITE, energyUpgradeButton, 175, 0)).left();
        upgradeOverlay.table().row();
        upgradeOverlay.table().add(new LabelledElement("ORE", smallFontRegular, Color.WHITE, oreUpgradeButton, 175, 0)).left().padBottom(20);
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
        eventMessageOverlay = new Overlay(Color.GRAY, Color.WHITE, 900, 200, 3);
        eventMessage = new Label("", new Label.LabelStyle(smallFontLight.font(), Color.WHITE));

        eventMessageOverlayVisible = false;

        eventMessageOverlay.table().add(new Label("RANDOM EVENT!", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE)));
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
    	tradeOverlay = new Overlay(Color.GRAY, Color.WHITE, 250, 300, 3);
    	tradeOverlayVisible = false;
        tradeOverlay.table().add(new Label("INCOMING TRADE", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE))).padBottom(20);

        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("From: Player " + trade.getSender().getPlayerNumber(), new Label.LabelStyle(smallFontLight.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("ORE: " + trade.oreAmount, new Label.LabelStyle(smallFontRegular.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("ENERGY: " + trade.energyAmount, new Label.LabelStyle(smallFontRegular.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("FOOD " + trade.foodAmount, new Label.LabelStyle(smallFontRegular.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(new Label("PRICE: " + trade.getPrice(), new Label.LabelStyle(smallFontRegular.font(), Color.WHITE))).left();
        tradeOverlay.table().row();
        tradeOverlay.table().add(confirmTradeButton);
        tradeOverlay.table().add(cancelTradeButton);
    }

    //new for assessment 3
    private void constructTooExpensiveOverlay(){
    	tooExpensiveOverlay = new Overlay(Color.GRAY, Color.WHITE, 250, 300, 3);
    	tooExpensiveOverlayVisible = false;

    	tooExpensiveOverlay.table().add(new Label("NOT ENOUGH MONEY!", new Label.LabelStyle(headerFontRegular.font(), Color.WHITE))).padBottom(20);
    	tooExpensiveOverlay.table().row();
    	tooExpensiveOverlay.table().add(closePriceOverlayButton);
    }

    private void constructMarketInterface() {
        engine.setMarketButtonFunctions();

        marketInterfaceTable.setMarketButtonText(ResourceType.ORE, true, engine.market().getOreBuyPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ORE, false, engine.market().getOreSellPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ENERGY, true, engine.market().getEnergyBuyPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ENERGY, false, engine.market().getEnergySellPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.FOOD, true, engine.market().getFoodBuyPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.FOOD, false, engine.market().getFoodSellPrice());
        marketInterfaceTable.setMarketButtonText(ResourceType.ROBOTICON, true, engine.market().getRoboticonBuyPrice());

        marketInterfaceTable.setMarketStockText(ResourceType.ORE, engine.market().getOreStock());
        marketInterfaceTable.setMarketStockText(ResourceType.ENERGY, engine.market().getEnergyStock());
        marketInterfaceTable.setMarketStockText(ResourceType.FOOD, engine.market().getFoodStock());
        marketInterfaceTable.setMarketStockText(ResourceType.ROBOTICON, engine.market().getRoboticonStock());

        closeMarketInterface();
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

        selectedTileInfoTable.hideTileInfo();
        selectedTileInfoTable.showTileInfo(tile);

        if (tile.isOwned()) {
            selectedTileInfoTable.toggleClaimTileButton(false);
            //Disable the button for claiming the tile if it's already been claimed

            if (tile.hasRoboticon()) {
                if (engine.phase() == 3 && tile.getOwner() == engine.currentPlayer()) {
                    selectedTileInfoTable.toggleDeployRoboticonButton(true);
                } else {
                    selectedTileInfoTable.toggleDeployRoboticonButton(false);
                }
            } else {
                if (engine.phase() == 3 && tile.getOwner() == engine.currentPlayer() && engine.currentPlayer().getRoboticonInventory() > 0) {
                    selectedTileInfoTable.toggleDeployRoboticonButton(true);
                } else {
                    selectedTileInfoTable.toggleDeployRoboticonButton(false);
                }
            }
        } else {
            if (engine.phase() == 1) {
                selectedTileInfoTable.toggleClaimTileButton(true);
            } else {
                selectedTileInfoTable.toggleClaimTileButton(false);
            }

            selectedTileInfoTable.toggleDeployRoboticonButton(false);
        }
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

    public void openResourceMarketInterface() {
        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.market().getOreBuyPrice()) {
            marketInterfaceTable.toggleButton(ResourceType.ORE, true, true, Color.GREEN);
        } else {
            marketInterfaceTable.toggleButton(ResourceType.ORE, true, false, Color.RED);
        }

        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.market().getEnergyBuyPrice()) {
            marketInterfaceTable.toggleButton(ResourceType.ENERGY, true, true, Color.GREEN);
        } else {
            marketInterfaceTable.toggleButton(ResourceType.ENERGY, true, false, Color.RED);
        }

        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.market().getFoodBuyPrice()) {
            marketInterfaceTable.toggleButton(ResourceType.FOOD, true, true, Color.GREEN);
        } else {
            marketInterfaceTable.toggleButton(ResourceType.FOOD, true, false, Color.RED);
        }

        if (engine.currentPlayer().getResource(ResourceType.ORE) > 0) {
            marketInterfaceTable.toggleButton(ResourceType.ORE, false, true, Color.GREEN);
        } else {
            marketInterfaceTable.toggleButton(ResourceType.ORE, false, false, Color.RED);
        }

        if (engine.currentPlayer().getResource(ResourceType.ENERGY) > 0) {
            marketInterfaceTable.toggleButton(ResourceType.ENERGY, false, true, Color.GREEN);
        } else {
            marketInterfaceTable.toggleButton(ResourceType.ENERGY, false, false, Color.RED);
        }

        if (engine.currentPlayer().getResource(ResourceType.FOOD) > 0) {
            marketInterfaceTable.toggleButton(ResourceType.FOOD, false, true, Color.GREEN);
        } else {
            marketInterfaceTable.toggleButton(ResourceType.FOOD, false, false, Color.RED);
        }
    }

    public void openRoboticonMarketInterface() {
        if (engine.currentPlayer().getResource(ResourceType.MONEY) >= engine.market().getRoboticonBuyPrice()) {
            marketInterfaceTable.toggleButton(ResourceType.ROBOTICON, true, true, Color.GREEN);
        } else {
            marketInterfaceTable.toggleButton(ResourceType.ROBOTICON, true, false, Color.RED);
        }
    }

    public void closeMarketInterface() {
        marketInterfaceTable.toggleButton(ResourceType.ORE, true, false, Color.GRAY);
        marketInterfaceTable.toggleButton(ResourceType.ORE, false, false, Color.GRAY);
        marketInterfaceTable.toggleButton(ResourceType.ENERGY, true, false, Color.GRAY);
        marketInterfaceTable.toggleButton(ResourceType.ENERGY, false, false, Color.GRAY);
        marketInterfaceTable.toggleButton(ResourceType.FOOD, true, false, Color.GRAY);
        marketInterfaceTable.toggleButton(ResourceType.FOOD, false, false, Color.GRAY);
        marketInterfaceTable.toggleButton(ResourceType.ROBOTICON, true, false, Color.GRAY);
    }

    public void updateChancellor() {
        if (phaseInfoTable.timer.seconds() <= 15) {
            drawer.drawChancellor(engine.chancellor().getCoordX(), engine.chancellor().getCoordY());
        }
        //Has chancellor been captured?
        if (engine.selectedTile() == engine.chancellor().getTile()){
            engine.chancellor().captured();
            playerInfoTable.updateResource(engine.currentPlayer(), ResourceType.MONEY);
        }
        //deselect latest tile
        engine.selectTile(engine.tiles()[0]);
        if (lastTileClickedFlash != null) {
            lastTileClickedFlash.cancelAnimation();
            lastTileClickedFlash = null;
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
