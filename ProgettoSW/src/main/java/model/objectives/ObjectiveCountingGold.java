package model.objectives;

import model.PlayingStation;
import model.cards.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectiveCountingGold implements Objective {

  private int countInkwell;
  private int countManuscript;
  private int countQuill;

  public ObjectiveCountingGold(int countInkwell, int countManuscript, int countQuill) {
    this.countInkwell = countInkwell;
    this.countManuscript = countManuscript;
    this.countQuill = countQuill;
  }

  public int checkObjective(PlayingStation station, CardRe) {
    int points = 0;
    if((countInkwell == countManuscript)&&(countInkwell == countQuill)&&(countInkwell == 1)){
      points = Math.min(Math.min(station.getCountInkwell(), station.getCountQuill()), station.getCountManuscript());
    }
    else if((countQuill != 0)&&(countInkwell == 0)&&(countManuscript==0)){
      points = station.getCountQuill() / countQuill;
    }
    else if ((countQuill == 0)&&(countInkwell != 0)&&(countManuscript==0)) {
      points = station.getCountInkwell() / countInkwell;
    }
    else if ((countQuill == 0)&&(countInkwell == 0)&&(countManuscript!=0)){
      points = station.getCountManuscript() / countManuscript;
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
}
