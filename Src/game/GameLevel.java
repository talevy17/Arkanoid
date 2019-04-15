package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import sprites.SpriteCollection;
import sprites.Paddle;
import sprites.Sprite;
import sprites.Block;
import sprites.Text;
import sprites.Velocity;
import sprites.Ball;
import levels.LevelInformation;
import collision.Collidable;
import geometry.Point;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

import java.util.List;

/**
 * this class is the game module.
 *
 * @author Tal Levy
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation level;
    private Counter blockCount;
    private Counter ballCount;
    private Counter score;
    private Counter lives;

    /**
     * class constructor, allocates memory for a new game.
     *
     * @param lev    the Level's information
     * @param ks     the ks
     * @param ar     the ar
     * @param points the points
     * @param tries  the tries
     */
    public GameLevel(LevelInformation lev, KeyboardSensor ks, AnimationRunner ar, Counter points, Counter tries) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.runner = ar;
        this.keyboard = ks;
        this.blockCount = new Counter(lev.numberOfBlocksToRemove());
        this.ballCount = new Counter(0);
        this.score = points;
        this.lives = tries;
        this.level = lev;
    }

    /**
     * adds a collidable to the game.
     *
     * @param c the collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * adds a sprite to the game.
     *
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initiates a new game, adds blocks, balls and a paddle.
     */
    public void initialize() {
        this.sprites.addSprite(this.level.getBackground());
        ScoreIndicator scoreKeeper = new ScoreIndicator(this.score);
        this.sprites.addSprite(scoreKeeper);
        LivesIndicator lifeLine = new LivesIndicator(this.lives);
        this.sprites.addSprite(lifeLine);
        String levelName = "Level Name: " + this.level.levelName();
        this.sprites.addSprite(new Text(levelName, (600 - (levelName.length() * 4)),
                Constants.FRAME_BOUND - 5, 20, java.awt.Color.BLACK));
        frameBuilder();
        BlockRemover blockRemover = new BlockRemover(this, this.blockCount);
        ScoreTrackingListener scoreTrack = new ScoreTrackingListener(this.score);
        List<Block> blocks = this.level.blocks();
        for (Block block : blocks) {
            block.addToGame(this);
            block.addHitListener(blockRemover, scoreTrack);
        }
    }

    /**
     * runs the game, start an animation loop.
     */
    public void playOneTurn() {
        Paddle paddle = new Paddle(this.keyboard, this.level.paddleWidth(), this.level.paddleSpeed());
        paddle.addToGame(this);
        BallRemover ballRemover = new BallRemover(this, this.ballCount);
        ballsGenerator(ballRemover);
        this.running = true;
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.runner.run(this);
        paddle.removeFromGame(this);
        if (this.ballCount.getValue() == 0) {
            this.lives.decrease();
        }
    }

    /**
     * Gets block count.
     *
     * @return the block count
     */
    public Counter getBlockCount() {
        return this.blockCount;
    }


    /**
     * Frame builder.
     */
    public void frameBuilder() {
        Block left = new Block(new Point(0, Constants.FRAME_BOUND),
                Constants.FRAME_BOUND, Constants.HEIGHT_BOUND, 0);
        Block right = new Block(new Point(Constants.WIDTH_BOUND,
                Constants.FRAME_BOUND), Constants.FRAME_BOUND, Constants.HEIGHT_BOUND, 0);
        Block top = new Block(new Point(Constants.FRAME_BOUND, Constants.FRAME_BOUND),
                Constants.WIDTH_BOUND - Constants.FRAME_BOUND, Constants.FRAME_BOUND, 0);
        left.addToGame(this);
        right.addToGame(this);
        top.addToGame(this);
    }


    /**
     * Balls generator.
     *
     * @param remover the remover
     */
    public void ballsGenerator(BallRemover remover) {
        Block bottom = new Block(new Point(Constants.FRAME_BOUND,
                Constants.GUI_HEIGHT), Constants.WIDTH_BOUND - Constants.FRAME_BOUND,
                Constants.FRAME_BOUND, 0);
        bottom.addToGame(this);
        List<Velocity> velocityList = this.level.initialBallVelocities();
        for (int i = 0; i < this.level.numberOfBalls(); i++) {
            Ball ball = new Ball(Constants.BALL_LOCATION_W, Constants.BALL_LOCATION_H,
                    Constants.BALL_RADIUS, java.awt.Color.WHITE, this.environment);
            ball.setVelocity(velocityList.get(i));
            ball.addToGame(this);
            bottom.addHitListener(remover);
            this.ballCount.increase();
        }
    }

    /**
     * listing the conditions for the continuation of the animation.
     *
     * @param d  draw Surface.
     * @param dt the relative time passed.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        if (this.blockCount.getValue() == 0) {
            this.score.increase(Constants.POINTS_FOR_WINNING);
            this.running = false;
        }
        if (this.ballCount.getValue() == 0) {
            this.running = false;
        }
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, this.keyboard.SPACE_KEY, new PauseScreen()));
        }
        this.sprites.notifyAllTimePassed(dt);
    }

    /**
     * notifies that the animation should stop running.
     *
     * @return !this.running
     */
    public boolean shouldStop() {
        return !this.running;
    }
}
