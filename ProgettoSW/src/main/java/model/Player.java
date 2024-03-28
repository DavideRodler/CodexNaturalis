package model;


import model.cards.*;
import model.enums.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Player {
    private final String nickname;
    private final Integer playerNumber;
    private Integer numberTurns;
    private Integer points;
    private Color token;
    private PlayingStation station;
    private Integer pointsObjective;
    private ArrayList<CardPlaying> hand;


    // Costruttore
    public Player(String nickname, Integer playerNumber) {
        this.nickname = nickname;
        this.playerNumber = playerNumber;
        this.numberTurns = 0;
        this.points = 0;
        this.pointsObjective = 0;
        this.hand = new ArrayList<>(3);
    }

    //  Getter e Setter methods


    public ArrayList<CardPlaying> getHand() {
        return hand;
    }

    public Color getToken() {
        return token;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getPointsObjective() {
        return pointsObjective;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getNumberTurns() {
        return numberTurns;
    }

    public void setNumberTurns(Integer numberTurns) {
        this.numberTurns = numberTurns;
    }

    public void setPointsObjective(Integer pointsObjective) {
        this.pointsObjective = pointsObjective;
    }

    public void setStation(PlayingStation station) {
        this.station = station;
    }
    public PlayingStation getStation(){return station;}

    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * This method adds a card to the player's hand from the deck. Using a try catch to check if decks are empty
     * @param resourceCardDeck the deck from which the card is drawn
     * @param goldCardDeck the deck from which the card is drawn
     * @param chose the choice of the player about the deck (or if i place a gold card, have i to draw from deckCardGold?)
     */
    public void addCardFromDeck(LinkedList<CardResource> resourceCardDeck, LinkedList<CardGold> goldCardDeck, String chose) { //Have to add model.exception classes
        try {
            if (chose.equals("resource")) {
                hand.add(resourceCardDeck.pop());
            } else if (chose.equals("gold")) {
                hand.add(goldCardDeck.pop());
            } else {
                throw new IllegalArgumentException("Scelta non valida.");
            }
        } catch (DeckEmptyException exception) {
            System.out.println("MAZZO VUOTO");
        }
    }


        /**
         * This method removes a card from the player's hand, given the card instance, using functional programming,
         * returning exception if not present
         */
        public void removeCard(CardPlaying cardToRemove)throws Exception {
            CardPlaying cardPlay = hand.stream()
                    .filter(card -> card.equals(cardToRemove))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Card not found in hand"));

            hand.remove(cardPlay);
        }

    //FOR NOW I HAVE PUT THE CHOICE OF THE RANDOM PAKERS
    public void chooseToken() {
        Random random = new Random();
        this.token = Color.values()[random.nextInt(Color.values().length)];
    }

    /**
     * This method is used to choose objectives from the two drawn
     * @param objectives the two objectives drawn
     * @return the chosen objective
     */
    public CardObjective chooseObjective(CardObjective[] objectives){
        //scelta del client delle due carte
        return objectives[0];
    }

    /**
     * This method is used to add a card to the player's hand from the central deck
     * @param card the card drawn
     */
    public void addCardFromCentral(CardPlaying card) {
        hand.add(card);
    }


    /**
     * This method is used to check if the player has won
     */
    public boolean checkForWin() {
        if(this.points >= 20){
            return true;
        }
        return false;
    }


}


//!!!!!!!!!BISOGNA AGGIUNGERE IN OGNI METODO CHE MANIPOLA LA MANO EXCEPTION CHE CONTROLLANO CHE NON SI SUPERI MAI IL LIMITE DELLA MAX_LEN DELLA MANO (3)!!!!!