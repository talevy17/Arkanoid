/**
 * the Collidable interface to create objects which can be collided with.
 *
 * @author Tal Levy
 */
package collision;

import geometry.Point;
import geometry.Rectangle;
import sprites.Velocity;
import sprites.Ball;

/**
 * The interface Collidable.
 */
public interface Collidable {
    /**
     * returns the rectangle of collision.
     *
     * @return Rectangle rect
     */
    Rectangle getCollisionRectangle();

    /**
     * notifies the object that it has been collided with.
     * returns the new velocity according to the limitiations.
     *
     * @param hitter          the hitting ball
     * @param collisionPoint  Point
     * @param currentVelocity Velocity
     * @return new Velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
