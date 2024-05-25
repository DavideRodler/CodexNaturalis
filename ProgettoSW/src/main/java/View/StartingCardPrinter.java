package View;

import model.cards.CardStarting;

import java.util.Arrays;

import static View.CardMatrixCreator.*;

/**
 * this class creates and prints the front and back of the starting card
 */
public class StartingCardPrinter {
    String[][] cardFrontAndBack = new String[3][16];

    public StartingCardPrinter() {
        initializeStartingCardPrinter();
    }

    private void initializeStartingCardPrinter(){
        for (String[] matrix : cardFrontAndBack) {
            Arrays.fill(matrix, " ");
        }
    }
    /**
     * this method prints the starting card's front and back
     * @param cardStarting
     */
    public void cardStartingPrinter(CardStarting cardStarting){
        String[][] front = createFrontPlayingCard(cardStarting);
        String[][] back = createBackPlayingCard(cardStarting);
        int m = 0;
        int l = 0;

        for(int i = 0; i < cardFrontAndBack.length; i++) { //copio front a sx.
            System.arraycopy(front[i], 0, cardFrontAndBack[i], 0, front[i].length);
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
