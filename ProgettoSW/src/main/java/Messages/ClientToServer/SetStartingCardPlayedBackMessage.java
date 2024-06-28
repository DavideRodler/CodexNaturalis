package Messages.ClientToServer;

import Messages.Message;

public class SetStartingCardPlayedBackMessage extends Message {
    private boolean playedBack;
    private String nickname;
    private int CardID;

    public SetStartingCardPlayedBackMessage(boolean playedBack, String nickname, int CardID) {
        super("SetStartingCardPlayedBack");
        this.playedBack = playedBack;
        this.nickname = nickname;
        this.CardID = CardID;
    }

    public boolean isPlayedBack() {
        return playedBack;
    }
    public String getNickname() {
        return nickname;
    }
    public int getId() {
        return CardID;
    }
}
