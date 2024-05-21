package View;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.objectives.*;

import static View.CardMatrixCreator.*;

public class BoardMatrix {
    String[][] centralCards = new String[6][16];
    String[][] commonObjectives = new String[3][16];

    //Per aggiungere gli obiettivi passo come argomento i due obiettivi. Il primo lo metto a sinistra il secondo a destra

    /**
     * this method prints the two common objectives
     * @param obj1 is the first objective
     * @param obj2 is the second objective
     */
    public void printCommonObjectives(CardObjective obj1, CardObjective obj2) {
        String black = "\033[0;30m";
        String reset = "\033[0m";
        int m = 0;
        int l = 0;
        Objective obj = obj1.getObjective();
        String[][] tmp = new String[3][7];
        if(obj instanceof ObjectiveDiagonal){
            tmp = createObjective((ObjectiveDiagonal) obj);
        } else if (obj instanceof ObjectivePositioning){
            tmp = createObjective((ObjectivePositioning) obj);
        } else if (obj instanceof ObjectiveCountingGold) {
            tmp = createObjective((ObjectiveCountingGold) obj);
        } else if (obj instanceof ObjectiveCountingResource) {
            tmp = createObjective((ObjectiveCountingResource) obj);
        }
        for(int i = 0; i < commonObjectives.length; i++) { //copio obiettivo in commonObjective.
            System.arraycopy(tmp[i], 0, commonObjectives[i], 0, tmp[i].length);
        }
        for(int i = 0; i < commonObjectives.length; i++) { //inserisco spazi tra le due carte
            for(int j = 7; j < 9; j++) {
                commonObjectives[i][j] =  black + "█" + reset;;
            }
        }
        obj = obj2.getObjective();
        if(obj instanceof ObjectiveDiagonal){
            tmp = createObjective((ObjectiveDiagonal) obj);
        } else if (obj instanceof ObjectivePositioning){
            tmp = createObjective((ObjectivePositioning) obj);
        } else if (obj instanceof ObjectiveCountingGold) {
            tmp = createObjective((ObjectiveCountingGold) obj);
        } else if (obj instanceof ObjectiveCountingResource) {
            tmp = createObjective((ObjectiveCountingResource) obj);
        }
        for(int i = 0; i < commonObjectives.length; i++) { //copio secondo obiettivo in commonObjective.
            for(int j = 9; j < 16; j++) {
                commonObjectives[i][j] = tmp[m][l];
                l++;
            }
            l = 0;
            m++;
        }
        printMatrix(commonObjectives);
    }

}
