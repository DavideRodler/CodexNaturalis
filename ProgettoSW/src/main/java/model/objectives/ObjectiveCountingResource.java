package model.objectives;
import model.PlayingStation;
import model.cards.Card;
import model.cards.CardResource;
import model.enums.Suit;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectiveCountingResource extends Objective{
    public Suit symbol;

    public int checkObjective(PlayingStation station) {
        return switch(symbol){
            case FUNGI: yield station.getCountFungi() / 3;
            case PLANT: yield station.getCountPlant() / 3;
            case ANIMAL: yield station.getCountAnimal() / 3;
            case INSECT: yield station.getCountInsect() / 3;
            case QUILL, MANUSCRIPT, INKWELL, EMPTY, NULL:
                yield -1; //errore
        };
    }

    public Suit getSymbol() {
        return symbol;
    }

    public ObjectiveCountingResource(Suit suit){
        this.symbol = suit;
    }
}