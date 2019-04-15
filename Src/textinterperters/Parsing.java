package textinterperters;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

/**
 * The type Parsing.
 */
public class Parsing {
    /**
     * Integer parse int.
     *
     * @param s the s
     * @return the int
     */
    public static int integerParse(String s) {
        try {
            int num = Integer.parseInt(s);
            return num;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Color parse java . awt . color.
     *
     * @param s the s
     * @return the java . awt . color
     * @throws Exception the exception
     */
    public static java.awt.Color colorParse(String s) throws Exception {
        if (s.contains("RGB")) {
            List<Integer> rgb = new ArrayList<>();
            String colorRGBPattern = "[0-9]+";
            Pattern pattern = Pattern.compile(colorRGBPattern);
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                rgb.add(integerParse(s.substring(matcher.start(), matcher.end())));
            }
            if (rgb.size() == 3) {
                return new java.awt.Color(rgb.get(0), rgb.get(1), rgb.get(2));
            } else {
                throw new Exception("Invalid color");
            }
        }
        String colorName = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
        java.awt.Color color = colorCheck(colorName);
        if (color == null) {
            throw new Exception("Invalid color name");
        }
        return color;
    }

    /**
     * Color check java . awt . color.
     *
     * @param name the name
     * @return the java . awt . color
     */
    private static java.awt.Color colorCheck(String name) {
        if (name.equals("black")) {
            return java.awt.Color.black;
        }
        if (name.equals("blue")) {
            return java.awt.Color.blue;
        }
        if (name.equals("cyan")) {
            return java.awt.Color.cyan;
        }
        if (name.equals("gray")) {
            return java.awt.Color.gray;
        }
        if (name.equals("lightGray")) {
            return java.awt.Color.lightGray;
        }
        if (name.equals("green")) {
            return java.awt.Color.green;
        }
        if (name.equals("orange")) {
            return java.awt.Color.orange;
        }
        if (name.equals("pink")) {
            return java.awt.Color.pink;
        }
        if (name.equals("red")) {
            return java.awt.Color.red;
        }
        if (name.equals("white")) {
            return java.awt.Color.white;
        }
        if (name.equals("yellow")) {
            return java.awt.Color.yellow;
        }
        return null;
    }

    /**
     * Image parse image.
     *
     * @param file the file
     * @return the image
     * @throws Exception the exception
     */
    public static Image imageParse(String file) throws Exception {
        // load the image data into an java.awt.Image object
        Image img = null;
        if (!file.contains("image(")) {
            throw new Exception("Wrong argument for image Parse");
        }
        String fileName = file.substring(file.indexOf("(") + 1, file.indexOf(")"));
        try {
            img = ImageIO.read(ClassLoader.getSystemResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
