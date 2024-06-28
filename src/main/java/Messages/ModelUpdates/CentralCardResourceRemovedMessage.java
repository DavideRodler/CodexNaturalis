package Messages.ModelUpdates;

import Messages.Message;

public class CentralCardResourceRemovedMessage extends Message {
    private int cardID;

    public CentralCardResourceRemovedMessage(int cardID){
        super("CentralCardResourceRemoved");
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }
}
