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

/**
 * This class represents the objective of the positioning
 */
public class ObjectivePositioning extends PatternUtils{
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
        return positioningPatternFound(station) * 3;
    }
}