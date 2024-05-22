package model.cards;

import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardObjectiveTest {

    Objective obj = new ObjectiveAssign();
    CardObjective cardObjective1 = new CardObjective(0, 1, obj);
    @Test
    void getInformation() {
        assertEquals(obj, cardObjective1.getObjective());
        assertEquals(1, cardObjective1.getPoints());
    }

}