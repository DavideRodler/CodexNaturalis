package model.objectives;

import model.PlayingStation;
import model.cards.CardResource;

public interface Objective {
    int countObjectivePoints(PlayingStation station);
}
