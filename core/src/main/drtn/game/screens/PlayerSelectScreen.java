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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import drtn.game.GameEngine;
import drtn.game.util.Drawer;
import drtn.game.util.TTFont;
import drtn.game.GameEngine;
import drtn.game.GameEngine;
import drtn.game.util.Drawer;
import drtn.game.util.TTFont;

/**
 * New Class for Assessment 3, allows user to slect the number of players
 * @author jc1850
 *
 */
//new class for assessment 3
public class PlayerSelectScreen implements Screen {
	
	/**
     * Stores current game-state, enabling transitions between screens and external QOL drawing functions
     */
    private Game game;

    /**
     * On-screen stage which can be populated with actors
     */
    private Stage stage;

    /**
     * Provides the spatial framework for menu buttons and labels to be organised over
     */
    private Table table;


    /**
     * Establishes the font which is used to encode the menu's options
     */
    private TTFont menuFont;

    /**
     * Establishes the font which is used to encode the game's title
     */
    private TTFont titleFont;

    /**
     * Establishes the font which, at the moment, encodes a "Title TBC" message
     */
    private TTFont tempFont;

    /**
     * Object defining QOL drawing functions for rectangles and on-screen tables
     * Used in this class accelerate table row creation
     */
    private Drawer drawer;

    /**
     * Batch that manages the rendering pipeline for all of the images to be displayed on the screen
     */
    private SpriteBatch batch;

    /**
     * The object which will encode the menu's background
     */
    private Sprite background;
    
    /**
     * increase number of human players
     */
	private TextButton addPlayerButton;

	/**
     * increase number of AI players
     */
	private TextButton addAIPlayerButton;

	/**
     * decrease number of human players
     */
	private TextButton removePlayerButton;

	/**
     * decrease number of AI players
     */
	private TextButton removeAIPlayerButton;
	
	/**
	 * show which part of the screen changes the number of human players
	 */
	private Label playerLabel;

	/**
	 * show which part of the screen changes the number of AI players
	 */
	private Label AIPlayerLabel;
	
	/**
	 * display number of human players
	 */
	private Label playerAmountLabel;

	/**
	 * display number of AI players
	 */
	private Label AIPlayerAmountLabel;
	
	/**
	 * button to confirm number of players
	 */
	private TextButton confirmButton;

	/**
	 * variable to store number of human players
	 */
	private int playerAmount;

	/**
	 * variable to store number of AI players
	 */
	private int AIPlayerAmount;

	private GameEngine engine;

	private GameScreen gameScreen;

    /**
     * The menu-screen's initial constructor
     *
     * @param game Variable storing the game's state for rendering purposes
     */
    PlayerSelectScreen(Game game) {
        this.game = game;
        this.gameScreen =  new GameScreen(this.game);
        this.engine = new GameEngine(this.game, this.gameScreen);
        gameScreen.assignEngine(engine);
        
    }
    //Import current game-state
    //create engine and game screen


