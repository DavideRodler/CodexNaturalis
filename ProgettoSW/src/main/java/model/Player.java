package model;


import Observers.ModelObserver;
import model.cards.*;
import model.enums.TokenEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import Exception.DeckEmptyException;


public class Player extends ModelObserver implements Serializable {
    private final String nickname;
    private int points;
    private final TokenEnum token;
    private PlayingStation station;
    private ArrayList<CardResource> hand;


    // Costruttore

    public Player(String nickname, TokenEnum token, PlayingStation station, int points, ArrayList<CardResource> hand) {
        this.nickname = nickname;
        this.token = token;
        this.station = station;
        this.points = points;
        this.hand = hand;
    }


    //setter
    public void setPoints(int points) {
        this.points = points;
    }
    public void setStation(PlayingStation station) {
        this.station = station;
    }

    //getter
    public ArrayList<CardResource> getHand() {
        return hand;
    }
    public TokenEnum getToken() {
        return token;
    }
    public String getNickname() {
        return nickname;
    }
    public Integer getPoints() {
        return points;
    }
    public PlayingStation getStation(){return station;}

    /**
     * This method removes a card from the player's hand, given the card instance, using functional programming,
     * returning exception if not present
     * @param cardToRemove the card to remove
     */
    public void removeCard(int cardId)throws Exception {
        CardPlaying cardPlay = hand.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new Exception("Card not found in hand"));

        hand.remove(cardPlay);
    }
}