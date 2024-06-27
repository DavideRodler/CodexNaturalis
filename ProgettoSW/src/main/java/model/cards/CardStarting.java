package model.cards;

import model.cards.face.Face;
import model.enums.SuitEnum;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a starting card.
 * It has a front and a back face.
 * It has a boolean to know if it is played back or not.
 * It has a list of symbols.
 */
public class CardStarting extends CardPlaying implements Serializable{
    private ArrayList<SuitEnum> symbols;


    public CardStarting(Integer ID, Face front, Face back, ArrayList<SuitEnum> symbols) {
        super(ID, front, back);
        this.symbols = symbols;
    }


    public ArrayList<SuitEnum> getSymbols() {
        return symbols;
    }


    /**
     * Count the number of resources of a certain suit on the card
     * @param suit the suit to count
     * @return the number of resources of the suit on the card
     */
    public int countResource(SuitEnum suit){
        int count = 0;
        if (getPlayingBack()){
            if (getBack().getUpRight().getDrawing().equals(suit)) {
                count++;
            }
            if(getBack().getDownRight().getDrawing().equals(suit)){
                count++;
            }
            if(getBack().getUpLeft().getDrawing().equals(suit)){
                count++;
            }
            if(getBack().getDownLeft().getDrawing().equals(suit)){
                count++;
            }
        }
        else {
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
            for(int i=0; i<getSymbols().size(); i++){
                if(getSymbols().get(i).equals(suit)){
                    count++;
                }
            }
        }
        return count;
    }
}
