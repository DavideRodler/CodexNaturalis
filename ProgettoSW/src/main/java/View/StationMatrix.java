package View;

import model.cards.*;
import model.client.ClientBoard;
import model.client.ReductPlayer;

import java.util.Arrays;

import static View.CardMatrixCreator.*;
import static View.CardMatrixCreator.createFrontPlayingCard;

public class StationMatrix {
    private String[][] stationPrint = new String[242][561]; // aggiunte due righe e una colonna per le coordinate, prima era 240 e 560

    private final String black = "\033[0;30m";
    private final String reset = "\033[0m";

    public StationMatrix() {
        initializeStationPrint();
    }

    /**
     * this method initializes the matrix stationPrint
     */
    public void initializeStationPrint(){
        for (String[] strings : stationPrint) {
            Arrays.fill(strings, black + " " + reset);
        }
    }

    //metodo addCardToBoard e addCardsToBoard e infine una stampa
    /**
     * this method adds a card resource to the station. distanceXFromStarting and distanceYFromStarting are the distance, respectively form the x and y coordinates from the starting card
     * also this method checks if a corner from the card that is added is covered and in that caso doesn't add to the station that corner.
     * @param card is the cards resource to be added
     * @param i is the row coordinate
     * @param j is the column coordinate
     */
    public void addCardToStation(CardResource card, int i, int j) {
        int m = 0;
        int l = 0;
        boolean[] cornerCovered;
        int distanceXFromStarting = 40 - i;
        int distanceYFromStarting = 40 - j;
        String[][] tmp;
        if(card.getPlayingBack()){
            tmp = createBackPlayingCard(card);
            cornerCovered = checkCornerCoveredPlayedBack(card);
        } else{
            tmp = createFrontPlayingCard(card);
            cornerCovered = checkCornerCovered(card);
        }
        for(int k = 3*i + distanceXFromStarting; k < 3*i+3+distanceXFromStarting; k++){
            for(int s = 7*j+distanceYFromStarting; s < 7*j+7+distanceYFromStarting; s++){
                if(m == 0 && l == 0 && cornerCovered[0]) { //controllo se angolo in alto a sx è coperto
                    stationPrint[k][s] = stationPrint[k][s];
                } else if(m == 0 && l == 6 && cornerCovered[1]) {
                    stationPrint[k][s] = stationPrint[k][s];
                } else if(m == 2 && l == 0 && cornerCovered[2]){
                    stationPrint[k][s] = stationPrint[k][s];
                } else if(m == 2 && l == 6 && cornerCovered[3]) {
                    stationPrint[k][s] = stationPrint[k][s];
                } else{
                    stationPrint[k][s] = tmp[m][l];
                }
                l++;
            }
            l=0;
            m++;
        }
    }
    //una small card è di dimensioni 2x3.

    /**
     * this method adds a card gold to the station. distanceXFromStarting and distanceYFromStarting are the distance, respectively form the x and y coordinates from the starting card
     * also this method checks if a corner from the card that is added is covered and in that caso doesn't add to the station that corner.
     * @param card is the gold cards to be added
     * @param i is the row coordinate
     * @param j is the column coordinate
     */
    public void addCardToStation(CardGold card, int i, int j){
        if(card.getPlayingBack()){
            addCardToStation((CardResource) card, i, j);
        } else{
            String[][] tmp = createFrontPlayingCard(card);
            boolean[] cornerCovered = checkCornerCovered(card);
            int m = 0;
            int l = 0;
            int distanceXFromStarting = 40 - i;
            int distanceYFromStarting = 40 - j;
            for(int k = 3*i + distanceXFromStarting; k < 3*i+3+distanceXFromStarting; k++){
                for(int s = 7*j+distanceYFromStarting; s < 7*j+7+distanceYFromStarting; s++){
                    if(m == 0 && l == 0 && cornerCovered[0]) { //controllo se angolo in alto a sx è coperto
                        stationPrint[k][s] = stationPrint[k][s];
                    } else if(m == 0 && l == 6 && cornerCovered[1]) {
                        stationPrint[k][s] = stationPrint[k][s];
                    } else if(m == 2 && l == 0 && cornerCovered[2]){
                        stationPrint[k][s] = stationPrint[k][s];
                    } else if(m == 2 && l == 6 && cornerCovered[3]) {
                        stationPrint[k][s] = stationPrint[k][s];
                    } else{
                        stationPrint[k][s] = tmp[m][l];
                    }
                    l++;
                }
                l = 0;
                m++;
            }

        }
    }

    /**
     * this method adds a starting card to the station.
     * @param card is the starting card to be added
     * @param i is the row of the card
     * @param j is the column of the card
     */
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

    /**
     * this method prints the station of a player distant ato most by max from the starting card. 120 to 122 and 280 to 286 is the position of the starting card.
     * @param max is the distance between the starting card and the furthest card in the station
     */
    public void printStation(int max){//121, 283 sono le coordinate della carta iniziale in stationPrint
        int x;
        int y;
        int coordMax;

        //addCoordinatesToMatrix(max);
        for(int i = 120-3*max; i < 123+3*max; i++){
            for(int j = 280-7*max; j < 287+7*max; j++){
                    System.out.print(stationPrint[i][j]);


            }
            System.out.println();
        }
    }


