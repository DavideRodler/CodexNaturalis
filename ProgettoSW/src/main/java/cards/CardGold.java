package cards;

import objectives.Objective;

public class CardGold extends CardPlaying {
    private int costAnimal;
    private int costInsect;
    private int costFungi;
    private int costPlant;
    private Objective objective;

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
    public boolean checksPlayable(Card[] table){

    }
}
