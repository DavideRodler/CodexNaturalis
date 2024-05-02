package exception;

public class DeckEmptyException extends Exception {
    public DeckEmptyException(String message) {
        super(message);
    }

    public DeckEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeckEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DeckEmptyException() {
    }

    public DeckEmptyException(Throwable cause) {
        super(cause);
    }
}
