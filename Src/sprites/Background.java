package sprites;

import biuoop.DrawSurface;
import java.awt.Image;
import game.Constants;

/**
 * The type Background.
 */
public class Background implements Sprite {
    private java.awt.Color color;
    private Image image;


    /**
     * Instantiates a new Background.
     *
     * @param color the color
     */
    public Background(java.awt.Color color) {
        this.color = color;
        this.image = null;
    }

    /**
     * Instantiates a new Background.
     *
     * @param image the image
     */
    public Background(Image image) {
        this.image = image;
        this.color = null;
    }

    /**
     * draws the background on the drawsurface.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        if (this.color != null) {
            d.setColor(this.color);
            d.fillRectangle(0, 0, Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        } else {
            d.drawImage(0, 0, this.image);
        }
    }

    /**
     * Notifies the Sprite that time has passed.
     *
     * @param dt relative to frames time passed.
     */
    public void timePassed(double dt) {
        return;
    }
}
