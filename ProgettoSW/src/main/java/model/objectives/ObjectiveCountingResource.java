package model.objectives;
import model.PlayingStation;
import model.enums.SuitEnum;

import java.io.Serializable;

public class ObjectiveCountingResource implements Objective, Serializable {
    public SuitEnum symbol;

    @Override
    public int countObjectivePoints(PlayingStation station) {
        return switch(symbol){
            case FUNGI: yield station.getCountSuit(SuitEnum.FUNGI) / 3;
            case PLANT: yield station.getCountSuit(SuitEnum.PLANT) / 3;
            case ANIMAL: yield station.getCountSuit(SuitEnum.ANIMAL) / 3;
            case INSECT: yield station.getCountSuit(SuitEnum.INSECT) / 3;
            case QUILL, MANUSCRIPT, INKWELL, EMPTY, NULL:
                yield -1; //errore
        };
    }



    public SuitEnum getSymbol() {
        return symbol;
    }

    public ObjectiveCountingResource(SuitEnum suit){
        this.symbol = suit;
    }
}