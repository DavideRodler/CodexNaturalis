package Socket.Messages;

public class DrawCardMessage extends Message{
    private int cardID;

    public DrawCardMessage(int cardID) {
        super("DRAW_CARD_CHOICE");
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }
}
