package model.objectives;

import model.PlayingStation;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.PositionEnum;
import model.enums.SuitEnum;
import model.enums.DirectionEnum;


import java.io.Serializable;
import java.util.*;

public class ObjectiveDiagonal implements Objective, Serializable { //direzione data dalla carta in alto
private DirectionEnum directionEnum;
private SuitEnum color;


    public ObjectiveDiagonal(DirectionEnum directionEnum, SuitEnum color){
        this.color = color;
        this.directionEnum = directionEnum;
    }

    public DirectionEnum getDirection (){
        return directionEnum;
    }

    public SuitEnum getColor (){
        return color;
    }


    /**
     * Conta i punti ottenuti completando le diagonali con le carte presenti nella PlayingStation
     * @param station la PlayingStation
     * @return il numero di punti ottenuti
     */
    @Override
    public int countObjectivePoints(PlayingStation station) {
        List<List<PatternCard>> patterns = Arrays.asList(
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.FUNGI),
                        new PatternCard(1, -1, SuitEnum.FUNGI),
                        new PatternCard(2, -2, SuitEnum.FUNGI)
                ),
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.ANIMAL),
                        new PatternCard(1, -1, SuitEnum.ANIMAL),
                        new PatternCard(2, -2, SuitEnum.ANIMAL)
                ),
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.INSECT),
                        new PatternCard(1, 1, SuitEnum.INSECT),
                        new PatternCard(2, 2, SuitEnum.INSECT)
                ),
                Arrays.asList(
                        new PatternCard(0, 0, SuitEnum.PLANT),
                        new PatternCard(1, 1, SuitEnum.PLANT),
                        new PatternCard(2, 2, SuitEnum.PLANT)
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

        return points*2;
    }

    /**
     * Controlla se il pattern di carte corrisponde a quello presente nella PlayingStation
     * @param station PlayingStation
     * @param start Punto di partenza del pattern
     * @param pattern Pattern di carte da cercare
     * @return true se il pattern è presente, false altrimenti
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
     * Rimuove le carte corrispondenti al pattern dalla PlayingStation
     * @param station la PlayingStation
     * @param start le coordinate della carta in alto a sinistra del pattern
     * @param pattern il pattern di carte da rimuovere
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
/**    public static void main(String[] args){
        HashMap<ArrayList<Integer>, CardPlaying> map = new HashMap<>();
        PlayingStation station = new PlayingStation(map);
        Face front = new Face(new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI));
        Face back = new Face(new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI), new Corner(SuitEnum.FUNGI));
        CardPlaying card1 = new CardResource(0, front, back, SuitEnum.FUNGI, 1, null);
        CardPlaying card2 = new CardResource(1, front, back, SuitEnum.ANIMAL, 1, null);
        CardPlaying card3 = new CardResource(4, front, back, SuitEnum.PLANT, 1, null);
        CardPlaying card4 = new CardResource(5, front, back, SuitEnum.INSECT, 1, null);
        //CardPlaying card7 = new CardResource(6, front, back, SuitEnum.ANIMAL, 1, null);
        //CardPlaying card8 = new CardResource(7, front, back, SuitEnum.ANIMAL, 1, null);
        ArrayList<SuitEnum> suits = new ArrayList<>();
        suits.add(SuitEnum.FUNGI);
        suits.add(SuitEnum.FUNGI);
        CardStarting cardS = new CardStarting(6, front, back, suits);
        station.getMap().put(new ArrayList<>(Arrays.asList(40, 40)), cardS);
        station.getMap().put(new ArrayList<>(Arrays.asList(56, 45)), card2);
        station.getMap().put(new ArrayList<>(Arrays.asList(57, 44)), card2);
        station.getMap().put(new ArrayList<>(Arrays.asList(58, 43)), card2);
        station.getMap().put(new ArrayList<>(Arrays.asList(32, 27)), card2);
        station.getMap().put(new ArrayList<>(Arrays.asList(33, 26)), card2);
        station.getMap().put(new ArrayList<>(Arrays.asList(34, 25)), card2);
        station.getMap().put(new ArrayList<>(Arrays.asList(25, 24)), card2);

        ObjectiveDiagonal objective = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.FUNGI);
        System.out.println(objective.countObjectivePoints(station)); // 1
    }**/


