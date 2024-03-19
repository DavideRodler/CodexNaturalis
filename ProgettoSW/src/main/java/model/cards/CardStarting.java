package model.cards;

import model.enums.Suit;

public class CardStarting extends CardPlaying {
    private Suit[] symbols;

    public CardStarting(Suit[] symbols) {
        this.symbols = symbols;
    }

    public Suit[] getSymbols() {
        return symbols;
    }
}
