package Messages.ClientToServer;

import Messages.Message;

public class AddCardFromCentralCardsToPlayerHandMessage extends Message {
    private final String nickname;
    private final int cardId;

    public AddCardFromCentralCardsToPlayerHandMessage(String nickname, int cardId) {
        super("AddCardFromCentralCardsToPlayerHand");
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
