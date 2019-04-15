package tasks;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import animation.FinalScreen;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import game.Constants;
import game.Counter;
import game.GameFlow;
import levels.LevelInformation;
import tables.HighScoresTable;
import tables.ScoreInfo;

import java.util.List;

/**
 * The type Game task.
 */
public class GameTask implements Task<Void> {
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private List<LevelInformation> levels;
    private HighScoresTable highScores;


    /**
     * Instantiates a new Game task.
     *
     * @param run      the run
     * @param ks       the ks
     * @param levels   the levels
     * @param hiScores the hi scores
     */
    public GameTask(AnimationRunner run, KeyboardSensor ks, List<LevelInformation> levels, HighScoresTable hiScores) {
        this.runner = run;
        this.keyboard = ks;
        this.levels = levels;
        this.highScores = hiScores;
    }

    /**
     * runs the task.
     * @return Void null
     */
    public Void run() {
        Counter score = new Counter(0);
        Counter lives = new Counter(Constants.LIVES);
        GameFlow game = new GameFlow(this.runner, this.keyboard, score, lives);
        game.runLevels(this.levels);
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                this.keyboard.SPACE_KEY, new FinalScreen(lives, score)));
        if (this.highScores.getRank(score.getValue()) <= this.highScores.size()) {
            DialogManager dialog = this.runner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            this.highScores.add(new ScoreInfo(name, score.getValue()));
        }
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                this.keyboard.SPACE_KEY, new HighScoresAnimation(this.highScores)));
        return null;
    }
}
