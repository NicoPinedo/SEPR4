/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import drtn.game.GameEngine;
import drtn.game.entity.Player;
import drtn.game.enums.ResourceType;
import drtn.game.util.TTFont;
import teamfractal.util.screens.AbstractAnimationScreen;
import drtn.game.GameEngine;
import drtn.game.entity.Player;
import drtn.game.enums.ResourceType;
import teamfractal.util.screens.AbstractAnimationScreen;
import drtn.game.GameEngine;
import drtn.game.entity.Player;
import drtn.game.enums.ResourceType;
import drtn.game.util.TTFont;
import teamfractal.util.screens.AbstractAnimationScreen;
import drtn.game.GameEngine;
import drtn.game.entity.Player;
import drtn.game.enums.ResourceType;
import teamfractal.util.screens.AbstractAnimationScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//new class for assessment 3
public class MiniGameScreen extends AbstractAnimationScreen implements Screen {
    private static TextureRegionDrawable cardTexture
            = new TextureRegionDrawable(new TextureRegion(new Texture("minigame/back.png")));
    private static TextureRegionDrawable cardTextureAddMoney
            = new TextureRegionDrawable(new TextureRegion(new Texture("minigame/add-money.png")));
    private static TextureRegionDrawable cardTextureAddRoboticon
            = new TextureRegionDrawable(new TextureRegion(new Texture("minigame/add-roboticon.png")));
    private static TextureRegionDrawable cardTextureNothing
            = new TextureRegionDrawable(new TextureRegion(new Texture("minigame/badluck.png")));
    private static List<TextureRegionDrawable> textures;

    /**
     * Static initialization block. Adds card textures to an ArrayList.
     */
    static {
        textures = new ArrayList<TextureRegionDrawable>(3);
        textures.add(cardTextureAddMoney);
        textures.add(cardTextureAddRoboticon);
        textures.add(cardTextureNothing);

        smallFontRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"), 16);
    }

    private Random rnd = new Random();
    private boolean clicked = false;
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private Table table;
    private int width;
    private int high;
    private Stage stage;

    private static TTFont smallFontRegular;

    /**
     * Method show() implements interface's method. Creates GUI for minigame and adds functions to it.
     */
    @Override
    public void show() {

        stage = new Stage();
        this.table = new Table();
        table.setFillParent(true);

        /**
         * New button objects created
         */
        button1 = new ImageButton(cardTexture);
        button2 = new ImageButton(cardTexture);
        button3 = new ImageButton(cardTexture);

        /**
         * Font style added to the button.
         */
        TextButton.TextButtonStyle buttonStyle = GameScreen.getLargeButtonStyle();
        buttonStyle.font = smallFontRegular.font();
        /**
         * Buttons are added to the screen.
         */
        TextButton buttonBack = new TextButton("BACK", buttonStyle);
        table.add(button1).padRight(10);
        table.add(button2).padRight(10);
        table.add(button3);
        stage.addActor(table);


        buttonBack.setPosition(Gdx.graphics.getWidth() - buttonBack.getWidth(), 0);
        stage.addActor(buttonBack);

        /**
         * Adds Listener to the "back" button. Allows to go back to the current game and updates players state.
         */
        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player player = GameEngine.getInstance().currentPlayer();
                GameEngine.getInstance().backToGame();
                GameEngine.getInstance().updateCurrentPlayer(player);
            }
        });

        /**
         * Implements behaviour when button object is clicked.
         */
        ChangeListener event = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (clicked) return ;
                clicked = true;

                GameActions[] allActions = GameActions.values();
                int index = rnd.nextInt(allActions.length);
                GameActions choose_gift = allActions[index];
                /**
                 * Implements update of Player's Money and quantity of Roboticons.
                 */
                Player player = GameEngine.getInstance().currentPlayer();
                System.out.println(choose_gift.toString());
                switch (choose_gift) {
                    case money:
                        GameEngine.getInstance().currentPlayer().setResource(ResourceType.MONEY, GameEngine.getInstance().currentPlayer().getResource(ResourceType.MONEY) - 20);
                        player.setResource(ResourceType.MONEY, player.getResource(ResourceType.MONEY) + 100);
                        break;
                    case robotcoin:
                        GameEngine.getInstance().currentPlayer().setResource(ResourceType.MONEY, GameEngine.getInstance().currentPlayer().getResource(ResourceType.MONEY) - 20);
                        player.increaseRoboticonInventory();
                        break;
                    case lose_money:
                        GameEngine.getInstance().currentPlayer().setResource(ResourceType.MONEY, GameEngine.getInstance().currentPlayer().getResource(ResourceType.MONEY) - 20);
                        break;
                }

                ImageButton listener = (ImageButton) event.getListenerActor();
                ImageButton.ImageButtonStyle style = listener.getStyle();
                style.imageUp = textures.get(index);
            }
        };

        button1.addListener(event);
        button2.addListener(event);
        button3.addListener(event);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {

    }

    @Override
    protected Batch getBatch() {
        return null;
    }

    @Override
    public Size getScreenSize() {
        return null;
    }

    enum GameActions {money, robotcoin, lose_money}
}