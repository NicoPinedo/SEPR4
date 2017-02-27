package drtn.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;



public class GameTimer extends com.badlogic.gdx.scenes.scene2d.ui.Label {

    /**
     * Holds the number of minutes that the timer is currently clocked to
     */
    private int minutes;

    /**
     * Holds the number of seconds that the timer is currently clocked to
     */
    private int seconds;

    /**
     * Core timer object that provides the main timing functionality
     */
    private Timer timer;

    /**
     * Holds custom-set method to be executed when the timer reaches zero
     */
    private Runnable terminalMethod;

    /***
     * Holds the colour that the timer's label assumes while it's running
     */
    private Color runColour;

    /**
     * Holds the colour that the timer's label assumes while it's stalled
     */
    private Color pauseColour;

    /**
     * Sets up the timer for later use
     * Works by instantiating the timer as label, changing that label's appearance to reflect the provided time and
     * setting up the label's internal Timer object to run the provided terminal method when the "minutes" and "seconds"
     * variables both reach 0 (after being changed enough times following each Timer interval)
     *
     * @param minutes The number of minutes to which the timer should initially be clocked
     * @param seconds The number of seconds to which the timer should initially be clocked
     * @param font The font of the timer's visual interface
     * @param runColour The colour that the timer will assume while it's running
     * @param pauseColour The colour that the timer will assume while it's paused
     * @param end The runnable subroutine to be executed when the timer reaches zero
     */
    private GameTimer(int minutes, int seconds, TTFont font, Color runColour, Color pauseColour, Runnable end) {
        super("", new LabelStyle(font.font(), Color.WHITE));
        //Set up timer label with the provided TTFont

        this.runColour = runColour;
        this.pauseColour = pauseColour;
        //Import the colours that the timer will assume when it's running and when it's paused

        this.setColor(pauseColour);
        //Set the timer's interface to appear as if it's paused

        setTime(minutes, seconds);
        //Set and render the provided amount of time

        this.terminalMethod = end;
        //Set up the given terminal method for execution when the private timer runs out

        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                decrement();
            }
        }, 1, 1);
        timer.stop();
        //Set up the local timer to decrement internal time variables after every second
        //The "timer.stop()" instruction prevents the timer from starting as soon as it's instantiated
    }

    /**
     * Sets up the timer for later use
     * Overloaded constructor that forgoes setting a terminal method
     *
     * @param minutes The number of minutes to which the timer should initially be clocked
     * @param seconds The number of seconds to which the timer should initially be clocked
     * @param font The font of the timer's visual interface
     * @param runColour The colour that the timer will assume while it's running
     * @param pauseColour The colour that the timer will assume while it's paused
     */
    public GameTimer(int minutes, int seconds, TTFont font, Color runColour, Color pauseColour) {
        this(minutes, seconds, font, runColour, pauseColour, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    /**
     * Sets up the timer for later use
     * Overloaded constructor that sets the timer based on a total number of seconds (rather than a combination
     * of minutes and seconds)
     *
     * @param seconds The number of seconds to which the timer should initially be clocked
     * @param font The font of the timer's visual interface
     * @param runColour The colour that the timer will assume while it's running
     * @param pauseColour The colour that the timer will assume while it's paused
     * @param end The runnable subroutine to be executed when the timer reaches zero
     */
    public GameTimer(int seconds, TTFont font, Color runColour, Color pauseColour, Runnable end) {
        this(0, seconds, font, runColour, pauseColour, end);
    }

    /**
     * Sets up the timer for later use
     * Overloaded constructor that sets the timer based on a total number of seconds (rather than a combination
     * of minutes and seconds) and forgoes setting a terminal method
     *
     * @param seconds The number of seconds to which the timer should initially be clocked
     * @param font The font of the timer's visual interface
     * @param runColour The colour that the timer will assume while it's running
     * @param pauseColour The colour that the timer will assume while it's paused
     */
    public GameTimer(int seconds, TTFont font, Color runColour, Color pauseColour) {
        this(0, seconds, font, runColour, pauseColour, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    /**
     * Sets up the timer for later use
     * Overloaded constructor that forgoes setting a terminal method and renders the timer gray when paused
     *
     * @param minutes The number of minutes to which the timer should initially be clocked
     * @param seconds The number of seconds to which the timer should initially be clocked
     * @param font The font of the timer's visual interface
     * @param runColour The colour that the timer will assume while it's running
     */
    public GameTimer(int minutes, int seconds, TTFont font, Color runColour) {
        this(minutes, seconds, font, runColour, Color.GRAY, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    /**
     * Sets up the timer for later use
     * Overloaded constructor that sets the timer based on a total number of seconds (rather than a combination
     * of minutes and seconds) and renders the timer gray when paused
     *
     * @param seconds The number of seconds to which the timer should initially be clocked
     * @param font The font of the timer's visual interface
     * @param runColour The colour that the timer will assume while it's running
     * @param end The runnable subroutine to be executed when the timer reaches zero
     */
    public GameTimer(int seconds, TTFont font, Color runColour, Runnable end) {
        this(0, seconds, font, runColour, Color.GRAY, end);
    }

    /**
     * Sets up the timer for later use
     * Overloaded constructor that sets the timer based on a total number of seconds (rather than a combination
     * of minutes and seconds), forgoes setting a terminal method and renders the timer gray when paused
     *
     * @param seconds The number of seconds to which the timer should initially be clocked
     * @param font The font of the timer's visual interface
     * @param runColour The colour that the timer will assume while it's running
     */
    public GameTimer(int seconds, TTFont font, Color runColour) {
        this(0, seconds, font, runColour, Color.GRAY, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    /**
     * Clocks the timer to the provided time
     * Specifically changes the internal minutes/seconds variables as necessary and updates the core label's
     * appearance to visualise the new timer provided
     *
     * Changed in Assessment 3
     *
     * @param minutes The number of minutes to which the timer should be clocked
     * @param seconds The number of seconds to which the timer should be clocked
     */
    public void setTime(int minutes, int seconds) {
        if (seconds < 0) seconds = 0; // New
        if (minutes < 0) minutes = 0; // New

        this.minutes = minutes + (seconds / 60);
        this.seconds = seconds % 60;
        this.setText(String.format("%02d:%02d", this.minutes, this.seconds)); // Changed to make cleaner
        //Instantiate private time variables with new values and render it out
    }

    /**
     * Decrements the internal timer by 1 second upon being called
     */
    private void decrement() {
        if (seconds == 0 && minutes > 0) {
            setTime(minutes - 1, 59);
            //Roll over on to the next minute if the seconds' counter ever reaches 0 and there are still minutes left on the clock
        } else {
            setTime(minutes, seconds - 1);
            //Decrement the seconds counter if there are still seconds on the clock
        }

        if (minutes == 0 && seconds == 0) {
            timer.stop();
            terminalMethod.run();
            //Stop the timer if it ever reaches 0...
            //...and execute the object's terminal method
        }
    }

    /**
     * Increments the internal timer by 1 second upon being called
     * This is required to circumvent a bug that causes the timer to lose a second whenever it's started from a state
     * where it has previously been stopped
     */
    public void increment() {
        if (seconds == 59) {
            setTime(minutes + 1, 0);
            //Roll over on to the next minute if the seconds' counter is at 59 and it's due to be incremented
        } else {
            setTime(minutes, seconds + 1);
            //Increment the seconds' counter
        }
    }

    /**
     * Returns the number of minutes currently clocked on the timer
     *
     * Changed in Assessment 3 (Integer -> int)
     *
     * @return int The number of minutes left on the clock
     */
    public int minutes() {
        return minutes;
    }

    /**
     * Returns the number of seconds currently clocked on the timer (within the current minute)
     *
     * Changed in assessment 3 (Integer -> int)
     *
     * @return int The number of seconds left on the clock during the current minute
     */
    public int seconds() {
        return seconds;
    }

    /**
     * Starts the timer (and changes the timer's colour appropriately)
     */
    public void start() {
        this.setColor(runColour);

        timer.start();
    }

    /**
     * Stops the timer (and changes the timer's colour appropriately)
     *
     * Changed in Assessment 3 (to reset back to 0:0)
     */
    public void stop() {
        timer.stop();
        setTime(0, 0);

        this.setColor(pauseColour);
    }

    /**
     * Returns the timer's terminal method inside of a Runnable object
     *
     * @return Runnable Object containing the timer's terminal method
     */
    public Runnable getTerminalMethod() {
        return this.terminalMethod;
    }

    /**
     * Sets a new method for the timer to execute when it hits 0
     *
     * @param terminalMethod The timer's new terminal method
     */
    public void setTerminalMethod(Runnable terminalMethod) {
        this.terminalMethod = terminalMethod;
    }

    /**
     * Runs the timer's terminal method upon being called
     * This can be called at any time
     */
    public void runTerminalMethod() {
        this.terminalMethod.run();
    }
}
