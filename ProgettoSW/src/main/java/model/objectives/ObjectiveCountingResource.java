package model.objectives;
import model.enums.Suit;

public class ObjectiveCountingResource extends Objective{
    public Suit Simbol;

    ///public boolean checkObjective(Card[][] table){

    //}

    public Suit getSimbol() {
        return Simbol;
    }

    public ObjectiveCountingResource(Suit suit){
        this.Simbol = suit;
    }
}