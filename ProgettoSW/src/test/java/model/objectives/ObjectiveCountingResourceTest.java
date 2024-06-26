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

class ObjectiveCountingResourceTest {

    @Test
    void countObjectivePoints() {
        Objective obj = new ObjectiveCountingResource(SuitEnum.ANIMAL);
        CardObjective objcard = new CardObjective(0, 1, obj);
        Objective obj2 = new ObjectiveCountingResource(SuitEnum.PLANT);
        CardObjective objcard2 = new CardObjective(1, 1, obj2);
        Objective obj3 = new ObjectiveCountingResource(SuitEnum.INSECT);
        CardObjective objcard3 = new CardObjective(2, 1, obj3);
        Objective obj4 = new ObjectiveCountingResource(SuitEnum.FUNGI);
        CardObjective objcard4 = new CardObjective(2, 1, obj4);

        PlayingStation station = new PlayingStation(new HashMap<>());
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL),
                new Corner(SuitEnum.ANIMAL));
        Face frontTmp2 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.INKWELL),
                new Corner(SuitEnum.ANIMAL));
        Face frontTmp3 = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL),
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
        assertEquals(4, objcard.getObjective().countObjectivePoints(station));
        assertEquals(2, objcard2.getObjective().countObjectivePoints(station));
        assertEquals(0, objcard3.getObjective().countObjectivePoints(station));
        assertEquals(0, objcard4.getObjective().countObjectivePoints(station));
    }

    public static ArrayList<Integer> creatingCordinatesArray (int x, int y){
        ArrayList<Integer> key = new ArrayList<>();
        key.add(0, x);
        key.add(1, y);
        return key;
    }

    @Test
    void getSymbol() {
        Objective obj = new ObjectiveCountingResource(SuitEnum.ANIMAL);
        SuitEnum suit = ((ObjectiveCountingResource) obj).getSymbol();
        assertEquals(SuitEnum.ANIMAL, suit);
    }
}