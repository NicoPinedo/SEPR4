/**
 * @author DRTN
 * Team Website with download:
 * https://misterseph.github.io/DuckRelatedFractalProject/
 *
 * This Class contains either modifications or is entirely new in Assessment 3
 *
 * If you are in any doubt a complete changelog can be found here:
 * https://github.com/NotKieran/DRTN-Fractal/compare/Fractal_Initial...development
 *
 * And a more concise report can be found in our Change3 document.
 **/

package io.github.teamfractal.util;

import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.PlayerEffect;

public class PlayerEffectSource extends Array<PlayerEffect> {

    /**
     * This class exists to declare and instantiate the different PlayerEffects that can be randomly applied to
     * players at various stages in the game, thereby providing an interface through which the core game can access
     * them
     */

    /**
     * The game's engine
     */
    private RoboticonQuest game;

    public PlayerEffect partyHard;

    public PlayerEffect uhOh;
    
    public PlayerEffect vikingRaid;

    public PlayerEffect freshersFair;

    public PlayerEffect brexit;

    /**
     * Constructor that prepares a variety of PlayerEffects and adds them all to the internal array structure for
     * later access and use by the game's engine
     *
     * @param game The game's engine
     */
    public PlayerEffectSource(final RoboticonQuest game) {
        this.game = game;
        //Import the game's engine for use by the effects

        configureEffects();
        implementEffects();
    }

    /**
     * Subroutine that instantiates each effect declared above
     */
    private void configureEffects() {
        partyHard = new PlayerEffect("Party Hard", "You decided to throw a party on your newfound acquisition because " +
                "you're a capitalist and your money\nis worthless to you. Unfortunately, you got too drunk and " +
                "attempted to use some your fat stacks as Cards\nAgainst Humanity by scrawling immature statements " +
                "all over them with a permanent marker, thereby\nrendering them worthess.\n\n-30 Money", 0, 0, 0, -30, false, new Runnable() {
            @Override
            public void run() {
                partyHard.impose(game.getPlayer());
            }
        });

        uhOh = new PlayerEffect("Uh Oh!", "Someone left the lights on over night again. Who was it? \nI bet it was Darrell," +
                " it's always Darrell. Either way, \nlooks like it used a lot of your energy. \n\n -50 Energy", 0, -50, 0, 0, false, new Runnable() {
            @Override
            public void run() {
                uhOh.impose(game.getPlayer());
            }
        });
        
        vikingRaid = new PlayerEffect("Viking Raid", "You have been raided by a band of intergalactic Vikings. " +
                "They took:\n\n-10 Ore -10 Energy -10 Food and -10 Money", -10, -10, -10, -10, false, new Runnable() {
            @Override
            public void run() {
                vikingRaid.impose(game.getPlayer());
            }
        });

        freshersFair = new PlayerEffect("Freshers Fair", "It's the University of York freshers fair! That means only one thing." +
                " Free stuff!\nYou receive 10 of each resource!", 10, 10, 10, 10, false, new Runnable() {
            @Override
            public void run() {
                freshersFair.impose(game.getPlayer());
            }
        });

        brexit = new PlayerEffect("Brexit", "Oh no, it looks like Britain finally pulled out of the European Union. " +
                "It only took a few centuries!\nNo need to pay for that membership fee anymore but there are more tariffs on food items." +
                "\n\n +30 Money  -20 Food", 0, 0, -20, 30, false, new Runnable() {
            @Override
            public void run() {
                brexit.impose(game.getPlayer());
            }
        });
    }

    /**
     * Subroutine that adds the effects instantiated above to the internal array structure for future access
     */
    private void implementEffects() {
        add(partyHard);
        add(uhOh);
        add(vikingRaid);
        add(freshersFair);
        add(brexit);
    }
}
