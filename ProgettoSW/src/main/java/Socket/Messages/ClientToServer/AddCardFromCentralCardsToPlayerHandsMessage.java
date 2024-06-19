package Socket.Messages.ClientToServer;

import Socket.Messages.Message;

public class AddCardFromCentralCardsToPlayerHandsMessage extends Message {
    private final String nickname;
    private final int cardId;

    public AddCardFromCentralCardsToPlayerHandsMessage(String nickname, int cardId) {
        super("AddCardFromCentralCardsToPlayerHands");
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
