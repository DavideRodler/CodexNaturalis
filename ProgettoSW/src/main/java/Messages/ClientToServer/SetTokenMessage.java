package Messages.ClientToServer;

import Messages.Message;
import model.enums.TokenEnum;

public class SetTokenMessage extends Message {
    private final TokenEnum token;
    private final String nickname;
    public SetTokenMessage(String nickname, TokenEnum token) {
        super("SetToken");
        this.token = token;
        this.nickname = nickname;
    }

    public TokenEnum getToken() {
        return token;
    }

    public String getNickname() {
        return nickname;
    }
}
