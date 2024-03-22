package main.java.model.objectives;

import model.enums.Suit;
import model.enums.Direction;
import model.enums.Position;
public class ObjectivePositioning extends Objective{
    public Suit colorOneCard;
    public Suit colorTwoCards;
public Direction horizontalDirection;
public Position verticalDirection;
    //public boolean checkObjective(Card[][] table){

    //}

    public Suit getColorOneCard() {
        return colorOneCard;
    }

    public Suit getColorTwoCards() {
        return colorTwoCards;
    }

    public Position getVerticalDirection() {
        return verticalDirection;
    }

    public Direction getHorizontalDirection() {
        return horizontalDirection;
    }

    public ObjectivePositioning(Suit colorOneCard, Suit colorTwoCards, Direction horizontalDirection, Position verticalDirection){
        this.colorOneCard = colorOneCard;
        this.colorTwoCards = colorTwoCards;
        this.horizontalDirection = horizontalDirection;
        this.verticalDirection = verticalDirection;
    }
}