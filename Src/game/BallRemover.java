package game;

import sprites.Ball;
import sprites.HitListener;
import sprites.Block;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param gameLevel    the game level
     * @param removedBalls the removed balls
     */
    public BallRemover(GameLevel gameLevel, Counter removedBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = removedBalls;
    }

    /**
     * balls that hit the block should be removed from the game.
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (hitter.wasRemoved()) {
            return;
        }
        this.remainingBalls.decrease();
        hitter.removeFromGame(this.gameLevel);
        beingHit.removeHitListener(this);
        if (remainingBalls.getValue() == 0) {
            beingHit.removeFromGame(this.gameLevel);
        }
    }
}
