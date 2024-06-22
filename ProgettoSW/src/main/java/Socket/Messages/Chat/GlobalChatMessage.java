package Socket.Messages.Chat;


import Socket.Messages.Message;

public class GlobalChatMessage extends Message {
    private String message;
    private String nickname;

    public GlobalChatMessage(String type,String message, String nickname) {
        super("GLOBAL");
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
