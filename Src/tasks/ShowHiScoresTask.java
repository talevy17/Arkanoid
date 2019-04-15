package tasks;

import animation.AnimationRunner;
import animation.Animation;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;

/**
 * The type Show hi scores task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    private KeyboardSensor keyboard;


    /**
     * Instantiates a new Show hi scores task.
     *
     * @param runner              the runner
     * @param highScoresAnimation the high scores animation
     * @param sensor              the sensor
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation, KeyboardSensor sensor) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
        this.keyboard = sensor;
    }

    /**
     * runs the task.
     *
     * @return Void null
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                this.keyboard.SPACE_KEY, this.highScoresAnimation));
        return null;
    }
}