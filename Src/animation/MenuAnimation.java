package animation;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

import biuoop.KeyboardSensor;
import sprites.Background;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<MenuSelections<T>> selections;
    private List<MenuSelections<Menu<T>>> subMenus;
    private String menuTitle;
    private T status;
    private Background background;
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private boolean stop;
    private boolean isPressed;

    /**
     * Instantiates a new Menu animation.
     *
     * @param title  the title
     * @param sensor the sensor
     * @param run    Animation Runner.
     */
    public MenuAnimation(String title, KeyboardSensor sensor, AnimationRunner run) {
        this.menuTitle = title;
        this.selections = new ArrayList<>();
        this.subMenus = new ArrayList<>();
        this.status = null;
        this.background = null;
        this.keyboard = sensor;
        this.stop = false;
        this.isPressed = true;
        this.runner = run;
    }

    /**
     * checks conditions for the running of a single frame.
     *
     * @param d  the draw surface
     * @param dt the relative running time passed.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        int i = 0;
        if (this.background != null) {
            this.background.drawOn(d);
        } else {
            d.setColor(java.awt.Color.GRAY);
            d.fillRectangle(0, 0, 800, 600);
        }
        d.setColor(java.awt.Color.WHITE);
        d.drawText(150, 100, this.menuTitle + ":", 70);
        for (MenuSelections<Menu<T>> subMenu : this.subMenus) {
            d.drawText(200, i + 200, subMenu.getKey() + ") " + subMenu.getMessage(), 60);
            i += 80;
        }
        for (MenuSelections<T> selection : this.selections) {
            d.drawText(200, i + 200, selection.getKey() + ") " + selection.getMessage(), 60);
            i += 80;
        }
        for (MenuSelections<Menu<T>> subMenu : this.subMenus) {
            if (this.keyboard.isPressed((subMenu.getKey())) && !this.isPressed) {
                this.stop = true;
                this.isPressed = true;
                this.runner.run(subMenu.getValue());
                this.status = subMenu.getValue().getStatus();
            } else {
                if (!this.keyboard.isPressed(subMenu.getKey())) {
                    this.isPressed = false;
                }
            }
        }
        for (MenuSelections<T> selection : this.selections) {
            if (this.keyboard.isPressed(selection.getKey()) && !this.isPressed) {
                this.status = selection.getValue();
                this.stop = true;
                this.isPressed = true;
                break;
            }
            if (!this.keyboard.isPressed(selection.getKey())) {
                this.isPressed = false;
            }
        }
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Add selection.
     *
     * @param key       the key
     * @param message   the message
     * @param returnVal the return val
     */
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new MenuSelections<>(key, message, returnVal));
    }
    /**
     * Add sub menu.
     *
     * @param key     the key
     * @param message the message
     * @param subMenu the sub menu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(new MenuSelections<>(key, message, subMenu));
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * Sets background.
     *
     * @param back the background
     */
    public void setBackground(Background back) {
        this.background = back;
    }
}
