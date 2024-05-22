package model.cards;

import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardGoldTest {

    Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));

    Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));

    Objective obj = new ObjectiveAssign();
    // the card resource is of type ANIMAL
    CardGold cardAnimal1 = new CardGold(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, 2,1,3,2, obj);
    @Test
    void getInformation() {
        assertEquals(2, cardAnimal1.getCostAnimal());
        assertEquals(1, cardAnimal1.getCostInsect());
        assertEquals(3, cardAnimal1.getCostFungi());
        assertEquals(2, cardAnimal1.getCostPlant());
        assertEquals(obj, cardAnimal1.getObjective());
    }

}