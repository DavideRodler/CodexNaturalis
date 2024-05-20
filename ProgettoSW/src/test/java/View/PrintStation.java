package View;

import model.PlayingStation;
import model.cards.CardPlaying;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintStation {
    //PlayngStationTameplate stationTempl = new PlayngStationTameplate();


    @Test
    public void CliPrintStation() throws RemoteException {
        PlayingStation station = PlayingStationTemplate.test_6Cards_2Positioning();
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
//        ArrayList<CardPlaying> cardPlayings = pla
   }
}
