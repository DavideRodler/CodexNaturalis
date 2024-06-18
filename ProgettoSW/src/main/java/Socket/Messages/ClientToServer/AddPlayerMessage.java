package Socket.Messages.ClientToServer;

import Network.Client.RMI.VirtualView;
import Socket.Messages.Message;
import model.enums.TokenEnum;

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
