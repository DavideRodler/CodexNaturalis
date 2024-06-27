package model.objectives;

import model.PlayingStation;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.enums.SuitEnum;
import model.enums.DirectionEnum;


import java.io.Serializable;
import java.util.*;

public class ObjectiveDiagonal implements Objective, Serializable {
    private DirectionEnum directionEnum;
    private SuitEnum color;


    public ObjectiveDiagonal(DirectionEnum directionEnum, SuitEnum color) {
        this.color = color;
        this.directionEnum = directionEnum;
    }

    public DirectionEnum getDirection() {
        return directionEnum;
    }

    public SuitEnum getColor() {
        return color;
    }


    /**
     * Counting of points made with this objective, checking how many times the specific diagonal has been completed
     *
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

        return points * 2;
    }

    /**
     * Checking if the pattern corrisponds to the PlayingStation
     * @param station  PlayingStation
     * @param start starting point of the pattern
     * @param pattern what pattern I'm looking for
     * @return true if there is a corrispondence, false otherwise
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
     * Remove cards that are part of a found pattern
     *
     * @param station  PlayingStation
     * @param start starting point of the pattern
     * @param pattern pattern to remove
     */
    private void removePattern(PlayingStation station, ArrayList<Integer> start, List<PatternCard> pattern) {
        for (PatternCard patternCard : pattern) {
            ArrayList<Integer> key = new ArrayList<>();
            key.add(start.get(0) + patternCard.getDx());
            key.add(start.get(1) + patternCard.getDy());
            station.getMap().remove(key);
        }
    }
}