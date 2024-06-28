package Messages.ClientToServer;

import Messages.Message;

public class AddPlayerMessage extends Message {
    private String playerName;

    public AddPlayerMessage(String playerName) {
        super("AddPlayer");
        this.playerName = playerName;
    }

    public String getNickname() {
        return playerName;
    }
}
