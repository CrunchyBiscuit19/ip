package nemo.exception;

/**
 * Exception for handling non-commands entered as input
 */
public class NotCommandException extends Exception {
    public NotCommandException(String message) {
        super(message);
    }
}
