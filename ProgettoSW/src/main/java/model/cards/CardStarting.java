package model.cards;

import model.cards.face.Face;
import model.enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class CardStarting extends CardPlaying {
    private List<Suit> symbols;

    public CardStarting(Integer ID, Face front, Face back, List<Suit> symbols) {
        super(ID, front, back);
        this.symbols = symbols;
    }

    public Suit[] getSymbols() {
        return symbols;
    }
}
