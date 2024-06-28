package model.objectives;

import model.PlayingStation;
import model.cards.CardResource;

import java.io.Serializable;


/**
 * Is a fake objective used for card resources that only give points
 */
public class ObjectiveAssign implements Points, Serializable {



    //TODO-> TOGLIERE CARTE DA 20 PUNTI

    /**
     * This method is used to count the points of a specific card
     * @param station the station of the player
     * @param card the card to count the points
     * @param x the x coordinate of the card
     * @param y the y coordinate of the card
     * @return the points of the card
     */
   @Override
    public int countObjectivePoints(PlayingStation station , CardResource card, Integer x, Integer y){
        return switch (card.getPoints()) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            case 5 -> 5;
            case 20 -> 20;
            default -> 0;
        };
    }

}
