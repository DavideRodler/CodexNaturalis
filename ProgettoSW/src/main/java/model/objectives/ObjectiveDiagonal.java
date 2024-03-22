package main.java.model.objectives;

import model.enums.Suit;
import model.enums.Direction;
import model.cards.Card;
public class ObjectiveDiagonal extends Objective{
private Direction direction;
private Suit color;

//public boolean checkObjective(Card[][] table){
//}

public Direction getDirection() {
        return direction;
    }
    public Suit getColor() {
        return color;
    }

    public ObjectiveDiagonal(Direction direction, Suit color){
    this.direction = direction;
    this.color = color;
    }
}