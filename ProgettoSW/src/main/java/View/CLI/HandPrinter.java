package View.CLI;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;

import java.util.ArrayList;
import java.util.Arrays;

import static View.CLI.CardMatrixCreator.*;

/**
 * This class prints the hand of the player
 */
public class HandPrinter {
    private String[][] handMatrix = new String[3][36];

    public String[][] getHandMatrix() {
        return handMatrix;
    }

    public HandPrinter(){
        initializeMatrix();
    }


    /**
     * this method initializes the hand matrix
     */
    //inizializzo la matrice
    private void initializeMatrix(){
        for (String[] matrix : handMatrix) {
            Arrays.fill(matrix, " ");
        }
    }

    /**
     * this method adds a card to the hand of the player
     * @param card is the card to be added
     * @param pos is the position of the card to be added in the hand
     */
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

    /**
     * this method adds cards to the hand of the player
     * @param cards in the array of cards to be added
     */
    public void addCardsToHand(ArrayList<CardResource> cards) { //cambiare anche hand in Player --> deve essere CardRes non playing
        //itero per ogni carta dentro l'arraylist chiamando la funzione sopra
        //devo fare in modo che restituisca una matrice
        for(int i = 0; i < cards.size(); i++) {
            if(cards.get(i) != null){
                addCardToHand(cards.get(i), i);
            }
        }
    }

    /**
     * this method adds the secret objective to the hand
     * @param cardObj is the secret objective
     */
    public void addObjectiveToHand(CardObjective cardObj){
        ObjectivePrinter objectivePrinter = new ObjectivePrinter();
        String[][] tmp = objectivePrinter.getObjectiveCard(cardObj.getObjective());
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 7; j++) {
                handMatrix[i][j+27] = tmp[i][j];
            }
        }
    }

    /**
     * this method prints the hand
     */
    public void printHandMatrix() {
        for(int i = 0; i < handMatrix.length; i++) {
            for(int j = 0; j < handMatrix[0].length-2; j++) {
                System.out.print(handMatrix[i][j]);
            }
            System.out.println();
        }
    }

}
