package model.objectives;

import model.PlayingStation;
import model.cards.CardResource;


public class ObjectiveAssign implements Objective {
   @Override
    public int checkObjective(PlayingStation station , CardResource card, Integer x, Integer y){
        return switch (card.getPoints()) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            case 5 -> 5;
            default -> 0;
        };
    }

}
