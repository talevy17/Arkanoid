package game;

import sprites.HitListener;
import sprites.Block;
import sprites.Ball;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * if a block was hit, the points should increase.
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitCount() > 0) {
            currentScore.increase(Constants.POINTS_FOR_HIT);
        } else {
            if (beingHit.getHitCount() == 0) {
                currentScore.increase(Constants.POINTS_FOR_REMOVAL);
                beingHit.removeHitListener(this);
            }
        }
    }
}
