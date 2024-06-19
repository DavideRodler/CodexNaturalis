package Socket.Messages.ClientToServer;

import Socket.Messages.Message;

public class AddCardToStationMessage extends Message {
    private final String nickname;
    private final int cardId;
    private final boolean playedBack;
    private final int x;
    private final int y;


    public AddCardToStationMessage( String nickname, int cardId, boolean playedBack, int x, int y) {
        super("AddCardToStation");
        this.nickname = nickname;
        this.cardId = cardId;
        this.playedBack = playedBack;
        this.x = x;
        this.y = y;
    }

    public int getCardId() {
        return cardId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getX() {
        return x;
    }

    public boolean isPlayedBack() {
        return playedBack;
    }

    public int getY() {
        return y;
    }
}
