package objectives;
import cards.Card;
import enums.Suit;

public class ObjectiveCountingResource extends Objective{
    public Suit Simbol;
    public boolean checkObjective(Card[][] table){ //returns true if objective has been completed
    }

    public Suit getSimbol() {
        return Simbol;
    } //getter
}