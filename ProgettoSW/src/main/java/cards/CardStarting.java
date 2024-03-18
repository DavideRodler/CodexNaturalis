package cards;

import enums.Suit;

public class CardStarting {
    private Suit[] symbols;

    public CardStarting(Suit[] symbols) {
        this.symbols = symbols;
    }

    public Suit[] getSymbols() {
        return symbols;
    }
}
