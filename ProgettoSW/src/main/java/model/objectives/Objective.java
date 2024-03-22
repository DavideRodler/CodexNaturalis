package model.objectives;

import model.cards.Card;
public class Objective {
    public int checkObjective(HashMap<Array<Integer>, Card> table, Card card ) {
        return card.getPoints(); //base case: points assigned when card is placed
    }
}
