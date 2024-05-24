package View;

import model.cards.CardStarting;

import static View.CardMatrixCreator.*;

/**
 * this class creates and prints the front and back of the starting card
 */
public class StartingCardPrinter {
    
    /**
     * this method prints the starting card's front and back
     * @param cardStarting
     */
    public static void cardStartingPrinter(CardStarting cardStarting){
        String[][] cardFrontAndBack = new String[3][16];
        String[][] front = createFrontPlayingCard(cardStarting);
        String[][] back = createBackPlayingCard(cardStarting);
        int m = 0;
        int l = 0;

        for(int i = 0; i < cardFrontAndBack.length; i++) { //copio front a sx.
            System.arraycopy(front[i], 0, cardFrontAndBack[i], 0, front[i].length);
        }
        for(int i = 0; i < cardFrontAndBack.length; i++) { //inserisco spazi tra le due carte
            for(int j = 7; j < 9; j++) {
                cardFrontAndBack[i][j] =  "";
            }
        }
        for(int i = 0; i < cardFrontAndBack.length; i++) { //copio back a dx
            for(int j = 9; j < 16; j++) {
                cardFrontAndBack[i][j] = back[m][l];
                l++;
            }
            l = 0;
            m++;
        }
        printMatrix(cardFrontAndBack);
    }
    
}
