package model.objectives;

import model.cards.Card;

import java.util.ArrayList;

public class Objective {
    public int checkObjective(HashMap<ArrayList<Integer>, Card> table, Card card ) {
        return card.getPoints(); //base case: points assigned when card is placed
    }
}
