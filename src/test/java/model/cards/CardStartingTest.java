package model.cards;

import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class CardStartingTest {

    @Test
    void getSymbols() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);
        assertEquals(suitList, cardStarting.getSymbols());
        CardStarting cardStarting1 = new CardStarting(3, frontTmp2, backTmp, suitList);
        cardStarting1.setPlayingBack(true);
        assertEquals(suitList, cardStarting.getSymbols());

    }

    @Test
    void countResource() {
        Face backTmp = new Face(new Corner(SuitEnum.PLANT), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.PLANT));
        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.PLANT));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        CardStarting cardStarting = new CardStarting(3, frontTmp2, backTmp, suitList);
        cardStarting.setPlayingBack(false);
        assertEquals(3, cardStarting.countResource(SuitEnum.ANIMAL));
        assertEquals(3, cardStarting.countResource(SuitEnum.PLANT));
        assertEquals(0, cardStarting.countResource(SuitEnum.FUNGI));
        cardStarting.setPlayingBack(true);
        assertEquals(0, cardStarting.countResource(SuitEnum.ANIMAL));
        assertEquals(4, cardStarting.countResource(SuitEnum.PLANT));

    }
}