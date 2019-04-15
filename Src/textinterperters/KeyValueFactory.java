package textinterperters;

import sprites.Velocity;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * The type Key value factory.
 */
public class KeyValueFactory {

    /**
     * Key value blocks map.
     *
     * @param line the line
     * @return the map
     * @throws Exception the exception
     */
    public static Map<BlockIdentifier, String> keyValueBlocks(String line) throws Exception {
        Map<BlockIdentifier, String> keyValue = new TreeMap<>();
        Map<BlockIdentifier, String> tempPattern = new TreeMap<>();
        String patternSymbol = "symbol:\\w";
        tempPattern.put(BlockIdentifier.SYMBOL, patternSymbol);
        String patternHeight = "height:[0-9]+";
        tempPattern.put(BlockIdentifier.HEIGHT, patternHeight);
        String patternWidth = "width:[0-9]+";
        tempPattern.put(BlockIdentifier.WIDTH, patternWidth);
        String patternHit = "[a-z]+_[a-z]+:[0-9]+";
        tempPattern.put(BlockIdentifier.HIT_POINTS, patternHit);
        String patternStroke = "stroke:color\\W[a-zA-Z]+(\\W[0-9]+,[0-9]+,[0-9]+\\W)?\\W";
        tempPattern.put(BlockIdentifier.STROKE, patternStroke);
        for (BlockIdentifier field : tempPattern.keySet()) {
            Pattern pattern = Pattern.compile(tempPattern.get(field));
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String rawKey = line.substring(matcher.start(), matcher.end());
                keyValue.put(field, rawKey.substring(rawKey.indexOf(":") + 1));
            }
        }
        if (keyValue.containsKey(BlockIdentifier.SYMBOL)) {
            if (keyValue.get(BlockIdentifier.SYMBOL).length() != 1) {
                throw new Exception("Block symbol can't exceed one char");
            }
        }
        return keyValue;
    }

    /**
     * Key value spacers map.
     *
     * @param line the line
     * @return the map
     * @throws Exception the exception
     */
    public static Map<String, Integer> keyValueSpacers(String line) throws Exception {
        Map<String, Integer> keyValue = new TreeMap<>();
        if (!line.contains("symbol") || !line.contains("width")) {
            throw new Exception("invalid spacer definition");
        }
        String symPattern = "symbol:\\W";
        Pattern pattern = Pattern.compile(symPattern);
        Matcher matcher = pattern.matcher(line);
        List<String> vals = new ArrayList<>();
        while (matcher.find()) {
            vals.add(line.substring(matcher.end() - 1, matcher.end()));
        }
        symPattern = "width:[0-9]+";
        pattern = Pattern.compile(symPattern);
        matcher = pattern.matcher(line);
        while (matcher.find()) {
            String sub = line.substring(matcher.start(), matcher.end());
            vals.add(sub.substring(sub.indexOf(":") + 1));
        }
        if (vals.size() != 2) {
            throw new Exception("invalid spacer definition");
        }
        int width = Parsing.integerParse(vals.get(1));
        if (width <= 0) {
            throw new Exception("Illegal spacer width");
        }
        keyValue.put(vals.get(0), width);
        return keyValue;
    }

    /**
     * Fill num int.
     *
     * @param s the s
     * @return the int
     */
    private static int fillNum(String s) {
        String pattern = "-[1-9][0-9]*";
        Pattern fillK = Pattern.compile(pattern);
        Matcher matcher = fillK.matcher(s);
        if (matcher.find()) {
            try {
                return Integer.parseInt(s.substring(matcher.start() + 1, s.indexOf(":")));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    /**
     * String int key value int.
     *
     * @param line the line
     * @return the int
     */
    public static int stringIntKeyValue(String line) {
        int value = -1;
        try {
            value = Integer.parseInt(line.substring(line.indexOf(":") + 1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * String string key value string.
     *
     * @param line the line
     * @return the string
     */
    public static String stringStringKeyValue(String line) {
        return line.substring(line.indexOf(":") + 1);
    }

    /**
     * Integer integer key value list.
     *
     * @param line the line
     * @return the list
     * @throws Exception the exception
     */
    public static List<Velocity> integerIntegerKeyValue(String line) throws Exception {
        String intPattern = "(-)?[0-9]+";
        Pattern pattern = Pattern.compile(intPattern);
        Matcher matcher = pattern.matcher(line);
        List<Velocity> velocities = new ArrayList<>();
        while (matcher.find()) {
            int angle = Integer.parseInt(line.substring(matcher.start(), matcher.end()));
            if (matcher.find()) {
                velocities.add(Velocity.fromAngleAndSpeed(angle,
                        Integer.parseInt(line.substring(matcher.start(), matcher.end()))));
            } else {
                throw new Exception("Invalid Velocity arguments");
            }
        }
        if (velocities.isEmpty()) {
            throw new Exception("Insufficient velocities");
        }
        return velocities;
    }

    /**
     * String color key value map.
     *
     * @param line the line
     * @return the map
     */
    public static Map<Integer, String> stringColorKeyValue(String line) {
        Map<Integer, String> colors = new TreeMap<>();
        Map<BlockIdentifier, String> tempPattern = new TreeMap<>();
        String patternFillK = "fill(-[1-9][0-9]*)?:color\\W[a-zA-Z]+(\\W[0-9]+,[0-9]+,[0-9]+\\W)?\\W";
        tempPattern.put(BlockIdentifier.COLOR, patternFillK);
        String patternImage = "fill(-[1-9][0-9]*)?:image\\W(\\w+\\W)?\\w+\\W[a-z]+\\W(\\W)?";
        tempPattern.put(BlockIdentifier.IMAGE, patternImage);
        for (BlockIdentifier field : tempPattern.keySet()) {
            Pattern pattern = Pattern.compile(tempPattern.get(field));
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String rawKey = line.substring(matcher.start(), matcher.end());
                int fillK = fillNum(rawKey);
                colors.put(fillK, rawKey.substring(rawKey.indexOf(":") + 1));
            }
        }
        return colors;
    }
}
