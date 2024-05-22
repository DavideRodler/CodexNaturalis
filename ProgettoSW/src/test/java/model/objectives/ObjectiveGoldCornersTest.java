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

class ObjectiveGoldCornersTest {

    @Test
    void countObjectivePoints() {
        PlayingStation station = new PlayingStation(new HashMap<>());
        Objective obj = new ObjectiveGoldCorners();
        CardObjective cardobj = new CardObjective(0,1, obj);
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL),
                new Corner(SuitEnum.ANIMAL));
        CardResource card1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        station.getMap().put(creatingCordinatesArray(38, 36), card1);
        station.getMap().put(creatingCordinatesArray(38, 38), card2);
        assertEquals(2,cardobj.getObjective().countObjectivePoints(station, 39, 37));

    }

    public static ArrayList<Integer> creatingCordinatesArray (int x, int y){
        ArrayList<Integer> key = new ArrayList<>();
        key.add(0, x);
        key.add(1, y);
        return key;
    }

}