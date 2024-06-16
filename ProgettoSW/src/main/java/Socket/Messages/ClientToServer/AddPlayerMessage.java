package Socket.Messages.ClientToServer;

import Network.Client.RMI.VirtualView;
import Socket.Messages.Message;
import model.enums.TokenEnum;

public class AddPlayerMessage extends Message {
    private String playerName;
    private TokenEnum token;

    public AddPlayerMessage(String playerName, TokenEnum token) {
        super("AddPlayer");
        this.playerName = playerName;
        this.token = token;
    }

    public String getNickname() {
        return playerName;
    }
    public TokenEnum getToken() {
        return token;
    }
}
