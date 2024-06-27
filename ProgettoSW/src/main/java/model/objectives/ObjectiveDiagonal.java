package model.objectives;

import model.PlayingStation;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.objectives.PatternUtils;
import model.enums.SuitEnum;
import model.enums.DirectionEnum;


import java.io.Serializable;
import java.util.*;

public class ObjectiveDiagonal extends PatternUtils {
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
        return diagonalPatternFound(station) * 2;
    }
}