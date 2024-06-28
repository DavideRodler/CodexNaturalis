package model.cards;

import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardPlayingTest {
    Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));

    Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));

//    Objective obj = new ObjectiveAssign();
//    // the card resource is of type ANIMAL
//    CardResource card = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
//    @Test
//    void getFront() {
//        assertEquals(frontTmp, card.getFront());
//    }
//
//    @Test
//    void getBack() {
//        assertEquals(backTmp, card.getBack());
//    }
//
//    @Test
//    void getPlayingBack() {
//        card.setPlayingBack(true);
//        assertEquals(true, card.getPlayingBack());
//    }
//
//    @Test
//    void countResource() {
//        assertEquals(1, card.countResource(SuitEnum.ANIMAL));
//    }
}