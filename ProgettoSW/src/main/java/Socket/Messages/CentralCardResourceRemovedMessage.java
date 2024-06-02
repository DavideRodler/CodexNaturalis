package Socket.Messages;

public class CentralCardResourceRemovedMessage extends Message{
    private int cardID;

    public CentralCardResourceRemovedMessage(int cardID){
        super("CentralCardResourceRemoved");
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }
}
