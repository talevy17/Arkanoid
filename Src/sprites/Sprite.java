package sprites;

import biuoop.DrawSurface;

/**
 * the sprite interface, unifies all objects in the game.
 *
 * @author Tal Levy
 */
public interface Sprite {
    /**
     * draws the Sprite.
     *
     * @param d DrawSurface
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the Sprite that time has passed.
     *
     * @param dt relative to frames time passed.
     */
    void timePassed(double dt);
}
