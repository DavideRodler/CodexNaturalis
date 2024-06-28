package Messages.ModelUpdates;

import Messages.Message;

public class CurrentPlayerMessage extends Message {
    private String currentPlayer;

    public CurrentPlayerMessage(String currentPlayer) {
        super("CurrentPlayer");
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