/*
@Override
public int countObjectivePoints(PlayingStation station){
    Boolean[][] flags = new Boolean[81][81];
    for(Boolean[] row: flags){
        Arrays.fill(row, false); //carte non ancora visualizzate
    }
    int points = 0;
    if(directionEnum.equals(DirectionEnum.LEFT)){ //carta in alto a sx, carta in basso a dx
        for(int i = 0; i < 79; i++){ //coordinata x
            for(int j = 80; j > 1; j--){ //coordinata y
                if(!flags[i][j]) {
                    if ((i != 41) && (j != 41)) {
                        ArrayList<Integer> key = new ArrayList<>();
                        key.add(0, i);
                        key.add(1, j);
                        if (station.getMap().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                            if (station.getMap().get(key) instanceof CardResource) {
                                CardResource card1 = (CardResource) station.getMap().get(key);
                                if (card1.getSymbol() == color) {
                                    int x = i + 1; //coordinate next card nella diagonale
                                    int y = j - 1;
                                    ArrayList<Integer> key1 = new ArrayList<>();
                                    key1.add(0, x);
                                    key1.add(1, y);
                                    if (station.getMap().containsKey(key1)) {
                                        if (station.getMap().get(key1) instanceof CardResource) {
                                            CardResource card2 = (CardResource) station.getMap().get(key1);
                                            if (card2.getSymbol() == color) {
                                                int w = x + 1; //coordinate next card nella diagonale
                                                int z = y - 1;
                                                ArrayList<Integer> key2 = new ArrayList<>();
                                                key2.add(0, w);
                                                key2.add(1, z);
                                                if (station.getMap().containsKey(key2)) {
                                                    if (station.getMap().get(key2) instanceof CardResource) {
                                                        CardResource card3 = (CardResource) station.getMap().get(key2);
                                                        if (card3.getSymbol() == color) {
                                                            points = points + 1; //ho fatto una diagonale
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
                    flags[i][j] = true;
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
                        key.add(0, i);
                        key.add(1, j);
                        if (station.getMap().containsKey(key)) { //guardo se esiste una carta posizionata in quell'indice
                            if (station.getMap().get(key) instanceof CardResource) {
                                CardResource card1 = (CardResource) station.getMap().get(key);
                                if (card1.getSymbol() == color) {
                                    int x = i - 1; //coordinate next card nella diagonale
                                    int y = j - 1;
                                    ArrayList<Integer> key1 = new ArrayList<>();
                                    key1.add(0, x);
                                    key1.add(1, y);
                                    if (station.getMap().containsKey(key1)) {
                                        if (station.getMap().get(key1) instanceof CardResource) {
                                            CardResource card2 = (CardResource) station.getMap().get(key1);
                                            if (card2.getSymbol() == color) {
                                                int w = x - 1; //coordinate next card nella diagonale
                                                int z = y - 1;
                                                ArrayList<Integer> key2 = new ArrayList<>();
                                                key2.add(0, w);
                                                key2.add(1, z);
                                                if (station.getMap().containsKey(key2)) {
                                                    if (station.getMap().get(key2) instanceof CardResource) {
                                                        CardResource card3 = (CardResource) station.getMap().get(key2);
                                                        if (card3.getSymbol() == color) {
                                                            points = points + 1; //ho fatto una diagonale
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

    public DirectionEnum getDirection() {
        return directionEnum;
    }
    public SuitEnum getColor() {
        return color;
    }

    public ObjectiveDiagonal(DirectionEnum directionEnum, SuitEnum color){
    this.color = color;

    this.directionEnum = directionEnum;

    }*/

}
