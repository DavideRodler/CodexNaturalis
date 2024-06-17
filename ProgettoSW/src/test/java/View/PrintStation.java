package View;

import View.CLI.StationMatrix;
import model.PlayingStation;
import model.cards.CardPlaying;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import static model.testsTemplate.PlayingStationTemplate.creatingCordinatesArray;

public class PrintStation {
    //PlayngStationTameplate stationTempl = new PlayngStationTameplate();


    @Test
    public void CliPrintStation() throws RemoteException {
        PlayingStation station = PlayingStationTemplate.test_6Cards_2Positioning();
        HashMap<ArrayList<Integer>, CardPlaying> table = station.getMap();
        showUpdatedStation(table);
    }

    public static void showUpdatedStation(HashMap<ArrayList<Integer>, CardPlaying> playingStationMap) {
        StationMatrix stationMatrix= new StationMatrix();
        stationMatrix.printStation(playingStationMap);
        //stationMatrix.printResources(fungi, plant, animal, insect, quill, manuscript, inkwell);
    }
    @Test
    public void printStartingCard(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY),
                new Corner(SuitEnum.FUNGI));
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);

        CardStarting cardStarting = new CardStarting(6, frontTmp, backTmp, suitList);
        PlayingStation station = new PlayingStation(new HashMap<ArrayList<Integer>, CardPlaying>());
        station.getMap().put(creatingCordinatesArray(40, 40), cardStarting);

        StationMatrix stationMatrix= new StationMatrix();
        stationMatrix.printStation(station.getMap());
   }

   @Test
    public void printHand(){
       Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
               new Corner(SuitEnum.EMPTY));
       Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY),
               new Corner(SuitEnum.FUNGI));

   }
   @Test
    public void printStationMatrix(){
        StationMatrix stationMatrix= new StationMatrix();
        stationMatrix.printStationTest();
   }
}
