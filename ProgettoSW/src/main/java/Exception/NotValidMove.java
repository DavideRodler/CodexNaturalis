package Exception;

public class NotValidMove extends Exception {
    public  NotValidMove(){
    }
    public NotValidMove(String message) {
        super(message);
    }
}
