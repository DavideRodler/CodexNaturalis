package model.objectives;

import model.PlayingStation;
import model.cards.CardResource;

public interface Points {
    int countObjectivePoints(PlayingStation station, CardResource card, Integer x, Integer y);
}
