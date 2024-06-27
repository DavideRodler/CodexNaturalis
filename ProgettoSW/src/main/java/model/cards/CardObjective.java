package model.cards;

import model.objectives.Objective;

import java.io.Serializable;

/**
 * This class represents an objective card.
 * It has a number of points and an objective.
 */
public class CardObjective extends Card implements Serializable {
  private int points;
  private Objective objective;

  public CardObjective(int ID, int points, Objective objective) {
    super(ID);
    this.objective = objective;
    this.points = points;
  }

  public Objective getObjective() {
    return objective;
  }

  public int getPoints() {
    return points;
  }
}
