package model.objectives;
import model.cards.Card;
import model.enums.Suit;

import java.util.ArrayList;

public class ObjectiveCountingResource extends Objective{
    public Suit Simbol;

    public int checkObjective(HashMap<ArrayList<Integer>, Card> table, Card card ) {

    }

    public Suit getSimbol() {
        return Simbol;
    }

    public ObjectiveCountingResource(Suit suit){
        this.Simbol = suit;
    }
}