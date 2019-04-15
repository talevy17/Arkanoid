/**
 * a class that holds all the details of the collision.
 *
 * @author Tal Levy
 */
package collision;

import geometry.Point;

/**
 * The type Collision info.
 */
public class CollisionInfo {

    // the point of collision
    private Point collisionPoint;
    //the object that was collided with
    private Collidable object;

    /**
     * class constructor.
     *
     * @param p the collision point
     * @param c the collidable
     */
    public CollisionInfo(Point p, Collidable c) {
        this.collisionPoint = p;
        this.object = c;
    }

    /**
     * returns the collision point.
     *
     * @return this.collisionPoint point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * returns the collision object.
     *
     * @return this.object collidable
     */
    public Collidable collisionObject() {
        return this.object;
    }
}
