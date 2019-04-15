package animation;

/**
 * The type Menu selections.
 *
 * @param <T> the type parameter
 */
public class MenuSelections<T> {
    private String key;
    private String message;
    private T value;

    /**
     * Instantiates a new Menu selections.
     *
     * @param key       the key
     * @param msg       the msg
     * @param returnVal the return val
     */
    public MenuSelections(String key, String msg, T returnVal) {
        this.key = key;
        this.message = msg;
        this.value = returnVal;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public T getValue() {
        return this.value;
    }
}
