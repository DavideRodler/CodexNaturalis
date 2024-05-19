package model;

import exception.InvalidPlacingCondition;
import model.cards.*;
//import model.cards.face.Corner;
import model.cards.face.Corner;
import model.enums.SuitEnum;
import observers.ObservableModel;
import socket.Messages.CardStartingMessage;
import socket.Messages.CardStartingPlayedBackMessage;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static model.enums.SuitEnum.*;

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



        //------------------SETTER-------------------
    public void setMap(HashMap<ArrayList<Integer>, CardPlaying> map) {
        this.map = map;
    }

    public void setCardStarting(CardStarting card,String nickname){
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(40);
        coordinates.add(40);
        map.put(coordinates, card);
        updateCounters(card);
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
    public CardPlaying getCard(int x, int y) throws Exception{
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, x);
        coordinates.add(1, y);
        if (map.get(coordinates) == null) {
            throw new Exception("There is no card in the given coordinates");
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
//        public boolean isPlayable (CardResource card, Integer X, Integer Y) throws InvalidPlacingCondition {
//
//            // Check if the coordinates are free
//            Map<ArrayList<Integer>, Boolean> numCornerCovered;
//            ArrayList<Integer> coordinates1;
//            ArrayList<Integer> coordinates2;
//            ArrayList<Integer> coordinates3;
//            ArrayList<Integer> coordinates4;
//
//
//            if(X<0 || Y<0 || X>80 || Y>80)
//                throw new InvalidPlacingCondition("Invalid coordinates");
//            else if (getCard(X, Y) != null) {
//                throw new InvalidPlacingCondition("There is already a card in the given coordinates");
//                //Check if there is already a card in the given coordinates
//            } else if (getCard(X - 1, Y - 1) == null && getCard(X + 1, Y - 1) == null && getCard(X - 1, Y + 1) == null && getCard(X + 1, Y + 1) == null) {
//                throw new InvalidPlacingCondition("The card is surrounded by empty spaces");
//                // Check if the card is surrounded by empty spaces
//            } else {
//
//
//                //creating record map for keeping track of the corners that potentially could be covered
//                numCornerCovered = new HashMap<>();
//                coordinates1 = new ArrayList<>();
//                coordinates2 = new ArrayList<>();
//                coordinates3 = new ArrayList<>();
//                coordinates4 = new ArrayList<>();
//
//                coordinates1.add(0, X - 1);
//                coordinates1.add(1, Y - 1);
//                numCornerCovered.put(coordinates1, false);
//
//                coordinates2.add(0, X + 1);
//                coordinates2.add(1, Y - 1);
//                numCornerCovered.put(coordinates2, false);
//
//                coordinates3.add(0, X - 1);
//                coordinates3.add(1, Y + 1);
//                numCornerCovered.put(coordinates3, false);
//
//                coordinates4.add(0, X + 1);
//                coordinates4.add(1, Y + 1);
//                numCornerCovered.put(coordinates4, false);
//
//
//                //checking that I can't place a card above 2 corners of the same card
//                if(getCard(X,Y-1) != null)
//                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");
//
//                if(getCard(X,Y+1) != null)
//                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");
//
//                if(getCard(X-1,Y) != null)
//                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");
//
//                if(getCard(X+1,Y) != null)
//                    throw new InvalidPlacingCondition("You can't place a card above 2 corners of the same card");
//
//                if (getCard(X - 1, Y - 1) != null) { //checking if existing the down-right card adjacent with the card I want to play
//                    if (!map.get(coordinates1).getPlayingBack()) { //checking if the card is played by front
//                        if (map.get(coordinates1).getFront().getDownRight().equals(SuitEnum.NULL)) { //checking if there is a corner available
//                            throw new InvalidPlacingCondition("The down-right corner is NULL");
//                        }
//                    }
//                    numCornerCovered.put(coordinates1, true); //flagging that this corner is available
//                }
//
//                if (getCard(X + 1, Y - 1) != null) { //checking if existing the down-left card adjacent with the card I want to play
//                    if (!map.get(coordinates2).getPlayingBack()) { //checking if the card is played by front
//                        if (map.get(coordinates2).getFront().getDownLeft().equals(SuitEnum.NULL)) { //checking if there is a corner available
//                            throw new InvalidPlacingCondition("Can't place a card over a NULL corner");
//                        }
//                    }
//                    numCornerCovered.put(coordinates2, true); //flagging that this corner is available
//                }
//
//                if (getCard(X - 1, Y + 1) != null) { //checking if existing the up-right card adjacent with the card I want to play
//                    if (!map.get(coordinates3).getPlayingBack()) { //checking if the card is played by front
//                        if (map.get(coordinates3).getFront().getUpRight().equals(SuitEnum.NULL)) { //checking if there is a corner available
//                            throw new InvalidPlacingCondition("Can't place a card over a NULL corner");
//                        }
//                    }
//                    numCornerCovered.put(coordinates3, true); //flagging that this corner is available
//                }
//
//                if (getCard(X + 1, Y + 1) != null) { //checking if existing the up-left card adjacent with the card I want to play
//                    if (!map.get(coordinates4).getPlayingBack()) { //checking if the card is played by front
//                        if (map.get(coordinates4).getFront().getUpLeft().equals(SuitEnum.NULL)) { //checking if there is a corner available
//                            throw new InvalidPlacingCondition("Can't place a card over a NULL corner");
//                        }
//                    }
//                    numCornerCovered.put(coordinates4, true); //flagging that this corner is available
//                }
//
//
//
//
//                //Check if the card is a goldCard and then use method enoughResources to check if it's playable
//                if (card instanceof CardGold)
//                    if (!enoughResources((CardGold) card)&& !(card.getPlayingBack()))
//                        throw new InvalidPlacingCondition("Not enough resources to play the card");
//
////TODO: questa roba va messa nella addcard
//                //Flagging the corners that are covered with method setCovered
//                // and updating resource with updateCounters method
//
//                if (numCornerCovered.get(coordinates1)) {
//                    map.get(coordinates1).getFront().getDownRight().setCovered(true);
//                    updateCounters(map.get(coordinates1).getFront().getDownRight());
//                }
//
//
//                if (numCornerCovered.get(coordinates2)) {
//                    map.get(coordinates2).getFront().getDownLeft().setCovered(true);
//                    updateCounters(map.get(coordinates2).getFront().getDownLeft());
//                }
//
//
//                if (numCornerCovered.get(coordinates3)) {
//                    map.get(coordinates3).getFront().getUpRight().setCovered(true);
//                    updateCounters(map.get(coordinates3).getFront().getUpRight());
//                }
//
//
//                if (numCornerCovered.get(coordinates4)) {
//                    map.get(coordinates4).getFront().getUpLeft().setCovered(true);
//                    updateCounters(map.get(coordinates4).getFront().getUpLeft());
//                }
//            }
//
//            return true;
//        }




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

