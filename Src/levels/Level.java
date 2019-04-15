package levels;

import sprites.Background;
import sprites.Block;
import sprites.Sprite;
import sprites.Velocity;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Level.
 */
public class Level implements LevelInformation {
    private int balls;
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String name;
    private Background background;
    private List<Block> blocks;
    private int numOfBlocks;

    /**
     * Instantiates a new Level.
     */
    public Level() {
        this.balls = 0;
        this.velocities = new ArrayList<>();
        this.paddleWidth = -1;
        this.paddleSpeed = -1;
        this.name = "";
        this.background = null;
        this.blocks = new ArrayList<>();
        this.numOfBlocks = -1;
    }

    /**
     * Number of balls int.
     *
     * @return the int
     */
    public int numberOfBalls() {
        return this.balls;
    }

    /**
     * Initial ball velocities list.
     *
     * @return the list
     */
// The initial velocity of each ball
    // Note that initialBallVelocities().size() == numberOfBalls()
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Level name string.
     *
     * @return the string
     */
// the level name will be displayed at the top of the screen.
    public String levelName() {
        return this.name;
    }

    /**
     * Gets background.
     *
     * @return the background
     */
// Returns a sprite with the background of the level
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Blocks list.
     *
     * @return the list
     */
// The Blocks that make up this level, each block contains
    // its size, color and location.
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Number of blocks to remove int.
     *
     * @return the int
     */
// Number of levels that should be removed
    // before the level is considered to be "cleared".
    // This number should be <= blocks.size();
    public int numberOfBlocksToRemove() {
        return this.numOfBlocks;
    }


    /**
     * Sets velocities.
     *
     * @param velocityList the velocity list
     */
    public void setVelocities(List<Velocity> velocityList) {
        this.velocities = velocityList;
        this.balls = velocityList.size();
    }

    /**
     * Sets paddle speed.
     *
     * @param speed the speed
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * Sets paddle width.
     *
     * @param width the width
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }

    /**
     * Sets background.
     *
     * @param back the background
     */
    public void setBackground(Background back) {
        this.background = back;
    }


    /**
     * Sets blocks.
     *
     * @param block the blocks
     */
    public void setBlocks(List<Block> block) {
        this.blocks.addAll(block);
    }

    /**
     * Add block.
     *
     * @param block the block
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    /**
     * Sets name.
     *
     * @param levelName the level name
     */
    public void setName(String levelName) {
        this.name = levelName;
    }

    /**
     * Sets num of blocks.
     *
     * @param num the num
     */
    public void setNumOfBlocks(int num) {
        this.numOfBlocks = num;
    }

    /**
     * Creatable boolean.
     *
     * @return the boolean
     */
    public boolean creatable() {
        if (this.balls <= 0 || this.paddleWidth <= 0 || this.paddleSpeed < 0 || this.numOfBlocks < 0
                || this.name == "" || this.velocities.isEmpty() || this.blocks.isEmpty()) {
            return false;
        }
        return true;
    }
}
