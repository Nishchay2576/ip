package minion.exception;

/**
 * Custom exception class for chatbot-specific errors.
 */
public class MinionException extends Exception {
    public MinionException(String message) {
        super(message);
    }
}