package tasks;

import animation.AnimationRunner;

/**
 * The type Quit task.
 */
public class QuitTask implements Task<Void> {
    private AnimationRunner runner;

    /**
     * Instantiates a new Quit task.
     *
     * @param run the run
     */
    public QuitTask(AnimationRunner run) {
        this.runner = run;
    }

    /**
     * runs the task.
     * @return Void null
     */
    public Void run() {
        this.runner.closeGui();
        System.exit(0);
        return null;
    }
}
