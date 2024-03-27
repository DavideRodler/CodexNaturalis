package model.objectives;

import model.enums.Suit;
import model.enums.Direction;
import model.cards.Card;

import java.util.ArrayList;

public class ObjectiveDiagonal extends Objective{ //direzione data dalla carta in alto
private Direction direction;
private Suit color;

//public int checkObjective(PlayingStation station) {
    //   Boolean[][] flags = new Boolean[80][80];
    //   for(Boolean[] row: flags){
    //      Arrays.fill(row, false);
    //  }
    //  int points = 0;
    //  if(direction.equals(Direction.LEFT)){ //carta in alto a sx, carta in basso a dx
    //     for(int i = 0; i < 80; i++){ //coordinata x
    //         for(int j = 0; j < 80; j++){ //coordinata y
    //             if((i!=40)&&(j!=40)){
    //                ArrayList<Integer> key = new ArrayList<>();
    //                 key.add(i);
    //                 key.add(j);
    //                if(station.getTable().get(key)
    //           }
    //        }
    //    }
    // }
    // else{

    // }
    // return points;
//}

public Direction getDirection() {
        return direction;
    }
    public Suit getColor() {
        return color;
    }

    public ObjectiveDiagonal(Direction direction, Suit color){
    this.color = color;
    this.direction = direction;
    }
}