package model.objectives;

import model.PlayingStation;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.enums.DirectionEnum;
import model.enums.PositionEnum;

import java.io.Serializable;
import java.util.*;

public class ObjectivePositioning implements Objective, Serializable {
    public SuitEnum colorOneCard;
    public SuitEnum colorTwoCards;
    public DirectionEnum horizontalDirection;
    public PositionEnum verticalDirection;


    public SuitEnum getColorOneCard() {
        return colorOneCard;
    }

    public SuitEnum getColorTwoCards() {
        return colorTwoCards;
    }

    public PositionEnum getVerticalDirection() {
        return verticalDirection;
    }

    public DirectionEnum getHorizontalDirection() {
        return horizontalDirection;
    }

    public ObjectivePositioning(SuitEnum colorOneCard, SuitEnum colorTwoCards, DirectionEnum horizontalDirection, PositionEnum verticalDirection){
        this.colorOneCard = colorOneCard;
        this.colorTwoCards = colorTwoCards;
        this.horizontalDirection = horizontalDirection;
        this.verticalDirection = verticalDirection;
    }


    /**
     * Conta i punti ottenuti completando le posizioni specificate dall'obiettivo
     * @param station la PlayingStation
     * @return i punti dell'obiettivo
     */
    @Override
    public int countObjectivePoints(PlayingStation station) {
        List<List<PatternCard>> patterns = Arrays.asList(
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.FUNGI),
                        new PatternCard(2, 0, SuitEnum.FUNGI),
                        new PatternCard(3, 1, SuitEnum.PLANT)
                ),
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.ANIMAL),
                        new PatternCard(2, 0, SuitEnum.ANIMAL),
                        new PatternCard(-1, 1, SuitEnum.FUNGI)
                ),
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.INSECT),
                        new PatternCard(2, 0, SuitEnum.INSECT),
                        new PatternCard(-1, -1, SuitEnum.ANIMAL)
                ),
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.PLANT),
                        new PatternCard(2, 0, SuitEnum.PLANT),
                        new PatternCard(3, -1, SuitEnum.INSECT)
                )
        );

        int points = 0;

        HashMap<ArrayList<Integer>, CardPlaying> patternMap = station.getMap();
        patternMap.remove(new ArrayList<>(Arrays.asList(40, 40)));
        Set<ArrayList<Integer>> keys = new HashSet<>(patternMap.keySet());
        for (ArrayList<Integer> key : keys) {
            for (List<PatternCard> pattern : patterns) {
                if (matchesPattern(station, key, pattern)) {
                    points++;
                    removePattern(station, key, pattern);
                }
            }
        }

        return points*3;
    }


    /**
     * Controlla se il pattern corrisponde alla PlayingStation
     * @param station la PlayingStation
     * @param start la posizione di partenza del pattern
     * @param pattern il pattern da controllare
     * @return true se il pattern corrisponde, false altrimenti
     */
    private boolean matchesPattern(PlayingStation station, ArrayList<Integer> start, List<PatternCard> pattern) {
        for (PatternCard patternCard : pattern) {
            ArrayList<Integer> key = new ArrayList<>();
            key.add(start.get(0) + patternCard.getDx());
            key.add(start.get(1) + patternCard.getDy());
            CardResource card = (CardResource) station.getMap().get(key);
            if (card == null || card.getSymbol() != patternCard.getSuit()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Rimuove le carte corrispondenti al pattern dal pattern
     * @param station la PlayingStation
     * @param start la posizione di partenza del pattern
     * @param pattern il pattern da rimuovere
     */
    private void removePattern(PlayingStation station, ArrayList<Integer> start, List<PatternCard> pattern) {
        for (PatternCard patternCard : pattern) {
            ArrayList<Integer> key = new ArrayList<>();
            key.add(start.get(0) + patternCard.getDx());
            key.add(start.get(1) + patternCard.getDy());
            station.getMap().remove(key);
        }
    }

    //MAIN DA TOGLIERE L'HO USATO COME TESTER
 /**   public static void main(String[] args){
        HashMap<ArrayList<Integer>, CardPlaying> map = new HashMap<>();
        PlayingStation station = new PlayingStation(map);
        Face front = new Face(new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI));
        Face back = new Face(new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI));
        CardPlaying card1 = new CardResource(0, front, back, SuitEnum.FUNGI, 1, null);
        CardPlaying card2 = new CardResource(1, front, back, SuitEnum.ANIMAL, 1, null);
        CardPlaying card3 = new CardResource(2, front, back, SuitEnum.ANIMAL, 1, null);
        CardPlaying card4 = new CardResource(3, front, back, SuitEnum.PLANT, 1, null);
        CardPlaying card5 = new CardResource(4, front, back, SuitEnum.PLANT, 1, null);
        CardPlaying card6 = new CardResource(5, front, back, SuitEnum.INSECT, 1, null);
        ArrayList<SuitEnum> suits = new ArrayList<>();
        suits.add(SuitEnum.FUNGI);
        suits.add(SuitEnum.FUNGI);
        CardStarting cardS = new CardStarting(6, front, back, suits);
        station.getMap().put(new ArrayList<>(Arrays.asList(40, 40)), cardS);
        station.getMap().put(new ArrayList<>(Arrays.asList(41, 41)), card1);
        station.getMap().put(new ArrayList<>(Arrays.asList(42, 40)), card2);
        station.getMap().put(new ArrayList<>(Arrays.asList(44, 40)), card3);
        station.getMap().put(new ArrayList<>(Arrays.asList(50, 50)), card4);
        station.getMap().put(new ArrayList<>(Arrays.asList(52, 50)), card5);
        station.getMap().put(new ArrayList<>(Arrays.asList(53, 49)), card6);


        ObjectivePositioning objective = new ObjectivePositioning(SuitEnum.FUNGI, SuitEnum.ANIMAL, DirectionEnum.RIGHT, PositionEnum.TOP);
        System.out.println(objective.countObjectivePoints(station)); // 1
    }**/

    /*
    @Override
    public int countObjectivePoints(PlayingStation station) {
        Boolean[][] flags = new Boolean[81][81];
        for(Boolean[] row: flags){
            Arrays.fill(row, false); //carte non ancora visualizzate
        }
        int points = 0;
        if((horizontalDirection== DirectionEnum.LEFT)&&(verticalDirection== PositionEnum.TOP)) {
            for(int i = 1; i < 81; i++){
                for(int j = 77; j >=0; j--){
                    if (!flags[i][j]){
                        if ((i != 40) && (j != 40)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getMap().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getMap().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getMap().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i - 1;
                                        int y = j + 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getMap().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getMap().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getMap().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y + 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getMap().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t + 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getMap().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getMap().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getMap().get(key3);
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
        else if ((horizontalDirection== DirectionEnum.RIGHT)&&(verticalDirection== PositionEnum.BOTTOM)){
            for(int i = 0; i < 80; i++){
                for(int j = 80; j > 2; j--){
                    if (!flags[i][j]){
                        if ((i != 40) && (j != 40)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getMap().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getMap().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getMap().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i + 1;
                                        int y = j - 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getMap().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getMap().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getMap().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y - 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getMap().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t - 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getMap().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getMap().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getMap().get(key3);
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

        else if ((horizontalDirection== DirectionEnum.LEFT)&&(verticalDirection== PositionEnum.BOTTOM)){
            for(int i = 1; i < 81; i++){
                for(int j = 80; j > 2; j--){
                    if (!flags[i][j]){
                        if ((i != 40) && (j != 40)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getMap().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getMap().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getMap().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i - 1;
                                        int y = j - 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getMap().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getMap().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getMap().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y - 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getMap().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t - 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getMap().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getMap().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getMap().get(key3);
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
                        if ((i != 40) && (j != 40)) {
                            ArrayList<Integer> key1 = new ArrayList<>();
                            key1.add(0, i);
                            key1.add(1, j);
                            if (station.getMap().containsKey(key1)) { //se c'e una carta li dentro
                                if (station.getMap().get(key1) instanceof CardResource) {
                                    CardResource card1 = (CardResource) station.getMap().get(key1);
                                    if(card1.getSymbol() == colorOneCard){
                                        int x = i + 1;
                                        int y = j + 1;
                                        ArrayList<Integer> key2 = new ArrayList<>();
                                        key2.add(0, x);
                                        key2.add(1, y);
                                        if (station.getMap().containsKey(key2)) { //se c'e una carta li dentro
                                            if (station.getMap().get(key2) instanceof CardResource) {
                                                CardResource card2 = (CardResource) station.getMap().get(key2);
                                                if((card2.getSymbol() == colorTwoCards)&&(!flags[x][y])){
                                                    int s = x;
                                                    int t = y + 1;
                                                    ArrayList<Integer> keyempty = new ArrayList<>();
                                                    keyempty.add(0, s);
                                                    keyempty.add(1, t);
                                                    if (!station.getMap().containsKey(keyempty) && (!flags[s][t])){ //spazio vuoto
                                                        int w = s;
                                                        int z = t + 1;
                                                        ArrayList<Integer> key3 = new ArrayList<>();
                                                        key3.add(0, w);
                                                        key3.add(1, z);
                                                        if (station.getMap().containsKey(key3)) { //se c'e una carta li dentro
                                                            if (station.getMap().get(key3) instanceof CardResource) {
                                                                CardResource card3 = (CardResource) station.getMap().get(key3);
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
    }*/
}