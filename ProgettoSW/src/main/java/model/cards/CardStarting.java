package model.cards;

import model.cards.face.Face;
import model.enums.Suit;

public class CardStarting extends CardPlaying {
    private Suit[] symbols;

    public CardStarting(Integer ID, Face front, Face back, Suit[] symbols) {
        super(ID, front, back);
        this.symbols = symbols;
    }

    public Suit[] getSymbols() {
        return symbols;
    }
}
