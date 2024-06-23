package Socket.Messages;

public class CurrentPlayerInfoMessage extends Message{
    private String currentPlayerName;

    public CurrentPlayerInfoMessage(String currentPlayerName) {
        super("CurrentPlayerInfo");
        this.currentPlayerName = currentPlayerName;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
}
