
package collision;

import geometry.Point;
import geometry.Rectangle;

import java.util.List;

/**
 * A line connects two points, has a slope and a line equation constant can.
 * return it's values, calcualate it's middle point and detect intersections.
 * with other lines.
 *
 * @author Tal Levy
 */
public class Line {
    // the starting point of the line
    private Point start;
    // the ending point of the line
    private Point end;
    // the slope of the line
    private double slope;
    // the free constant of the line equation (e.c y1-mx1)
    private double constant;

    /**
     * class constructor, when two points are given.
     *
     * @param start point
     * @param end   point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.slope = findSlope();
        this.constant = findConstant();
    }

    /**
     * class constructor, when only coordinates are given.
     *
     * @param x1 start point x
     * @param y1 start point y
     * @param x2 end point x
     * @param y2 end point y
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        this.slope = findSlope();
        this.constant = findConstant();
    }

    /**
     * class constructor, getting a start point and two coordinates.
     *
     * @param start Point
     * @param x     coordinate
     * @param y     coordinate
     */
    public Line(Point start, double x, double y) {
        this.start = start;
        this.end = new Point(x, y);
        this.slope = findSlope();
        this.constant = findConstant();
    }

    /**
     * class constructor, getting two coordinates and an end point.
     *
     * @param x   coordinate
     * @param y   coordinate
     * @param end Point
     */
    public Line(double x, double y, Point end) {
        this.start = new Point(x, y);
        this.end = end;
        this.slope = findSlope();
        this.constant = findConstant();
    }

    /**
     * calculates the slope of a line segment.
     *
     * @return slope
     */
    private double findSlope() {
        // the slope equation m = (y2-y1)/(x2-x1)
        if (this.start.getX() == this.end.getX()) {
            return 0;
        }
        return ((this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX()));
    }

    /**
     * calculates the constant (-slope*x1 + y1) of a linear equation.
     *
     * @return constant
     */
    private double findConstant() {
        // the constant equation: c = (y1-m1*x1)
        return (this.start.getY() - (this.slope * this.start.getX()));
    }

    /**
     * calculates the length of the line.
     *
     * @return the distance between the points
     */
    public double length() {
        // the distance between two points is the length of the line
        return this.start.distance(this.end);
    }

    /**
     * calculates and returns the middle point of a line.
     *
     * @return middle point
     */
    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2;
        double y = (this.start.getY() + this.end.getY()) / 2;
        return new Point(x, y);
    }

    /**
     * returns the start point of the line.
     *
     * @return this.start point
     */
    public Point start() {
        return this.start;
    }

    /**
     * returns the end point of the line.
     *
     * @return this.end point
     */
    public Point end() {
        return this.end;
    }

    /**
     * returns the slope of the line.
     *
     * @return this.slope double
     */
    public double slope() {
        return this.slope;
    }

    /**
     * returns the constant of the line.
     *
     * @return this.constant double
     */
    public double constant() {
        return this.constant;
    }

    /**
     * compares two lines.
     *
     * @param other line
     * @return boolean (true\false)
     */
    public boolean equals(Line other) {
        // if the start point and end point of two lines are the same
        if ((this.start.equals(other.start()) && this.end.equals(other.end()))
                || (this.end.equals(other.start()) && this.start.equals(other.end()))) {
            return true;
        }
        return false;
    }

    /**
     * checks if two line segments are intersecting.
     *
     * @param other line
     * @return boolean value
     */
    public boolean isIntersecting(Line other) {
        // if an intersection point is returned there is an intersection
        Point intersection = this.intersectionWith(other);
        if (intersection == null) {
            return false;
        }
        return true;
    }

    /**
     * finds an intersection point between two line segments.
     *
     * @param other line
     * @return intersection point or null if there is no intersection
     */
    public Point intersectionWith(Line other) {
        double x;
        double y;
        // if the slopes are the same, check if the lines are parallel
        if (this.slope == other.slope()) {
            return this.sameLinearEq(other);
        }
        // if one of the lines is vertical, the x value is not affected by the slope
        if (this.start.getX() == this.end.getX()) {
            x = this.start.getX();
            y = (other.slope() * x + other.constant());
        } else {
            if (other.start().getX() == other.end().getX()) {
                x = other.start().getX();
            } else {
                // if neither line is vertical.
                x = (other.constant() - this.constant) / (this.slope - other.slope());
            }
            y = (this.slope * x + this.constant);
        }
        // checks if the intersection point found is on both lines
        return this.intermediateValueTheorem(other, x, y);
    }

    /**
     * returns the closest intersection point of a line with a rectangle.
     *
     * @param rect the rectangle
     * @return the closest point or null (if there are no intersections)
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intPoints = rect.intersectionPoints(this);
        if (intPoints.isEmpty()) {
            return null;
        }
        Point closest = intPoints.get(0);
        int i = 1;
        while (i < intPoints.size()) {
            if (this.start.distance(intPoints.get(i)) < this.start.distance(closest)) {
                closest = intPoints.get(i);
            }
            i++;
        }
        return closest;
    }

    /**
     * checks if two lines with the same slope are parallel, intersecting or.
     * presenting the same line.
     *
     * @param other line
     * @return intersection point or null
     */
    private Point sameLinearEq(Line other) {
        // if the lines are not the same line
        if (!this.equals(other)) {
            // if the start or end point of the line is equal to the end point
            if (this.start.equals(other.end()) || this.end.equals(other.end())) {
                // checks if the start point of the other line is not found on the line
                if (this.pointNotThere(other.start().getX(), other.start().getY())) {
                    return other.end();
                }
                // if the end or start of the line is equal to the start of the other line
            } else {
                if (this.end.equals(other.start()) || this.start.equals(other.start())) {
                    // checks if the end point of other line is not on the line
                    if (this.pointNotThere(other.end().getX(), other.end().getY())) {
                        return other.start();
                    }
                }
            }
            if ((this.start.getX() == this.end.getX() && other.start().getY() == other.end().getY())) {
                return this.intermediateValueTheorem(other, this.start.getX(), other.start.getY());
            } else {
                if (this.start.getY() == this.end.getY() && other.start().getX() == other.end().getX()) {
                    return this.intermediateValueTheorem(other, this.start.getY(), other.start.getX());
                }
            }
        }
        return null;
    }

    /**
     * checks if a point exists on two different lines.
     *
     * @param other line
     * @param x     value of a point
     * @param y     value of a point
     * @return the point or null
     */
    private Point intermediateValueTheorem(Line other, double x, double y) {
        //checks if the point is on two different lines.
        if (!(this.pointNotThere(x, y)) && !(other.pointNotThere(x, y))) {
            return new Point(x, y);
        }
        return null;
    }

    /**
     * checks if a point is not present on a line.
     *
     * @param x value of a point
     * @param y value of a point
     * @return true or false
     */
    public boolean pointNotThere(double x, double y) {
        //gets x and y coordinates, checks if the point given is not on the line.
        if (x <= Math.max(this.start.getX(), this.end.getX()) && x >= Math.min(this.start.getX(), this.end.getX())
                && y <= Math.max(this.start.getY(), this.end.getY())
                && y >= Math.min(this.start.getY(), this.end.getY())) {
            return false;
        }
        return true;
    }

    /**
     * returns the part of the line on which the point resides.
     *
     * @param p Point
     * @return the part of the line
     */
    public int partOfLine(Point p) {
        for (int i = 1; i < 5; i++) {
            //each iteration checks if the x value of the point is on a fifth of the line.
            if (p.getX() <= (0.2 * i) * length() + this.start.getX()) {
                return i;
            }
        }
        return 5;
    }
}