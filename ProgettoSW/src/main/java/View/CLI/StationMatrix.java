package View.CLI;

import model.cards.*;
import model.client.ClientBoard;
import model.client.ReductPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static View.CLI.CardMatrixCreator.*;
import static View.CLI.CardMatrixCreator.createFrontPlayingCard;

public class StationMatrix {
    private String[][] stationPrint = new String[120][480]; // aggiunte due righe e una colonna per le coordinate, prima era 240 e 560
    //il numero di righe = 3n+n-1 = 4n - 1 = 320 - 1 = 319
    //il numero di colonne = 7n+(n-1)x5 = 12n-5 = 960 - 5 = 955
    //per comodità arrotonderei a un numero pari così da avere un centro
    // righe --> 319/2 = 159
    // colonne --> 955/2 = 477
    // centro è stationMatrix[159][477]

    //TODO: modificare il codice per fare in modo che vada bene con le nuove dimensioni della matrice

    private final String black = "\033[0;30m";
    private final String reset = "\03cR[0m";
    private final int cardHeight = 3;
    private final int cardLength = 7;
    private final int cardStartingPosition = 40;
    //TODO: dimCardStation forse non è il nome più adatto. indica la posizione della carta starting

    /**
     * this method is the constructor where the matrix StationPrint is initiated.
     */
    public StationMatrix() {
        initializeStationPrint();
    }

    //TODO: in questo metodo bisogna aggiungere le coordinate.
    /**
     * this method initializes the matrix stationPrint with the coordinates for card placement
     */
    public void initializeStationPrint(){
        for(int i  = 0; i < stationPrint.length; i++) {
            Arrays.fill(stationPrint[i], " ");
        }
    }

