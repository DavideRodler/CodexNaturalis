package Socket.Messages;

import model.enums.TokenEnum;

public class TokenOfPlayerMessage extends Message{
    private String nickname;
    private TokenEnum token;

    public TokenOfPlayerMessage(String nickname, TokenEnum token) {
        super("TokenOfPlayer");
        this.nickname = nickname;
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public TokenEnum getToken() {
        return token;
    }
}
