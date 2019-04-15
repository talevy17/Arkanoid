package animation;

import biuoop.DrawSurface;
import game.Constants;
import tables.HighScoresTable;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable table;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.table = scores;
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
        d.drawText(50, 100, "High Scores", 70);
        int location = 200;
        int xScore = 500;
        int xName = 100;
        d.drawText(xName, location, "NAME", 50);
        d.drawText(xScore, location, "SCORE", 50);
        location += 50;
        xScore += 55;
        for (int i = 1; i <= this.table.actualSize(); i++) {
            String name = this.table.getHighScores().get(i - 1).getName();
            String score = Integer.toString(this.table.getHighScores().get(i - 1).getScore());
            d.drawText(xName, location + (i * 40), name, 30);
            d.drawText(xScore, location + (i * 40), score, 30);
        }
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return false;
    }
}
