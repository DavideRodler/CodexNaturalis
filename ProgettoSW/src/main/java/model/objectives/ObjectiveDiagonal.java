package model.objectives;

import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.Suit;
import model.enums.Direction;


import java.util.ArrayList;
import java.util.Arrays;

public class ObjectiveDiagonal implements Objective { //direzione data dalla carta in alto
private Direction direction;
private Suit color;

public int checkObjective(PlayingStation station){
    Boolean[][] flags = new Boolean[81][81];
    for(Boolean[] row: flags){
        Arrays.fill(row, false); //carte non ancora visualizzate
    }
    int points = 0;
    if(direction.equals(Direction.LEFT)){ //carta in alto a sx, carta in basso a dx
        for(int i = 0; i < 79; i++){ //coordinata x
            for(int j = 80; j > 1; j--){ //coordinata y
                if(!flags[i][j]) {
                    if ((i != 41) && (j != 41)) {
                        ArrayList<Integer> key = new ArrayList<>();
                        key.add(i);
                        key.add(j);
                        if (station.getTable().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                            if (station.getTable().get(key) instanceof CardResource) {
                                CardResource card1 = (CardResource) station.getTable().get(key);
                                if (card1.getSymbol() == color) {
                                    int x = i + 1; //coordinate next card nella diagonale
                                    int y = j - 1;
                                    key.add(x);
                                    key.add(y);
                                    if (station.getTable().containsKey(key)) {
                                        if (station.getTable().get(key) instanceof CardResource) {
                                            CardResource card2 = (CardResource) station.getTable().get(key);
                                            if (card2.getSymbol() == color) {
                                                int w = x + 1; //coordinate next card nella diagonale
                                                int z = y - 1;
                                                key.add(w);
                                                key.add(z);
                                                if (station.getTable().containsKey(key)) {
                                                    if (station.getTable().get(key) instanceof CardResource) {
                                                        CardResource card3 = (CardResource) station.getTable().get(key);
                                                        if (card3.getSymbol() == color) {
                                                            points++; //ho fatto una diagonale
                                                            flags[w][z] = true; //posizione toccata, non piu' da valutare -> diagonale fatta
                                                            flags[x][y] = true; //posizione toccata, non piu' da valutare -> diagonale fatta
                                                        }
                                                        else {
                                                            flags[w][z] = true; //posizione toccata, non piu' da valutare -> colore sbagliato
                                                        }
                                                    }
                                                    //else exception -> carta non di tipo CardResource
                                                } else {
                                                    flags[w][z] = true; //posizione toccata, non piu' da valutare -> posizione non riempita
                                                }
                                            } else {
                                                flags[x][y] = true; //posizione toccata, non piu' da valutare -> colore sbagliato
                                            }
                                        }
                                        //else exception -> carta non di tipo CardResource
                                    } else {
                                        flags[i][j] = true; //posizione toccata, non piu' da valutare -> posizione non riempita
                                         }
                                } else {
                                    flags[i][j] = true; //posizione toccata, non piu' da valutare -> colore sbagliato
                                }
                            }
                            //else exception -> carta non di tipo CardResource
                        } else {
                            flags[i][j] = true; //posizione toccata, non piu' da valutare -> posizione non riempita
                        }
                    } else {
                        flags[i][j] = true; //carta iniziale
                    }
                }
            }//end for di j
        }//end for di i
    }
    else{ //carta in alto a dx, carta in basso a sx
        for(int i = 80; i > 1; i--){ //coordinata x
            for(int j = 80; j > 1; j--){ //coordinata y
                if(!flags[i][j]) {
                    if ((i != 41) && (j != 41)) {
                        ArrayList<Integer> key = new ArrayList<>();
                        key.add(i);
                        key.add(j);
                        if (station.getTable().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                            if (station.getTable().get(key) instanceof CardResource) {
                                CardResource card1 = (CardResource) station.getTable().get(key);
                                if (card1.getSymbol() == color) {
                                    int x = i - 1; //coordinate next card nella diagonale
                                    int y = j - 1;
                                    key.add(x);
                                    key.add(y);
                                    if (station.getTable().containsKey(key)) {
                                        if (station.getTable().get(key) instanceof CardResource) {
                                            CardResource card2 = (CardResource) station.getTable().get(key);
                                            if (card2.getSymbol() == color) {
                                                int w = x - 1; //coordinate next card nella diagonale
                                                int z = y - 1;
                                                key.add(w);
                                                key.add(z);
                                                if (station.getTable().containsKey(key)) {
                                                    if (station.getTable().get(key) instanceof CardResource) {
                                                        CardResource card3 = (CardResource) station.getTable().get(key);
                                                        if (card3.getSymbol() == color) {
                                                            points++; //ho fatto una diagonale
                                                            flags[w][z] = true; //posizione toccata, non piu' da valutare -> diagonale fatta
                                                            flags[x][y] = true; //posizione toccata, non piu' da valutare -> diagonale fatta
                                                        }
                                                        else {
                                                            flags[w][z] = true; //posizione toccata, non piu' da valutare -> colore sbagliato
                                                        }
                                                    }
                                                    //else exception -> carta non di tipo CardResource
                                                } else {
                                                    flags[w][z] = true; //posizione toccata, non piu' da valutare -> posizione non riempita
                                                }
                                            } else {
                                                flags[x][y] = true; //posizione toccata, non piu' da valutare -> colore sbagliato
                                            }
                                        }
                                        //else exception -> carta non di tipo CardResource
                                    } else {
                                        flags[i][j] = true; //posizione toccata, non piu' da valutare -> posizione non riempita
                                    }
                                } else {
                                    flags[i][j] = true; //posizione toccata, non piu' da valutare -> colore sbagliato
                                }
                            }
                            //else exception -> carta non di tipo CardResource
                        } else {
                            flags[i][j] = true; //posizione toccata, non piu' da valutare -> posizione non riempita
                        }
                    } else {
                        flags[i][j] = true; //carta iniziale
                    }
                    flags[i][j] = true;//posizione toccata, non piu' da valutare
                }
            }//end for di j
        }//end for di i
    }
    return points;
}

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
