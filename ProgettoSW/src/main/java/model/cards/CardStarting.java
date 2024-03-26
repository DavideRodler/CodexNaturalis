package model.cards;

import model.cards.face.CornerResource;
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

    public int countResource(Suit suit){
        int count = 0;
        if (!getPlayingBack()){
            if(getFront().getDownLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getDownLeft();
                if (corner.getDrawing().equals(suit)) {
                    count++;
                }
            }
            if(getFront().getDownRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getDownRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(getFront().getUpLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getUpLeft();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(getFront().getUpRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getUpRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
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
