package Socket.Messages.ClientToServer;

import Socket.Messages.Message;

public class AddCardFromDeckToPlayerHandMessage extends Message {
    private final String nickname;
    private final int cardId;

    public AddCardFromDeckToPlayerHandMessage( String nickname, int cardId) {
        super("AddCardFromDeckToPlayerHand");
        this.nickname = nickname;
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    public String getNickname() {
        return nickname;
    }
}
