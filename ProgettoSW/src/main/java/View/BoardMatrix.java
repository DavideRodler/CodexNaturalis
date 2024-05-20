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

    CentralCardsCreator centralCardsCreator = new CentralCardsCreator();

    public void printCentralCard(CardResource card, int pos) {
        String[][] tmp = createFrontPlayingCard(card);
        int m = 0;
        int l = 0;
        if(pos == 0) { //aggiungo la carta in alto a sinistra
            for(int i = 0; i < tmp.length; i++) {
                System.arraycopy(tmp[i], 0, centralCards[i], 0, tmp[0].length);
            }
            for(int i = 0; i < 3; i++) { //aggiungo spazi tra carta in alto a destra e in alto a sinistra
                centralCards[i][7] = " ";
                centralCards[i][8] = " ";
            }
        } else if (pos == 1) { //aggiungo la carta in alto a destra
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 7; j++) {
                    centralCards[i][j+9] = tmp[i][j];
                }
            }

        } else if (pos == 2) {//in basso a sinistra
            for(int i = 3; i < 6; i++) {
                for(int j = 0; j < 7; j++) {
                    centralCards[i][j] = tmp[m][l];
                    l++;
                }
                l = 0;
                m++;
            }
            for(int i = 3; i < 6; i++) { //aggiungo spazi tra carta in basso a destra e in basso a sinistra.
                centralCards[i][7] = " ";
                centralCards[i][8] = " ";
            }
        } else if(pos == 3) { //in basso a destra.
            for(int i = 3; i < 6; i++) {
                for(int j = 0; j < 7; j++) {
                    centralCards[i][j+9] = tmp[m][l];
                    l++;
                }
                l = 0;
                m++;
            }
        }
    }

    public void printCentral(){
        for(int i = 0; i < centralCards.length; i++) {
            for(int j = 0; j < centralCards[0].length; j++) {
                System.out.print(centralCards[i][j]);
            }
            System.out.println();
        }
    }

//    public void addCentralCard(CardGold card, int pos) { teoricamente inutile.
//        String[][] tmp = new String[3][7];
//    }

}
