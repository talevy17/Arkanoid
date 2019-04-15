package levels;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import textinterperters.BlockIdentifier;
import textinterperters.KeyValueFactory;
import textinterperters.Parsing;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {

    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     * @throws Exception the exception
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws Exception {
        List<String> defaults = new ArrayList<>();
        List<String> bdefs = new ArrayList<>();
        List<String> sdefs = new ArrayList<>();
        BlocksFromSymbolsFactory blocks = new BlocksFromSymbolsFactory();
        BufferedReader buff = new BufferedReader(reader);
        String line;
        try {
            while ((line = buff.readLine()) != null) {
                if (line.contains("default")) {
                    if (!line.contains("#")) {
                        defaults.add(line);
                    }
                }
                if (line.contains("bdef")) {
                    bdefs.add(line);
                }
                if (line.contains("sdef")) {
                    sdefs.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BlockGenerator defBlock = new BlockGenerator();
        if (defaults.size() != 0) {
            try {
                defBlock = blockAttributes(KeyValueFactory.keyValueBlocks(defaults.get(0)),
                        KeyValueFactory.stringColorKeyValue(defaults.get(0)), defBlock);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String bdef : bdefs) {
            try {
                Map<BlockIdentifier, String> temp = KeyValueFactory.keyValueBlocks(bdef);
                Map<Integer, String> colors = KeyValueFactory.stringColorKeyValue(bdef);
                BlockGenerator bg = new BlockGenerator();
                bg.setWidth(defBlock.getWidth());
                bg.setHitPoints(defBlock.getHitPoints());
                bg.setHeight(defBlock.getHeight());
                bg.addImages(defBlock.getImages());
                bg.addColors(defBlock.getColors());
                bg = blockAttributes(temp, colors, bg);
                if (bg.creatable()) {
                    if (temp.containsKey(BlockIdentifier.SYMBOL)) {
                        blocks.addBlock(temp.get(BlockIdentifier.SYMBOL), bg);
                    } else {
                        throw new Exception("Insufficient block attributes");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String sdef : sdefs) {
            try {
                Map<String, Integer> spacer = KeyValueFactory.keyValueSpacers(sdef);
                blocks.addSpacer(spacer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return blocks;
    }

    /**
     * Block attributes block generator.
     *
     * @param blocks the blocks
     * @param colors the colors
     * @param bg     the bg
     * @return the block generator
     * @throws Exception the exception
     */
    private static BlockGenerator blockAttributes(Map<BlockIdentifier,
            String> blocks, Map<Integer, String> colors, BlockGenerator bg) throws Exception {
        for (BlockIdentifier block : blocks.keySet()) {
            switch (block) {
                case HEIGHT:
                    bg.setHeight(Parsing.integerParse(blocks.get(block)));
                    break;
                case WIDTH:
                    bg.setWidth(Parsing.integerParse(blocks.get(block)));
                    break;
                case HIT_POINTS:
                    bg.setHitPoints(Parsing.integerParse(blocks.get(block)));
                    break;
                case STROKE:
                    bg.addColor(-1, Parsing.colorParse(blocks.get(block)));
                    break;
                default:
                    break;
            }
        }
        for (int fill : colors.keySet()) {
            if (colors.get(fill).contains("color")) {
                try {
                    bg.addColor(fill, Parsing.colorParse(colors.get(fill)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    bg.addImage(fill, Parsing.imageParse(colors.get(fill)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bg;
    }
}
