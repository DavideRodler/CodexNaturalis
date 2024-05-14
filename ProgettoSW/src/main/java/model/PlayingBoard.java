package model;

import model.cards.*;
import model.enums.GameState;
import model.enums.TokenEnum;
import observers.ObservableModel;
import socket.Messages.ChangeStateMessage;
import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;
import java.util.*;

public class PlayingBoard extends ObservableModel {

    private LinkedList<CardGold> deckCardGold;
    private LinkedList<CardResource> deckCardResource;
    private LinkedList<CardObjective> deckCardObjective;
    private LinkedList<CardStarting> deckCardStarting;
    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private CardObjective firstObjective;
    private CardObjective secondObjective;


    //the current player playing
    private String currentPlayer;
    //saving the number of player
    private int playernumber;
    //the player map that for each nickname has a player
    private ArrayList<Player> players;
    //gameState
    private GameState gameState;

    public PlayingBoard(CardObjective firstObjective, CardObjective secondObjective, int playernumber, ArrayList<Player> playerList, LinkedList<CardStarting> deckCardStarting, LinkedList<CardResource> deckCardResource, LinkedList<CardObjective> deckCardObjective, LinkedList<CardGold> deckCardGold, String currentPlayer, ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState) {
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.playernumber = playernumber;
        this.players = playerList;
        this.deckCardStarting = deckCardStarting;
        this.deckCardResource = deckCardResource;
        this.deckCardObjective = deckCardObjective;
        this.deckCardGold = deckCardGold;
        this.currentPlayer = currentPlayer;
        this.centralCardsResource = centralCardsResource;
        this.centralCardsGold = centralCardsGold;
        this.gameState = gameState;
    }

    public PlayingBoard() {
        this.playernumber = 0;
        this.players = new ArrayList<>();
        this.deckCardStarting = new LinkedList<>();
        this.deckCardResource = new LinkedList<>();
        this.deckCardObjective = new LinkedList<>();
        this.deckCardGold = new LinkedList<>();
        this.centralCardsResource = new ArrayList<>();
        this.centralCardsGold = new ArrayList<>();
    }


    //-------------------GETTER-----------------------------
    public LinkedList<CardGold> getDeckCardGold() {
        return deckCardGold;
    }

    public CardObjective getFirstObjective() {
        return firstObjective;
    }

    public CardObjective getSecondObjective() {
        return secondObjective;
    }

    public int getPlayernumber() {
        return playernumber;
    }

    public ArrayList<CardGold> getCentralCardsGold() {
        return centralCardsGold;
    }

    public ArrayList<CardResource> getCentralCardsResource() {
        return centralCardsResource;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public LinkedList<CardObjective> getDeckCardObjective() {
        return deckCardObjective;
    }

    public LinkedList<CardResource> getDeckCardResource() {
        return deckCardResource;
    }

    public LinkedList<CardStarting> getDeckCardStarting() {
        return deckCardStarting;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    //--------------------SETTER----------------------------


    public void addPlayer(Player p) {
        players.add(p);
    }

    public void setPlayernumber(int playernumber) {
        this.playernumber = playernumber;
    }

    public void setCentralCardsGold(ArrayList<CardGold> centralCardsGold) {
        this.centralCardsGold = centralCardsGold;
    }

    public void setCentralCardsResource(ArrayList<CardResource> centralCardsResource) {
        this.centralCardsResource = centralCardsResource;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setDeckCardGold(LinkedList<CardGold> deckCardGold) {
        this.deckCardGold = deckCardGold;
    }

    public void setDeckCardObjective(LinkedList<CardObjective> deckCardObjective) {
        this.deckCardObjective = deckCardObjective;
    }

    public void setDeckCardResource(LinkedList<CardResource> deckCardResource) {
        this.deckCardResource = deckCardResource;
    }

    public void setDeckCardStarting(LinkedList<CardStarting> deckCardStarting) {
        this.deckCardStarting = deckCardStarting;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        try {
            notifyObservers(new ChangeStateMessage("ChangeState", gameState));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFirstObjective(CardObjective firstObjective) {
        this.firstObjective = firstObjective;
    }

    public void setSecondObjective(CardObjective secondObjective) {
        this.secondObjective = secondObjective;
    }
//--------------------SETTING FASE ENDED----------------------------


    public Player getPlayer(String nickname) throws IllegalStateException {
        return players.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player " + nickname + " not found"));
    }
    private int getPlayerPositon(String nickname){
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equals(nickname)){
                return i;
            }
        }
        return -1;
    }


    public Optional<CardResource> getCardResource(int id) {
        return centralCardsResource
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Optional<CardGold> getCardGold(int id) {
        return centralCardsGold
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    // shuffle players
    public void shufflePlayer() {
        Collections.shuffle(players);
        HashMap<String,TokenEnum> playerslist = new HashMap<String, TokenEnum>();
        for (Player p : players) {
            playerslist.put(p.getNickname(),p.getToken());

        }

        try {
            notifyObservers(new PlayersInfoMessage(playerslist));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getnextPlayer(){
        int indexOfCurrentPlayer = players.indexOf(getPlayer(currentPlayer));
        if (indexOfCurrentPlayer == players.size()-1){
            return players.get(0).getNickname();
        }
        else {
            return players.get(indexOfCurrentPlayer+1).getNickname();
        }
    }
}
