package Socket.Messages;

public class NicknameMessage extends Message{
    private String nickname;

    public NicknameMessage(String nickname) {
        super("NICKNAME_CHOICE");
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
