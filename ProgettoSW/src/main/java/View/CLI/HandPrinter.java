package View.CLI;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;

import java.util.ArrayList;
import java.util.Arrays;

import static View.CLI.CardMatrixCreator.*;

/**
 * Oltre alla mano c'è anche l'obiettivo segreto.
 */
public class HandPrinter {
    private String[][] handMatrix = new String[3][36];

    public String[][] getHandMatrix() {
        return handMatrix;
    }

    public HandPrinter(){
        initializeMatrix();
    }

//    private void addCardToHand(CardPlaying card, int pos) { //pos da 0 a 2
//        //prende una carta e a seconda della pos la aggiunge alla matrice in coordinate diverse
//        //es. la prima carta andrà da 0.0 a 2.10 poi una colonna di spazi e poi si aggiunge la seconda e così via.
//    }

    //inizializzo la matrice
    private void initializeMatrix(){
        for (String[] matrix : handMatrix) {
            Arrays.fill(matrix, " ");
        }
    }

    private void addCardToHand(CardPlaying card, int pos) {
        String[][] tmp = new String[3][7];
        if(card instanceof CardGold) {
            tmp = createFrontPlayingCard((CardGold) card);
        }
        else if(card instanceof CardResource){
            tmp = createFrontPlayingCard((CardResource) card);
        }
        if(pos == 0){
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 7; j++) {
                    handMatrix[i][j] = tmp[i][j];
                }
            }
        }
        else if(pos == 1){
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 7; j++) {
                    handMatrix[i][j+9] = tmp[i][j];
                }
            }
        } else if(pos == 2){
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 7; j++) {
                    handMatrix[i][j+18] = tmp[i][j];
                }
            }
        }
    }

//    private void addCardToHand(CardGold card, int pos) {
//        String[][] tmp = createFrontPlayingCard(card);
//        if(pos == 0){
//            for(int i = 0; i < 3; i++) {
//                for(int j = 0; j < 7; j++) {
//                    handMatrix[i][j] = tmp[i][j];
//                }
//            }
//            for(int i = 0; i < 3; i++) {
//                handMatrix[i][7] = " ";
//                handMatrix[i][8] = " ";
//            }
//        }
//        else if(pos == 1){
//            for(int i = 0; i < 3; i++) {
//                for(int j = 0; j < 7; j++) {
//                    handMatrix[i][j+9] = tmp[i][j];
//                }
//            }
//            for(int i = 0; i < 3; i++) {
//                handMatrix[i][16] = " ";
//                handMatrix[i][17] = " ";
//            }
//        } else if(pos == 2){
//            for(int i = 0; i < 3; i++) {
//                for(int j = 0; j < 7; j++) {
//                    handMatrix[i][j+18] = tmp[i][j];
//                }
//            }
//        }
//    }
    public void addCardsToHand(ArrayList<CardResource> cards) { //cambiare anche hand in Player --> deve essere CardRes non playing
        //itero per ogni carta dentro l'arraylist chiamando la funzione sopra
        //devo fare in modo che restituisca una matrice
        for(int i = 0; i < cards.size(); i++) {
            if(cards.get(i) != null){
                addCardToHand(cards.get(i), i);
            }
        }
    }

    public void addObjectiveToHand(CardObjective cardObj){
        ObjectivePrinter objectivePrinter = new ObjectivePrinter();
        String[][] tmp = objectivePrinter.getObjectiveCard(cardObj.getObjective());
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 7; j++) {
                handMatrix[i][j+27] = tmp[i][j];
            }
        }
    }

    public void printHandMatrix() {
        for(int i = 0; i < handMatrix.length; i++) {
            for(int j = 0; j < handMatrix[0].length-2; j++) {
                System.out.print(handMatrix[i][j]);
            }
            System.out.println();
        }
    }

    //fare metodo
}
