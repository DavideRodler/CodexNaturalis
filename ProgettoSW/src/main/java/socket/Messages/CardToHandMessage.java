package socket.Messages;

import model.cards.CardResource;

public class CardToHandMessage extends Message{
    private CardResource card;

    public CardToHandMessage(CardResource card){
        super("CardToHand");
        this.card = card;
    }
}
