package model.cards;

import model.cards.face.Face;
import model.enums.Suit;
import model.objectives.Objective;

public class CardGold extends CardResource {
    private int costAnimal;
    private int costInsect;
    private int costFungi;
    private int costPlant;
    private Objective objective;

    public CardGold(int ID, Face front, Face back, Suit symbol, int points, int costAnimal, int costInsect, int costFungi, int costPlant, Objective objective) {
        super(ID, front, back, symbol, points);
        this.costAnimal = costAnimal;
        this.costInsect = costInsect;
        this.costFungi = costFungi;
        this.costPlant = costPlant;
        this.objective = objective;
        this.pointType = pointType;
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
    //public boolean checkPlayable(Card[][] table){
    //}
}
