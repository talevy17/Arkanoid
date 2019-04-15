package animation;

import game.Constants;
import sprites.SpriteCollection;
import biuoop.DrawSurface;
import game.Counter;


/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private int countFrom;
    private double frameNumber;
    private SpriteCollection game;
    private Counter frames;
    private boolean stop;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.frameNumber = Constants.FRAMES_PER_SECOND / (countFrom / numOfSeconds);
        this.game = gameScreen;
        this.frames = new Counter(0);
        this.stop = false;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        frames.increase();
        this.game.drawAllOn(d);
        int count = this.countFrom - ((int) (this.frames.getValue() / this.frameNumber));
        if (frames.getValue() >= countFrom * this.frameNumber) {
            this.stop = true;
            return;
        }
        d.setColor(java.awt.Color.DARK_GRAY);
        d.drawText(Constants.GUI_WIDTH / 4 - 3, Constants.GUI_HEIGHT / 2 + 5,
                "Game start in: " + Integer.toString(count), 50);
        d.setColor(java.awt.Color.red);
        d.drawText(Constants.GUI_WIDTH / 4, Constants.GUI_HEIGHT / 2,
                "Game starts in: " + Integer.toString(count), 50);
    }

    /**
     * returns true if the animation should stop.
     * @return !this.running.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
