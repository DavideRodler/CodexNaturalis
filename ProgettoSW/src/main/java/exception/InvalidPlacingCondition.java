package exception;

public class InvalidPlacingCondition extends Exception {
    public InvalidPlacingCondition(String message) {
        super(message);
    }

    public InvalidPlacingCondition(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPlacingCondition(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidPlacingCondition() {
    }

    public InvalidPlacingCondition(Throwable cause) {
        super(cause);
    }
}
