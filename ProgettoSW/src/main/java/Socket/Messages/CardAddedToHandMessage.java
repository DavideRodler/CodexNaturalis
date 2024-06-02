package Socket.Messages;

import model.cards.CardResource;

public class CardAddedToHandMessage extends Message{
    private CardResource cardAdded;

    public CardAddedToHandMessage(CardResource cardResource) {
        super("CardAddedToHand");
        this.cardAdded = cardResource;
    }

    public CardResource getCardAdded() {
        return cardAdded;
    }
}
