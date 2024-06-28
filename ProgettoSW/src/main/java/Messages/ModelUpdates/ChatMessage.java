package Messages.ModelUpdates;

import Messages.Message;

public class ChatMessage extends Message {
    private String message;
    private String nickname;

    public ChatMessage(String type,String message, String nickname) {
        super(type);
        this.message = message;
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public String getNickname() {
        return nickname;
    }
}
