package Socket.Messages;

public class TokenMessage extends Message{
    private String token;

    public TokenMessage(String token) {
        super("TOKEN_CHOICE");
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
