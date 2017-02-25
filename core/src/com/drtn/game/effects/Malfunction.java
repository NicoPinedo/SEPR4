package com.drtn.game.effects;

import com.drtn.game.GameEngine;
import com.drtn.game.entity.Roboticon;
import com.drtn.game.entity.Tile;
import com.drtn.game.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

// This class was implemented during Assessment 3.
//TODO: Preserve effect but update with our style

public class Malfunction extends RandomEvent {

    private int playerAffected;
    private List<Tile> playerTiles;
    private ArrayList<Tile> tilesWithRoboticons = new ArrayList<Tile>();
    private int roboticonTileNo;
    private Roboticon roboticonToMalfunction;
    private int[] startingRoboticonLevels;

    public Malfunction(GameEngine gameEngine, GameScreen gameScreen, int playerToAffect) {
        super(gameScreen);
        this.playerAffected = playerToAffect;
        this.playerTiles = gameEngine.players()[this.playerAffected].getTileList();
        this.gatherRoboticonTiles();
        Tile selectedRoboticonTile = this.tilesWithRoboticons.get(randomiser.nextInt(this.tilesWithRoboticons.size()));
        this.roboticonTileNo = selectedRoboticonTile.getID();
        this.roboticonToMalfunction = selectedRoboticonTile.getRoboticonStored();
        this.startingRoboticonLevels = this.roboticonToMalfunction.getLevel();
    }

    /**
     * Gets the roboticon production levels before the malfunction occurs.
     * @return: An int[] array storing the roboticon production levels.
     */
    public int[] getStartingRoboticonLevels() {
        return this.startingRoboticonLevels;
    }

    /**
     * Gets the roboticon which will malfunction.
     * @return: The roboticon object representing the roboticon to malfunction.
     */
    public Roboticon getRoboticonToMalfunction() {
        return this.roboticonToMalfunction;
    }

    /**
     * Overridden eventEffect() method initially found in the RandomEvent class.
     * @param doOrUndo: boolean determining whether to cause the event effect, or
     *                reverse it. True -> Cause, False -> Reverse.
     */
    public void eventEffect(boolean doOrUndo) {
        if (doOrUndo) {
            this.roboticonToMalfunction.setOreLevel(0);
            this.roboticonToMalfunction.setEnergyLevel(0);
            this.roboticonToMalfunction.setFoodLevel(0);
        }
        else {
            this.roboticonToMalfunction.setOreLevel(this.startingRoboticonLevels[0]);
            this.roboticonToMalfunction.setEnergyLevel(this.startingRoboticonLevels[1]);
            this.roboticonToMalfunction.setFoodLevel(this.startingRoboticonLevels[2]);
        }
    }

    /**
     * Overridden eventMessage() method initially found in the RandomEvent class.
     * @param doOrUndo: boolean determining whether to cause the event effect, or
     *                reverse it. True -> Cause, False -> Reverse.
     * @return: A String containing the event message.
     */
    public String eventMessage(boolean doOrUndo) {
        String messageToReturn;
        if (doOrUndo) {
            messageToReturn = "Player " + (this.playerAffected + 1) + "'s roboticon on tile " +
            this.roboticonTileNo + " has malfunctioned and is now out of use for " + this.duration +
            " turns.";
        }
        else {
            messageToReturn = "Player " + (this.playerAffected + 1) + "'s roboticon on tile " +
            this.roboticonTileNo + " has now been fixed and can produce resources as normal.";
        }
        return messageToReturn;
    }

    /**
     * A method which gathers all player tiles which currently have roboticons stored on them.
     */
    private void gatherRoboticonTiles() {
        for (Tile tile: this.playerTiles) {
            if (tile.getRoboticonStored() != null) {
                this.tilesWithRoboticons.add(tile);
            }
        }
    }

    /**
     * Returns a string representation of a Malfunction random event.
     * @return: The string <Malfunction: Duration = x> where x = duration.
     */
    public String toString() {
        return "<Malfunction: Duration = " + this.duration + ">";
    }
}
