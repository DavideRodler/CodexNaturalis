package Socket.Messages;

import model.enums.SuitEnum;

public class BackOfFirstCardOfDeckMessage extends Message {
    private boolean isDeckGold;
    private SuitEnum backOfCard;

    public BackOfFirstCardOfDeckMessage(SuitEnum backOfCard, boolean isDeckGold) {
        super("BackOfFirstCardOfDeck");
        this.backOfCard = backOfCard;
        this.isDeckGold = isDeckGold;
    }


    public SuitEnum getBackOfCard() {
        return backOfCard;
    }


    public boolean isDeckGold() {
        return isDeckGold;
    }
}
