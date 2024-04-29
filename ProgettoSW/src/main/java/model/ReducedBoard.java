package model;

import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ReducedBoard implements Serializable {
    private final String nickName;
    private ArrayList<CardPlaying> hand ;
    private Map<ArrayList<Integer>, CardPlaying> playingStation;
    private final CardObjective secretObjective;

    public ReducedBoard(String nickName, ArrayList<CardPlaying> hand, Map<ArrayList<Integer>, CardPlaying> playingStation, CardObjective secretObjective) {
        this.nickName = nickName;
        this.hand = hand;
        this.playingStation = playingStation;
        this.secretObjective = secretObjective;
    }

    public String getNickName() {
        return nickName;
    }

    public ArrayList<CardPlaying> getHand() {
        return hand;
    }

    public Map<ArrayList<Integer>, CardPlaying> getPlayingStation() {
        return playingStation;
    }

    public void addCardToPlayingStation( CardPlaying card, Integer X, Integer Y) {
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(X);
        coordinates.add(Y);
        playingStation.put(coordinates, card);
        hand.remove(card);
    }

    public void addCardToHand(CardPlaying card) {
        hand.add(card);
    }
}