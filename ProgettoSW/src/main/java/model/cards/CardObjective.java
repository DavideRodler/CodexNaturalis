package model.cards;

import model.objectives.Objective;

public class CardObjective extends Card{
    private int points;
    private Objective objective;

    public CardObjective(int ID, int points, Objective objective) {
        super(ID);
        this.objective = objective;
        this.points = points;
    }

    public int getPoints() {
        return points;
    } //getter
}
