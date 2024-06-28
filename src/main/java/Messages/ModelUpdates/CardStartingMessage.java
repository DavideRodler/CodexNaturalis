package Messages.ModelUpdates;

import Messages.Message;
import model.cards.CardStarting;

public class CardStartingMessage extends Message {
    private CardStarting cardStarting;
    private String nickname;

    public CardStartingMessage(String nickname, CardStarting cardStarting) {
        super("CardStarting");
        this.cardStarting = cardStarting;
        this.nickname = nickname;
    }

    public CardStarting getCardStarting() {
        return cardStarting;
    }

    public String getNickname() {
        return nickname;
    }
}
