package View;

import Network.Cli2;
import Network.Client.RmiClient;
import Network.Server.RmiServer;
import Network.Server.VirtualServer;
import controller.GameController;
import model.PlayingStation;
import model.cards.CardPlaying;
import model.testsTameplate.PlayngStationTameplate;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrintStation {
    //PlayngStationTameplate stationTempl = new PlayngStationTameplate();


    @Test
    public void CliPrintStation() throws RemoteException {
        PlayingStation station = PlayngStationTameplate.test_6Cards_2Positioning();
        HashMap<ArrayList<Integer>, CardPlaying> table = station.getTable();
        showUpdatedStation(table);
    }

    public static void showUpdatedStation(HashMap<ArrayList<Integer>, CardPlaying> playingStation) {
        int x, y, maxX, maxY, distanceX, distanceY, max;
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
            distanceX = Math.abs(x - 40);
            distanceY = Math.abs(y - 40);
            if(distanceX > maxX || distanceY > maxY){
                maxX = distanceX;
                maxY = distanceY;
                max = Math.max(distanceX, distanceY);
            }
        }
        StationMatrix stationMatrix = new StationMatrix();
        stationMatrix.addCardsToSmallStation(stationCard, max);
        stationMatrix.printSmallStation(max);
    }
}
