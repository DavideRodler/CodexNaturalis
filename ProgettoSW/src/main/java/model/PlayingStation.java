package model;

import model.cards.*;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.Direction;
import model.enums.Suit;
import Exception.InvalidPlacingCondition;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import model.objectives.ObjectiveCountingGold;
import model.objectives.ObjectiveDiagonal;


import java.lang.reflect.Array;
import java.util.*;

import static model.enums.Suit.ANIMAL;
import static model.enums.Suit.QUILL;

public class PlayingStation {
    private HashMap<ArrayList<Integer>, CardPlaying> table;
    private Integer countInsect;
    private Integer countAnimal;
    private Integer countPlant;
    private Integer countFungi;
    private Integer countInkwell;
    private Integer countQuill;
    private Integer countManuscript;
    private Player player;
    private CardObjective firstSecretObjective;
    private CardObjective secondSecretObjective;


    public void setFirstSecretObjective(CardObjective firstSecretObjective) {
        this.firstSecretObjective = firstSecretObjective;
    }

    public void setSetSecondSecretObjective(CardObjective SecondSecretObjective) {
        this.secondSecretObjective = SecondSecretObjective;
    }

    public CardObjective getFirstSecretObjective() {
        return firstSecretObjective;
    }

    public CardObjective getSecondSecretObjective() {
        return secondSecretObjective;
    }

