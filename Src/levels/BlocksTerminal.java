package levels;

import sprites.Block;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Blocks terminal.
 */
public class BlocksTerminal {
    private int xpos;
    private int ypos;
    private BlocksFromSymbolsFactory blocks;
    private int rowHeight;

    /**
     * Instantiates a new Blocks terminal.
     */
    public BlocksTerminal() {
        this.xpos = -1;
        this.ypos = -1;
        this.blocks = null;
        this.rowHeight = -1;
    }

    /**
     * Sets xpos.
     *
     * @param xPosition the xpos
     */
    public void setXpos(int xPosition) {
        this.xpos = xPosition;
    }

    /**
     * Sets ypos.
     *
     * @param yPosition the ypos
     */
    public void setYpos(int yPosition) {
        this.ypos = yPosition;
    }

    /**
     * Sets blocks.
     *
     * @param fileName the file name
     */
    public void setBlocks(String fileName) {
        java.io.Reader reader = null;
        reader = new java.io.InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName));
        try {
            this.blocks = BlocksDefinitionReader.fromReader(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets blocks.
     *
     * @param block the blocks
     */
    public void setBlocks(BlocksFromSymbolsFactory block) {
        this.blocks = block;
    }

    /**
     * Sets row height.
     *
     * @param height the row height
     */
    public void setRowHeight(int height) {
        this.rowHeight = height;
    }

    /**
     * Creatable boolean.
     *
     * @return the boolean
     */
    public boolean creatable() {
        if (this.xpos < 0 || this.ypos < 0 || this.blocks == null || this.rowHeight <= 0) {
            return false;
        }
        return true;
    }

    /**
     * Gets row blocks.
     *
     * @param line the line
     * @return the row blocks
     */
    public List<Block> getRowBlocks(String line) {
        int currentXpos = this.xpos;
        int currentYpos = this.ypos;
        boolean flag = false;
        List<Block> rowBlocks = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            String s = line.substring(i, i + 1);
            if (this.blocks.isBlockSymbol(s)) {
                Block block = this.blocks.getBlock(s, currentXpos, currentYpos);
                rowBlocks.add(block);
                currentXpos += (int) block.getCollisionRectangle().getWidth();
                flag = true;
            }
            if (this.blocks.isSpaceSymbol(s)) {
                currentXpos += this.blocks.getSpaceWidth(s);
                flag = true;
            }
        }
        if (flag) {
            this.ypos += this.rowHeight;
        }
        return rowBlocks;
    }
}
