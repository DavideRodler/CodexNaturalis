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
        CardResource card = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 2, obj);
        assertEquals(2, card.getObjective().countObjectivePoints(new PlayingStation(new HashMap<>()), card, 0, 2));
    }
}