    /**
     * this method adds a card resource to the station. distanceXFromStarting and distanceYFromStarting are the distances, respectively from the x and y coordinates of the
     * starting card. If a corner from the card that is added is covered by some other card, the corner doesn't get added to the station what is in that corner.
     * @param card is the card resource to be added
     * @param i is the row coordinate
     * @param j is the column coordinate
     */
    public void addCardToStation(CardResource card, int i, int j, boolean[] voidPositions) {
        boolean[] cornerCovered;
        String[][] tmp;
        if(card.getPlayingBack()){
            tmp = createBackPlayingCard(card);
            cornerCovered = checkCornerCoveredPlayedBack(card);
        } else{
            tmp = createFrontPlayingCard(card);
            cornerCovered = checkCornerCovered(card);
        }
        addCard(tmp, cornerCovered, voidPositions, i, j);

        //TODO nella prima parte di questo metodo aggiungo le coordinate --> un metodo unico chiamato più volte
        // nella seconda parte chiamo il metodo addCard che effettivamente la aggiunge alla matrice
        // di che parametri ha bisogno? la carta da aggiungere e corner covered

//        for(int k = cardHeight*i + distanceXFromStarting; k < cardHeight*i+cardHeight+distanceXFromStarting; k++){
//            for(int s = cardLength*j+distanceYFromStarting; s < cardLength*j+cardLength+distanceYFromStarting; s++){
//                if(m == 0 && l == 0) { //controllo se angolo in alto a sx è coperto
//                    if(cornerCovered[0]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                } else if(m == 0 && l == 6) { //alto a dx coperto
//                    if(cornerCovered[1]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                } else if(m == 2 && l == 0){ // alto dx non cop
//                    if(cornerCovered[2]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                } else if(m == 2 && l == 6){
//                    if(cornerCovered[3]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                } else {
//                    stationPrint[k][s] = tmp[m][l];
//                }
//                l++;
//            }
//            l=0;
//            m++;
//        }
    }
    //TODO: possibile che il controllo del corner covered sia inutile?
    // metto qua le coordinate
    private void addCard(String[][] cardToAdd, boolean[] cornerCovered, boolean[] voidPositions,int i, int j){
        //al posto che ottenere la posizione di partenza della stampa nel for --> uso la formula --> servono i e j.
        int m = 0, l = 0;
        int rowLeftCorner = 2*i - 2;
        int colLeftCorner = 6*j - 6;

        if(voidPositions[0] && !cardToAdd[0][0].equals("N")){ //aggiungo in alto a sx
            addCoordinates(i-1, j-1); //TODO!!!!!!!" con formule i: 2i-1 e j: 6j-5
        }
        if(voidPositions[1] && !cardToAdd[0][cardLength-1].equals("N")){ //aggiungo in alto a dx
            addCoordinates(i-1, j+1);
        }
        if(voidPositions[2] && !cardToAdd[cardHeight-1][0].equals("N")){ // basso sx
            addCoordinates(i+1, j-1);
        }
        if(voidPositions[3] && !cardToAdd[cardHeight-1][cardLength-1].equals("N")){ //basso dx
            addCoordinates(i+1, j+1);
        }
        for(int k = rowLeftCorner; k < rowLeftCorner + cardHeight; k++){
            for(int s = colLeftCorner; s < colLeftCorner + cardLength; s++) {
                if (m == 0 && l == 0) { //sono nel corner in alto a sx
                    if (cornerCovered[0]) {
                        stationPrint[k][s] = stationPrint[k][s];
                    } else {
                        stationPrint[k][s] = cardToAdd[m][l];
                    }
                } else if (m == 0 && l == cardLength-1) {
                    if(cornerCovered[1]){
                        stationPrint[k][s] = stationPrint[k][s];
                    } else{
                        stationPrint[k][s] = cardToAdd[m][l];
                    }
                }else if(m == cardHeight-1 && l == 0){
                    if(cornerCovered[2]){
                        stationPrint[k][s] = stationPrint[k][s];
                    } else{
                        stationPrint[k][s] = cardToAdd[m][l];
                    }
                }else if(m == cardHeight-1 && l == cardLength-1){
                    if(cornerCovered[3]){
                        stationPrint[k][s] = stationPrint[k][s];
                    } else{
                        stationPrint[k][s] = cardToAdd[m][l];
                    }
                } else{
                    stationPrint[k][s] = cardToAdd[m][l];
                }
                l++;
            }
            l = 0;
            m++;
        }
    }

//    //TODO: tutte queste da togliere
//    /**
//     * this method checks if it is an up right corner of the card and if it is covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is an up right corner and the corner is covered
//     */
//    private boolean UpRightCornerCovered(int m, int l, boolean covered){
//        return m == 0 && l == 6 && covered;
//    }
//    /**
//     * this method checks if it is an up right corner of the card and if it is not covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is an up right corner and the corner is not covered
//     */
//    private boolean UpRightCornerNotCovered(int m, int l, boolean covered){
//        return m == 0 && l == 6 && !covered;
//    }
//    /**
//     * this method checks if it is an up left corner of the card and if it is covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is an up left corner and the corner is covered
//     */
//    private boolean UpLeftCornerCovered(int m, int l, boolean covered){
//        return m == 0 && l == 0 && covered;
//    }
//    /**
//     * this method checks if it is an up left corner of the card and if it is not covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is an up left corner and the corner is not covered
//     */
//    private boolean UpLeftCornerNotCovered(int m, int l, boolean covered){
//        return m == 0 && l == 0 && !covered;
//    }
//    /**
//     * this method checks if it is a down right corner of the card and if it is covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is a down right corner and the corner is covered
//     */
//    private boolean DownRightCornerCovered(int m, int l, boolean covered){
//        return m == 2 && l == 6 && covered;
//    }
//    /**
//     * this method checks if it is a down right corner of the card and if it is not covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is a down right corner and the corner is not covered
//     */
//    private boolean DownRightCornerNotCovered(int m, int l, boolean covered){
//        return m == 2 && l == 6 && !covered;
//    }
//    /**
//     * this method checks if it is a down left corner of the card and if it is covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is a down left corner and the corner is covered
//     */
//    private boolean DownLeftCornerCovered(int m, int l, boolean covered){
//        return m == 2 && l == 0 && covered;
//    }
//    /**
//     * this method checks if it is a down left corner of the card and if it is not covered
//     * @param m is the row
//     * @param l is the column
//     * @param covered is if it is covered or not
//     * @return true if it is a down left corner and the corner is not covered
//     */
//    private boolean DownLeftCornerNotCovered(int m, int l, boolean covered){
//        return m == 2 && l == 0 && !covered;
//    }

