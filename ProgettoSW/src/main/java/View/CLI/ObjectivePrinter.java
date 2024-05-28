package View.CLI;

import model.cards.CardObjective;
import model.objectives.*;

import java.util.Arrays;

import static View.CLI.CardMatrixCreator.*;

public class ObjectivePrinter {
    final int cardHeight = 3;
    final int cardLength = 7;
    String[][] commonObjectives = new String[cardHeight][16];
    String[][] secretObjective = new String[cardHeight][cardLength];

    //Per aggiungere gli obiettivi passo come argomento i due obiettivi. Il primo lo metto a sinistra il secondo a destra

    public ObjectivePrinter() {
        initializeCommonObjectives();
        initializeSecretObjective();
    }

    private void initializeCommonObjectives(){
        for (String[] matrix : commonObjectives) {
            Arrays.fill(matrix, " ");
        }
    }

    private void initializeSecretObjective(){
        for (String[] matrix : secretObjective) {
            Arrays.fill(matrix, " ");
        }
    }
    /**
     * this method prints the two common objectives to be chosen
     * @param obj1 is the first objective
     * @param obj2 is the second objective
     */
    public void printSelectableObjectives(CardObjective obj1, CardObjective obj2) {
        String black = "\033[0;30m";
        String reset = "\033[0m";
        int m = 0;
        int l = 0;
        Objective obj = obj1.getObjective();
        String[][] tmp = getObjectiveCard(obj);
        for(int i = 0; i < commonObjectives.length; i++) { //copio obiettivo in commonObjective.
            System.arraycopy(tmp[i], 0, commonObjectives[i], 0, tmp[i].length);
        }
//        for(int i = 0; i < commonObjectives.length; i++) { //inserisco spazi tra le due carte
//            for(int j = 7; j < 9; j++) {
//                commonObjectives[i][j] =  black + "█" + reset;;
//            }
//        }
        obj = obj2.getObjective();
        tmp = getObjectiveCard(obj);
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

    public void printSecretObjective(CardObjective obj1) {
        Objective obj = obj1.getObjective();
        String[][] tmp = getObjectiveCard(obj);
        for(int i = 0; i < secretObjective.length; i++) { //copio obiettivo in commonObjective.
            System.arraycopy(tmp[i], 0, secretObjective[i], 0, tmp[i].length);
        }
        printMatrix(secretObjective);
    }

    public String[][] getObjectiveCard(Objective obj) {
        String[][] objCard = new String[cardHeight][cardLength];
        if(obj instanceof ObjectiveDiagonal){
            objCard = createObjective((ObjectiveDiagonal) obj);
        } else if (obj instanceof ObjectivePositioning){
            objCard = createObjective((ObjectivePositioning) obj);
        } else if (obj instanceof ObjectiveCountingGold) {
            objCard = createObjective((ObjectiveCountingGold) obj);
        } else if (obj instanceof ObjectiveCountingResource) {
            objCard = createObjective((ObjectiveCountingResource) obj);
        }
        return objCard;
    }

}
