package nemo.exception;

/**
 * Exception for handling incorrect from and to datetime in Event tasks
 */
public class IncorrectDateTimeException extends Exception {
    public IncorrectDateTimeException(String message) {
        super(message);
    }
}
