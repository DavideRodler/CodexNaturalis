package Socket.Messages;

public class PrivateChatNicknameMessage extends Message{
    private String nickname;

    public PrivateChatNicknameMessage(String nickname) {
        super("PRIVATE_CHAT_NICKNAME_CHOICE");
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
