package Socket.Messages.ClientToServer;

import Socket.Messages.Message;

public class ReconnectMessage extends Message {
    private String nickname;

    public ReconnectMessage(String nickname) {
        super("ReconnectMessage");
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
