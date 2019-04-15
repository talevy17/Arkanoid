package game;

import sprites.Sprite;
import biuoop.DrawSurface;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter liveCount;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param lives the lives
     */
    public LivesIndicator(Counter lives) {
        liveCount = lives;
    }

    /**
     * draws the Sprite.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        d.drawText(0, Constants.FRAME_BOUND - 5,
                "Lives: " + Integer.toString(this.liveCount.getValue()), 20);
    }

    /**
     * Notifies the Sprite that time has passed.
     *
     * @param dt relative to frames time passed.
     */
    public void timePassed(double dt) {
        return;
    }
}
