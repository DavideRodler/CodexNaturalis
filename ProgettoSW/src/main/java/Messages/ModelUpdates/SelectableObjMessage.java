package Messages.ModelUpdates;

import Messages.Message;
import model.cards.CardObjective;

import java.util.ArrayList;

public class SelectableObjMessage  extends Message {
    private ArrayList<CardObjective> selectableObjectives;

    public SelectableObjMessage(ArrayList<CardObjective> selectableObjectives) {
        super("SelectableObj");
        this.selectableObjectives = selectableObjectives;
    }

    public ArrayList<CardObjective> getSelectableObjectives() {
        return selectableObjectives;
    }
}
