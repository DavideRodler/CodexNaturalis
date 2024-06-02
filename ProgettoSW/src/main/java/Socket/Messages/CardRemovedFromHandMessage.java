package Socket.Messages;

public class CardRemovedFromHandMessage extends Message{
    private int cardID;

    public CardRemovedFromHandMessage(int cardID) {
        super("CardRemovedFromHand");
        this.cardID = cardID;
    }

    public int getID() {
        return cardID;
    }

}

