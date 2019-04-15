package geometry;
import collision.CollisionInfo;
import java.util.List;
/**
 * represents a two dimensional point.
 * @author Tal Levy
 *
 */
public class Point {
    private double x;
    private double y;

    /**
     * Class constructor.
     *
     * @param x value of the point
     * @param y value of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * calculates the distance between two points.
     *
     * @param other point
     * @return distance between two points.
     */
    public double distance(Point other) {
        return (Math.sqrt(((this.x - other.getX()) * (this.x - other.getX()))
                + ((this.y - other.getY()) * (this.y - other.getY()))));
    }

    /**
     * compares two points.
     *
     * @param other point
     * @return boolean (true or false)
     */
    public boolean equals(Point other) {
        if (null == other) {
            return false;
        }
        if (this.x == other.getX() && this.y == other.getY()) {
            return true;
        }
        return false;
    }

    /**
     * returns the x value of the point.
     *
     * @return this.x
     */
    public double getX() {
        return this.x;
    }

    /**
     * the y value of the point.
     *
     * @return this.y
     */
    public double getY() {
        return this.y;
    }

    /**
     * checks whats the closest collision point to a point.
     * @param colList List<CollissionInfo>
     * @return Point closest
     */
    public CollisionInfo closestCollision(List<CollisionInfo> colList) {
        CollisionInfo closest = colList.get(0);
        int i = 1;
        while (i < colList.size()) {
            if (this.distance(colList.get(i).collisionPoint()) < this.distance(closest.collisionPoint())) {
                closest = colList.get(i);
            }
            i++;
        }
        return closest;
    }
}
