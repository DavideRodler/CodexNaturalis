package View;

import model.cards.*;
import model.client.ClientBoard;
import model.client.ReductPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static View.CardMatrixCreator.*;
import static View.CardMatrixCreator.createFrontPlayingCard;

public class StationMatrix {
    private String[][] stationPrint = new String[242][561]; // aggiunte due righe e una colonna per le coordinate, prima era 240 e 560

    private final String black = "\033[0;30m";
    private final String reset = "\03cR[0m";
    private final int cardHeight = 3;
    private final int cardLength = 7;
    private final int dimCardStation = 40;

    /**
     * this method is the constructor where the matrix StationPrint is initiated.
     */
    public StationMatrix() {
        initializeStationPrint();
    }

    /**
     * this method initializes the matrix stationPrint
     */
    public void initializeStationPrint(){
        for(int i  = 0; i < stationPrint.length; i++) {
            for(int j = 0; j < stationPrint[i].length; j++) {
                stationPrint[i][j] = " ";
            }

        }
    }

    /**
     * this method adds a card resource to the station. distanceXFromStarting and distanceYFromStarting are the distances, respectively from the x and y coordinates of the starting card.
     * also this method checks if a corner from the card that is added is covered and in that case doesn't add to the station what is in that corner.
     * @param card is the card resource to be added
     * @param i is the row coordinate
     * @param j is the column coordinate
     */
    public void addCardToStation(CardResource card, int i, int j) {
        int m = 0;
        int l = 0;
        boolean[] cornerCovered;
        int distanceXFromStarting = dimCardStation - i;
        int distanceYFromStarting = dimCardStation - j;
        String[][] tmp;
        if(card.getPlayingBack()){
            tmp = createBackPlayingCard(card);
            cornerCovered = checkCornerCoveredPlayedBack(card);
        } else{
            tmp = createFrontPlayingCard(card);
            cornerCovered = checkCornerCovered(card);
        }
        for(int k = cardHeight*i + distanceXFromStarting; k < cardHeight*i+cardHeight+distanceXFromStarting; k++){
            for(int s = cardLength*j+distanceYFromStarting; s < cardLength*j+cardLength+distanceYFromStarting; s++){
                if(UpLeftCornerCovered(m, l, cornerCovered[0])) { //controllo se angolo in alto a sx è coperto
                    stationPrint[k][s] = stationPrint[k][s];
                } else if(UpLeftCornerNotCovered(m, l, cornerCovered[0])){ //alto sx non coperto
                    stationPrint[k][s] = tmp[m][l];
                    //addCoordinatesUpLeft(i, j);
                } else if(UpRightCornerCovered(m, l, cornerCovered[1])) { //alto a dx coperto
                    stationPrint[k][s] = stationPrint[k][s];
                } else if(UpRightCornerNotCovered(m, l, cornerCovered[1])){ // alto dx non cop
                    stationPrint[k][s] = tmp[m][l];
                    //addCoordinatesUpRight(i, j);
                } else if(DownLeftCornerCovered(m, l, cornerCovered[2])){
                    stationPrint[k][s] = stationPrint[k][s];
                } else if(DownLeftCornerNotCovered(m, l, cornerCovered[2])){
                    stationPrint[k][s] = tmp[m][l];
                    //addCoordinatesDownLeft(i, j);
                }else if(DownRightCornerCovered(m, l, cornerCovered[3])) {
                    stationPrint[k][s] = stationPrint[k][s];
                } else if(DownRightCornerNotCovered(m, l, cornerCovered[3])){
                    stationPrint[k][s] = tmp[m][l];
                    //addCoordinatesDownRight(i, j);
                }else {
                    stationPrint[k][s] = tmp[m][l];
                }
                l++;
            }
            l=0;
            m++;
        }
    }

