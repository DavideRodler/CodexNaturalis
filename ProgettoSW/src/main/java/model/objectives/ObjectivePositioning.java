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
     * Counting of points made with this objective, checking how many times the specific positioning has been completed
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