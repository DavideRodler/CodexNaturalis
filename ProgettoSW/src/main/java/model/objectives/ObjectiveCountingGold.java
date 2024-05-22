package model.objectives;

import model.PlayingStation;
import model.cards.*;
import model.enums.SuitEnum;

import java.io.Serializable;

public class ObjectiveCountingGold implements Objective,Points ,Serializable {

  private int countInkwell;
  private int countManuscript;
  private int countQuill;

  public ObjectiveCountingGold(int countInkwell, int countManuscript, int countQuill) {
    this.countInkwell = countInkwell;
    this.countManuscript = countManuscript;
    this.countQuill = countQuill;
  }


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

  @Override
  public int countObjectivePoints(PlayingStation station, CardResource card, Integer x, Integer y) {
    return countObjectivePoints(station,null,null,null);
  }
}
