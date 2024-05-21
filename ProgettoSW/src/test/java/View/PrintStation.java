package View;

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
        PlayingStation station = PlayingStationTemplate.test_3Cards_1Positioning();
        HashMap<ArrayList<Integer>, CardPlaying> table = station.getMap();
        showUpdatedStation(table);
    }

    public static void showUpdatedStation(HashMap<ArrayList<Integer>, CardPlaying> playingStation) {
        int x, y, maxX, maxY, distanceX, distanceY, max;
        int maxXPos, maxXNeg, maxYPos, maxYNeg;
        maxXPos = 0;
        maxXNeg = 0;
        maxYPos = 0;
        maxYNeg = 0;
        maxX = 0;
        maxY = 0;
        max = 0;
        System.out.println("Station ");//name);
        CardPlaying[][] stationCard = new CardPlaying[80][80];
        for (HashMap.Entry<ArrayList<Integer>, CardPlaying> entry : playingStation.entrySet()) {
            ArrayList<Integer> coordinates = entry.getKey();
            CardPlaying card = entry.getValue();
            x = coordinates.get(0);
            y = coordinates.get(1);
            stationCard[x][y] = card;
            distanceX = x - 40;
            distanceY = y - 40;
//            if(distanceX > maxX || distanceY > maxY){
//                maxX = distanceX;
//                maxY = distanceY;
//                max = Math.max(distanceX, distanceY);
//            }
            if(distanceX > maxXPos){
                maxXPos = distanceX;
            }
            if(distanceY > maxYPos){
                maxYPos = distanceY;
            }
            if(distanceX < maxXNeg) {
                maxXNeg = distanceX;
            }
            if(distanceY < maxYNeg){
                maxYNeg = distanceY;
            }
            max = Math.max(Math.max(maxXPos, -maxXNeg), Math.max(maxYPos, -maxYNeg));
        }
        StationMatrix stationMatrix = new StationMatrix();
        stationMatrix.initializeStationPrint();
        stationMatrix.addCardsToStation(stationCard, max);
        stationMatrix.printStation(max);
    }
    @Test
    public void printStartingcard(){
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

        int x, y, maxX, maxY, distanceX, distanceY, max;
        int maxXPos, maxXNeg, maxYPos, maxYNeg;
        maxXPos = 0;
        maxXNeg = 0;
        maxYPos = 0;
        maxYNeg = 0;
        maxX = 0;
        maxY = 0;
        max = 0;
        System.out.println("Station ");//name);
        CardPlaying[][] stationCard = new CardPlaying[80][80];
        for (HashMap.Entry<ArrayList<Integer>, CardPlaying> entry : station.getMap().entrySet()) {
            ArrayList<Integer> coordinates = entry.getKey();
            CardPlaying card = entry.getValue();
            x = coordinates.get(0);
            y = coordinates.get(1);
            stationCard[x][y] = card;
            distanceX = x - 40;
            distanceY = y - 40;
//            if(distanceX > maxX || distanceY > maxY){
//                maxX = distanceX;
//                maxY = distanceY;
//                max = Math.max(distanceX, distanceY);
//            }
            if(distanceX > maxXPos){
                maxXPos = distanceX;
            }
            if(distanceY > maxYPos){
                maxYPos = distanceY;
            }
            if(distanceX < maxXNeg) {
                maxXNeg = distanceX;
            }
            if(distanceY < maxYNeg){
                maxYNeg = distanceY;
            }
            max = Math.max(Math.max(maxXPos, -maxXNeg), Math.max(maxYPos, -maxYNeg));
        }
        StationMatrix stationMatrix = new StationMatrix();
        stationMatrix.initializeStationPrint();
        stationMatrix.addCardsToStation(stationCard, max);
        stationMatrix.printStation(max);
   }

   @Test
    public void printHand(){
       Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY),
               new Corner(SuitEnum.EMPTY));
       Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY),
               new Corner(SuitEnum.FUNGI));

   }
}
