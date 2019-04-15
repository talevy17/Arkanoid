package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;
import game.Constants;

/**
 * The type Animation runner.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Instantiates a new Animation runner.
     */
    public AnimationRunner() {
        this.gui = new GUI("Arkanois", Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        this.framesPerSecond = Constants.FRAMES_PER_SECOND;
        this.sleeper = new Sleeper();
    }

    /**
     * Gets gui.
     *
     * @return the gui
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Gets keyboard sensor.
     *
     * @return the keyboard sensor
     */
    public KeyboardSensor getKeyboardSensor() {
        return this.gui.getKeyboardSensor();
    }

    /**
     * Close gui.
     */
    public void closeGui() {
        this.gui.close();
    }

    /**
     * Run.
     *
     * @param animation the animation
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = Constants.ANIMATION / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, (1 / (double) this.framesPerSecond));

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