    //TODO: tutte queste da togliere
    /**
     * this method checks if it is an up right corner of the card and if it is covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is an up right corner and the corner is covered
     */
    private boolean UpRightCornerCovered(int m, int l, boolean covered){
        return m == 0 && l == 6 && covered;
    }
    /**
     * this method checks if it is an up right corner of the card and if it is not covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is an up right corner and the corner is not covered
     */
    private boolean UpRightCornerNotCovered(int m, int l, boolean covered){
        return m == 0 && l == 6 && !covered;
    }
    /**
     * this method checks if it is an up left corner of the card and if it is covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is an up left corner and the corner is covered
     */
    private boolean UpLeftCornerCovered(int m, int l, boolean covered){
        return m == 0 && l == 0 && covered;
    }
    /**
     * this method checks if it is an up left corner of the card and if it is not covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is an up left corner and the corner is not covered
     */
    private boolean UpLeftCornerNotCovered(int m, int l, boolean covered){
        return m == 0 && l == 0 && !covered;
    }
    /**
     * this method checks if it is a down right corner of the card and if it is covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is a down right corner and the corner is covered
     */
    private boolean DownRightCornerCovered(int m, int l, boolean covered){
        return m == 2 && l == 6 && covered;
    }
    /**
     * this method checks if it is a down right corner of the card and if it is not covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is a down right corner and the corner is not covered
     */
    private boolean DownRightCornerNotCovered(int m, int l, boolean covered){
        return m == 2 && l == 6 && !covered;
    }
    /**
     * this method checks if it is a down left corner of the card and if it is covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is a down left corner and the corner is covered
     */
    private boolean DownLeftCornerCovered(int m, int l, boolean covered){
        return m == 2 && l == 0 && covered;
    }
    /**
     * this method checks if it is a down left corner of the card and if it is not covered
     * @param m is the row
     * @param l is the column
     * @param covered is if it is covered or not
     * @return true if it is a down left corner and the corner is not covered
     */
    private boolean DownLeftCornerNotCovered(int m, int l, boolean covered){
        return m == 2 && l == 0 && !covered;
    }

