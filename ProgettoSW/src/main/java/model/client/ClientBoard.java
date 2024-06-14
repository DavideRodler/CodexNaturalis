package model.client;

import exception.NonePlayerFoundException;
import model.Player;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.SuitEnum;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientBoard implements Serializable {

    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private CardObjective firstObjective;
    private CardObjective secondObjective;
    private GameState gameState;
    private String currentPlayer;
    private Player myplayer;
    private String currentPlayerName;
    private ArrayList<ReductPlayer> otherplayers;
    private SuitEnum backOfResourceDeck;
    private SuitEnum backOfGoldDeck;

    public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, ArrayList<ReductPlayer> otherplayers, Player myplayer, ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.otherplayers = otherplayers;
        this.myplayer = myplayer;
        this.centralCardsResource = centralCardsResource;
        this.centralCardsGold = centralCardsGold;
        this.gameState = gameState;
    }

   // public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, GameState gameState) {
   //     this.firstObjective = firstObjective;
   //     this.secondObjective = secondObjective;
   // }


    public ArrayList<CardGold> getCentralCardsGold() {
        return centralCardsGold;
    }

    public ArrayList<CardResource> getCentralCardsResource() {
        return centralCardsResource;
    }

    public CardObjective getFirstObjective() {
        return firstObjective;
    }

    public Player getMyplayer() {
        return myplayer;
    }
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<ReductPlayer> getOtherplayers() {
        return otherplayers;
    }

    public CardObjective getSecondObjective() {
        return secondObjective;
    }
    public GameState getGameState() {
        return gameState;
    }

    public SuitEnum getBackOfResourceDeck() {
        return backOfResourceDeck;
    }

    public SuitEnum getBackOfGoldDeck() {
        return backOfGoldDeck;
    }

    public void setCentralCardsGold(ArrayList<CardGold> centralCardsGold) {
        this.centralCardsGold = centralCardsGold;
    }

    public void setCentralCardsResource(ArrayList<CardResource> centralCardsResource) {
        this.centralCardsResource = centralCardsResource;
    }

    public void setMyplayer(Player myplayer) {
        this.myplayer = myplayer;
    }

    public void setOtherplayers(ArrayList<ReductPlayer> otherplayers) {
        this.otherplayers = otherplayers;
    }
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ReductPlayer getOtherPlayer(String nickname) throws NonePlayerFoundException{
        return otherplayers.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(()->
                        new NonePlayerFoundException("Player not found"));
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setFirstObjective(CardObjective firstObjective) {
        this.firstObjective = firstObjective;
    }

    public void setSecondObjective(CardObjective secondObjective) {
        this.secondObjective = secondObjective;
    }

    public void setBackOfResourceDeck(SuitEnum backOfResourceDeck) {
        this.backOfResourceDeck = backOfResourceDeck;
    }

    public void setBackOfGoldDeck(SuitEnum backOfGoldDeck) {
        this.backOfGoldDeck = backOfGoldDeck;
    }
}