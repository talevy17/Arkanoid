package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String stopKey;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the sensor
     * @param key       the key
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.stopKey = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        animation.doOneFrame(d, dt);
        if (!this.keyboard.isPressed(this.stopKey)) {
            this.isAlreadyPressed = false;
        }
        if (this.keyboard.isPressed(this.stopKey) && !this.isAlreadyPressed) {
            this.stop = true;
        }
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return this.stop;
    }
}