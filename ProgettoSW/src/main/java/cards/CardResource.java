package cards;

import enums.Suit;

public class CardResource extends Card{
    private Suit symbol;
    private int Points;

    public Suit getSymbol() {
        return symbol;
    }

    public int getPoints() {
        return Points;
    }
}
