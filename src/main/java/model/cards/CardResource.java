package model.cards;

import model.cards.face.Face;
import model.enums.SuitEnum;
import model.objectives.Objective;
import model.objectives.Points;

import java.io.Serializable;

/**
 * This class represents a resource card.
 * It has a symbol, a number of points and an objective.
 */
public class CardResource extends CardPlaying implements Serializable {
    private final SuitEnum symbol;
    private final Integer points;
    private final Points objective;


    public CardResource(int ID, Face front, Face back, SuitEnum symbol, Integer points, Points objective) {
        super(ID, front, back);
        this.symbol = symbol;
        this.points = points;
        this.objective = objective;
    }

    public SuitEnum getSymbol() {
        return symbol;
    }

    public int getPoints() {
        return points;
    }
    public Points getObjective() {  // Aggiunto il metodo getter per Objective
        return objective;
    }


    /**
     * This method counts the number of resources of a certain suit in the card
     * @param suit the suit to count
     * @return the number of resources of the suit in the card
     */
    public int countResource(SuitEnum suit){
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
            if(getSymbol().equals(suit)){ //On the back of the card there is only one symbol in the middle
                count++;
            }
        }
        return count;
    }

}
