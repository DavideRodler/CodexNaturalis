package model.objectives;

import model.enums.Suit;
import model.enums.Direction;
import model.cards.Card;

import java.util.ArrayList;

public class ObjectiveDiagonal extends Objective{
private Direction direction;
private Suit color;

public int checkObjective(HashMap<ArrayList<Integer>, Card> table, Card card ) {

}

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