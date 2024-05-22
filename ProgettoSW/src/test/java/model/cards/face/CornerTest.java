package model.cards.face;

import model.enums.SuitEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerTest {

    @Test
    void isCovered() {
        Corner corner = new Corner(SuitEnum.ANIMAL);
        assertEquals(false, corner.isCovered());
    }

    @Test
    void setCovered() {
        Corner corner = new Corner(SuitEnum.ANIMAL);
        corner.setCovered(true);
        assertEquals(true, corner.isCovered());
    }

    @Test
    void getDrawing() {
        Corner corner = new Corner(SuitEnum.ANIMAL);
        assertEquals(SuitEnum.ANIMAL, corner.getDrawing());
    }
}