package View;

import model.cards.*;

import static View.CardMatrixCreator.*;
import static View.CardMatrixCreator.createFrontPlayingCard;

public class StationMatrix {
    String[][] stationPrint = new String[240][560];
    String[][] SmallStationPrint = new String[160][240];
    //metodo addCardToBoard e addCardsToBoard e infine una stampa
    public void addCardToStation(CardResource card, int i, int j) {
        int m = 0;
        int l = 0;
        String[][] tmp;
        if(card.getPlayingBack()){
            tmp = createBackPlayingCard(card);
        } else{
            tmp = createFrontPlayingCard(card);
        }
        for(int k = 3*i; k < 3*i+3; k++){
            for(int s = 7*j; s < 7*j+7; s++){
                stationPrint[k][s] = tmp[m][l];
                l++;
            }
            l=0;
            m++;
        }
    }
    //una small card è di dimensioni 2x3.
    public void addCardToSmallStation(CardResource card, int i, int j) {
        int m = 0;
        int l = 0;
        String[][] tmp;
        if(card.getPlayingBack()){
            tmp = createBackSmallPlayingCard(card);
        } else{
            tmp = createFrontSmallPlayingCard(card);
        }
        for(int k = 2*i; k < 2*i+2; k++){
            for(int s = 3*j; s < 3*j+3; s++){
                SmallStationPrint[k][s] = tmp[m][l];
                l++;
            }
            l=0;
            m++;
        }
    }

    public void addCardToStation(CardGold card, int i, int j){
        if(card.getPlayingBack()){
            addCardToStation((CardResource) card, i, j);
        } else{
            String[][] tmp = createFrontPlayingCard(card);
            int m = 0;
            int l = 0;
            for(int k = 3*i; k < 3*i+3; k++){
                for(int s = 7*j; s < 7*j+7; s++){
                    stationPrint[k][s] = tmp[m][l];
                    l++;
                }
                l = 0;
                m++;
            }

        }
    }

    public void addCardToSmallStation(CardGold card, int i, int j) {
        if(card.getPlayingBack()){
            addCardToSmallStation((CardResource) card, i, j);
        } else{
            String[][] tmp = createFrontSmallPlayingCard(card);
            int m = 0;
            int l = 0;
            for(int k = 2*i; k < 2*i+2; k++){
                for(int s = 3*j; s < 3*j+3; s++){
                    SmallStationPrint[k][s] = tmp[m][l];
                    l++;
                }
                l = 0;
                m++;
            }

        }
    }

    public void addCardToStation(CardStarting card, int i, int j){
        String[][] tmp;
        int m = 0;
        int l = 0;
        if(card.getPlayingBack()){
            tmp = createBackPlayingCard(card);
        } else{
            tmp = createFrontPlayingCard(card);
        }
        for(int k = 3*i; k < 3*i+3; k++){
            for(int s = 7*j; s < 7*j+7; s++){
                stationPrint[k][s] = tmp[m][l];
                l++;
            }
            l = 0;
            m++;
        }
    }

    public void addCardToSmallStation(CardStarting card, int i, int j) {
        String[][] tmp;
        int m = 0;
        int l = 0;
        if(card.getPlayingBack()){
            tmp = creteBackSmallPlayingCard(card);
        } else{
            tmp = createFrontSmallPlayingCard(card);
        }
        for(int k = 2*i; k < 2*i+2; k++){
            for(int s = 3*j; s < 3*j+3; s++){
                SmallStationPrint[k][s] = tmp[m][l];
                l++;
            }
            l = 0;
            m++;
        }
    }
//    dovrò stampare una matrice di dimensione max+1 x max+1. Non c'è bisogno di scorrere tutta la station, ma solo
//    public void addCardsToBoard(CardPlaying[][] station, int max){
//        for(int i = 0; i < station.length; i++){
//            for(int j = 0; j < station[0].length; j++){
//                if(station[i][j] != null){
//                    if(station[i][j].getPlayingBack()){
//                        if(i == 40 && j == 40){ //controllo se è la carta iniziale
//                            addCardToBoard((CardStarting) station[i][j], i, j);
//
//                        } else { // carta risorsa o gold
//                            //chiamo addCardToBoard(CardRes)
//                            addCardToBoard((CardResource) station[i][j], i, j);
//                        }
//                    } else{
//                        if(i == 40 && j == 40){ // controllo se è la carta iniziale
//                            addCardToBoard((CardStarting) station[i][j], i, j);
//                        } else{
//                            if(station[i][j] instanceof CardGold){
//                                addCardToBoard((CardGold) station[i][j], i, j);
//                            } else{
//                                addCardToBoard((CardResource) station[i][j], i, j);
//                            }
//                        }
//                    }
//                } else{
//                    String black = "\033[0;30m";
//                    String reset = "\033[0m";
//                    for(int k = 3*i; k < 3*i+3; k++){
//                        for(int s = 7*j; s < 7*j+7; s++){
//                            stationPrint[k][s] = black + "█" + reset;
//                        }
//                    }
//                }
//            }
//        }
//    }
    public void printStation(int max){//120, 280 sono le coordinate della carta iniziale in stationPrint
        for(int i = 120-3*max; i < 123+3*max; i++){
            for(int j = 280-7*max; j < 287+7*max; j++){
                System.out.print(stationPrint[i][j]);
            }
            System.out.println();
        }
    }

