package model.cards;

import model.cards.face.Face;
import model.enums.Suit;
import model.objectives.Objective;

import java.io.Serializable;

public class CardResource extends CardPlaying implements Serializable {
    private final Suit symbol;
    private final Integer points;
    private final Objective objective;  // Aggiunto l'attributo Objective


    public CardResource(int ID, Face front, Face back, Suit symbol, Integer points, Objective objective) {
        super(ID, front, back);
        this.symbol = symbol;
        this.points = points;
        this.objective = objective;  // Inizializzato l'attributo Objective
    }

    public Suit getSymbol() {
        return symbol;
    }

    public int getPoints() {
        return points;
    }
    public Objective getObjective() {  // Aggiunto il metodo getter per Objective
        return objective;
    }

    public int countResource(Suit suit){
        int count = 0;
        if (!getPlayingBack()){
                if (getFront().getUpRight().equals(suit)) {
                    count++;
                }
                if(getFront().getDownRight().equals(suit)){
                    count++;
                }
                if(getFront().getUpLeft().equals(suit)){
                    count++;
                }
                if(getFront().getDownLeft().equals(suit)){
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
