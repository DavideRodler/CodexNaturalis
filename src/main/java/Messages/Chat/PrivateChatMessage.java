package Messages.Chat;

import Messages.Message;

public class PrivateChatMessage extends Message {
    private String message;
    private String nicknameSender;
    private String nicknameReceiver;

    public PrivateChatMessage(String message, String nicknameSender, String nicknameReceiver) {
        super("PRIVATE");
        this.message = message;
        this.nicknameSender = nicknameSender;
        this.nicknameReceiver = nicknameReceiver;
    }

    public String getMessage() {
        return message;
    }

    public String getNicknameSender() {
        return nicknameSender;
    }

    public String getNicknameReceiver() {
        return nicknameReceiver;
    }
}

