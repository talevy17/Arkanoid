package game;

import animation.AnimationRunner;
import biuoop.KeyboardSensor;
import levels.LevelInformation;

import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private Counter score;
    private Counter lives;

    /**
     * Instantiates a new Game flow.
     *
     * @param ar     the ar
     * @param ks     the ks
     * @param points the points
     * @param tries  the tries
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, Counter points, Counter tries) {
        this.runner = ar;
        this.keyboard = ks;
        this.score = points;
        this.lives = tries;
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboard, this.runner, this.score, this.lives);
            level.initialize();
            while (this.lives.getValue() >= 0 && level.getBlockCount().getValue() > 0) {
                level.playOneTurn();
            }
            if (this.lives.getValue() == -1) {
                break;
            }
        }
    }
}
