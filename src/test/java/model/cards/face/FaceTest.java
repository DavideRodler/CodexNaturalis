package model.cards.face;

import model.cards.CardResource;
import model.enums.SuitEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FaceTest {

    @Test
    void getEachCorner() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, null);
        assertEquals(SuitEnum.ANIMAL, cardAnimal1.getFront().getUpRight().getDrawing());
        assertEquals(SuitEnum.NULL, cardAnimal1.getFront().getUpLeft().getDrawing());
        assertEquals(SuitEnum.EMPTY, cardAnimal1.getFront().getDownRight().getDrawing());
        assertEquals(SuitEnum.FUNGI, cardAnimal1.getFront().getDownLeft().getDrawing());
    }

}