    /**
     * this method adds the cards to the station matrix, so that they can be printed
     * @param station is a matrix of cards
     * @param max is the distance between the starting card and the furthest card in the station
     */
    public void addCardsToStation(CardPlaying[][] station, int max){
        for(int i = 40-max; i < 41+max; i++){
            for(int j = 40-max; j < 41+max; j++){
                if(station[i][j] != null) {
                    if (station[i][j].getPlayingBack()) {
                        if (i == 40 && j == 40) { //controllo se è la carta iniziale
                            addCardToStation((CardStarting) station[i][j], i, j);

                        } else { // carta risorsa o gold
                            //chiamo addCardToStation(CardRes)
                            addCardToStation((CardResource) station[i][j], i, j);
                        }
                    } else {
                        if (i == 40 && j == 40) { // controllo se è la carta iniziale
                            addCardToStation((CardStarting) station[i][j], i, j);
                        } else {
                            if (station[i][j] instanceof CardGold) {
                                addCardToStation((CardGold) station[i][j], i, j);
                            } else {
                                addCardToStation((CardResource) station[i][j], i, j);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * this method checks whether corners of the gold card are covered or not
     * @param card is the card that needs to be checked
     * @return is an array where the first element says if the left up corner is covered, then the right up, then the left down and the right down
     */
    private boolean[] checkCornerCovered(CardResource card){
        boolean[] cornerCovered = new boolean[4];
        if(card.getFront().getUpLeft().isCovered()){
            cornerCovered[0] = true;
        }
        if(card.getFront().getUpRight().isCovered()){
            cornerCovered[1] = true;
        }
        if(card.getFront().getDownLeft().isCovered()){
            cornerCovered[2] = true;
        }
        if(card.getFront().getDownRight().isCovered()){
            cornerCovered[3] = true;
        }
        return cornerCovered;
    }

    /**
     * this method checks whether corners of the resource card are covered or not
     * @param card is the card that needs to be checked
     * @return is an array where the first element says if the left up corner is covered, then the right up, then the left down and the right down
     */
    private boolean[] checkCornerCoveredPlayedBack(CardResource card) {
        boolean[] cornerCovered = new boolean[4];
        if(card.getBack().getUpLeft().isCovered()){
            cornerCovered[0] = true;
        }
        if(card.getBack().getUpRight().isCovered()){
            cornerCovered[1] = true;
        }
        if(card.getBack().getDownLeft().isCovered()){
            cornerCovered[2] = true;
        }
        if(card.getBack().getDownRight().isCovered()){
            cornerCovered[3] = true;
        }
        return cornerCovered;
    }

    /**
     * this method prints the resources of station of the player
     * @param fungi is the counter of fungis in the station
     * @param plant is the counter of plants in the station
     * @param animal is the counter of animals in the station
     * @param insect is the counter of insects in the station
     * @param quill is the counter of quills in the station
     * @param manuscript is the counter of manuscripts in the station
     * @param inkwell is the counter of inkwells in the station
     */
    public void printResources (int fungi, int plant, int animal, int insect, int quill, int manuscript, int inkwell) {
        System.out.println("You have: "+fungi+"of fungi, "+plant+"of plant, "+animal+"of animal, "+insect+"of insect, "+quill+"of quill, "+manuscript+"of manuscript, "+inkwell+"of inkwell");
    }

    /**
     * this method prints the point of the players
     * @param clientBoard is the board of the client needed to get the points.
     */
    public void printPoints(ClientBoard clientBoard){ //TODO: togliere obiettivi degli altri giocatori e aggiungere il colore
        int clientPoints = clientBoard.getMyplayer().getPoints();
        System.out.println("You have: " + clientPoints + " points");
        for(ReductPlayer reductPlayer : clientBoard.getOtherplayers()){
            System.out.println(reductPlayer.getNickname() + "has " + reductPlayer.getPoints() + " points");
        }
    }

    /**
     * adding coordinates to the edges of stationPrint.
     * 3 is the height of a single card, 7 is the length
     * @param max is the maximum distance between the starting card, positioned in 40, 40 and a furthest playing card.
     */
//    public void addCoordinatesToMatrix(int max){
//        addRowsCoordinates(max);
//        addColumnsCoordinates(max);
//        int x = 40 - max; //coordinata x di partenza
//        int y = 40 - max; //coordinata y di partenza
//         // è l'altezza di una singola carta
//        for(int i = 120-3*max; i < 123*max; i+=3){
//            for(int j = 280-7*max; j < 287+7*max; j+=7){
//                if(i == 120-3*max){ //aggiungo coordinate laterali ogni due posti
//                    stationPrint[120-2*max-2][j] = String.valueOf(x/10); //scrivo la prima cifra
//                    stationPrint[120-2*max-1][j] = String.valueOf(x%10); //sotto scrivo la seconda cifra
//                    x++;
//                }
//                if(j == 280-7*max){
//                    stationPrint[i][280-6*max-2] = String.valueOf(y);
//                    stationPrint[i][280-6*max-1] = "";
//                }
//            }
//        }
//    }

    private void addRowsCoordinates(int max) {
        int coord = 40 - max;
        int rowMin = (stationPrint.length/2) - 3*max;
        int rowMax = (stationPrint.length/2) - 3*max + 1;
        int colMin = (stationPrint[0].length/2) - 3 -7*max;
        int colMax = (stationPrint[0].length/2) -7*max - 2; // non devo scorrere per tutte le colonne!! Basta solo la prima!
        for(int i = rowMin; i < rowMax; i++){
            for(int j = colMin; j < colMax; j++){
                stationPrint[i][j] = String.valueOf(coord);
                coord++;
            }
        }
    }

    private void addColumnsCoordinates(int max){

    }
}
