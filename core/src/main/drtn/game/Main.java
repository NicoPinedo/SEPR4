/**
 * @author DRTN
 * Team Website with download:
 * https://nicopinedo.github.io/SEPR4/
 *
 * This Class contains either modifications or is entirely new in Assessment 4
 **/

package drtn.game;

import com.badlogic.gdx.Game;
import drtn.game.screens.SplashScreen;


public class Main extends Game {

	/**
	 * Automatically set the game-state to load the splash-screen as soon as the game window opens
	 */
	@Override
	public void create () {
		setScreen(new SplashScreen(this));
		// setScreen(new MiniGameScreen());
		//Load the splash screen as soon as the game opens
	}

	//It's a bit quiet in here, so I might as well leave you with a few tips
	//Use CTRL-I to generate any essential subroutines required to implement the current class
	//Use CTRL-Q after clicking on a keyword to read up on what it does
	//If you ever reference a class that hasn't been imported, use ALT-ENTER to generate the import call
	//Each screen will run create(), resize() and then render() in that order
}
