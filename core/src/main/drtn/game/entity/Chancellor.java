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
     * The image object providing the Chancellor's visual representation
     */
    private Image icon;
    /**
     * The texture encoding the Chancellor's visual representation
     */
    private Texture iconTexture;

    public Chancellor(Player Player, Tile[] tiles){
        this.currentPlayer = Player;
        this.tiles = tiles;
        this.moveChancellor();



    }

    public void moveChancellor(){
        Random rand = new Random();
        int tileID;
        tileID = rand.nextInt(16); //Chooses random tile value (0-15)
        this.currentTile = tiles[tileID];

    }

}
