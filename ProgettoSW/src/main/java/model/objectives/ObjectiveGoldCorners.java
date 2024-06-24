package model.objectives;

import model.PlayingStation;
import model.cards.CardResource;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjectiveGoldCorners implements Points, Serializable {

    @Override
    public int countObjectivePoints(PlayingStation station , CardResource card, Integer x, Integer y) {
        int points = 0;
        //alto a dx
        int i = x+1;
        int j = y+1;
        ArrayList<Integer> key1 = new ArrayList<>();
        key1.add(0, i);
        key1.add(1, j);
        if (station.getMap().containsKey(key1)){
           points = points + 1;
        }
        //basso a dx
        int w = x+1;
        int z = y-1;
        ArrayList<Integer> key2 = new ArrayList<>();
        key2.add(0, w);
        key2.add(1, z);
        if (station.getMap().containsKey(key2)){
            points = points + 1;
        }
        //alto a sx
        int a = x-1;
        int b = y+1;
        ArrayList<Integer> key3 = new ArrayList<>();
        key3.add(0, a);
        key3.add(1, b);
        if (station.getMap().containsKey(key3)){
            points = points + 1;
        }
        //basso a sx
        int h = x-1;
        int k = y-1;
        ArrayList<Integer> key4 = new ArrayList<>();
        key4.add(0, h);
        key4.add(1, k);
        if (station.getMap().containsKey(key4)){
            points = points + 1;
        }
        return points*2;
    }
}