package levels;

import sprites.Block;

import java.awt.Image;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Block generator.
 */
public class BlockGenerator implements BlockCreator {
    private int height;
    private int width;
    private int hitPoints;
    private Map<Integer, java.awt.Color> colors;
    private Map<Integer, Image> images;

    /**
     * Instantiates a new Block generator.
     */
    public BlockGenerator() {
        this.height = -1;
        this.width = -1;
        this.hitPoints = -1;
        this.colors = new TreeMap<>();
        this.images = new TreeMap<>();
    }


    /**
     * Sets height.
     *
     * @param h the h
     */
    public void setHeight(int h) {
        this.height = h;
    }

    /**
     * Sets width.
     *
     * @param w the w
     */
    public void setWidth(int w) {
        this.width = w;
    }

    /**
     * Sets hit points.
     *
     * @param hits the hits
     */
    public void setHitPoints(int hits) {
        this.hitPoints = hits;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Gets colors.
     *
     * @return the colors
     */
    public Map<Integer, java.awt.Color> getColors() {
        return this.colors;
    }

    /**
     * Gets images.
     *
     * @return the images
     */
    public Map<Integer, Image> getImages() {
        return this.images;
    }

    /**
     * Add color.
     *
     * @param fill  the fill
     * @param color the color
     */
    public void addColor(int fill, java.awt.Color color) {
        this.colors.put(fill, color);
    }

    /**
     * Add image.
     *
     * @param fill the fill
     * @param file the file
     */
    public void addImage(int fill, Image file) {
        this.images.put(fill, file);
    }

    /**
     * Add colors.
     *
     * @param color the colors
     */
    public void addColors(Map<Integer, java.awt.Color> color) {
        this.colors.putAll(color);
    }

    /**
     * Add images.
     *
     * @param image the images
     */
    public void addImages(Map<Integer, Image> image) {
        this.images.putAll(image);
    }

    /**
     * Creatable boolean.
     *
     * @return the boolean
     */
    public boolean creatable() {
        if (this.height == -1 || this.width == -1 || this.colors == null || this.hitPoints == -1) {
            return false;
        }
        return true;
    }
    /**
     * Create block.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    public Block create(int xpos, int ypos) {
        return new Block(xpos, ypos, this.width, this.height, this.hitPoints, this.colors, this.images);
    }
}
