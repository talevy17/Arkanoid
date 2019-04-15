package game;

/**
 * The type Counter.
 */
public class Counter {
    private int count;

    /**
     * Instantiates a new Counter.
     *
     * @param num the num
     */
    public Counter(int num) {
        this.count = num;
    }

    /**
     * Increase.
     *
     * @param number the number
     */
// add number to current count.
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decrease.
     *
     * @param number the number
     */
// subtract number from current count.
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Increase.
     */
    public void increase() {
        this.count += 1;
    }

    /**
     * Decrease.
     */
// subtract number from current count.
    public void decrease() {
        this.count -= 1;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
// get current count.
    public int getValue() {
        return this.count;
    }
}
