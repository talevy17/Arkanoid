package game;
/**
 * a class of constants that are used in various classes.
 * @author Tal Levy
 *
 */
public class Constants {
    public static final int GUI_WIDTH = 800;
    public static final int GUI_HEIGHT = 600;
    public static final int BALL_RADIUS = 7;
    public static final double BALL_VELOCITY = 480;
    public static final int FRAME_BOUND = 25;
    public static final int HEIGHT_BOUND = GUI_HEIGHT - FRAME_BOUND;
    public static final int WIDTH_BOUND = GUI_WIDTH - FRAME_BOUND;
    public static final int BALL_LOCATION_W = GUI_WIDTH / 2;
    public static final int POINTS_FOR_HIT = 5;
    public static final int POINTS_FOR_REMOVAL = 10;
    public static final int POINTS_FOR_WINNING = 100;
    public static final int LIVES = 5;
    public static final int ANIMATION = 1000;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int PADDLE_HEIGHT = 8;
    public static final int BALL_LOCATION_H = GUI_HEIGHT - PADDLE_HEIGHT - BALL_RADIUS;
    public static final int HIGH_SCORES_SIZE = 5;
    public static final String FILE_PATH = "resources/highscores.ser";
}
