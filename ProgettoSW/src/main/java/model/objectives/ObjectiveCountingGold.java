package model.objectives;


import model.cards.Card;

import java.lang.reflect.Array;

public class ObjectiveCountingGold extends Objective{

   private int countInkwell;
   private int countManuscript;
   private int countQuill;

public boolean checkObjective(HashMap<Array<Integer>, Card> table, Card card){
    Boolean flag;
if (card.getCountInkwell().equal()) {

}
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

  public ObjectiveCountingGold(int inkwell, int manuscript, int quill){ //constructor
    this.countInkwell = inkwell;
    this.countManuscript = manuscript;
    this.countQuill = quill;
  }
}
