package geometry;
import java.util.ArrayList;
import java.util.List;
import collision.Line;
/**
 * this class is the abstract shape of the blocks and paddle.
 * @author Tal Levy
 *
 */
public class Rectangle {

    private Point upperLeft;
    private Point bottomRight;
    private double width;
    private double height;

    /**
     * Class constructor from a point, width and height.
     * @param upperLeft Point
     * @param width Double
     * @param height Double
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.bottomRight = new Point((upperLeft.getX() + width), (upperLeft.getY() + height));
        this.width = width;
        this.height = height;
    }


    /**
     * class constructor, given only height and width.
     * @param width Double
     * @param height Double
     */
    public Rectangle(double width, double height) {
        this.upperLeft = new Point(0, 0);
        this.bottomRight = new Point(width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     * @param line trajectory
     * @return new List
     */

    public List<Point> intersectionPoints(Line line) {
        List<Point> intPoints = new ArrayList<>();
        Point intersection = line.intersectionWith(this.getTop());
        if (intersection != null) {
            intPoints.add(intersection);
        }
        intersection = line.intersectionWith(this.getBottom());
        if (intersection != null) {
            intPoints.add(intersection);
        }
        intersection = line.intersectionWith(this.getLeft());
        if (intersection != null) {
            intPoints.add(intersection);
        }
        intersection = line.intersectionWith(this.getRight());
        if (intersection != null) {
            intPoints.add(intersection);
        }
        return intPoints;
    }

    /**
     * returns the rect's width.
     * @return this.width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * returns the height of the rect.
     * @return this.height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * returns the upper left point.
     * @return this.upperLeft
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * returns the bottom right point.
     * @return this.bottomRight
     */
    public Point getBottomRight() {
        return this.bottomRight;
    }


    /**
     * returns the top line of the rect.
     * @return Line top
     */
    public Line getTop() {
        double x1 = this.upperLeft.getX();
        double y1 = this.upperLeft.getY();
        double x2 = this.bottomRight.getX();
        Line top = new Line(x1, y1, x2, y1);
        return top;
    }

    /**
     * returns the bottom line of the rect.
     * @return Line bottom
     */
    public Line getBottom() {
        double x1 = this.upperLeft.getX();
        double x2 = this.bottomRight.getX();
        double y2 = this.bottomRight.getY();
        Line bottom = new Line(x1, y2, x2, y2);
        return bottom;
    }

    /**
     * returns the left line of the rect.
     * @return Line left
     */
    public Line getLeft() {
        double x1 = this.upperLeft.getX();
        double y1 = this.upperLeft.getY();
        double y2 = this.bottomRight.getY();
        Line left = new Line(x1, y1, x1, y2);
        return left;
    }

    /**
     * returns the right line of the rect.
     * @return Line right
     */
    public Line getRight() {
        double y1 = this.upperLeft.getY();
        double x2 = this.bottomRight.getX();
        double y2 = this.bottomRight.getY();
        Line right = new Line(x2, y1, x2, y2);
        return right;
    }

    /**
     * returns all lines of the rect.
     * @return List<Line> lines
     */
    public List<Line> getLines() {
        List<Line> lines = new ArrayList<>();
        lines.add(this.getTop());
        lines.add(this.getBottom());
        lines.add(this.getLeft());
        lines.add(this.getRight());
        return lines;
    }

    /**
     * returns the minimum width of the frame.
     *
     * @return minWidth
     */
    public int getMinWidth() {
        return (int) this.upperLeft.getX();
    }

    /**
     * checks if a point is inside a rectangle.
     * @param point Point
     * @return true or false
     */
    public boolean pointInRect(Point point) {
        if (point.getX() >= this.upperLeft.getX() && point.getX() <= this.bottomRight.getX()) {
            if (point.getY() <= this.bottomRight.getY() && point.getY() >= this.upperLeft.getY()) {
                return true;
            }
        }
        return false;
    }
}
