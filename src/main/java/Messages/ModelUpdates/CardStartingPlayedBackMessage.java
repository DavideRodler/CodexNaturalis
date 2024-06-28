package Messages.ModelUpdates;

import Messages.Message;

public class CardStartingPlayedBackMessage extends Message {
    private boolean playedBack;
    private String nickname;

    public CardStartingPlayedBackMessage(String nickname, boolean playedBack) {
        super("CardStartingPlayedBack");
        this.nickname = nickname;
        this.playedBack = playedBack;
    }

    public boolean isPlayedBack() {
        return playedBack;
    }

    public String getNickname() {
        return nickname;
    }
}
