package game;

import sprites.Sprite;
import biuoop.DrawSurface;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreCount;

    /**
     * Instantiates a new Score indicator.
     *
     * @param score the score
     */
    public ScoreIndicator(Counter score) {
        this.scoreCount = score;
    }

    /**
     * draws the Sprite.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.WHITE);
        d.fillRectangle(0, 0, Constants.GUI_WIDTH, Constants.FRAME_BOUND);
        d.setColor(java.awt.Color.BLACK);
        d.drawText(Constants.GUI_WIDTH / 2 - 50, Constants.FRAME_BOUND - 5,
                "Score: " + Integer.toString(this.scoreCount.getValue()), 20);
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
