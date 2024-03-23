package model.objectives;


import model.cards.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ObjectiveCountingGold extends Objective{

   private int countInkwell;
   private int countManuscript;
   private int countQuill;

   public int checkObjective(HashMap<ArrayList<Integer>, Card> table, Card card )
    int points = 0;
    Boolean flag = true;
       if (!(card.getCountManuscript() == 0)) {
           if(table.getCountManuscript() >= card.getCountManuscript()){
               points = points + (table.getCountManuscript() / card.getCountManuscript());
           }
           else{
               flag = false;
           }
       }
       if (!(card.getCountQuill() == 0)) {
           if(table.getCountQuill() >= card.getCountQuill()){
               points = points + (table.getCountQuill() / card.getCountQuill());
           }
           else{
               flag = false;
           }
       }
       if (!(card.getCountInkwell() == 0)) {
           if(table.getCountInkwell() >= card.getCountInkwell()){
               points = points + (table.getCountInkwell() / card.getCountInkwell());
           }
           else{
               flag = false;
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

  public ObjectiveCountingGold(int inkwell, int manuscript, int quill){ //constructor
    this.countInkwell = inkwell;
    this.countManuscript = manuscript;
    this.countQuill = quill;
  }
}
