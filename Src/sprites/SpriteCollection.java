package sprites;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * this class unifies all sprites of the game under one roof.
 *
 * @author Tal Levy
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * class constructor, creates a new list of sprites.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * class constructor given a list of sprites.
     *
     * @param sprites a list
     */
    public SpriteCollection(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    /**
     * class constructor by an unknown number of sprites.
     *
     * @param arr array of sprites
     */
    public SpriteCollection(Sprite... arr) {
        int i = 0;
        this.sprites = new ArrayList<>();
        while (i < arr.length) {
            this.sprites.add(arr[i]);
            i++;
        }
    }

    /**
     * adds a sprite to the collection.
     *
     * @param s Sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Notifies the Sprite that time has passed.
     * @param dt relative to frames time passed.
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> temp = new ArrayList<>(this.sprites);
        for (Sprite sprite : temp) {
            sprite.timePassed(dt);
        }
    }

    /**
     * draws on all of the sprites.
     *
     * @param d DrawSurface
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> temp = new ArrayList<>(this.sprites);
        for (Sprite sprite : temp) {
            sprite.drawOn(d);
        }
    }
}
