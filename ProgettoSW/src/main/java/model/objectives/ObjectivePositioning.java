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
            for(int i = 1; i < 81; i++){
                for(int j = 77; j >=0; j--){
                    if (!flags[i][j]){
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getTable().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getTable().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i - 1;
                                        int y = j + 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getTable().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getTable().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y + 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getTable().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t + 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getTable().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getTable().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getTable().get(key3);
                                                                if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                    points = points + 1;
                                                                    flags[w][z] = true;
                                                                    flags[s][t] = true;
                                                                    flags[x][y] = true;
                                                                    flags[i][j] = true;
                                                                }//else exception
                                                            } //else exception
                                                        }//else skip
                                                    }//else skip
                                                }//else skip
                                            }//else exception
                                        }//else skip
                                    }//else skip alla prossima
                                }//else exception
                            }//else skippo alla prossima
                        } else{flags[i][j] = true; } //carta centrale
                    }//else skip alla prossima -> questa fa gia parte di un punto fatto
                }//end for j
            }//end for i
        }
        else if ((horizontalDirection==Direction.RIGHT)&&(verticalDirection==Position.BOTTOM)){
            for(int i = 0; i < 80; i++){
                for(int j = 80; j > 2; j--){
                    if (!flags[i][j]){
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getTable().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getTable().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i + 1;
                                        int y = j - 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getTable().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getTable().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y - 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getTable().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t - 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getTable().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getTable().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getTable().get(key3);
                                                                if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                    points = points + 1;
                                                                    flags[w][z] = true;
                                                                    flags[s][t] = true;
                                                                    flags[x][y] = true;
                                                                    flags[i][j] = true;
                                                                }//else exception
                                                            } //else exception
                                                        }//else skip
                                                    }//else skip
                                                }//else skip
                                            }//else exception
                                        }//else skip
                                    }//else skip alla prossima
                                }//else exception
                            }//else skippo alla prossima
                        } else{flags[i][j] = true; } //carta centrale
                    }//else skip alla prossima -> questa fa gia parte di un punto fatto
                }//end for j
            }//end for i
        }

        else if ((horizontalDirection==Direction.LEFT)&&(verticalDirection==Position.BOTTOM)){
            for(int i = 1; i < 81; i++){
                for(int j = 80; j > 2; j--){
                    if (!flags[i][j]){
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getTable().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getTable().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i - 1;
                                        int y = j - 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getTable().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getTable().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y - 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getTable().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t - 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getTable().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getTable().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getTable().get(key3);
                                                                if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                    points = points + 1;
                                                                    flags[w][z] = true;
                                                                    flags[s][t] = true;
                                                                    flags[x][y] = true;
                                                                    flags[i][j] = true;
                                                                }//else exception
                                                            } //else exception
                                                        }//else skip
                                                    }//else skip
                                                }//else skip
                                            }//else exception
                                        }//else skip
                                    }//else skip alla prossima
                                }//else exception
                            }//else skippo alla prossima
                        } else{flags[i][j] = true; } //carta centrale
                    }//else skip alla prossima -> questa fa gia parte di un punto fatto
                }//end for j
            }//end for i
        }
        else { //((horizontalDirection==Direction.RIGHT)&&(verticalDirection==Position.TOP))
            for(int i = 0; i < 80; i++){
                for(int j = 77; j >=0; j--){
                    if (!flags[i][j]){
                        if ((i != 41) && (j != 41)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getTable().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getTable().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getTable().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i + 1;
                                        int y = j + 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getTable().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getTable().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getTable().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y + 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getTable().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t + 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getTable().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getTable().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getTable().get(key3);
                                                                if ((card3.getSymbol() == colorTwoCards)&&(!flags[w][z])) {
                                                                    points = points + 1;
                                                                    flags[w][z] = true;
                                                                    flags[s][t] = true;
                                                                    flags[x][y] = true;
                                                                    flags[i][j] = true;
                                                                }//else exception
                                                            } //else exception
                                                        }//else skip
                                                    }//else skip
                                                }//else skip
                                            }//else exception
                                        }//else skip
                                    }//else skip alla prossima
                                }//else exception
                            }//else skippo alla prossima
                        } else{flags[i][j] = true; } //carta centrale
                    }//else skip alla prossima -> questa fa gia parte di un punto fatto
                }//end for j
            }//end for i
        }
        return points;
    }
}