    //TODO: fare un metodo unico per aggiunta di carte alla stationMatrix --> il duplicated code
    /**
     * this method adds a card gold to the station. distanceXFromStarting and distanceYFromStarting are the distance, respectively the x and y coordinates, from the
     * starting card also this method checks if a corner from the card that is added is covered and in that caso doesn't add to the station that corner.
     * @param card is the gold cards to be added
     * @param i is the row coordinate
     * @param j is the column coordinate
     */
    private void addCardToStation(CardGold card, int i, int j, boolean[] voidPositions){
        if(card.getPlayingBack()){
            addCardToStation((CardResource) card, i, j, voidPositions);
        } else{
            String[][] tmp = createFrontPlayingCard(card);
            boolean[] cornerCovered = checkCornerCovered(card);

            addCard(tmp, cornerCovered, voidPositions, i, j);
//            for(int k = cardHeight*i + distanceXFromStarting; k < cardHeight*i+cardHeight+distanceXFromStarting; k++){
//                for(int s = cardLength*j+distanceYFromStarting; s < cardLength*j+cardLength+distanceYFromStarting; s++){
//                    if(m == 0 && l == 0) { //controllo se angolo in alto a sx è coperto
//                        if(cornerCovered[0]){
//                            stationPrint[k][s] = stationPrint[k][s];
//                        } else{
//                            stationPrint[k][s] = tmp[m][l];
//                        }
//                    } else if(m == 0 && l == 6) { //alto a dx coperto
//                        if(cornerCovered[1]){
//                            stationPrint[k][s] = stationPrint[k][s];
//                        } else{
//                            stationPrint[k][s] = tmp[m][l];
//                        }
//                    } else if(m == 2 && l == 0){ // alto dx non cop
//                        if(cornerCovered[2]){
//                            stationPrint[k][s] = stationPrint[k][s];
//                        } else{
//                            stationPrint[k][s] = tmp[m][l];
//                        }
//                    } else if(m == 2 && l == 6){
//                        if(cornerCovered[3]){
//                            stationPrint[k][s] = stationPrint[k][s];
//                        } else{
//                            stationPrint[k][s] = tmp[m][l];
//                        }
//
//                    } else {
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                    l++;
//                }
//                l = 0;
//                m++;
//            }
        }
    }

    /**
     * this method adds a starting card to the station.
     * @param card is the starting card to be added
     * @param i is the row of the card
     * @param j is the column of the card
     */
    public void addCardToStation(CardStarting card, int i, int j, boolean[] voidPositions){ //TODO: devo passare il posVoid e a quel punto decidere se stampare le coordinate o meno
        String[][] tmp;
        boolean[] cornerCovered;
        if(card.getPlayingBack()){
            tmp = createBackPlayingCard(card);
            cornerCovered = checkCornerCoveredPlayedBack(card);
        } else{
            tmp = createFrontPlayingCard(card);
            cornerCovered = checkCornerCovered(card);
        }

        addCard(tmp, cornerCovered, voidPositions, i, j);
//        for(int k = cardHeight*i; k < cardHeight*i+cardHeight; k++){
//            for(int s = cardLength*j; s < cardLength*j+cardLength; s++){
//                if(m == 0 && l == 0) { //controllo se angolo in alto a sx è coperto
//                    if(cornerCovered[0]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                } else if(m == 0 && l == 6) { //alto a dx coperto
//                    if(cornerCovered[1]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                } else if(m == 2 && l == 0){ // alto dx non cop
//                    if(cornerCovered[2]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//                } else if(m == 2 && l == 6){
//                    if(cornerCovered[3]){
//                        stationPrint[k][s] = stationPrint[k][s];
//                    } else{
//                        stationPrint[k][s] = tmp[m][l];
//                    }
//
//                } else {
//                    stationPrint[k][s] = tmp[m][l];
//                }
//                l++;
//            }
//            l = 0;
//            m++;
//        }
    }

    //TODO: fare metodi che aggiungono le coordinate.

    private void addCoordinates(int i, int j){
        //tramite la formula posso calcolare il centro di ogni carta, ovvero dove vanno le coordinate di ogni carta
        // i, j è direttamente la coordinata che voglio inserire
        int rowCoordinate = (cardHeight-1)*i - 1;
        int colCoordinate = (cardLength-1)*j - 5;
        int firstDigitFirstCoordinate = i % 10;
        int secondDigitFirstCoordinate = i / 10;
        int firstDigitSecondCoordinate = j % 10;
        int secondDigitSecondCoordinate = j / 10;

        stationPrint[rowCoordinate][colCoordinate] = String.valueOf(secondDigitFirstCoordinate);
        stationPrint[rowCoordinate][colCoordinate+1] = String.valueOf(firstDigitFirstCoordinate);
        stationPrint[rowCoordinate][colCoordinate+2] = "x";
        stationPrint[rowCoordinate][colCoordinate+3] = String.valueOf(secondDigitSecondCoordinate);
        stationPrint[rowCoordinate][colCoordinate+4] = String.valueOf(firstDigitSecondCoordinate);

    }