	@Override
	public void show() {
		drawer = new Drawer(game);
		
        batch = new SpriteBatch();
        //Initialise sprite-batch

        stage = new Stage();
        table = new Table();
        //Initialise stage and button-table

        titleFont = new TTFont(Gdx.files.internal("font/earthorbiterxtrabold.ttf"), 120, 2, Color.BLACK, false);
        menuFont = new TTFont(Gdx.files.internal("font/enterthegrid.ttf"), 36, 2, Color.BLACK, false);

        Gdx.input.setInputProcessor(stage);
        //Set the stage up to accept user inputs

        background = new Sprite(new Texture("image/MenuBG.png"));
        background.setSize(background.getWidth(), background.getHeight());
        background.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        //Create logo sprite and re-size/re-position it to fit into game window

        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Fill the screen with the table
        //This is bound to change in the future for obvious reasons

        TextButton.TextButtonStyle menuButtonStyle = new TextButton.TextButtonStyle();
        menuButtonStyle.font = menuFont.font();
        menuButtonStyle.fontColor = Color.WHITE;
        menuButtonStyle.pressedOffsetX = 1;
        menuButtonStyle.pressedOffsetY = -1;
        //Set up the format for the buttons on the menu

        playerAmount = 0;
        AIPlayerAmount = 0;
        
        playerLabel = new Label("HUMAN", new Label.LabelStyle(menuFont.font(), Color.WHITE));
        playerLabel.setAlignment(Align.left);
        playerAmountLabel = new Label("0", new Label.LabelStyle(menuFont.font(), Color.WHITE));
        playerAmountLabel.setAlignment(Align.center);
        AIPlayerLabel = new Label("CPU", new Label.LabelStyle(menuFont.font(), Color.WHITE));
        AIPlayerLabel.setAlignment(Align.left);
        AIPlayerAmountLabel = new Label("0", new Label.LabelStyle(menuFont.font(), Color.WHITE));
        AIPlayerAmountLabel.setAlignment(Align.center);

        addPlayerButton = new TextButton("ADD", menuButtonStyle);
        addPlayerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                playerAmount += 1;
                refreshLabels();
            }
        });
        //increase number of human players
        
        addAIPlayerButton = new TextButton("ADD", menuButtonStyle);
        addAIPlayerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                AIPlayerAmount += 1;
                refreshLabels();
            }
        });
        //increase number of AI players
        
        removePlayerButton = new TextButton("RMV", menuButtonStyle);
        removePlayerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                playerAmount -= 1;
                refreshLabels();
            }
        });
        //decrease number of human players
        
        removeAIPlayerButton = new TextButton("RMV", menuButtonStyle);
        removeAIPlayerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                AIPlayerAmount -= 1;
                refreshLabels();
            }
        });
        //decrease number of AI players
        
        confirmButton = new TextButton("Confirm", menuButtonStyle);
        confirmButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
               engine.initialisePlayers(AIPlayerAmount, playerAmount);
               game.setScreen(gameScreen);
            }
        });
        //confirm number of players
        
        
        //add all buttons to the table
        table.add(new Label("SET PLAYERS", new Label.LabelStyle(titleFont.font(), Color.WHITE))).colspan(4).padBottom(5);
        table.row();
        table.add(playerLabel).width(200).padLeft(150);
        table.add(removePlayerButton).width(100);
        table.add(playerAmountLabel).width(50);
        table.add(addPlayerButton).width(100).padRight(150);
        table.row();
        table.add(AIPlayerLabel).width(200).padLeft(150);
        table.add(removeAIPlayerButton).width(100);
        table.add(AIPlayerAmountLabel).width(50);
        table.add(addAIPlayerButton).width(100).padRight(150);
        table.row();
        table.add(confirmButton).colspan(4).padTop(20);
        stage.addActor(table);
        refreshLabels();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //OpenGL nonsense
        //First instruction sets background colour

        batch.begin();
        background.draw(batch);
        batch.end();
        //Run through the rendering pipeline to draw the menu's background image to the screen

        stage.act(delta);
        stage.draw();
        //Draw the stage onto the screen
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		menuFont.dispose();

        stage.dispose();

	}
	
	/**
	 * update labels and buttons on screen
	 * disable buttons which should not be pressed
	 * change numbers displayed when incremented/decremented
	 */
    private void refreshLabels() {
        //default gray and disabled
        drawer.toggleButton(addPlayerButton, false, Color.GRAY);
        drawer.toggleButton(addAIPlayerButton, false, Color.GRAY);
		drawer.toggleButton(removePlayerButton, false, Color.GRAY);
		drawer.toggleButton(removeAIPlayerButton, false, Color.GRAY);
		drawer.toggleButton(confirmButton, false, Color.GRAY);
		
		if (playerAmount > 0){
			drawer.toggleButton(removePlayerButton, true, Color.WHITE);
		}
		
		if (AIPlayerAmount > 0){
			drawer.toggleButton(removeAIPlayerButton, true, Color.WHITE);
		}
		
		if(AIPlayerAmount + playerAmount < 4){
			drawer.toggleButton(addPlayerButton, true, Color.WHITE);
			drawer.toggleButton(addAIPlayerButton, true, Color.WHITE);
		}
		
		if (AIPlayerAmount + playerAmount > 1 && playerAmount > 0){
			drawer.toggleButton(confirmButton, true, Color.WHITE);
		}
		playerAmountLabel.setText("" + playerAmount);
		AIPlayerAmountLabel.setText("" + AIPlayerAmount);
	}
}
