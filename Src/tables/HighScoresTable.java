package tables;

import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import game.Constants;


/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> scores;
    private int maxSize;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
// Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.scores = new ArrayList<>();
        this.maxSize = size;
    }

    /**
     * Add.
     *
     * @param score the score
     */
// Add a high-score.
    public void add(ScoreInfo score) {
        int i = 0;
        int j = this.scores.size() - 1;
        List<ScoreInfo> temp = this.scores;
        if (temp.isEmpty()) {
            this.scores.add(i, score);
            return;
        }
        while (i < this.scores.size()) {
            if (temp.get(i).getScore() < score.getScore()) {
                break;
            }
            i++;
        }
        if (i < this.maxSize) {
            temp.add(i, score);
            if (temp.size() > this.maxSize) {
                temp.remove(this.maxSize);
            }
            this.scores = temp;
        }
    }

    /**
     * Size int.
     *
     * @return the int
     */
// Return table size.
    public int size() {
        return this.maxSize;
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */
// Return the current high scores.
// The list is sorted such that the highest
// scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Actual size int.
     *
     * @return the int
     */
    public int actualSize() {
        return this.scores.size();
    }

    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
// return the rank of the current score: where will it
// be on the list if added?
// Rank 1 means the score will be highest on the list.
// Rank `size` means the score will be lowest.
// Rank > `size` means the score is too low and will not
//      be added to the list.
    public int getRank(int score) {
        int i;
        for (i = 0; i < this.scores.size(); i++) {
            if (scores.get(i).getScore() < score) {
                return (i + 1);
            }
        }
        return (i + 1);
    }

    /**
     * Clear.
     */
// Clears the table
    public void clear() {
        for (int i = this.scores.size() - 1; i >= 0; i--) {
            this.scores.remove(i);
        }
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Load table data from file.
// Current table data is cleared.
    public void load(File filename) throws IOException {
        this.clear();
        ObjectInputStream objectInputStream = null;
        HighScoresTable highScoresTable;
        try {
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(filename));
            highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.scores = highScoresTable.getHighScores();
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        HighScoresTable highScoresTable = this;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(filename));

            objectOutputStream.writeObject(highScoresTable);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
// Read a table from file and return it.
// If the file does not exist, or there is a problem with
// reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoreTable = new HighScoresTable(Constants.HIGH_SCORES_SIZE);
        try {
            highScoreTable.load(filename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return highScoreTable;
        }
        return highScoreTable;
    }
}
