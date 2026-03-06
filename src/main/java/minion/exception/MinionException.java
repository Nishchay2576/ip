package minion.exception;

/**
 * Custom exception class for chatbot-specific errors.
 */
public class MinionException extends Exception {

    /**
     * Initializes a MinionException with the specified error message.
     *
     * @param message The detailed error message to be displayed.
     */
    public MinionException(String message) {
        super(message);
    }
}