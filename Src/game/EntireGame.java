package game;

import animation.AnimationRunner;
import animation.Menu;
import animation.HighScoresAnimation;
import animation.MenuAnimation;
import biuoop.KeyboardSensor;
import levels.LevelSpecificationReader;
import tables.HighScoresTable;
import tasks.QuitTask;
import tasks.GameTask;
import tasks.ShowHiScoresTask;
import tasks.Task;

import java.io.Reader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;


/**
 * runs the entire game, from the level choices to the final animation and gui close.
 */
public class EntireGame {
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private HighScoresTable highScores;
    private String filePath;

    /**
     * Instantiates a new Entire game.
     *
     * @param levelsRequested the user's choices of levels.
     */
    public EntireGame(String[] levelsRequested) {
        this.runner = new AnimationRunner();
        this.keyboard = this.runner.getKeyboardSensor();
        File file = new File(Constants.FILE_PATH);
        if (file.exists()) {
            this.highScores = HighScoresTable.loadFromFile(file);
        } else {
            this.highScores = new HighScoresTable(Constants.HIGH_SCORES_SIZE);
            try {
                this.highScores.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (levelsRequested.length == 1) {
            this.filePath = levelsRequested[0];
        } else {
            this.filePath = "level_sets.txt";
        }
    }

    /**
     * Run game.
     */
    public void runGame() {
        while (true) {
            Menu<Task<Void>> menu = getMenu();
            menu.addSelection("h", "High Scores",
                    new ShowHiScoresTask(this.runner, new HighScoresAnimation(this.highScores), this.keyboard));
            menu.addSelection("q", "Quit Game", new QuitTask(this.runner));
            this.runner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            try {
                this.highScores.save(new File(Constants.FILE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets sub menu.
     *
     * @return the sub menu
     */
    private Menu<Task<Void>> getSubMenu() {
        Menu<Task<Void>> menu = new MenuAnimation<>("Level-set Menu", this.keyboard, this.runner);
        File file = new File(this.filePath);
        Reader read = null;
        try {
            if (file.exists()) {
                read = new FileReader(file);
            } else {
                read = new InputStreamReader(ClassLoader.getSystemResourceAsStream(this.filePath));
            }
            LineNumberReader reader = new LineNumberReader(
                    new BufferedReader(read));
            String line;
            String key = "";
            String name = "";
            while ((line = reader.readLine()) != null) {
                if (reader.getLineNumber() % 2 == 1) {
                    key = line.substring(0, line.indexOf(":"));
                    name = line.substring(line.indexOf(":") + 1);
                } else {
                    java.io.Reader levelReader = null;
                    try {
                        levelReader = new InputStreamReader(
                                ClassLoader.getSystemResourceAsStream(line));
                        LevelSpecificationReader readFile = new LevelSpecificationReader();
                        menu.addSelection(key, name, new GameTask(this.runner,
                                this.keyboard, readFile.fromReader(levelReader), this.highScores));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (levelReader != null) {
                            try {
                                levelReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return menu;
    }

    /**
     * Gets menu.
     *
     * @return the menu
     */
    private MenuAnimation<Task<Void>> getMenu() {
        MenuAnimation<Task<Void>> menu = new MenuAnimation<>("Arkanoid", this.keyboard, this.runner);
        Menu<Task<Void>> subMenu = getSubMenu();
        menu.addSubMenu("s", "Start Game", subMenu);
        return menu;
    }
}
