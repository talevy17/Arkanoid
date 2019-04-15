package sprites;

import geometry.Point;

/**
 * this class portrays the "velocity" (the rate of the movement of a ball).
 *
 * @author Tal Levy
 */
public class Velocity {
    // the change on the x value of the point
    private double dx;
    // the change on the y value of the point
    private double dy;

    /**
     * a class constructor.
     *
     * @param dx change on x
     * @param dy change on y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * allows setting a velocity providing angle and speed.
     *
     * @param angle of the movement
     * @param speed of the movement
     * @return new Velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double newDx = Math.round((Math.cos(Math.toRadians(angle) - Math.PI / 2) * speed));
        double newDy = Math.round((Math.sin(Math.toRadians(angle) - Math.PI / 2) * speed));
        return new Velocity(newDx, newDy);
    }

    /**
     * returns the change on the x.
     *
     * @return this.dx dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * returns the change on the y.
     *
     * @return this.dy dy
     */
    public double getDy() {
        return this.dy;
    }


    /**
     * Apply to point point.
     *
     * @param p  the p
     * @param dt the dt
     * @return the point
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + (dt * this.dx), p.getY() + (dt * this.dy));
    }
}