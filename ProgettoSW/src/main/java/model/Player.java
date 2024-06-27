package model;

import Socket.Messages.*;
import model.cards.*;
import model.enums.TokenEnum;
import observers.ObservableModel;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This class represents the player of the game
 */
public class Player extends ObservableModel implements Serializable{
    private final String nickname;
    private int points;
    private TokenEnum token;
    private PlayingStation station;
    private ArrayList<CardResource> hand;
    private ArrayList<CardObjective> selectibleObjectives;
    private CardObjective secretObjective;


    public Player(String nickname, PlayingStation station, int points, ArrayList<CardResource> hand) {
        this.nickname = nickname;
        this.station = station;
        this.points = points;
        this.hand = hand;
        this.selectibleObjectives = new ArrayList<>();
        this.secretObjective = null;
    }

    public Player(String nickname,TokenEnum token, PlayingStation station, int points, ArrayList<CardResource> hand) {
        this.nickname = nickname;
        this.token = token;
        this.station = station;
        this.points = points;
        this.hand = hand;
        this.selectibleObjectives = new ArrayList<>();
        this.secretObjective = null;
    }



    public void setPoints(int points) {
        this.points = points;
    }
    public void setStation(PlayingStation station) {
        this.station = station;
    }
    public void setSelectibleObjectivesWithObserver(ArrayList<CardObjective> selectibleObjectives) {
        setSelectibleObjectives(selectibleObjectives);
        Message message = new SelectableObjMessage(selectibleObjectives);
        try {
            this.notifySpecificObserver(this.nickname, message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void setSelectibleObjectives(ArrayList<CardObjective> selectibleObjectives){
        this.selectibleObjectives = selectibleObjectives;
    }
    public void setToken(TokenEnum token) {
        this.token = token;
        Message message = new TokenOfPlayerMessage(this.nickname, token);
        try {
            this.notifyObservers(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSecretObjectiveWithObs(CardObjective secretObjective) {
        this.secretObjective = secretObjective;
        Message message = new SelectionOfSecretObjMessage(secretObjective.getId());
        try {
            notifySpecificObserver(nickname, message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void setSecretObjective(CardObjective secretObjective) {
        this.secretObjective = secretObjective;
    }
    public void setHand(ArrayList<CardResource> hand) {
        this.hand = hand;
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

    public CardObjective getSecretObjective() {
        return secretObjective;
    }

    /**
     * This method removes a card from the player's hand, given the card instance, using functional programming,
     * returning exception if not present
     *
     * @return
     */
    public void removeCardFromHandWithObs(int cardId)throws Exception {
        removeCardFromHand(cardId);
        Message message = new CardRemovedFromHandMessage(cardId);
        try {
            notifySpecificObserver(nickname, message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeCardFromHand(int cardId) throws Exception {
        hand.remove(hand.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new Exception("Card not found in hand")));
    }
    public int getNumberOfCardInHand(){
        return hand.size();
    }
    public void addCardToHandWithObserver(CardResource card) {
        hand.add(card);
        Message message = new CardAddedToHandMessage(card);
        try {
            notifySpecificObserver(this.nickname, message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }





}