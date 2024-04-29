package model;


import Observers.ModelObserver;
import model.cards.*;
import model.enums.Color;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import Exception.DeckEmptyException;
import Exception.InvalidPlacingCondition;


public class Player extends ModelObserver implements Serializable {
    private final String nickname;
    private Integer playerNumber;
    private Integer numberTurns;
    private Integer points;
    private Color token;
    private PlayingStation station;
    private Integer pointsObjective;
    private ArrayList<CardPlaying> hand;


    // Costruttore
    public Player(String nickname) {
        this.nickname = nickname;
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
    public CardPlaying addCardFromDeck(LinkedList<CardResource> resourceCardDeck, LinkedList<CardGold> goldCardDeck, String chose) {

        try {
            if(resourceCardDeck.isEmpty() || goldCardDeck.isEmpty()) {
                throw new DeckEmptyException("MAZZO VUOTO");
            }
            else if (chose.equals("resource")) {
                CardPlaying card = resourceCardDeck.pop();
                hand.add(card);
                return card;
            } else if (chose.equals("gold")) {
                CardPlaying card1 = goldCardDeck.pop();
                hand.add(card1);
                return card1;
            } else {
                throw new IllegalArgumentException("Scelta non valida.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DeckEmptyException d) {
            System.out.println(d.getMessage());
        }
        return goldCardDeck.pop();

    }


        /**
         * This method removes a card from the player's hand, given the card instance, using functional programming,
         * returning exception if not present
         * @param cardToRemove the card to remove
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
     */
    public CardPlaying addCardFromCentral(CardResource[] centralCards, LinkedList<CardResource> resourceCardDeck, LinkedList<CardGold> goldCardDeck, String chose) {
        switch (chose) {
            case "upleft":
                hand.add(centralCards[0]);
                CardPlaying card = centralCards[0];
                centralCards[0] = resourceCardDeck.pop();
                return card;
            case "upright":
                hand.add(centralCards[1]);
                CardPlaying card1 = centralCards[1];
                centralCards[1] = resourceCardDeck.pop();
                return card1;
            case "downleft":
                hand.add(centralCards[2]);
                CardPlaying card2 = centralCards[2];
                centralCards[2] = goldCardDeck.pop();
                return card2;
            default:
                hand.add(centralCards[3]);
                CardPlaying card3 = centralCards[3];
                centralCards[3] = goldCardDeck.pop();
                return card3;
        }
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


    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setHand(ArrayList<CardPlaying> hand) {
        this.hand = hand;
    }
}