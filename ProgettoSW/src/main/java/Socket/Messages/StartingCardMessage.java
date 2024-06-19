package Socket.Messages;

public class StartingCardMessage extends Message{
    private String cardName;

    public StartingCardMessage(String cardName) {
        super("STARTING_CARD_CHOICE");
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }
}
