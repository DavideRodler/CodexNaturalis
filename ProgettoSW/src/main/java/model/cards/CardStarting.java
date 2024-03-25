package model.cards;

import model.cards.face.Face;
import model.enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class CardStarting extends CardPlaying {
    private ArrayList<Suit> symbols;

    public CardStarting(Integer ID, Face front, Face back, ArrayList<Suit> symbols) {
        super(ID, front, back);
        this.symbols = symbols;
    }

    public ArrayList<Suit> getSymbols() {
        return symbols;
    }
}
