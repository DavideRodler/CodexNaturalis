package exception;

public class NotMyTurnException extends Exception {
    public NotMyTurnException() {
    }

    public NotMyTurnException(Throwable cause) {
        super(cause);
    }

    public NotMyTurnException(String message) {
        super(message);
    }

    public NotMyTurnException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMyTurnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
