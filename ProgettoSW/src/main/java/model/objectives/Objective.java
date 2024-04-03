package model.objectives;

import model.PlayingStation;
import model.cards.CardResource;

public interface Objective {
    default int checkObjective(PlayingStation station) {
        return 0; //base case: points assigned when card is placed
    }

    default int checkObjective(PlayingStation station, CardResource card, Integer x, Integer y) {
        return 0; //base case: points assigned when card is placed
    }
}
