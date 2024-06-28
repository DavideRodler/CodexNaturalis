package Messages.ModelUpdates;

import Messages.Message;
import model.cards.CardObjective;

public class CommonObjectivesMessage extends Message {
    private CardObjective firstobjective;
private CardObjective secondobjective;


    public CommonObjectivesMessage(CardObjective firstobjective, CardObjective secondobjective) {
        super("CommonObjectives");
        this.firstobjective = firstobjective;
        this.secondobjective = secondobjective;
    }

    public CardObjective getFirstobjective() {
        return firstobjective;
    }

    public CardObjective getSecondobjective() {
        return secondobjective;
    }
}
