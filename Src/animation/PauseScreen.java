package animation;

import biuoop.DrawSurface;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {

    /**
     * checks conditions for the running of a single frame.
     *
     * @param d  the draw surface
     * @param dt the relative running time passed.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * returns true if the animation should stop.
     *
     * @return this.stop
     */
    public boolean shouldStop() {
        return false;
    }
}
