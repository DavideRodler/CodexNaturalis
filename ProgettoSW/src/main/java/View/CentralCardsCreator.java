package View;

import model.cards.CardResource;

import static View.CardMatrixCreator.createFrontPlayingCard;

public class CentralCardsCreator {
    // in questa classe creo la matrice per le carte centrali
    String[][] centralCards = new String[6][16];
    public void     addCentralCard(CardResource card, int pos) {
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
}