    //TODO: fare un metodo unico per aggiunta di carte alla stationMatrix --> il duplicated code
    /**
     * this method adds a card gold to the station. distanceXFromStarting and distanceYFromStarting are the distance, respectively the x and y coordinates, from the
     * starting card also this method checks if a corner from the card that is added is covered and in that caso doesn't add to the station that corner.
     * @param card is the gold cards to be added
     * @param i is the row coordinate
     * @param j is the column coordinate
     */
    private void addCardToStation(CardGold card, int i, int j){
        if(card.getPlayingBack()){
            addCardToStation((CardResource) card, i, j);
        } else{
            String[][] tmp = createFrontPlayingCard(card);
            boolean[] cornerCovered = checkCornerCovered(card);
            int m = 0;
            int l = 0;
            int distanceXFromStarting = dimCardStation - i;
            int distanceYFromStarting = dimCardStation - j;
            for(int k = cardHeight*i + distanceXFromStarting; k < cardHeight*i+cardHeight+distanceXFromStarting; k++){
                for(int s = cardLength*j+distanceYFromStarting; s < cardLength*j+cardLength+distanceYFromStarting; s++){
                    if(UpLeftCornerCovered(m, l, cornerCovered[0])) { //controllo se angolo in alto a sx è coperto
                        stationPrint[k][s] = stationPrint[k][s];
                    } else if(UpLeftCornerNotCovered(m, l, cornerCovered[0])){ //alto sx non coperto
                        stationPrint[k][s] = tmp[m][l];
                        //addCoordinatesUpLeft(i, j);
                    } else if(UpRightCornerCovered(m, l, cornerCovered[1])) { //alto a dx coperto
                        stationPrint[k][s] = stationPrint[k][s];
                    } else if(UpRightCornerNotCovered(m, l, cornerCovered[1])){ // alto dx non cop
                        stationPrint[k][s] = tmp[m][l];
                       // addCoordinatesUpRight(i, j);
                    } else if(DownLeftCornerCovered(m, l, cornerCovered[2])){
                        stationPrint[k][s] = stationPrint[k][s];
                    } else if(DownLeftCornerNotCovered(m, l, cornerCovered[2])){
                        stationPrint[k][s] = tmp[m][l];
                        //addCoordinatesDownLeft(i, j);
                    }else if(DownRightCornerCovered(m, l, cornerCovered[3])) {
                        stationPrint[k][s] = stationPrint[k][s];
                    } else if(DownRightCornerNotCovered(m, l, cornerCovered[3])){
                        stationPrint[k][s] = tmp[m][l];
                        //addCoordinatesDownRight(i, j);
                    }else {
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
        for(int k = cardHeight*i; k < cardHeight*i+3; k++){
            for(int s = cardLength*j; s < cardLength*j+cardLength; s++){
                stationPrint[k][s] = tmp[m][l];
                l++;
            }
            l = 0;
            m++;
        }
    }

    //TODO: fare metodi che aggiungono le coordinate.

    //Vorrei ad esempio che se mi viene passata la carta in pos 40x40 aggiungo 39x39. Avrò bisogno della posizione della carta piazzata
    private void addCoordinatesUpLeft(int i, int j){
        int row = i * cardHeight - 2; //numeri per estetica
        int col = j * cardLength - 6; //numeri per estetica
        String rowCoordinate = String.valueOf(i-1);
        String colCoordinate = String.valueOf(j-1);
        stationPrint[row][col] = rowCoordinate + "x" + colCoordinate;
    }

    private void addCoordinatesUpRight(int i, int j){
        int row = i * cardHeight - 2; //numeri per estetica
        int col = j * cardLength + 2; //numeri per estetica
        String rowCoordinate = String.valueOf(i-1);
        String colCoordinate = String.valueOf(j+1);
        stationPrint[row][col] = rowCoordinate + "x" + colCoordinate;
    }

    private void addCoordinatesDownLeft(int i, int j){
        int row = i * cardHeight + 2; //numeri per estetica
        int col = j * cardLength - 6; //numeri per estetica
        String rowCoordinate = String.valueOf(i+1);
        String colCoordinate = String.valueOf(j-1);
        stationPrint[row][col] = rowCoordinate + "x" + colCoordinate;
    }

    private void addCoordinatesDownRight(int i, int j){
        int row = i * cardHeight + 2; //numeri per estetica
        int col = j * cardLength + 2; //numeri per estetica
        String rowCoordinate = String.valueOf(i+1);
        String colCoordinate = String.valueOf(j+1);
        stationPrint[row][col] = rowCoordinate + "x" + colCoordinate;
    }

    /**
     * this method prints the station of a player distant ato most by max from the starting card. 120 to 122 and 280 to 286 is the position of the starting card.
     * @param playingStationMap is the distance between the starting card and the furthest card in the station
     */
    public void printStation(HashMap<ArrayList<Integer>, CardPlaying> playingStationMap){//121, 283 sono le coordinate della carta iniziale in stationPrint
        populateStation(playingStationMap);
        int max = calculateMaxDistance(playingStationMap);
        for(int i = 120-cardHeight*max; i < 123+cardHeight*max; i++){
            for(int j = 280-cardLength*max; j < 287+cardLength*max; j++){
                    System.out.print(stationPrint[i][j]);


            }
            System.out.println();
        }
    }

    /**
     * this method calculates the distance between the starting card and the furthest card played.
     * @param playingStationMap is the HashMap containing the cards played
     * @return the maximum distance
     */
    private int calculateMaxDistance(HashMap<ArrayList<Integer>, CardPlaying> playingStationMap) {
        int x, y, maxX, maxY, distanceX, distanceY, max;
        maxX = 0;
        maxY = 0;
        max = 0;
        for (HashMap.Entry<ArrayList<Integer>, CardPlaying> entry : playingStationMap.entrySet()) {
            ArrayList<Integer> coordinates = entry.getKey();
            x = coordinates.get(0);
            y = coordinates.get(1);
            distanceX = Math.abs(x - dimCardStation);
            distanceY = Math.abs(y - dimCardStation);
            if(distanceX > maxX || distanceY > maxY){
                maxX = distanceX;
                maxY = distanceY;
                max = Math.max(distanceX, distanceY);
            }
        }
        return max;
    }


    /**
     * this method adds the cards to the station matrix, so that they can be printed. 40x40 is the position of the starting card
     * @param station is a matrix of cards
     * @param max is the distance between the starting card and the furthest card played in the station
     */

    //TODO: qua dentro quando chiamo la addCard aggiungo un array che mi dice se la carta ha nei suoi angoli carte null
    // se così è --> allora metterò le coordinate else no.
    // inoltre devo potenzialmente aggiungere coordinate anche alla carta centrale
    // aggiungo parametri alla chiamata passando anche il booleano di ogni carta adiacente: se true --> allorca c'è una carta, else --> non c'è una carta --> stampa coordinate
    public void addCardsToStation(CardPlaying[][] station, int max){
        for(int i = dimCardStation-max; i < dimCardStation+max+1; i++){
            for(int j = dimCardStation-max; j < dimCardStation+max+1; j++){
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
     * this method populates a Card matrix from the player's station map.
     */
    private void populateStation(HashMap<ArrayList<Integer>, CardPlaying> playingStationMap){
        int x, y;
        int max;
        CardPlaying[][] station = new CardPlaying[80][80];
        for (HashMap.Entry<ArrayList<Integer>, CardPlaying> entry : playingStationMap.entrySet()) {
            ArrayList<Integer> coordinates = entry.getKey();
            CardPlaying card = entry.getValue();
            x = coordinates.get(0);
            y = coordinates.get(1);
            station[x][y] = card;
        }
        max = calculateMaxDistance(playingStationMap);
        addCardsToStation(station, max);
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
        System.out.println("You have: "+fungi+" of fungi, "+plant+" of plant, "+animal+" of animal, "+insect+" of insect, "+quill+" of quill, "+manuscript+" of manuscript, "+inkwell+" of inkwell");
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

}
