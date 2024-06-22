package Socket.Messages.Chat;

import Socket.Messages.Message;

public class AddPrivateChatMessage extends Message {

    private String nickname1;
    private String nickname2;

    public AddPrivateChatMessage(String nickname1, String nickname2) {
        super("ADD_PRIVATE_CHAT");
    }

    public String getNickname1() {
        return nickname1;
    }

    public String getNickname2() {
        return nickname2;
    }
}
