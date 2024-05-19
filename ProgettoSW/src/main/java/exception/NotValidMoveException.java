package exception;

public class NotValidMoveException extends Exception {
    public NotValidMoveException(){
    }

    public NotValidMoveException(Throwable cause) {
        super(cause);
    }

    public NotValidMoveException(String message) {
        super(message);
    }

    public NotValidMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidMoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
