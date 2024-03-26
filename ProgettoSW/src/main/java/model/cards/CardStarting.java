package model.cards;

import model.cards.face.Face;
import model.enums.Suit;

import java.util.ArrayList;

public class CardStarting extends CardPlaying {
    private ArrayList<Suit> symbols;

    public CardStarting(Integer ID, Face front, Face back, ArrayList<Suit> symbols) {
        super(ID, front, back);
        this.symbols = symbols;
    }

    public ArrayList<Suit> getSymbols() {
        return symbols;
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
            for(int i=0; i<getSymbols().size(); i++){
                if(getSymbols().get(i).equals(suit)){
                    count++;
                }
            }
        }
        return count;
    }
}
