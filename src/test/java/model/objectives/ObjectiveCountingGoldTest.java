package model.objectives;

import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCountingGoldTest {

    @Test
    void countObjectivePoints() {
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard_inkwells = new CardObjective(0, 2, obj1);
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard_all = new CardObjective(1, 2, obj2);
        Objective obj3 = new ObjectiveCountingGold(0,1,0);
        CardObjective objcard_manuscripts = new CardObjective(2, 1, obj3);
        Objective obj4 = new ObjectiveCountingGold(0,0,2);
        CardObjective objcard_quills = new CardObjective(2, 2, obj4);

        PlayingStation station = new PlayingStation(new HashMap<>());
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.INKWELL),
                new Corner(SuitEnum.ANIMAL));
        Face frontTmp2 = new Face(new Corner(SuitEnum.INKWELL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.INKWELL),
                new Corner(SuitEnum.ANIMAL));
        Face frontTmp3 = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.MANUSCRIPT), new Corner(SuitEnum.INKWELL),
                new Corner(SuitEnum.ANIMAL));
        // the card resource is of type ANIMAL
        CardResource card1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card2 = new CardResource(1, frontTmp2, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card3 = new CardResource(2, frontTmp3, backTmp, SuitEnum.ANIMAL, 0, null);

        card1.setPlayingBack(false);
        card2.setPlayingBack(false);
        card3.setPlayingBack(false);
        station.getMap().put(creatingCordinatesArray(39, 39), card1);
        station.updateCounters(card1);
        station.getMap().put(creatingCordinatesArray(38, 38), card2);
        station.updateCounters(card2);
        station.getMap().put(creatingCordinatesArray(37, 37), card3);
        station.updateCounters(card3);
        assertEquals(2, objcard_inkwells.getObjective().countObjectivePoints(station)); //count inkwells/2
        assertEquals( 1, objcard_all.getObjective().countObjectivePoints(station)); //count all
        assertEquals( 1, objcard_manuscripts.getObjective().countObjectivePoints(station)); //count manuscripts/1
        assertEquals( 1, objcard_quills.getObjective().countObjectivePoints(station)); //count quills/2
    }

    public static ArrayList<Integer> creatingCordinatesArray (int x, int y){
        ArrayList<Integer> key = new ArrayList<>();
        key.add(0, x);
        key.add(1, y);
        return key;
    }

    @Test
    void getCounters() {
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard_all = new CardObjective(1, 2, obj2);
        int quills = ((ObjectiveCountingGold) obj2).getCountQuill();
        int inkwell = ((ObjectiveCountingGold) obj2).getCountInkwell();
        int manuscript = ((ObjectiveCountingGold) obj2).getCountManuscript();
        assertEquals(1, quills);
        assertEquals(1, inkwell);
        assertEquals(1, manuscript);


    }

}