package cards;

import cards.face.Face;
import enums.Suit;

public class CardResource extends CardPlaying{
    private Suit symbol;
    private Integer points;

    public CardResource(int ID, Face front, Face back, Suit symbol, Integer points) {
        super(ID, front, back);
        this.symbol = symbol;
        this.points = points;
    }

    public Suit getSymbol() {
        return symbol;
    }

    public int getPoints() {
        return points;
    }
}
