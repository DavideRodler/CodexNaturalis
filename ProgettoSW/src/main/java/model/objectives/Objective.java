package model.objectives;

import model.PlayingStation;

public interface Objective {
    public default int checkObjective(PlayingStation station) {
        return 0; //base case: points assigned when card is placed
    }
}
