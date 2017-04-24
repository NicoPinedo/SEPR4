package drtn.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import drtn.game.enums.ResourceType;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Nico on 27/02/2017.
 */
public class Chancellor {
    /**
     * The player currently taking a turn
     */
    private Player currentPlayer;
    /**
     * An array holding all tiles
     */
    private Tile[] tiles;
    /**
     * The tile that the Chancellor is currently on
     */
    private Tile currentTile;
    /**
     * The current location of the Chancellor, stores x and y coordinates as separate elements in array
     */
    private Integer[] location;
    /**
     * The increase in money once the chancellor has been captured
     */
    private Integer reward;
    /**
     * The period in which the chancellor will move to another location (in milliseconds)
     */
    private Integer movePeriod;
    /**
     * The timer within which the chancellor moves.
     */
    private Timer moveDelay;
    /**
     * Boolean representing whether the chancellor is visible or not
     */
    private Boolean isActive;
    /**
     * The image object providing the Chancellor's visual representation
     */
    private Image icon;
    /**
     * The texture encoding the Chancellor's visual representation
     */
    private Texture iconTexture;

    /**
     * Initialises chancellor for later use in the game
     * Default location in bottom left tile (tile 12)
     *
     * @param tiles The array of tiles from the game
     */
    public Chancellor(Tile[] tiles) {
        this.tiles = tiles;
        this.reward = 50;
        this.movePeriod = 490;
        this.moveDelay = new Timer();
        this.location = new Integer[2];
        this.location[0] = 256; //x coordinate
        this.location[1] = 0; //y coordinate
        this.isActive = Boolean.FALSE;
        this.iconTexture = new Texture("image/chancellor.png");
        this.icon = new Image(iconTexture);
    }

    public void move() {
        Random rand = new Random();
        int tileNum;
        tileNum = rand.nextInt(15)+1; //Chooses random tile value (1-15)
        currentTile = tiles[tileNum];

        int tileWidth;
        tileWidth = 128;

        //Switch case to set a location of chancellor dependant on the currentTile
        switch(tileNum){
            case 0:
                setCoordX(256);
                setCoordY(tileWidth*3);
                break;
            case 1:
                setCoordX(256 + tileWidth);
                setCoordY(tileWidth*3);
                break;
            case 2:
                setCoordX(256 + tileWidth*2);
                setCoordY(tileWidth*3);
                break;
            case 3:
                setCoordX(256 + tileWidth*3);
                setCoordY(tileWidth*3);
                break;
            case 4:
                setCoordX(256);
                setCoordY(tileWidth*2);
                break;
            case 5:
                setCoordX(256 + tileWidth);
                setCoordY(tileWidth*2);
                break;
            case 6:
                setCoordX(256 + tileWidth*2);
                setCoordY(tileWidth*2);
                break;
            case 7:
                setCoordX(256 + tileWidth*3);
                setCoordY(tileWidth*2);
                break;
            case 8:
                setCoordX(256);
                setCoordY(tileWidth);
                break;
            case 9:
                setCoordX(256 + tileWidth);
                setCoordY(tileWidth);
                break;
            case 10:
                setCoordX(256 + tileWidth*2);
                setCoordY(tileWidth);
                break;
            case 11:
                setCoordX(256 + tileWidth*3);
                setCoordY(tileWidth);
                break;
            case 12:
                setCoordX(256);
                setCoordY(0);
                break;
            case 13:
                setCoordX(256 + tileWidth);
                setCoordY(0);
                break;
            case 14:
                setCoordX(256 + tileWidth*2);
                setCoordY(0);
                break;
            case 15:
                setCoordX(256 + tileWidth*3);
                setCoordY(0);
                break;
        }

       //The following gives a random offset to the chancellor, so that it may appear at a random point on the tile
        int offset;
        offset = rand.nextInt(tileWidth - 32); //32 is width of chancellor image. Prevents it from displaying across the tile borders.
        setCoordX(getCoordX() + offset);

        offset = rand.nextInt(tileWidth - 44); //44 is height of chancellor image
        setCoordY(getCoordY() + offset);

    }

    public void captured() {
        deactivate();
        currentPlayer.varyResource(ResourceType.MONEY, reward);
    }

    public void activate() {
        this.isActive = Boolean.TRUE;

        //Begin task to continually move the chancellor
        moveDelay.schedule(new TimerTask(){
            @Override
            public void run() {
                move();
            }
        }, 0, movePeriod);
    }

    public void deactivate() {
        this.isActive = Boolean.FALSE;
        this.currentTile = null;
    }

    public void updatePlayer(Player p) {
        currentPlayer = p;
    }

    private void setCoordX(int x) { location[0] = x;}

    private void setCoordY(int y) { location[1] = y;}

    public int getCoordX() {return location[0];}

    public int getCoordY() {return location[1];}

    public Tile getTile() {return currentTile;}

    public boolean getisActive() {return isActive;}
}
