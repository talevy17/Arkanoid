package animation;

import biuoop.DrawSurface;
import game.Constants;
import game.Counter;

/**
 * The type Final screen.
 */
public class FinalScreen implements Animation {
    private Counter score;
    private Counter lifeLine;

    /**
     * Instantiates a new Final screen.
     *
     * @param lives the lives
     * @param score the score
     */
    public FinalScreen(Counter lives, Counter score) {
        this.score = score;
        this.lifeLine = lives;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(java.awt.Color.GRAY);
        d.fillRectangle(0, 0, Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        d.setColor(java.awt.Color.WHITE);
        if (this.lifeLine.getValue() == -1) {
            d.drawText(Constants.GUI_WIDTH / 2 - 80, d.getHeight() / 2, "Game Over", 32);
            d.drawText(Constants.GUI_WIDTH / 2 - 125, d.getHeight() / 2 + 40,
                    "Your score is " + Integer.toString(this.score.getValue()), 32);
        } else {
            d.drawText(Constants.GUI_WIDTH / 2 - 8, d.getHeight() / 2, "You Win!", 32);
            d.drawText(Constants.GUI_WIDTH / 2 - 20, d.getHeight() / 2 + 40,
                    "Your score is " + Integer.toString(this.score.getValue()), 32);
        }
    }

    /**
     * returns true if the animation should stop.
     * @return !this.running.
     */
    public boolean shouldStop() {
        return false;
    }
}
