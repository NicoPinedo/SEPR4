/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

public class Roboticon {
    /**
     * Unique numerical identifier of the roboticon.
     */
    private int RoboticonID;

    /**
     * Variable holding which player the roboticon belongs to.
     */
    private Player Owner;

    /**
     * Variable holding what tile the roboticon is stored on.
     */
    private Tile CurrentTile;

    /**
     * int variable determining the maximum level of roboticons allowed in the game.
     */
    private int MaxLevel = 10;

    /**
     * Holds the current ore-mining level of the Roboticon
     */
    private int oreLevel = 1;

    /**
     * Holds the current energy-generating level of the Roboticon
     */
    private int energyLevel = 1;

    /**
     * Holds the current food-growing level of the Roboticon
     */
    private int foodLevel = 1;

    /**
     * The image object providing the roboticon's visual representation
     */
    private Image icon;

    /**
     * The texture encoding the roboticon's visual representation
     */
    private Texture iconTexture;

    /**
     * Constructor of the class
     *
     * @param ID     An integer uniquely defining the roboticon, starting at 0
     * @param Player A Player object to own the roboticon
     * @param Tile   A Tile object the roboticon is positioned on and therefore belongs to
     */
    public Roboticon(int ID, Player Player, Tile Tile) {
        RoboticonID = ID;
        this.CurrentTile = Tile;
        this.Owner = Player;

        this.iconTexture = new Texture("image/Roboticon.png");
        this.icon = new Image(iconTexture);

        this.CurrentTile.assignRoboticon(this);
    }

    /**
     * Function to get the current roboticon Level
     * @return int array of the resource
     */
    public int[] getLevel() {
        int[] levels = new int[3];
        levels[0] = oreLevel;
        levels[1] = energyLevel;
        levels[2] = foodLevel;
        return levels;
    }

    public int getRoboticonID(){
        return RoboticonID;
    }

    /**
     * Method to upgrade a single level of the roboticon.
     * <p>
     * The parameter 'Resource' specifies 'Ore', 'Energy' or 'Food' to be upgraded one level.
     * </p>
     * Changed in Assessment 3 - refactored to use switch statement instead of if-else
     * 0: ORE
     * 1: ENERGY
     * 2: FOOD
     */
    public void upgrade(int resource) {
        if (!(this.getLevel()[0] == 0)) {

            switch (resource) {
                case (0):
                    oreLevel += 1;
                    break;
                case (1):
                    energyLevel += 1;
                    break;
                case (2):
                    foodLevel += 1;
                    break;
            }

        }
    }

    /**
     * A method to return the production modifier offered by the roboticon.
     * <p>
     * Contains inherent randomness, not just a 1:1 ratio of level to return each phase of production. The modifier is used outside of
     * this class to multiply the inherent resources located on that tile.
     * </p>
     *
     * @return Modifiers Array to return the modifier for resource production, stored [Ore, Energy, Food]
     */
    public int[] productionModifier() {
        int[] Modifiers = {1, 1, 1};
        int Max = 5;
        int Min = 1;
        Random rand = new Random();

        int n = rand.nextInt(Max) + Min;
        Modifiers[0] = oreLevel * n;

        n = rand.nextInt(Max) + Min;
        Modifiers[1] = energyLevel * n;

        n = rand.nextInt(Max) + Min;
        Modifiers[2] = foodLevel * n;

        return Modifiers;


    }

    /**
     * Returns an Image object with the texture of the roboticon's icon mapped to it
     *
     * @return Image Icon representing the roboticon
     */
    public Image getIcon() {
        return this.icon;
    }

    /**
     * Returns the texture file encoding the roboticon's icon
     *
     * @return Texture The texture encoding the roboticon's icon
     */
    public Texture getIconTexture() {
        return iconTexture;
    }

    /**
     * Provides the roboticon's current ore-mining upgrade cost
     * @return int The roboticon's ore-mining upgrade cost
     */
    public int getOreUpgradeCost() {
        return (oreLevel * 6);
    }

    /**
     * Provides the roboticon's current food-growing upgrade cost
     * @return int The roboticon's food-growing upgrade cost
     */
    public int getFoodUpgradeCost() {
        return (foodLevel * 6);
    }

    /**
     * Provides the roboticon's current energy-generating upgrade cost
     * @return int The roboticon's energy-generating upgrade cost
     */
    public int getEnergyUpgradeCost() {
        return (energyLevel * 6);
    }

    /**
     * Provides the maximum level to which this roboticon can be upgraded to (across all three individual upgrade types)
     * @return int The roboticon's consistent level cap
     */
    public int getMaxLevel() {
        return MaxLevel;
    }
}