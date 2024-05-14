package socket.Messages;

import model.enums.TokenEnum;

public class AddedPlayerMessage extends Message {

private String nickname;
    private TokenEnum token;

    public AddedPlayerMessage(String nickname, TokenEnum token){
        super("AddedPlayer");
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