    //Vorrei ad esempio che se mi viene passata la carta in pos 40x40 aggiungo 39x39. Avrò bisogno della posizione della carta piazzata
    private void addCoordinatesUpLeft(int i, int j){
        int row = i * 4 - 1; //numeri per estetica
        int col = j * 8 - 6; //numeri per estetica
        int rowCoordinate = i-1;
        int colCoordinate = j-1;
        int firstDigitRowCoordinate = rowCoordinate/10;
        int secondDigitRowCoordinate = rowCoordinate%10;
        int firstDigitColCoordinate = colCoordinate/10;
        int secondDigitColCoordinate = colCoordinate%10;
        stationPrint[row][col] = String.valueOf(firstDigitRowCoordinate);
        stationPrint[row][col+1] = String.valueOf(secondDigitRowCoordinate);
        stationPrint[row][col+2] = "x";
        stationPrint[row][col+3] = String.valueOf(firstDigitColCoordinate);
        stationPrint[row][col+4] = String.valueOf(secondDigitColCoordinate);
    }

    private void addCoordinatesUpRight(int i, int j){
        int row = i * 4 - 1; //numeri per estetica
        int col = j * 8 + 8; //numeri per estetica
        int rowCoordinate = i-1;
        int colCoordinate = j+1;
        int firstDigitRowCoordinate = rowCoordinate/10;
        int secondDigitRowCoordinate = rowCoordinate%10;
        int firstDigitColCoordinate = colCoordinate/10;
        int secondDigitColCoordinate = colCoordinate%10;
        stationPrint[row][col] = String.valueOf(firstDigitRowCoordinate);
        stationPrint[row][col+1] = String.valueOf(secondDigitRowCoordinate);
        stationPrint[row][col+2] = "x";
        stationPrint[row][col+3] = String.valueOf(firstDigitColCoordinate);
        stationPrint[row][col+4] = String.valueOf(secondDigitColCoordinate);

    }

    private void addCoordinatesDownLeft(int i, int j){
        int row = i * 4 + 3; //numeri per estetica
        int col = j * 8 - 6; //numeri per estetica
        int rowCoordinate = i+1;
        int colCoordinate = j-1;
        int firstDigitRowCoordinate = rowCoordinate/10;
        int secondDigitRowCoordinate = rowCoordinate%10;
        int firstDigitColCoordinate = colCoordinate/10;
        int secondDigitColCoordinate = colCoordinate%10;
        stationPrint[row][col] = String.valueOf(firstDigitRowCoordinate);
        stationPrint[row][col+1] = String.valueOf(secondDigitRowCoordinate);
        stationPrint[row][col+2] = "x";
        stationPrint[row][col+3] = String.valueOf(firstDigitColCoordinate);
        stationPrint[row][col+4] = String.valueOf(secondDigitColCoordinate);
    }

    private void addCoordinatesDownRight(int i, int j){
        int row = i * 4 + 3; //numeri per estetica
        int col = j * 8 + 8; //numeri per estetica
        int rowCoordinate = i+1;
        int colCoordinate = j+1;
        int firstDigitRowCoordinate = rowCoordinate/10;
        int secondDigitRowCoordinate = rowCoordinate%10;
        int firstDigitColCoordinate = colCoordinate/10;
        int secondDigitColCoordinate = colCoordinate%10;
        stationPrint[row][col] = String.valueOf(firstDigitRowCoordinate);
        stationPrint[row][col+1] = String.valueOf(secondDigitRowCoordinate);
        stationPrint[row][col+2] = "x";
        stationPrint[row][col+3] = String.valueOf(firstDigitColCoordinate);
        stationPrint[row][col+4] = String.valueOf(secondDigitColCoordinate);
    }

