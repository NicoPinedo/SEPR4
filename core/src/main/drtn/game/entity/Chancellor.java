package main.drtn.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import main.drtn.game.entity.Player;
import main.drtn.game.entity.Tile;

import java.util.Random;

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

    public Chancellor(Player Player, Tile[] tiles) {
        this.currentPlayer = Player;
        this.tiles = tiles;

        this.location = new Integer[2];
        this.location[0] = 256; //x coordinate
        this.location[1] = 0; //y coordinate

        this.isActive = Boolean.FALSE;
        this.iconTexture = new Texture("image/chancellor.png");
        this.icon = new Image(iconTexture);

        this.move();
    }

    public void move() {
        Random rand = new Random();
        int tileNum;
        tileNum = rand.nextInt(16); //Chooses random tile value (0-15)
        currentTile = tiles[tileNum];

        int tileWidth;
        int tileCentre; //offset from edge to centre of tile
        tileWidth = 128;
        tileCentre = 64;

        System.out.println("TILENUM == " + tileNum);

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
        setCoordX(getCoordX() + tileCentre - 16);
        setCoordY(getCoordY() + tileCentre - 22);
    }

    public void activate() {
        this.isActive = Boolean.TRUE;
    }

    public void deactivate() {
        this.isActive = Boolean.FALSE;
        this.currentTile = null;
    }

    public void captured() {

    }

    private void setCoordX(int x) { location[0] = x;}

    private void setCoordY(int y) { location[1] = y;}


    public boolean getisActive() {return isActive;}

    public int getCoordX() {return location[0];}

    public int getCoordY() {return location[1];}
}
