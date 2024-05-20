package Socket.Messages;

import model.cards.CardObjective;

public class ChooseObjectives extends Message {
    private CardObjective firstobject;
    private CardObjective secondobject;

    public ChooseObjectives( CardObjective firstobject, CardObjective secondobject) {
        super("ChooseObjective");
        this.firstobject = firstobject;
        this.secondobject = secondobject;
    }

    public CardObjective getFirstobject() {
        return firstobject;
    }

    public CardObjective getSecondobject() {
        return secondobject;
    }
}
