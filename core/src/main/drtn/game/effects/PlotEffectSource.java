/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game.effects;

import com.badlogic.gdx.utils.Array;
import drtn.game.GameEngine;
import drtn.game.entity.Tile;
import drtn.game.enums.ResourceType;
import drtn.game.exceptions.InvalidResourceTypeException;

/**
 * Created by Joseph on 13/02/2017.
 */
public class PlotEffectSource extends Array<PlotEffect> {

    /**
     * This class exists to declare and instantiate the different PlotEffects that can be randomly applied to different
     * plots at various stages in the game, thereby providing an interface through which the core game can access
     * them
     */

    /**
     * The game's engine
     */
    private GameEngine game;

    public PlotEffect duckRelatedDisaster;

    public PlotEffect spicy;
    
    public PlotEffect earthquakeDisaster;

    public PlotEffect tornado;

    public PlotEffect strike;

    /**
     * Constructor that prepares a variety of PlotEffects and adds them all to the internal array structure for
     * later access and use by the game's engine
     *
     * @param game The game's engine
     */
    public PlotEffectSource(final GameEngine game) throws InvalidResourceTypeException {
        this.game = game;
        //Import the game's engine for use by the effects

        configureEffects();
        implementEffects();
    }

    /**
     * Subroutine that instantiates each effect declared above
     */
    public void configureEffects() throws InvalidResourceTypeException {
        duckRelatedDisaster = new PlotEffect("Duck-Related Disaster", "A horde of ducks pillage your most " +
                "food-producing tile, ruining many of the crops on it. Food\nproduction on that tile is reduced by " +
                "80% for this turn.", new Float[]{(float) 1, (float) 1, (float) 0.2}, new Runnable() {
            @Override
            public void run() {
                if (game.currentPlayer().getTileList().size() == 0) {
                    return;
                }

                Tile foodProducer = game.currentPlayer().getTileList().get(0);

                for (Tile plot : game.tiles()) {
                    if (plot.getResource(ResourceType.FOOD) > foodProducer.getResource(ResourceType.FOOD)) {
                        foodProducer = plot;
                    }
                }

                try {
                    duckRelatedDisaster.impose(foodProducer, 1);
                } catch (InvalidResourceTypeException e) {
                    e.printStackTrace();
                }
            }
        });

        spicy = new PlotEffect("It's getting spicy", "Some students got hold of some hot pepper seeds and all of your food " +
                "production \nhas been turned over to peppers. Increasing Food output by 200% However this spicy craze " +
                "\nhas caused all other production values to drop to 0.", new Float[]{(float) 0, (float) 0, (float) 2}, new Runnable() {
            @Override
            public void run() {
                if (game.currentPlayer().getTileList().size() == 0) {
                    return;
                }



                for (Tile plot : game.currentPlayer().getTileList()) {
                    try {
                        spicy.impose(plot, 1);
                    } catch (InvalidResourceTypeException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        
        earthquakeDisaster = new PlotEffect("Earthquake disaster", "Due to experiments committed in" +
                "University's of York secret laboratory, a massive\n earthquake hit the surroundings of York." +
        "Ore mines were severely damaged therefore\n ore production has dropped by 90% for this turn.",
                new Float[]{(float) 0.1, (float) 1, (float) 1}, new Runnable() {
            @Override
            public void run() {
                if (game.currentPlayer().getTileList().size() == 0) {
                    return;
                }

                for (Tile plot : game.currentPlayer().getTileList()) {
                    try {
                        earthquakeDisaster.impose(plot, 1);
                    } catch (InvalidResourceTypeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tornado = new PlotEffect("Tornado","Looks like a tornado has struck the campus!" +
        "\nThe gale force winds have blown some of your crops away, reducing food production by 50%. "+
        "\nHowever the winds have increased the output of your wind farms, increasing energy production by 60%",
         new Float[]{(float) 1, (float) 0.5, (float) 1.6}, new Runnable(){
            @Override
            public void run() {
                if (game.currentPlayer().getTileList().size() == 0) {
                    return;
                }

                for (Tile plot : game.currentPlayer().getTileList()) {
                    try {
                        tornado.impose(plot, 1);
                    } catch (InvalidResourceTypeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        strike = new PlotEffect("Roboticon Strike","Some of your roboticons have decided to go on strike." +
                "\nThey are bored of standing in the same place doing the same thing all the time."+
                "\nAll resource production has depleted by 30%",
                new Float[]{(float) 0.7, (float) 0.7, (float) 0.7}, new Runnable(){
            @Override
            public void run() {
                if (game.currentPlayer().getTileList().size() == 0) {
                    return;
                }

                for (Tile plot : game.currentPlayer().getTileList()) {
                    try {
                        strike.impose(plot, 1);
                    } catch (InvalidResourceTypeException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    /**
     * Subroutine that adds the effects instantiated above to the internal array structure for future access
     */
    public void implementEffects() {
        add(duckRelatedDisaster);
        add(spicy);
        add(earthquakeDisaster);
        add(tornado);
        add(strike);
    }
}
