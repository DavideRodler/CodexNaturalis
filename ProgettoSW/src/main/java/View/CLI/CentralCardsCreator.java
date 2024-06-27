package View.CLI;

import model.cards.CardGold;
import model.cards.CardResource;
import model.enums.SuitEnum;

import java.util.Arrays;

import static View.CLI.CardMatrixCreator.*;

/**
 * this class creates and prints the centrals card.
 */
public class CentralCardsCreator {
    // in questa classe creo la matrice per le carte centrali

    private String[][] centralCards = new String[6][25];

    public void initializeMatrix(){
        for (String[] matrix : centralCards) {
            Arrays.fill(matrix, " ");
        }
    }

    public void addCentralCardGold(CardGold card, int pos) {
        String[][] tmp = createFrontPlayingCard(card);
        int m = 0;
        int l = 0;
        if(pos == 0) { //aggiungo la carta in alto a sinistra
            for(int i = 0; i < tmp.length; i++) {
                System.arraycopy(tmp[i], 0, centralCards[i], 0, tmp[0].length);
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

    /**
     * this method adds a card resource to centralCards
     * @param card is the card resource to be addes
     * @param pos is the position in which the card is added
     */
    public void addCentralCardRes(CardResource card, int pos) {
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

    /** this method add the decks next to the central cards
     *
     */
    public void addDeckResToBoard(SuitEnum suit){
        //chiamo la add central card res in posizione 4
        int m = 3;
        int l = 18;
        String [][] deckRes = createBackResCardDeck(suit);
        for(int i = 0; i < deckRes.length; i++){
            for(int j = 0; j < deckRes[0].length; j++){
                centralCards[m][l] = deckRes[i][j];
                l++;
            }
            l = 18;
            m++;
        }
    }

    public void addDeckGoldToBoard(SuitEnum suit){
        //chiamo la add central card gold in posizione 5
        int m = 0;
        int l = 18;
        String[][] deckGold = createBackGoldCardDeck(suit);
        for(int i = 0; i < deckGold.length; i++){
            for(int j = 0; j < deckGold[0].length; j++){
                centralCards[m][l] = deckGold[i][j];
                l++;
            }
            l = 18;
            m++;
        }
    }

    /**
     * this method prints the central cards.
     */
    public void printCentral(){
        for(int i = 0; i < centralCards.length; i++) {
            for(int j = 0; j < centralCards[0].length; j++) {
                System.out.print(centralCards[i][j]);
            }
            System.out.println();
        }
    }
}
