package levels;

import sprites.Background;
import sprites.Block;
import textinterperters.KeyValueFactory;
import textinterperters.Parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levels = new ArrayList<>();
        BufferedReader buff = new BufferedReader(reader);
        String line;
        try {
            while ((line = buff.readLine()) != null) {
                if (line.contains("START_LEVEL")) {
                    try {
                        levels.add(readLevel(buff));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levels;
    }

    /**
     * Read level level.
     *
     * @param buff the buff
     * @return the level
     * @throws Exception the exception
     */
    public Level readLevel(java.io.BufferedReader buff) throws Exception {
        String line;
        Level level = new Level();
        BlocksTerminal bt = new BlocksTerminal();
        List<Block> blocks = new ArrayList<>();
        try {
            line = buff.readLine();
            while (!(line.contains("START_BLOCKS"))) {
                if (line.contains("background")) {
                    String val = KeyValueFactory.stringStringKeyValue(line);
                    if (val.contains("color")) {
                        level.setBackground(new Background(Parsing.colorParse(val)));
                    } else {
                        level.setBackground(new Background(Parsing.imageParse(val)));
                    }
                }
                if (line.contains("level_name")) {
                    level.setName(KeyValueFactory.stringStringKeyValue(line));
                }
                if (line.contains("paddle_speed")) {
                    level.setPaddleSpeed(KeyValueFactory.stringIntKeyValue(line));
                }
                if (line.contains("paddle_width")) {
                    level.setPaddleWidth(KeyValueFactory.stringIntKeyValue(line));
                }
                if (line.contains("block_definitions")) {
                    bt.setBlocks(KeyValueFactory.stringStringKeyValue(line));
                }
                if (line.contains("blocks_start_x")) {
                    bt.setXpos(KeyValueFactory.stringIntKeyValue(line));
                }
                if (line.contains("blocks_start_y")) {
                    bt.setYpos(KeyValueFactory.stringIntKeyValue(line));
                }
                if (line.contains("row_height")) {
                    bt.setRowHeight(KeyValueFactory.stringIntKeyValue(line));
                }
                if (line.contains("num_blocks:")) {
                    level.setNumOfBlocks(KeyValueFactory.stringIntKeyValue(line));
                }
                if (line.contains("ball_velocities")) {
                    level.setVelocities(KeyValueFactory.integerIntegerKeyValue(line));
                }
                line = buff.readLine();
            }
            if (bt.creatable()) {
                line = buff.readLine();
                while (!(line.contains("END_BLOCKS"))) {
                    blocks.addAll(bt.getRowBlocks(line));
                    line = buff.readLine();
                }
                level.setBlocks(blocks);
            } else {
                throw new Exception("Couldn't create blocks");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!level.creatable()) {
            throw new Exception("Invalid or Insufficient arguments for a level");
        }
        return level;
    }
}
