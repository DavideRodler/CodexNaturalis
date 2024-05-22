package model.cards;

import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardResourceTest {
    Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));

    Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));

    Objective obj = new ObjectiveAssign();
    // the card resource is of type ANIMAL
    CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
    @Test
    void getSymbol() {
        assertEquals(SuitEnum.ANIMAL, cardAnimal1.getSymbol());
    }

    @Test
    void getPoints() {
        assertEquals(1, cardAnimal1.getPoints());
    }

    @Test
    void getObjective() {
        assertEquals(obj, cardAnimal1.getObjective());
    }

    @Test
    void countResource() {
        assertEquals(1, cardAnimal1.countResource(SuitEnum.ANIMAL));
        assertEquals(0, cardAnimal1.countResource(SuitEnum.PLANT));
    }
}