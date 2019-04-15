package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import collision.Line;
import collision.Collidable;
import collision.CollisionInfo;
import game.Constants;
import game.GameEnvironment;
import game.GameLevel;

/**
 * allows creating and using an element "ball".
 *
 * @author Tal Levy
 */
public class Ball implements Sprite {
    // center of the ball
    private Point center;
    // radius of the ball
    private int r;
    // color of the ball
    private java.awt.Color color;
    // the change of the circle's center
    private Velocity v;
    // the game environment consisting all collidables
    private GameEnvironment game;
    // a flag that notifies if the ball was removed.
    private boolean wasRemoved;

    /**
     * class constructor.
     *
     * @param center point
     * @param r      the radius
     * @param color  ball's color
     * @param limit  the frame
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment limit) {
        this.center = center;
        this.r = r;
        this.color = color;
        setVelocity(0, 0);
        this.game = limit;
        this.wasRemoved = false;
    }

    /**
     * class constructor.
     *
     * @param x     the x value
     * @param y     the y value
     * @param r     the radius
     * @param color the ball's color
     * @param limit the frame
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment limit) {
        this.center = new Point((double) x, (double) y);
        this.r = r;
        this.color = color;
        setVelocity(0, 0);
        this.game = limit;
        this.wasRemoved = false;
    }

    /**
     * Was removed boolean.
     *
     * @return the boolean
     */
    public boolean wasRemoved() {
        return this.wasRemoved;
    }

    /**
     * returns the x value of the center of the ball.
     *
     * @return this.center.getX() x
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * returns the y value of the center of the ball.
     *
     * @return this.center.getY() y
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * returns the radius of the ball.
     *
     * @return this.r size
     */
    public int getSize() {
        return this.r;
    }

    /**
     * returns the color of the ball.
     *
     * @return this.color color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * sets a new velocity to the ball.
     *
     * @param speed the new velocity
     */
    public void setVelocity(Velocity speed) {
        this.v = speed;
    }

    /**
     * sets a new velocity to the ball.
     *
     * @param dx the change on the x
     * @param dy the change on the y
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * returns the velocity of the ball.
     *
     * @return this.v velocity
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * moves the ball according the it's given velocity, if it generates out of
     * bounds, corrects it. then, changes the horizontal\vertical velocity to remain
     * in bounds.
     *
     * @param dt the relative time passed.
     */
    public void moveOneStep(double dt) {
        Point p = this.getVelocity().applyToPoint(this.center, dt);
        Line traj = getTrajectory(dt);
        CollisionInfo col = game.getClosestCollision(traj);
        //if there is a collision
        if (col != null) {
            //if the distance between the next point to the col point is smaller than the radius
            if (col.collisionPoint().distance(p) <= (this.r)
                    || col.collisionObject().getCollisionRectangle().pointInRect(p)) {
                //set the velocity to the RV of the hit of the Collidable
                setVelocity(col.collisionObject().hit(this, col.collisionPoint(), this.v));
            }
        }
        //check if the next movement with the new velocity isn't directed inside other collidables.
        p = this.getVelocity().applyToPoint(this.center, dt);
        Collidable block = game.inBlocks(p);
        if (block != null) {
            setVelocity(-this.getVelocity().getDx(), -this.getVelocity().getDy());
        }
        this.center = this.getVelocity().applyToPoint(this.center, dt);
    }

    /**
     * draws the ball on the given surface.
     *
     * @param surface the draw surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.getX(), (int) this.getY(), this.r);
        surface.setColor(java.awt.Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.r);
    }

    /**
     * returns the trajectory of the ball.
     *
     * @param dt the relative time passed
     * @return a new Line
     */
    public Line getTrajectory(double dt) {
        Point p = this.center;
        while (true) {
            if (p.getX() + this.r >= Constants.GUI_WIDTH) {
                return new Line(this.center, Constants.GUI_WIDTH, p.getY());
            }
            if (p.getX() - this.r <= 0) {
                return new Line(this.center, 0, p.getY());
            }
            if (p.getY() + this.r >= Constants.GUI_HEIGHT) {
                return new Line(this.center, p.getX(), Constants.GUI_HEIGHT);
            }
            if (p.getY() - this.r <= 0) {
                return new Line(this.center, p.getX(), 0);
            }
            p = this.getVelocity().applyToPoint(p, dt);
        }
    }

    /**
     * Notifies the Sprite that time has passed.
     *
     * @param dt relative to frames time passed.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * adds a ball the the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove from game.
     *
     * @param g the g
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        this.wasRemoved = true;
    }
}