package model.objectives;
import model.PlayingStation;
import model.enums.SuitEnum;

import java.io.Serializable;

public class ObjectiveCountingResource implements Objective, Serializable {
    public SuitEnum symbol;

    @Override
    public int countObjectivePoints(PlayingStation station) {
        return switch(symbol){
            case FUNGI: yield (station.getCountFungi() / 3)*2;
            case PLANT: yield (station.getCountPlant() / 3)*2;
            case ANIMAL: yield (station.getCountAnimal() / 3)*2;
            case INSECT: yield (station.getCountInsect() / 3)*2;
            case QUILL, MANUSCRIPT, INKWELL, EMPTY, NULL:
                throw new RuntimeException("not a possible Objective");
        };
    }



    public SuitEnum getSymbol() {
        return symbol;
    }

    public ObjectiveCountingResource(SuitEnum suit){
        this.symbol = suit;
    }
}