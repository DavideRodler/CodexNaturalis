package model.client;

import model.Player;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.client.ReductPlayer;
import model.enums.GameState;
import Socket.Messages.ChangeStateMessage;
import socket.Messages.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientBoard implements Serializable {

    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private CardObjective firstObjective;
    private CardObjective secondObjective;
    private GameState gameState;
    private Player myplayer;
    private ArrayList<ReductPlayer> otherplayers;

    public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, ArrayList<ReductPlayer> otherplayers, Player myplayer, ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.otherplayers = otherplayers;
        this.myplayer = myplayer;
        this.centralCardsResource = centralCardsResource;
        this.centralCardsGold = centralCardsGold;
        this.gameState = gameState;
    }

    public ClientBoard(CardObjective firstObjective, CardObjective secondObjective, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
    }

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

    public ArrayList<ReductPlayer> getOtherplayers() {
        return otherplayers;
    }

    public CardObjective getSecondObjective() {
        return secondObjective;
    }
    public GameState getGameState() {
        return gameState;
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

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setFirstObjective(CardObjective firstObjective) {
        this.firstObjective = firstObjective;
    }

    public void setSecondObjective(CardObjective secondObjective) {
        this.secondObjective = secondObjective;
    }

    public void update(Message message) {
        switch (message.getType()){
            case "ChangeState":
                gameState = ((ChangeStateMessage) message).getGameState();
                break;
            default:
                break;
        }
    }
}