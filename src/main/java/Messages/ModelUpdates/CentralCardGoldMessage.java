package Messages.ModelUpdates;

import Messages.Message;
import model.cards.CardGold;

public class CentralCardGoldMessage extends Message {
    private CardGold cardGold;

    public CentralCardGoldMessage(CardGold cardGold) {
        super("CentralCardGold");
        this.cardGold = cardGold;
    }

    public CardGold getCardGold() {
        return cardGold;
    }
}
