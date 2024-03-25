package model.objectives;
import model.cards.Card;
import model.cards.CardResource;
import model.enums.Suit;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectiveCountingResource extends Objective{
    public Suit symbol;

    public int checkObjective(HashMap<ArrayList<Integer>, CardResource> table, Card card ) {
        int points = 0;
        return switch(symbol){
            case FUNGI: points = table.getCountFungi() / 3;
            case PLANT: points = table.getCountPlant() / 3;
            case ANIMAL: points = table.getCountAnimal() / 3;
            default: points = table.getCountInsect() / 3;
        };
        return points;

    }

    public Suit getSymbol() {
        return symbol;
    }

    public ObjectiveCountingResource(Suit suit){
        this.symbol = suit;
    }
}