    public ArrayList<Integer> getCoordinates(CardPlaying card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : table.entrySet()) {
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
    public Integer getXCoordinate(CardPlaying card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : table.entrySet()) {
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
    public Integer getYCoordinate(CardPlaying card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : table.entrySet()) {
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
    public Card getCard(int x, int y) {
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, x);
        coordinates.add(1, y);
        if (table.get(coordinates) == null) {
            return null;
        } else {
            return table.get(coordinates);
        }
    }


    // Costruttore
    public PlayingStation(Player player, CardStarting startCart, CardObjective firstObjective, CardObjective secondObjective) {
        this.table = new HashMap<>();
        this.player = player;
        ArrayList<Integer> coordi = new ArrayList<Integer>();
        coordi.add(40);
        coordi.add(40);
        this.table.put(coordi, startCart);
        this.firstSecretObjective = firstObjective;
        this.secondSecretObjective = secondObjective;
        this.countInsect = 0;
        this.countAnimal = 0;
        this.countPlant = 0;
        this.countFungi = 0;
        this.countInkwell = 0;
        this.countQuill = 0;
        this.countManuscript = 0;
        updateCounters(startCart);
    }


    /**
     * This method adds a card to the playing station
     *
     * @param card the card to add
     * @param X    the x coordinate
     * @param Y    the y coordinate
     */
    public void addCard(CardResource card, Integer X, Integer Y) {

        try {
            if (!isPlayable(card, X, Y)) {
                // Check if the card can be placed
                throw new InvalidPlacingCondition("Dove cazzo la stai piazzando brutto idiota");
            }
        } catch (InvalidPlacingCondition e) {
            System.out.println(e.getMessage());
            return;
        }

        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, X);
        coordinates.add(1, Y);
        table.put(coordinates, card);

        // Update the counters
        updateCounters(card);

        //adding points to the player
        if (!(card.getPlayingBack())) {
            int points = card.getObjective().checkObjective(this, card, X, Y);
            player.setPoints(player.getPoints() + points);
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
        public boolean isPlayable (CardResource card, Integer X, Integer Y){

            // Check if the coordinates are free
            Map<ArrayList<Integer>, Boolean> numCornerCovered;
            ArrayList<Integer> coordinates1;
            ArrayList<Integer> coordinates2;
            ArrayList<Integer> coordinates3;
            ArrayList<Integer> coordinates4;


            if(X<0 || Y<0 || X>80 || Y>80)
                return false;
            else if (getCard(X, Y) != null) {
                return false;
                //Check if there is already a card in the given coordinates
            } else if (getCard(X - 1, Y - 1) == null && getCard(X + 1, Y - 1) == null && getCard(X - 1, Y + 1) == null && getCard(X + 1, Y + 1) == null) {
                return false;
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
                    return false;

                if(getCard(X,Y+1) != null)
                    return false;

                if(getCard(X-1,Y) != null)
                    return false;

                if(getCard(X+1,Y) != null)
                    return false;

                if (getCard(X - 1, Y - 1) != null) { //checking if existing the down-right card adjacent with the card I want to play
                    if (!table.get(coordinates1).getPlayingBack()) { //checking if the card is played by front
                        if (table.get(coordinates1).getFront().getDownRight().getDrawing().equals(Suit.NULL)) { //checking if there is a corner available
                            return false;
                        }
                    }
                    numCornerCovered.put(coordinates1, true); //flagging that this corner is available
                }

                if (getCard(X + 1, Y - 1) != null) { //checking if existing the down-left card adjacent with the card I want to play
                    if (!table.get(coordinates2).getPlayingBack()) { //checking if the card is played by front
                        if (table.get(coordinates2).getFront().getDownLeft().getDrawing().equals(Suit.NULL)) { //checking if there is a corner available
                            return false;
                        }
                    }
                    numCornerCovered.put(coordinates2, true); //flagging that this corner is available
                }

                if (getCard(X - 1, Y + 1) != null) { //checking if existing the up-right card adjacent with the card I want to play
                    if (!table.get(coordinates3).getPlayingBack()) { //checking if the card is played by front
                        if (table.get(coordinates3).getFront().getUpRight().getDrawing().equals(Suit.NULL)) { //checking if there is a corner available
                            return false;
                        }
                    }
                    numCornerCovered.put(coordinates3, true); //flagging that this corner is available
                }

                if (getCard(X + 1, Y + 1) != null) { //checking if existing the up-left card adjacent with the card I want to play
                    if (!table.get(coordinates4).getPlayingBack()) { //checking if the card is played by front
                        if (table.get(coordinates4).getFront().getUpLeft().getDrawing().equals(Suit.NULL)) { //checking if there is a corner available
                            return false;
                        }
                    }
                    numCornerCovered.put(coordinates4, true); //flagging that this corner is available
                }




                //Check if the card is a goldCard and then use method enoughResources to check if it's playable
                if (card instanceof CardGold)
                    if (!enoughResources((CardGold) card)&& !(card.getPlayingBack()))
                        return false;


                //Flagging the corners that are covered with method setCovered
                // and updating resource with updateCounters method

                if (numCornerCovered.get(coordinates1)) {
                    table.get(coordinates1).getFront().getDownRight().setCovered(true);
                    updateCounters(table.get(coordinates1).getFront().getDownRight());
                }


                if (numCornerCovered.get(coordinates2)) {
                    table.get(coordinates2).getFront().getDownLeft().setCovered(true);
                    updateCounters(table.get(coordinates2).getFront().getDownLeft());
                }


                if (numCornerCovered.get(coordinates3)) {
                    table.get(coordinates3).getFront().getUpRight().setCovered(true);
                    updateCounters(table.get(coordinates3).getFront().getUpRight());
                }


                if (numCornerCovered.get(coordinates4)) {
                    table.get(coordinates4).getFront().getUpLeft().setCovered(true);
                    updateCounters(table.get(coordinates4).getFront().getUpLeft());
                }
            }

            return true;
        }




        /**
         * This method checks if the player has enough resources to play a gold card
         *
         * @param goldCard the gold card
         * @return true if the player has enough resources, false otherwise
         */
        public boolean enoughResources (CardGold goldCard){
            if (countAnimal < goldCard.getCostAnimal() || countFungi < goldCard.getCostFungi() ||
                    countInsect < goldCard.getCostInsect() || countPlant < goldCard.getCostPlant()) {
                return false;
            }
            return true;
        }


        //--------------GETTER AND SETTER----------------

        public Integer getCountInsect () {
            return countInsect;
        }

        public Integer getCountAnimal () {
            return countAnimal;
        }

        public Integer getCountPlant () {
            return countPlant;
        }

        public Player getPlayer() {
            return player;
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

        public void setCountInsect (CardPlaying card){
            countInsect += card.countResource(Suit.INSECT);
        }

        public void setCountAnimal (CardPlaying card){
            countAnimal += card.countResource(Suit.ANIMAL);
        }

        public void setCountPlant (CardPlaying card){
            countPlant += card.countResource(Suit.PLANT);
        }

        public void setCountFungi (CardPlaying card){
            countFungi += card.countResource(Suit.FUNGI);
        }

        public void setCountInkwell (CardResource card){
            countInkwell += card.countResource(Suit.INKWELL);
        }

        public void setCountQuill (CardResource card){
            countQuill += card.countResource(QUILL);
        }

        public void setCountManuscript (CardResource card){
            countManuscript += card.countResource(Suit.MANUSCRIPT);
        }

        public void setObjective (CardObjective objective){

        }

        public HashMap<ArrayList<Integer>, CardPlaying> getTable () {
            return table;
        }
//--------------GETTER AND SETTER ENDED----------------


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
                    //  case EMPTY -> null;
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
                ArrayList<Suit> symbols = card.getSymbols();
                for (Suit symbol : symbols) {
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



        public static void main(String[] args) {
            Face back = new Face(new Corner(Suit.EMPTY), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                    new Corner(Suit.EMPTY));

            Face front = new Face(new Corner(Suit.ANIMAL), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                    new Corner(Suit.FUNGI));

            Face frontGold = new Face(new Corner(Suit.INKWELL), new Corner(Suit.EMPTY), new Corner(Suit.EMPTY),
                    new Corner(Suit.EMPTY));

            ObjectiveCountingGold objectiveGold = new ObjectiveCountingGold(1,0,0);

            // the card resource is of type ANIMAL
            CardGold cardAnimal1 = new CardGold(0, frontGold, back, Suit.ANIMAL, 1, 2,1,0,0, objectiveGold);
            CardGold cardAnimal2 = new CardGold(0, frontGold, back, Suit.ANIMAL, 1, 4,2,0,0, objectiveGold);
            cardAnimal2.setPlayingBack(true);
            // now i have to make the starting card, i use the same front and back as the
            // resources
            // first i make the ArrayList for the centralsuit
            ArrayList<Suit> suitList = new ArrayList<Suit>();
            suitList.add(Suit.ANIMAL);
            suitList.add(Suit.PLANT);
            suitList.add(Suit.INSECT);

            CardStarting cardStarting = new CardStarting(3, front, back, suitList);

            // i make a PlayingStation with a cardObjective of type diagonalLeft and no
            // central card, for this test i don't need them
            //
            // the objective is of type Diagonal and type ANIMAL
            ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(Direction.LEFT, Suit.ANIMAL);
            CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);

            // for the second objective i set it to null
            Player player = new Player("test", 1);
            PlayingStation station = new PlayingStation(player, cardStarting, cardObjectiveTmp, null);

            // now i have to populate the table
            station.addCard(cardAnimal1, 41, 41);
            station.addCard(cardAnimal2, 39, 39);
            System.out.println(player.getPoints());
        }
    }

