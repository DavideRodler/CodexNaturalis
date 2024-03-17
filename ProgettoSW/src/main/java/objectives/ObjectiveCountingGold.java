package objectives;
import cards.Card;
public class ObjectiveCountingGold extends Objective{

    private int countInkwell;
    private int countManuscript;
    private int countQuill;

    public boolean checkObjective(Card[][] table){

    }

    public int getCountInkwell() {
        return countInkwell;
    }

    public int getCountQuill() {
        return countQuill;
    }

    public int getCountManuscript() {
        return countManuscript;
    }
}