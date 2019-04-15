package sprites;

import collision.Line;
import collision.Collidable;
import geometry.Rectangle;
import geometry.Point;
import game.GameLevel;
import game.Constants;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * the paddle class creates and defines the paddle in the game.
 *
 * @author Tal Levy
 */
public class Paddle implements Sprite, Collidable {
    // class members.
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddle;
    private int moveBy;
    private java.awt.Color color;

    /**
     * Instantiates a new Paddle.
     *
     * @param k     the k
     * @param width the width
     * @param move  the move
     */
    public Paddle(KeyboardSensor k, int width, int move) {
        this.keyboard = k;
        this.moveBy = move;
        Point upperLeft = new Point(Constants.GUI_WIDTH / 2 - (width / 2),
                Constants.GUI_HEIGHT - Constants.PADDLE_HEIGHT);
        this.paddle = new Rectangle(upperLeft, width, Constants.PADDLE_HEIGHT);
        this.color = java.awt.Color.RED;
    }


    /**
     * defines the movement of the paddle to the left.
     *
     * @param dt the dt
     */
    public void moveLeft(double dt) {
        Point upperLeft = new Point((this.paddle.getMinWidth() - (dt * this.moveBy)),
                Constants.GUI_HEIGHT - Constants.PADDLE_HEIGHT);
        if ((int) upperLeft.getX() >= Constants.FRAME_BOUND) {
            this.paddle = new Rectangle(upperLeft, this.paddle.getWidth(), this.paddle.getHeight());
        } else {
            upperLeft = new Point(Constants.FRAME_BOUND, Constants.GUI_HEIGHT - Constants.PADDLE_HEIGHT);
            this.paddle = new Rectangle(upperLeft, this.paddle.getWidth(), this.paddle.getHeight());
        }
    }


    /**
     * defines the movement of the paddle to the right.
     *
     * @param dt the dt
     */
    public void moveRight(double dt) {
        Point upperLeft = new Point((this.paddle.getMinWidth() + (dt * this.moveBy)),
                Constants.GUI_HEIGHT - Constants.PADDLE_HEIGHT);
        if (upperLeft.getX() + this.paddle.getWidth() <= Constants.GUI_WIDTH - Constants.FRAME_BOUND) {
            this.paddle = new Rectangle(upperLeft, this.paddle.getWidth(), this.paddle.getHeight());
        } else {
            upperLeft = new Point(Constants.GUI_WIDTH - Constants.FRAME_BOUND - this.paddle.getWidth(),
                    Constants.GUI_HEIGHT - Constants.PADDLE_HEIGHT);
            this.paddle = new Rectangle(upperLeft, this.paddle.getWidth(), this.paddle.getHeight());
        }
    }

    /**
     * Notifies the Sprite that time has passed.
     *
     * @param dt relative to frames time passed.
     */
    public void timePassed(double dt) {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
    }

    /**
     * Sprite interface, draw the sprite on the drawsurface.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
    }

    /**
     * Collidable interface, return the collision rect (the paddle).
     *
     * @return this.paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * changes the velocity of the ball upon hitting the paddle (the angle varies by the collision point.
     *
     * @param hitter          the hitting ball
     * @param collisionPoint  Point
     * @param currentVelocity Velocity
     * @return new Velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line top = this.paddle.getTop();
        if (!top.pointNotThere(collisionPoint.getX(), collisionPoint.getY())) {
            switch (top.partOfLine(collisionPoint)) {
                case 1:
                    return Velocity.fromAngleAndSpeed(300, Constants.BALL_VELOCITY);
                case 2:
                    return Velocity.fromAngleAndSpeed(330, Constants.BALL_VELOCITY);
                case 4:
                    return Velocity.fromAngleAndSpeed(60, Constants.BALL_VELOCITY);
                case 5:
                    return Velocity.fromAngleAndSpeed(30, Constants.BALL_VELOCITY);
                default:
                    return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            }
        }
        return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
    }

    /**
     * adds the paddle to the game as a collidable and a sprite.
     *
     * @param g GameLevel
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
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
}
