package model.cards;

import model.cards.face.Face;
import model.enums.SuitEnum;

import java.io.Serializable;
import java.util.ArrayList;

public class CardStarting extends CardPlaying implements Serializable{
    private ArrayList<SuitEnum> symbols;


    public CardStarting(Integer ID, Face front, Face back, ArrayList<SuitEnum> symbols) {
        super(ID, front, back);
        this.symbols = symbols;
    }


    public ArrayList<SuitEnum> getSymbols() {
        return symbols;
    }

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