    /**
     * this method prints the station of a player distant ato most by max from the starting card. 120 to 122 and 280 to 286 is the position of the starting card.
     * @param playingStationMap is the distance between the starting card and the furthest card in the station
     */
    public void printStation(HashMap<ArrayList<Integer>, CardPlaying> playingStationMap){//121, 283 sono le coordinate della carta iniziale in stationPrint
        //con le dimensioni aggiornate, la carta iniziale si trova in:
        populateStation(playingStationMap);
        int max = calculateMaxDistance(playingStationMap);
        int rowPositionOccupiedByStarting = 78; //estratto da formula per righe: 2n-2 per trovare riga del corner in alto a sx
        int colPositionOccupiedByStarting = 234;//estratto da formula per colonne: 6n-6 per trovare colonna del corner in alto a sx
        for(int i = rowPositionOccupiedByStarting-cardHeight*max-2; i < rowPositionOccupiedByStarting+cardHeight*max+5; i++){
            for(int j = colPositionOccupiedByStarting-cardLength*max-6; j < colPositionOccupiedByStarting+cardLength*max+13; j++){
                    System.out.print(stationPrint[i][j]);
            }
            System.out.println();
        }
    }

    public void printStationTest(){
        printMatrix(stationPrint);
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
            distanceX = Math.abs(x - cardStartingPosition);
            distanceY = Math.abs(y - cardStartingPosition);
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
    // aggiungo parametri alla chiamata passando anche il booleano di ogni carta adiacente: se true --> allora c'è una carta, else --> non c'è una carta --> stampa coordinate
    public void addCardsToStation(CardPlaying[][] station, int max){
        boolean[] voidPositions;
        for(int i = cardStartingPosition -max; i < cardStartingPosition +max+1; i++){
            for(int j = cardStartingPosition -max; j < cardStartingPosition +max+1; j++){
                if(station[i][j] != null) {
                    if (station[i][j].getPlayingBack()) {
                        if (i == 40 && j == 40) { //controllo se è la carta iniziale
                            voidPositions = positionIsVoid(station, i, j);
                            addCardToStation((CardStarting) station[i][j], i, j, voidPositions);

                        } else { // carta risorsa o gold
                            //chiamo addCardToStation(CardRes)
                            voidPositions = positionIsVoid(station, i, j);
                            addCardToStation((CardResource) station[i][j], i, j, voidPositions);
                        }
                    } else {
                        if (i == 40 && j == 40) { // controllo se è la carta iniziale
                            voidPositions = positionIsVoid(station, i, j);
                            addCardToStation((CardStarting) station[i][j], i, j, voidPositions);
                        } else {
                            if (station[i][j] instanceof CardGold) {
                                voidPositions = positionIsVoid(station, i, j);
                                addCardToStation((CardGold) station[i][j], i, j, voidPositions);
                            } else {
                                voidPositions = positionIsVoid(station, i, j);
                                addCardToStation((CardResource) station[i][j], i, j, voidPositions);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean[] positionIsVoid(CardPlaying[][] station, int i, int j){
        boolean[] voidPositions = new boolean[4];
        if(station[i-1][j-1] == null){ //angolo in alto a sinistra
            voidPositions[0] = true;
        }
        if(station[i-1][j+1] == null){ //angolo in alto a destra
            voidPositions[1] = true;
        }
        if(station[i+1][j-1] == null){ //basso a sx
            voidPositions[2] = true;
        }
        if(station[i+1][j+1] == null){
            voidPositions[3] = true;
        }
        return voidPositions;
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
    private boolean[] checkCornerCovered(CardPlaying card){
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
    private boolean[] checkCornerCoveredPlayedBack(CardPlaying card) {
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
        System.out.println("There are: "+fungi+" of fungi, "+plant+" of plant, "+animal+" of animal, "+insect+" of insect, "+quill+" of quill, "+manuscript+" of manuscript, "+inkwell+" of inkwell");
    }

    /**
     * this method prints the point of the players
     * @param clientBoard is the board of the client needed to get the points.
     */
    public void printPoints(ClientBoard clientBoard){ //TODO: togliere obiettivi degli altri giocatori e aggiungere il colore
        int clientPoints = clientBoard.getMyplayer().getPoints();
        System.out.println( clientPoints + " points");
        for(ReductPlayer reductPlayer : clientBoard.getOtherplayers()){
            System.out.println(reductPlayer.getNickname() + " has " + reductPlayer.getPoints() + " points");
        }
    }

}
