package model.objectives;

import model.PlayingStation;
import model.cards.Card;
import model.cards.CardResource;
import model.enums.Suit;
import model.enums.Direction;
import model.enums.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectivePositioning implements Objective{
    public Suit colorOneCard;
    public Suit colorTwoCards;
    public Direction horizontalDirection;
    public Position verticalDirection;
//    public int checkObjective(HashMap<ArrayList<Integer>, CardPlaying> table) {
//
//    }

    public Suit getColorOneCard() {
        return colorOneCard;
    }

    public Suit getColorTwoCards() {
        return colorTwoCards;
    }

    public Position getVerticalDirection() {
        return verticalDirection;
    }

    public Direction getHorizontalDirection() {
        return horizontalDirection;
    }

    public ObjectivePositioning(Suit colorOneCard, Suit colorTwoCards, Direction horizontalDirection, Position verticalDirection){
        this.colorOneCard = colorOneCard;
        this.colorTwoCards = colorTwoCards;
        this.horizontalDirection = horizontalDirection;
        this.verticalDirection = verticalDirection;
    }

    @Override
    public int checkObjective(PlayingStation station) {
        Boolean[][] flags = new Boolean[81][81];
        for(Boolean[] row: flags){
            Arrays.fill(row, false); //carte non ancora visualizzate
        }
        int points = 0;
        if((horizontalDirection==Direction.LEFT)&&(verticalDirection==Position.TOP)) {
            for (int i = 1; i < 81; i++) {
                for (int j = 78; j >= 0; j--) {
                    if (!flags[i][j]) {
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key = new ArrayList<>();
                            key.add(0, i);
                            key.add(1, j);
                            if (station.getTable().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                                if (station.getTable().get(key) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key);
                                    if (card1.getSymbol() == colorOneCard) {
                                        int x = i - 1;
                                        int y = j + 1;
                                        ArrayList<Integer> key1 = new ArrayList<>();
                                        key1.add(0, x);
                                        key1.add(1, y);
                                        if (station.getTable().containsKey(key1)) { //guardo se esiste una carta posizionata in quell'indice
                                            if (station.getTable().get(key1) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key1);
                                                if ((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])) {
                                                    int w = x;
                                                    int z = y + 1;
                                                    ArrayList<Integer> key2 = new ArrayList<>();
                                                    key2.add(0, w);
                                                    key2.add(1, z);
                                                    if (station.getTable().containsKey(key2)) { //guardo se esiste una carta posizionata in quell'indice
                                                        if (station.getTable().get(key2) instanceof CardResource) {
                                                            CardResource card3 = (CardResource) station.getTable().get(key2);
                                                            if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                points = points + 1;
                                                                flags[w][z] = true;
                                                                flags[x][y] = true;
                                                                flags[i][j] = true;
                                                            } //else skippo alla prossima
                                                        }//else exception -> non e' cardResource
                                                    }
                                                    else{
                                                        flags[i][j] = true; //posizione toccata -> carta non contenuta
                                                    }
                                                }//else skippo alla prossima
                                            }//else exception -> non e' cardResource
                                        }
                                        else{
                                            flags[i][j] = true; //posizione toccata -> carta non contenuta
                                        }
                                    } //else skippo alla prossima
                                }//else exception -> non e' cardResource
                            }
                            else{
                                flags[i][j] = true; //posizione toccata -> carta non contenuta
                            }
                        }
                        else{
                            flags[i][j] = true; //carta centrale da non considerare
                        }
                    } //else -->carta gia considerata
                }//end ciclo for di j
            } //end ciclo for di i
        }
        else if ((horizontalDirection==Direction.RIGHT)&&(verticalDirection==Position.BOTTOM)){
            for (int i = 0; i < 80; i++) {
                for (int j = 80; j > 1; j--) {
                    if (!flags[i][j]) {
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key = new ArrayList<>();
                            key.add(0, i);
                            key.add(1, j);
                            if (station.getTable().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                                if (station.getTable().get(key) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key);
                                    if (card1.getSymbol() == colorOneCard) {
                                        int x = i + 1;
                                        int y = j - 1;
                                        ArrayList<Integer> key1 = new ArrayList<>();
                                        key1.add(0, x);
                                        key1.add(1, y);
                                        if (station.getTable().containsKey(key1)) { //guardo se esiste una carta posizionata in quell'indice
                                            if (station.getTable().get(key1) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key1);
                                                if ((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])) {
                                                    int w = x;
                                                    int z = y - 1;
                                                    ArrayList<Integer> key2 = new ArrayList<>();
                                                    key2.add(0, w);
                                                    key2.add(1, z);
                                                    if (station.getTable().containsKey(key2)) { //guardo se esiste una carta posizionata in quell'indice
                                                        if (station.getTable().get(key2) instanceof CardResource) {
                                                            CardResource card3 = (CardResource) station.getTable().get(key2);
                                                            if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                points = points + 1;
                                                                flags[w][z] = true;
                                                                flags[x][y] = true;
                                                                flags[i][j] = true;
                                                            } //else skippo alla prossima
                                                        }//else exception -> non e' cardResource
                                                    }
                                                    else{
                                                        flags[i][j] = true; //posizione toccata -> carta non contenuta
                                                    }
                                                }//else skippo alla prossima
                                            }//else exception -> non e' cardResource
                                        }
                                        else{
                                            flags[i][j] = true; //posizione toccata -> carta non contenuta
                                        }
                                    } //else skippo alla prossima
                                }//else exception -> non e' cardResource
                            }
                            else{
                                flags[i][j] = true; //posizione toccata -> carta non contenuta
                            }
                        }
                        else{
                            flags[i][j] = true; //carta centrale da non considerare
                        }
                    } //else -->carta gia considerata
                }//end ciclo for di j
            } //end ciclo for di i
        }

        else if ((horizontalDirection==Direction.LEFT)&&(verticalDirection==Position.BOTTOM)){
            for (int i = 1; i < 81; i++) {
                for (int j = 80; j > 1; j--) {
                    if (!flags[i][j]) {
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key = new ArrayList<>();
                            key.add(0, i);
                            key.add(1, j);
                            if (station.getTable().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                                if (station.getTable().get(key) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key);
                                    if (card1.getSymbol() == colorOneCard) {
                                        int x = i - 1;
                                        int y = j - 1;
                                        ArrayList<Integer> key1 = new ArrayList<>();
                                        key1.add(0, x);
                                        key1.add(1, y);
                                        if (station.getTable().containsKey(key1)) { //guardo se esiste una carta posizionata in quell'indice
                                            if (station.getTable().get(key1) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key1);
                                                if ((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])) {
                                                    int w = x;
                                                    int z = y - 1;
                                                    ArrayList<Integer> key2 = new ArrayList<>();
                                                    key2.add(0, w);
                                                    key2.add(1, z);
                                                    if (station.getTable().containsKey(key2)) { //guardo se esiste una carta posizionata in quell'indice
                                                        if (station.getTable().get(key2) instanceof CardResource) {
                                                            CardResource card3 = (CardResource) station.getTable().get(key2);
                                                            if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                points = points + 1;
                                                                flags[w][z] = true;
                                                                flags[x][y] = true;
                                                                flags[i][j] = true;
                                                            } //else skippo alla prossima
                                                        }//else exception -> non e' cardResource
                                                    }
                                                    else{
                                                        flags[i][j] = true; //posizione toccata -> carta non contenuta
                                                    }
                                                }//else skippo alla prossima
                                            }//else exception -> non e' cardResource
                                        }
                                        else{
                                            flags[i][j] = true; //posizione toccata -> carta non contenuta
                                        }
                                    } //else skippo alla prossima
                                }//else exception -> non e' cardResource
                            }
                            else{
                                flags[i][j] = true; //posizione toccata -> carta non contenuta
                            }
                        }
                        else{
                            flags[i][j] = true; //carta centrale da non considerare
                        }
                    } //else -->carta gia considerata
                }//end ciclo for di j
            } //end ciclo for di i
        }
        else { //((horizontalDirection==Direction.RIGHT)&&(verticalDirection==Position.TOP))
            for (int i = 0; i < 80; i++) {
                for (int j = 78; j >= 0; j--) {
                    if (!flags[i][j]) {
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key = new ArrayList<>();
                            key.add(0, i);
                            key.add(1, j);
                            if (station.getTable().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                                if (station.getTable().get(key) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key);
                                    if (card1.getSymbol() == colorOneCard) {
                                        int x = i + 1;
                                        int y = j + 1;
                                        ArrayList<Integer> key1 = new ArrayList<>();
                                        key1.add(0, x);
                                        key1.add(1, y);
                                        if (station.getTable().containsKey(key1)) { //guardo se esiste una carta posizionata in quell'indice
                                            if (station.getTable().get(key1) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key1);
                                                if ((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])) {
                                                    int w = x;
                                                    int z = y + 1;
                                                    ArrayList<Integer> key2 = new ArrayList<>();
                                                    key2.add(0, w);
                                                    key2.add(1, z);
                                                    if (station.getTable().containsKey(key2)) { //guardo se esiste una carta posizionata in quell'indice
                                                        if (station.getTable().get(key2) instanceof CardResource) {
                                                            CardResource card3 = (CardResource) station.getTable().get(key2);
                                                            if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                points = points + 1;
                                                                flags[w][z] = true;
                                                                flags[x][y] = true;
                                                                flags[i][j] = true;
                                                            } //else skippo alla prossima
                                                        }//else exception -> non e' cardResource
                                                    }
                                                    else{
                                                        flags[i][j] = true; //posizione toccata -> carta non contenuta
                                                    }
                                                }//else skippo alla prossima
                                            }//else exception -> non e' cardResource
                                        }
                                        else{
                                            flags[i][j] = true; //posizione toccata -> carta non contenuta
                                        }
                                    } //else skippo alla prossima
                                }//else exception -> non e' cardResource
                            }
                            else{
                                flags[i][j] = true; //posizione toccata -> carta non contenuta
                            }
                        }
                        else{
                            flags[i][j] = true; //carta centrale da non considerare
                        }
                    } //else -->carta gia considerata
                }//end ciclo for di j
            } //end ciclo for di i
        }
        return 0;
    }
}