package model.cards;

import model.cards.face.Face;
import model.enums.GoldSuit;
import model.enums.Suit;

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


    public int countResource(Suit suit){
        int count = 0;
        if (!getPlayingBack()){
                if (getFront().getUpRight().getDrawing().equals(suit)) {
                    count++;
                }
                if(getFront().getDownRight().getDrawing().equals(suit)){
                    count++;
                }
                if(getFront().getUpLeft().getDrawing().equals(suit)){
                    count++;
                }
                if(getFront().getDownLeft().getDrawing().equals(suit)){
                    count++;
                }
        }
        else {
            if(getSymbol().equals(suit)){ //in back there is only one symbol in the middle
                count++;
            }
        }
        return count;
    }

}
