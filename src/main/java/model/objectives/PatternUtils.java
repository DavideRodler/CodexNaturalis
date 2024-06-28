package model.objectives;

import model.PlayingStation;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.enums.SuitEnum;

import java.io.Serializable;
import java.util.*;

public abstract class PatternUtils implements Objective, Serializable {


    /**
     * Create positioning pattern and counting the number of positioning patterns found
     * @param station PlayingStation
     * @return the number of patterns found
     */
    public static int positioningPatternFound(PlayingStation station) {

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
        return patternCounter(station, patterns);
    }




    /**
     * Create diagonal pattern and counting the number of diagonal patterns found
     * @param station PlayingStation
     * @return the number of patterns found
     */
    public static int diagonalPatternFound(PlayingStation station){

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

        return patternCounter(station, patterns);
    }





    /**
     * Counting the number of patterns found
     * @param station PlayingStation
     * @param patterns list of patterns
     * @return the number of patterns found
     */
    public static int patternCounter(PlayingStation station, List<List<PatternCard>> patterns) {
        int patternNumber = 0;

        HashMap<ArrayList<Integer>, CardPlaying> patternMap = station.getMap();
        patternMap.remove(new ArrayList<>(Arrays.asList(40, 40)));
        Set<ArrayList<Integer>> keys = new HashSet<>(patternMap.keySet());
        for (ArrayList<Integer> key : keys) {
            for (List<PatternCard> pattern : patterns) {
                if (matchesPattern(station, key, pattern)) {
                    patternNumber++;
                    removePattern(station, key, pattern);
                }
            }
        }

        return patternNumber;
    }





    /**
     * Checking if the pattern corrisponds to the PlayingStation
     * @param station  PlayingStation
     * @param start starting point of the pattern
     * @param pattern what pattern I'm looking for
     * @return true if there is a corrispondence, false otherwise
     */
    public static boolean matchesPattern(PlayingStation station, ArrayList<Integer> start, List<PatternCard> pattern) {
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
    public static void removePattern(PlayingStation station, ArrayList<Integer> start, List<PatternCard> pattern) {
        for (PatternCard patternCard : pattern) {
            ArrayList<Integer> key = new ArrayList<>();
            key.add(start.get(0) + patternCard.getDx());
            key.add(start.get(1) + patternCard.getDy());
            station.getMap().remove(key);
        }
    }


}
