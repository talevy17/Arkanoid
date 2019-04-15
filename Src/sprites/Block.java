package sprites;

import collision.Line;
import collision.Collidable;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import game.GameLevel;
import biuoop.DrawSurface;

/**
 * this class represents a block in the game.
 *
 * @author Tal Levy
 */
public class Block implements Sprite, Collidable, HitNotifier {
    private Rectangle block;
    private List<HitListener> hitListeners;
    private int hitCount;
    private Map<Integer, java.awt.Color> colors;
    private Map<Integer, Image> images;

    /**
     * class constructor, by Rect and hitCount.
     *
     * @param block    the rect
     * @param hitCount the count
     */
    public Block(Rectangle block, int hitCount) {
        this.block = block;
        this.hitCount = hitCount;
        this.hitListeners = new ArrayList<>();
        this.colors = new TreeMap<>();
        this.images = new TreeMap<>();
        this.colors.put(1, java.awt.Color.gray);
    }

    /**
     * class constructor by a point, width and height.
     *
     * @param p        the point
     * @param width    the width
     * @param height   the height
     * @param hitCount the count
     */
    public Block(Point p, double width, double height, int hitCount) {
        this.block = new Rectangle(p, width, height);
        this.hitCount = hitCount;
        this.hitListeners = new ArrayList<>();
        this.colors = new TreeMap<>();
        this.images = new TreeMap<>();
        this.colors.put(1, java.awt.Color.gray);
    }

    /**
     * class constructor by a point, width, height and the color.
     *
     * @param p        the point
     * @param width    the width
     * @param height   the height
     * @param hitCount the count
     * @param color    java.awt.Color
     * @param image    the image
     */
    public Block(Point p, double width, double height, int hitCount,
                 Map<Integer, java.awt.Color> color, Map<Integer, Image> image) {
        this.block = new Rectangle(p, width, height);
        this.hitCount = hitCount;
        this.hitListeners = new ArrayList<>();
        this.colors = color;
        this.images = image;
    }

    /**
     * class constructor by coordinates.
     *
     * @param x        value
     * @param y        value
     * @param width    w
     * @param height   h
     * @param hitCount the count
     * @param color    the color
     * @param image    the image
     */
    public Block(double x, double y, double width, double height, int hitCount,
                 Map<Integer, java.awt.Color> color, Map<Integer, Image> image) {
        this.block = new Rectangle(new Point(x, y), width, height);
        this.hitCount = hitCount;
        this.hitListeners = new ArrayList<>();
        this.colors = color;
        this.images = image;
    }

    /**
     * constructor only by height and width.
     *
     * @param width  w
     * @param height h
     * @param color  the color
     */
    public Block(double width, double height, java.awt.Color color) {
        this.block = new Rectangle(width, height);
        this.hitCount = 0;
        this.hitListeners = new ArrayList<>();
        this.colors.put(1, color);
    }

    /**
     * returns the collision rect.
     *
     * @return this.block
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * Gets hit count.
     *
     * @return the hit count
     */
    public int getHitCount() {
        return this.hitCount;
    }

    /**
     * dictates what happens upon a hit on the block.
     *
     * @param collisionPoint  Point
     * @param currentVelocity Velocity
     * @param hitter          the hitting ball
     * @return new Velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //the lines of the collision block
        List<Line> lines = this.block.getLines();
        //index of the closest collision
        int closeIndex = -1;
        //reduce the hit count of it's bigger than zero
        if (this.hitCount > 0) {
            this.hitCount -= 1;
        }
        notifyHit(hitter);
        //checks all lines for the collision point
        for (int i = 0; i < 4; i++) {
            if (!(lines.get(i).pointNotThere(collisionPoint.getX(), collisionPoint.getY()))) {
                closeIndex = i;
                break;
            }
        }
        //if the ball hit the top or bottom change the vertical speed
        if (closeIndex == 0 || closeIndex == 1) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        //if the ball hit the left or right change the horizontal speed
        return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
    }

    /**
     * draws the block.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        //draws the stroke if it exists.
        //fills the block
        if (this.colors.containsKey(this.hitCount)) {
            d.setColor(this.colors.get(this.hitCount));
            d.fillRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                    (int) this.block.getWidth(), (int) this.block.getHeight());
            drawStroke(d);
            return;
        }
        if (this.images.containsKey(this.hitCount)) {
            d.drawImage((int) this.block.getUpperLeft().getX(),
                    (int) this.block.getUpperLeft().getY(), this.images.get(this.hitCount));
            drawStroke(d);
            return;
        }
        if (this.images.containsKey(1)) {
            d.drawImage((int) this.block.getUpperLeft().getX(),
                    (int) this.block.getUpperLeft().getY(), this.images.get(1));
            drawStroke(d);
            return;
        }
        d.setColor(this.colors.get(1));
        d.fillRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                (int) this.block.getWidth(), (int) this.block.getHeight());
        drawStroke(d);
    }

    /**
     * Draw stroke.
     *
     * @param d the d
     */
    private void drawStroke(DrawSurface d) {
        if (this.colors.containsKey(-1)) {
            d.setColor(this.colors.get(-1));
            d.drawRectangle((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(),
                    (int) this.block.getWidth(), (int) this.block.getHeight());
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

    /**
     * adds the block to the game as a sprite and a collidable.
     *
     * @param g GameLevel
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove from game.
     *
     * @param g the g
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }


    /**
     * Adds multiple listeners to the same block.
     *
     * @param hitListener the hit listeners
     */
    public void addHitListener(HitListener... hitListener) {
        for (HitListener hl : hitListener) {
            this.hitListeners.add(hl);
        }
    }

    /**
     * adds an hitListener for the block to notify hit events.
     *
     * @param hl the hl
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hl
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notifies all listeners about the block that was hit.
     *
     * @param hitter the hitting ball.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}