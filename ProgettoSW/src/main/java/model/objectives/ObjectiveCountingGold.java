package model.objectives;

import model.PlayingStation;
import model.cards.*;
import model.enums.SuitEnum;

import java.io.Serializable;


/**
 * this class represents the objective that counts the gold cards, it is used both in the cardObjective to count the
 *
 */
public class ObjectiveCountingGold implements Objective, Points ,Serializable {

  private int countInkwell;
  private int countManuscript;
  private int countQuill;

  public ObjectiveCountingGold(int countInkwell, int countManuscript, int countQuill) {
    this.countInkwell = countInkwell;
    this.countManuscript = countManuscript;
    this.countQuill = countQuill;
  }


  /**
   * @param station is the station where we want to count the points
   * @return the points of the objective
   */
  @Override
  public int countObjectivePoints(PlayingStation station) {
    int points = 0;
    synchronized (this) {
      if ((countInkwell == countManuscript) && (countInkwell == countQuill) && (countInkwell == 1)) {
        points = Math.min(Math.min(station.getCountInkwell(), station.getCountQuill()), station.getCountManuscript());
      } else if ((countQuill != 0) && (countInkwell == 0) && (countManuscript == 0)) {
        points = station.getCountQuill() / countQuill;
      } else if ((countQuill == 0) && (countInkwell != 0) && (countManuscript == 0)) {
        points = station.getCountInkwell() / countInkwell;
      } else if ((countQuill == 0) && (countInkwell == 0) && (countManuscript != 0)) {
        points = station.getCountManuscript() / countManuscript;
      }
    }
    return points;
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


  /**
   * @param station is the station where we want to count the points
   * @param card is the card we want to place(in this case we don't need it)
   * @param x is the position(in this case we don't need it)
   * @param y is the position(in this case we don't need it)
   * @return the points of the objective
   * this function is used to count the points of the objective
   * and also to count the points of a gold card
   */
  @Override
  public int countObjectivePoints(PlayingStation station, CardResource card, Integer x, Integer y) {
    return countObjectivePoints(station);
  }
}
