package levels;

import sprites.Block;

import java.util.Map;
import java.util.TreeMap;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Instantiates a new Blocks from symbols factory.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<>();
        this.blockCreators = new TreeMap<>();
    }

    /**
     * Add spacer.
     *
     * @param s     the s
     * @param width the width
     */
    public void addSpacer(String s, int width) {
        this.spacerWidths.put(s, width);
    }

    /**
     * Add spacer.
     *
     * @param spacers the spacers
     */
    public void addSpacer(Map<String, Integer> spacers) {
        for (String key : spacers.keySet()) {
            addSpacer(key, spacers.get(key));
        }
    }

    /**
     * Add block.
     *
     * @param s     the s
     * @param block the block
     */
    public void addBlock(String s, BlockCreator block) {
        this.blockCreators.put(s, block);
    }

    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        if (s.length() > 1) {
            return false;
        }
        for (String spacer : this.spacerWidths.keySet()) {
            if (spacer.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        if (s.length() > 1) {
            return false;
        }
        for (String block : this.blockCreators.keySet()) {
            if (block.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets space width.
     *
     * @param s the s
     * @return the space width
     */
// Return a block according to the definitions associated
    // with symbol s. The block will be located at position (xpos, ypos).
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Gets block.
     *
     * @param s the s
     * @param x the x
     * @param y the y
     * @return the block
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }
}
