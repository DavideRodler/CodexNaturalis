package model.objectives;

import model.PlayingStation;

public class ObjectiveAssign implements Objective {
    public ObjectiveAssign() {
    }

    public int checkObjective(PlayingStation station){
        return 1;
    }

}