    public void printSmallStation(int max){ //80, 120 sono le coordinare della carta iniziale in smallStation
        for(int i = 80-2*max; i < 82+2*max; i++){
            for(int j = 120-3*max; j < 123+3*max; j++){
                System.out.print(SmallStationPrint[i][j]);
            }
            System.out.println();
        }
    }

    public void addCardsToStation(CardPlaying[][] station, int max){
        for(int i = 40-max; i < 41+max; i++){
            for(int j = 40-max; j < 41+max; j++){
                if(station[i][j] != null){
                    if(station[i][j].getPlayingBack()){
                        if(i == 40 && j == 40){ //controllo se è la carta iniziale
                            addCardToStation((CardStarting) station[i][j], i, j);

                        } else { // carta risorsa o gold
                            //chiamo addCardToStation(CardRes)
                            addCardToStation((CardResource) station[i][j], i, j);
                        }
                    } else{
                        if(i == 40 && j == 40){ // controllo se è la carta iniziale
                            addCardToStation((CardStarting) station[i][j], i, j);
                        } else{
                            if(station[i][j] instanceof CardGold){
                                addCardToStation((CardGold) station[i][j], i, j);
                            } else{
                                addCardToStation((CardResource) station[i][j], i, j);
                            }
                        }
                    }
                } else{
                    String black = "\033[0;30m";
                    String reset = "\033[0m";
                    for(int k = 3*i; k < 3*i+3; k++){
                        for(int s = 7*j; s < 7*j+7; s++){
                            stationPrint[k][s] = black + "█" + reset;
                        }
                    }
                }
            }
        }
    }

    public void addCardsToSmallStation(CardPlaying[][] SmallStation, int max){
        for(int i = 40-max; i < 41+max; i++){
            for(int j = 40-max; j < 41+max; j++){
                if(SmallStation[i][j] != null){
                    if(SmallStation[i][j].getPlayingBack()){
                        if(i == 40 && j == 40){ //controllo se è la carta iniziale
                            addCardToSmallStation((CardStarting) SmallStation[i][j], i, j);

                        } else { // carta risorsa o gold
                            //chiamo addCardToStation(CardRes)
                            addCardToSmallStation((CardResource) SmallStation[i][j], i, j);
                        }
                    } else{
                        if(i == 40 && j == 40){ // controllo se è la carta iniziale
                            addCardToSmallStation((CardStarting) SmallStation[i][j], i, j);
                        } else{
                            if(SmallStation[i][j] instanceof CardGold){
                                addCardToSmallStation((CardGold) SmallStation[i][j], i, j);
                            } else{
                                addCardToSmallStation((CardResource) SmallStation[i][j], i, j);
                            }
                        }
                    }
                } else{
                    String black = "\033[0;30m";
                    String reset = "\033[0m";
                    for(int k = 2*i; k < 2*i+2; k++){
                        for(int s = 3*j; s < 3*j+3; s++){
                            SmallStationPrint[k][s] =" ";
                        }
                    }
                }
            }
        }
    }
}
