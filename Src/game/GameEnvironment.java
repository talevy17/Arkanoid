package game;

import collision.Line;
import collision.Collidable;
import collision.CollisionInfo;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * the game environment class holds all of the game's collidables.
 *
 * @author Tal Levy
 */
public class GameEnvironment {
    //a list of collidables
    private List<Collidable> collidables;

    /**
     * class constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * class constructor given an existing list of collidables.
     *
     * @param collidables List<Collidable>
     */
    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    /**
     * adds a collidable to the game environment.
     *
     * @param c Collidable
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * checks which block intersects with the trajectory of the ball, returns the collision info.
     *
     * @param trajectory Line
     * @return new collisionInfo
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> colList = new ArrayList<>();
        int i = 0;
        Point colPoint;
        List<Collidable> temp = new ArrayList<>(this.collidables);
        while (i < temp.size()) {
            colPoint = trajectory.closestIntersectionToStartOfLine(temp.get(i).getCollisionRectangle());
            if (colPoint != null) {
                colList.add(new CollisionInfo(colPoint, temp.get(i)));
            }
            i++;
        }
        if (colList.isEmpty()) {
            return null;
        }
        return trajectory.start().closestCollision(colList);
    }

    /**
     * checks if a point is inside a collidable, returns the collidable if so.
     *
     * @param p       Point
     * @return Collidable collidable
     */
    public Collidable inBlocks(Point p) {
        int i = 0;
        List<Collidable> temp = new ArrayList<>(this.collidables);
        while (i < temp.size()) {
            if (temp.get(i).getCollisionRectangle().pointInRect(p)) {
                return temp.get(i);
            }
            i++;
        }
        return null;
    }
}
