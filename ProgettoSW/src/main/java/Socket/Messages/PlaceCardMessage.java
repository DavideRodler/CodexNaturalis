package Socket.Messages;

public class PlaceCardMessage extends Message{
    private int cardID;
    private boolean playedBack;
    private int Xcoordinate;
    private int Ycoordinate;


    public PlaceCardMessage(int cardID, boolean playedBack, int Xcoordinate, int Ycoordinate) {
        super("PLACE_CARD_CHOICE");
        this.cardID = cardID;
        this.playedBack = playedBack;
        this.Xcoordinate = Xcoordinate;
        this.Ycoordinate = Ycoordinate;
    }

    public int getCardID() {
        return cardID;
    }

    public boolean getPlayedBack() {
        return playedBack;
    }

    public int getXcoordinate() {
        return Xcoordinate;
    }

    public int getYcoordinate() {
        return Ycoordinate;
    }
}
