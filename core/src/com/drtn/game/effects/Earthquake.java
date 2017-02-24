package com.drtn.game.effects;

import com.drtn.game.GameEngine;
import com.drtn.game.entity.Player;
import com.drtn.game.entity.Tile;
import com.drtn.game.screens.GameScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This class was implemented during Assessment 3.

public class Earthquake extends RandomEvent {

    private int playerAffected;
    private GameEngine gameEngine;
    private ArrayList<Tile> tilesDamaged;
    private int tileDamageValue;

    public Earthquake(GameEngine engine, GameScreen gameScreen) {
        super(gameScreen);
        this.gameEngine = engine;
        this.tilesDamaged = chooseAffectedTiles();
        this.tileDamageValue = getNumberGreaterThanX(5, 2);
    }

    /**
     * A method which returns the tiles chosen to be damaged by the earthquake.
     * @return: An ArrayList<Tile> containing tiles to be damaged.
     */
    public ArrayList<Tile> getTilesDamaged() {
        return this.tilesDamaged;
    }

    /**
     * A method which returns the value which production will be divided by on the damaged tiles.
     * @return: An integer x, where x is the value production will be divided by.
     */
    public int getTileDamageValue() {
        return this.tileDamageValue;
    }

    /**
     * Overridden eventEffect() method initially found in the RandomEvent abstract class.
     * @param doOrUndo: boolean determining whether to cause the event effect, or
     *                reverse it. True -> Cause, False -> Reverse.
     */
    public void eventEffect(boolean doOrUndo) {
        // Divides production on each tile by damage value
        for (Tile tile : this.tilesDamaged) {
            if (doOrUndo) {
                tile.changeOreCount(tile.getOreCount() / this.tileDamageValue);
                tile.changeEnergyCount(tile.getEnergyCount() / this.tileDamageValue);
                tile.changeFoodCount(tile.getFoodCount() / this.tileDamageValue);
            } else {
                tile.changeOreCount(tile.getOreCount() * this.tileDamageValue);
                tile.changeEnergyCount(tile.getEnergyCount() * this.tileDamageValue);
                tile.changeFoodCount(tile.getFoodCount() * this.tileDamageValue);
            }
        }
    }

    /**
     * Overridden eventMessage() method initially found in the RandomEvent abstract class.
     * @param doOrUndo: boolean determining whether to cause the event effect, or
     *                reverse it. True -> Cause, False -> Reverse.
     * @return: A String containing the message to be displayed.
     */
    public String eventMessage(boolean doOrUndo) {
        String messageToReturn;
        if (doOrUndo) {
            messageToReturn = "An earthquake has damaged Player " + (this.playerAffected + 1) +  "'s tiles! " +
                    "Production is now divided by " + tileDamageValue + " on their tiles for "
                    + this.duration + " turns.";
        }
        else {
            messageToReturn = "The damage to Player " + (this.playerAffected + 1) +" from the earthquake 2 turns ago " +
                    "has been repaired! The effects of this have been reversed.";
        }
        return messageToReturn;
    }

    /**
     * A method which chooses the tiles to be damaged by the earthquake.
     * @return: An ArrayList<Tile> containing the affected tiles.
     */
    private ArrayList<Tile> chooseAffectedTiles() {

        ArrayList<Tile> tilesAffected = new ArrayList<Tile>();

        Player players[] = this.gameEngine.players();

        this.playerAffected = randomiser.nextInt(players.length);
        System.out.println("Earthquake affecting Player " + this.playerAffected);

        List<Tile> playerTiles = players[this.playerAffected].getTileList();

        Collections.shuffle(playerTiles);

        for (Tile playerTile : playerTiles) {
            tilesAffected.add(playerTile);
        }

        return tilesAffected;
    }

    /**
     * A method which returns an integer i where x <= i < limit.
     * @param limit upper limit of calculation
     * @param x number to receive number greater than
     * @return: Integer i
     */
    private int getNumberGreaterThanX(int limit, int x) {
        int numberGenerated = randomiser.nextInt(limit);
        if (numberGenerated == 0 || numberGenerated == 1) {
            numberGenerated += x;
        }
        return numberGenerated;
    }

    /**
     * Returns a string representation of an Earthquake instance.
     * @return: The string "<Earthquake: Duration = x> where x = duration."
     */
    public String toString() {
        return "<Earthquake: Duration = " + this.duration + ">";
    }


}
