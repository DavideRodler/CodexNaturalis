package model.cards;

import model.cards.face.Face;
import model.enums.SuitEnum;
import model.objectives.Objective;

import java.io.Serializable;

public class CardGold extends CardResource implements Serializable {
    private int costAnimal;
    private int costInsect;
    private int costFungi;
    private int costPlant;
    private Objective objective;

    public CardGold(int ID, Face front, Face back, SuitEnum symbol, Integer points, int costAnimal, int costInsect, int costFungi, int costPlant, Objective objective) {
        super(ID, front, back, symbol, points, objective);
        this.costAnimal = costAnimal;
        this.costInsect = costInsect;
        this.costFungi = costFungi;
        this.costPlant = costPlant;
        this.objective = objective;
    }

    public int getCostAnimal() {
        return costAnimal;
    }

    public int getCostFungi() {
        return costFungi;
    }

    public int getCostInsect() {
        return costInsect;
    }

    public int getCostPlant() {
        return costPlant;
    }

    public Objective getObjective() {
        return objective;
    }

}
