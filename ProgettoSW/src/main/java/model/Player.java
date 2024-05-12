package model;


import model.cards.*;
import model.enums.TokenEnum;

import java.io.Serializable;
import java.util.ArrayList;


public class Player implements Serializable {
    private final String nickname;
    private int points;
    private final TokenEnum token;
    private PlayingStation station;
    private ArrayList<CardResource> hand;
    private ArrayList<CardObjective> selectibleObjectives;


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
    public void setSelectibleObjectives(ArrayList<CardObjective> selectibleObjectives) {
        this.selectibleObjectives = selectibleObjectives;
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
    public ArrayList<CardObjective> getSelectibleObjectives() {
        return selectibleObjectives;
    }
    public PlayingStation getStation(){return station;}


    /**
     * This method removes a card from the player's hand, given the card instance, using functional programming,
     * returning exception if not present
     *
     * @param cardToRemove the card to remove
     * @return
     */
    public void removeCardFromHand(int cardId)throws Exception {
         hand.remove(hand.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new Exception("Card not found in hand")));

    }
    public int getNumberOfCardInHand(){
        return hand.size();
    }
    public void addCardToHand(CardResource card){
        hand.add(card);
    }



}