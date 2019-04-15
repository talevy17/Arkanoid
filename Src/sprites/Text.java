package sprites;

import biuoop.DrawSurface;
import geometry.Point;

/**
 * The type Text.
 */
public class Text implements Sprite {
    private String text;
    private Point start;
    private int size;
    private java.awt.Color color;

    /**
     * Instantiates a new Text.
     *
     * @param t   the text
     * @param x   the x coordinate
     * @param y   the y coordinate
     * @param num the size
     * @param c the color
     */
    public Text(String t, int x, int y, int num, java.awt.Color c) {
        this.text = t;
        this.start = new Point(x, y);
        this.size = num;
        this.color = c;
    }

    /**
     * draws the Sprite.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawText((int) this.start.getX(), (int) this.start.getY(), this.text, this.size);
    }

    /**
     * Notifies the Sprite that time has passed.
     * @param dt relative to frames time passed.
     */
    public void timePassed(double dt) {
        return;
    }
}
