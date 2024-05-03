package View;

import Network.Cli2;
import Network.Client.RmiClient;
import Network.Server.VirtualServer;
import model.cards.Card;
import model.cards.CardGold;
import model.cards.CardPlaying;
import model.cards.CardResource;

import java.util.ArrayList;
import View.CardMatrixCreator;

import static View.CardMatrixCreator.*;

public class HandMatrix {
    private String[][] handMatrix = new String[3][27];

    public String[][] getHandMatrix() {
        return handMatrix;
    }

//    private void addCardToHand(CardPlaying card, int pos) { //pos da 0 a 2
//        //prende una carta e a seconda della pos la aggiunge alla matrice in coordinate diverse
//        //es. la prima carta andrà da 0.0 a 2.10 poi una colonna di spazi e poi si aggiunge la seconda e così via.
//    }

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
            for(int i = 0; i < 3; i++) {
                handMatrix[i][7] = " ";
                handMatrix[i][8] = " ";
            }
        }
        else if(pos == 1){
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 7; j++) {
                    handMatrix[i][j+9] = tmp[i][j];
                }
            }
            for(int i = 0; i < 3; i++) {
                handMatrix[i][16] = " ";
                handMatrix[i][17] = " ";
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
    public void addCardsToHand(ArrayList<CardPlaying> cards) { //cambiare anche hand in Player --> deve essere CardRes non playing
        //itero per ogni carta dentro l'arraylist chiamando la funzione sopra
        //devo fare in modo che restituisca una matrice
        for(int i = 0; i < 3; i++) {
            if(cards.get(i) != null){
                addCardToHand(cards.get(i), i);
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
