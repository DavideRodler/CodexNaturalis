package Socket.Messages;

import socket.Messages.Message;
import model.cards.CardResource;

public class CardToHandMessage extends Message{
    private CardResource card;

    public CardToHandMessage(CardResource card){
        super("CardToHand");
        this.card = card;
    }
}
