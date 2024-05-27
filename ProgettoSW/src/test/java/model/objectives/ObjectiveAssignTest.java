package model.objectives;

import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveAssignTest {

    @Test
    void countObjectivePoints() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
        CardResource card0 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, obj);
        CardResource card1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
        CardResource card2 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 2, obj);
        CardResource card3 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 3, obj);
        CardResource card4 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 4, obj);
        CardResource card5 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 5, obj);
        assertEquals(0, card0.getObjective().countObjectivePoints(new PlayingStation(new HashMap<>()), card0, 0, 2));
        assertEquals(1, card1.getObjective().countObjectivePoints(new PlayingStation(new HashMap<>()), card1, 0, 2));
        assertEquals(2, card2.getObjective().countObjectivePoints(new PlayingStation(new HashMap<>()), card2, 0, 2));
        assertEquals(3, card3.getObjective().countObjectivePoints(new PlayingStation(new HashMap<>()), card3, 0, 2));
        assertEquals(4, card4.getObjective().countObjectivePoints(new PlayingStation(new HashMap<>()), card4, 0, 2));
        assertEquals(5, card5.getObjective().countObjectivePoints(new PlayingStation(new HashMap<>()), card5, 0, 2));
    }
}