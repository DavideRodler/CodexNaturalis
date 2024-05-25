package model;

import exception.InvalidPlacingCondition;
import exception.NotValidMoveException;
import model.cards.*;
//import model.cards.face.Corner;
import model.cards.face.Corner;
import model.enums.GameState;
import model.enums.SuitEnum;
import observers.ObservableModel;
import Socket.Messages.*;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class PlayingStation extends ObservableModel implements Serializable {
    private HashMap<ArrayList<Integer>, CardPlaying> map;
    private Integer countInsect;
    private Integer countAnimal;
    private Integer countPlant;
    private Integer countFungi;
    private Integer countInkwell;
    private Integer countQuill;
    private Integer countManuscript;


    //constructor
    public PlayingStation(HashMap<ArrayList<Integer>, CardPlaying> map) {
        this.map = map;
        countAnimal = 0;
        countFungi = 0;
        countInsect = 0;
        countPlant = 0;
        countInkwell = 0;
        countManuscript = 0;
        countQuill = 0;
    }

    //-------------------GETTER-----------------------------
    public HashMap<ArrayList<Integer>,CardPlaying> getMap() {return map;}
    public Integer getCountInsect () {
        return countInsect;
    }

    public Integer getCountAnimal () {
        return countAnimal;
    }

    public Integer getCountPlant () {
        return countPlant;
    }

    public Integer getCountFungi () {
        return countFungi;
    }

    public Integer getCountInkwell () {
        return countInkwell;
    }

    public Integer getCountQuill () {
        return countQuill;
    }

    public Integer getCountManuscript () {
        return countManuscript;
    }

    public CardStarting getCardStarting(){
        return (CardStarting) map.get(new ArrayList<>(Arrays.asList(40,40)));
    }



        //------------------SETTER-------------------
    public void setMap(HashMap<ArrayList<Integer>, CardPlaying> map) {
        this.map = map;
    }

    public void setCardStarting(CardStarting card,String nickname){
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(40);
        coordinates.add(40);
        map.put(coordinates, card);
        try {
            notifyObservers(new CardStartingMessage(nickname,card));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void setCardStartingPlayedBack(String nickname, boolean playedback){
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(40);
        coordinates.add(40);
        map.get(coordinates).setPlayingBack(playedback);
        updateCounters((CardStarting) map.get(coordinates));
        try {
            notifyObservers(new CardStartingPlayedBackMessage(nickname,playedback));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


//    TODO: passare id della carta e non la carta, mettere exception se la carta non c'e'
    public ArrayList<Integer> getCoordinates(CardPlaying card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : map.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey();
            }
        }
        return null; // Return null if the card is not found
    }

    /**
     * This method returns the x coordinate of the given card
     *
     * @param card the card
     * @return the x coordinate of the given card or null if the card is not found
     */

//    TODO: passare id della carta e non la carta
    public Integer getXCoordinate(CardPlaying card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : map.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey().get(0);
            }
        }
        return null; // Return null if the card is not found
    }


    /**
     * This method returns the y coordinate of the given card
     *
     * @param card the card
     * @return the y coordinate of the given card or null if the card is not found
     */
//    TODO: passare id della carta e non la carta
    public Integer getYCoordinate(CardPlaying card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : map.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey().get(1);
            }
        }
        return null; // Return null if the card is not found
    }


    /**
     * This method returns the card at the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the card at the given coordinates
     */
    public CardPlaying getCard(int x, int y){
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, x);
        coordinates.add(1, y);
        if (map.get(coordinates) == null) {
            return null;
        } else {
            return map.get(coordinates);
        }
    }





        /**
         * This method checks if a card can be placed at the given coordinates, Checks if the
         * card is played by front (if back I can always place it), then checks
         * if the corners, of the card that are in the adjacent diagonal, are NULL.
         * Checks this for all the cards that are in the adjacent diagonal, if they exist.
         * At the end flag the corners that are covered with method setCovered and check if the
         * card is a goldCard and then use method enoughResources to check if it's playable
         *
         * @param card the card to place
         * @param X    the x coordinate
         * @param Y    the y coordinate
         * @return true if the card can be placed, false otherwise
         */
        //TODO: devo sapere se la carta e' giocata front o back
        public HashMap isPlayable (boolean playedback, CardResource card, Integer X, Integer Y) throws InvalidPlacingCondition {

            // Check if the coordinates are free
            HashMap<ArrayList<Integer>, Boolean> numCornerCovered;
            ArrayList<Integer> coordinates1;
            ArrayList<Integer> coordinates2;
            ArrayList<Integer> coordinates3;
            ArrayList<Integer> coordinates4;


            if(X<0 || Y<0 || X>80 || Y>80)
                throw new InvalidPlacingCondition("Invalid coordinates");
            else if (getCard(X, Y) != null) {
                throw new InvalidPlacingCondition("There is already a card in the given coordinates");
                //Check if there is already a card in the given coordinates
            } else if (getCard(X - 1, Y - 1) == null && getCard(X + 1, Y - 1) == null && getCard(X - 1, Y + 1) == null && getCard(X + 1, Y + 1) == null) {
                throw new InvalidPlacingCondition("The card is surrounded by empty spaces");
                // Check if the card is surrounded by empty spaces
            } else {


                //creating record map for keeping track of the corners that potentially could be covered
                numCornerCovered = new HashMap<>();
                coordinates1 = new ArrayList<>();
                coordinates2 = new ArrayList<>();
                coordinates3 = new ArrayList<>();
                coordinates4 = new ArrayList<>();

                coordinates1.add(0, X - 1);
                coordinates1.add(1, Y - 1);
                numCornerCovered.put(coordinates1, false);

                coordinates2.add(0, X + 1);
                coordinates2.add(1, Y - 1);
                numCornerCovered.put(coordinates2, false);

                coordinates3.add(0, X - 1);
                coordinates3.add(1, Y + 1);
                numCornerCovered.put(coordinates3, false);

                coordinates4.add(0, X + 1);
                coordinates4.add(1, Y + 1);
                numCornerCovered.put(coordinates4, false);


                //checking that I can't place a card above 2 corners of the same card
                if(getCard(X,Y-1) != null)
                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");

                if(getCard(X,Y+1) != null)
                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");

                if(getCard(X-1,Y) != null)
                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");

                if(getCard(X+1,Y) != null)
                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");


                //:Todo mettere un metodo che ritorna il lato della carta che gioco

                if (getCard(X - 1, Y - 1) != null) { //checking if existing the down-right card adjacent with the card I want to play
                    if (!map.get(coordinates1).getPlayingBack()) { //checking if the card is played by front
                        if (map.get(coordinates1).getFront().getDownRight().getDrawing().equals(SuitEnum.NULL)) { //checking if there is a corner available
                            throw new InvalidPlacingCondition("The down-right corner is NULL");
                        }
                    }
                    numCornerCovered.put(coordinates1, true); //flagging that this corner is available
                }

                if (getCard(X + 1, Y - 1) != null) { //checking if existing the down-left card adjacent with the card I want to play
                    if (!map.get(coordinates2).getPlayingBack()) { //checking if the card is played by front
                        if (map.get(coordinates2).getFront().getDownLeft().getDrawing().equals(SuitEnum.NULL)) { //checking if there is a corner available
                            throw new InvalidPlacingCondition("Can't place a card over a NULL corner");
                        }
                    }
                    numCornerCovered.put(coordinates2, true); //flagging that this corner is available
                }

                if (getCard(X - 1, Y + 1) != null) { //checking if existing the up-right card adjacent with the card I want to play
                    if (!map.get(coordinates3).getPlayingBack()) { //checking if the card is played by front
                        if (map.get(coordinates3).getFront().getUpRight().getDrawing().equals(SuitEnum.NULL)) { //checking if there is a corner available
                            throw new InvalidPlacingCondition("Can't place a card over a NULL corner");
                        }
                    }
                    numCornerCovered.put(coordinates3, true); //flagging that this corner is available
                }

                if (getCard(X + 1, Y + 1) != null) { //checking if existing the up-left card adjacent with the card I want to play
                    if (!map.get(coordinates4).getPlayingBack()) { //checking if the card is played by front
                        if (map.get(coordinates4).getFront().getUpLeft().getDrawing().equals(SuitEnum.NULL)) { //checking if there is a corner available
                            throw new InvalidPlacingCondition("Can't place a card over a NULL corner");
                        }
                    }
                    numCornerCovered.put(coordinates4, true); //flagging that this corner is available
                }




                //Check if the card is a goldCard and then use method enoughResources to check if it's playable
                if (card instanceof CardGold)
                    if (!enoughResources((CardGold) card)&& !playedback)
                        throw new InvalidPlacingCondition("Not enough resources to play the card");
            }

            return numCornerCovered;
        }




        /**
         * This method checks if the player has enough resources to play a gold card
         *
         * @param goldCard the gold card
         * @return true if the player has enough resources, false otherwise
         */
        //TODO: mettere i metodi che contano le risorse delle carte al posto dei contatori
        public boolean enoughResources (CardGold goldCard){
            if (countAnimal < goldCard.getCostAnimal() || countFungi < goldCard.getCostFungi() ||
                    countInsect < goldCard.getCostInsect() || countPlant < goldCard.getCostPlant()) {
                return false;
            }
            return true;
        }

        public int addCard(CardResource card, Integer X, Integer Y,boolean playedback, String nickname) throws InvalidPlacingCondition {
            int points = 0;
            int counter = 0;
            HashMap<ArrayList<Integer>, Boolean> numCornerCovered;
            //check if the card is Playable
            numCornerCovered = isPlayable(playedback,card, X, Y);
            //putting the card in the map
            ArrayList<Integer> coordinates = new ArrayList<>();
            coordinates.add(0, X);
            coordinates.add(1, Y);
            this.getMap().put(coordinates, card);


            //removing the counters of the covered cards
            if(!playedback) {
                card.setPlayingBack(false);
            } else card.setPlayingBack(true);

            for (var var : numCornerCovered.keySet()) {
                switch (counter) {
                    case 0: {
                        if (numCornerCovered.get(var)) {
                            this.getMap().get(var).getFront().getDownRight().setCovered(true);
                            this.updateCounters(this.getMap().get(var).getFront().getDownRight());
                            this.getMap().get(var).getFront().getDownRight().setCovered(true);
                        }
                    }
                    case 1: {
                        if (numCornerCovered.get(var)) {
                            this.getMap().get(var).getFront().getDownLeft().setCovered(true);
                            this.updateCounters(this.getMap().get(var).getFront().getDownLeft());
                            this.getMap().get(var).getFront().getDownLeft().setCovered(true);
                        }
                    }
                    case 2: {
                        if (numCornerCovered.get(var)) {
                            this.getMap().get(var).getFront().getUpRight().setCovered(true);
                            this.updateCounters(this.getMap().get(var).getFront().getUpRight());
                            this.getMap().get(var).getFront().getUpRight().setCovered(true);
                        }
                    }
                    case 3: {
                        if (numCornerCovered.get(var)) {
                            this.getMap().get(var).getFront().getUpLeft().setCovered(true);
                            this.updateCounters(this.getMap().get(var).getFront().getUpLeft());
                            this.getMap().get(var).getFront().getUpLeft().setCovered(true);
                        }
                    }
                }
                counter++;
            }

            //adding counters of te new card that has been added
            this.updateCounters(card);

            //calculating the points that the card generates
            if (!(card.getPlayingBack())) {
                //     points = card.getObjective().countObjectivePoints(player.getStation(), card, X, Y);
            } else points = 0;

            try {
                notifyObservers(new CardAddedToStationMessage(card, nickname, X, Y, playedback));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            return points;
        }








        /**
         * This method updates the counters of the playing station
         *
         * @param corner the corner that is covered by the card
         */
        public void updateCounters (Corner corner){
            if (corner.isCovered()) {
                switch (corner.getDrawing()) {
                    case QUILL -> countQuill--;
                    case MANUSCRIPT -> countManuscript--;
                    case INKWELL -> countInkwell--;
                    case FUNGI -> countFungi--;
                    case PLANT -> countPlant--;
                    case ANIMAL -> countAnimal--;
                    case INSECT -> countInsect--;
                    // case EMPTY -> null;
                }
            } else {
                switch (corner.getDrawing()) {
                    case QUILL -> countQuill++;
                    case MANUSCRIPT -> countManuscript++;
                    case INKWELL -> countInkwell++;
                    case FUNGI -> countFungi++;
                    case PLANT -> countPlant++;
                    case ANIMAL -> countAnimal++;
                    case INSECT -> countInsect++;
                    //  case EMPTY -> null;
                }
            }
        }

        public void updateCounters (CardStarting card){
            if (card.getPlayingBack()) {
                updateCounters(card.getBack().getUpRight());
                updateCounters(card.getBack().getDownRight());
                updateCounters(card.getBack().getUpLeft());
                updateCounters(card.getBack().getDownLeft());
            } else {
                updateCounters(card.getFront().getUpRight());
                updateCounters(card.getFront().getDownRight());
                updateCounters(card.getFront().getUpLeft());
                updateCounters(card.getFront().getDownLeft());
                ArrayList<SuitEnum> symbols = card.getSymbols();
                for (SuitEnum symbol : symbols) {
                    switch (symbol) {
                        case QUILL -> countQuill++;
                        case MANUSCRIPT -> countManuscript++;
                        case INKWELL -> countInkwell++;
                        case FUNGI -> countFungi++;
                        case PLANT -> countPlant++;
                        case ANIMAL -> countAnimal++;
                        case INSECT -> countInsect++;
                        //  case EMPTY -> null;
                    }
                }
            }
        }

        public void updateCounters (CardResource card){
            if (!card.getPlayingBack()) {
                updateCounters(card.getFront().getUpRight());
                updateCounters(card.getFront().getDownRight());
                updateCounters(card.getFront().getUpLeft());
                updateCounters(card.getFront().getDownLeft());
            }
            else{
                switch(card.getSymbol()){
                    case ANIMAL -> countAnimal++;
                    case PLANT -> countPlant++;
                    case INSECT -> countInsect++;
                    case FUNGI -> countFungi++;
                }
            }
        }
    }

