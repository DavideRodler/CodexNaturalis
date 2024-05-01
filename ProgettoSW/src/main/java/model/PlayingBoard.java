package model;

import Observers.ModelObserver;
import model.cards.*;
import Observers.Observable;

import java.io.Serializable;
import java.util.*;

public class PlayingBoard extends ModelObserver implements Serializable {

    private LinkedList<CardGold> deckCardGold;
    private LinkedList<CardResource> deckCardResource;
    private LinkedList<CardObjective> deckCardObjective;
    private LinkedList<CardStarting> deckCardStarting;
    private ArrayList<CardGold> centralCardsGold;
    private ArrayList<CardResource> centralCardsResource;
    private final CardObjective firstObjective;
    private final CardObjective secondObjective;


    // the list with the name of the player in the order that they are playing
    private ArrayList<String> turnList;
    //the current player playing
    private String currentPlayer;
    //saving the number of player
    private int playernumber;
    //the player map that for each nickname has a player
    private ArrayList<Player> playerList;

    public PlayingBoard(LinkedList<CardGold> deckCardGold, LinkedList<CardObjective> deckCardObjective, LinkedList<CardResource> deckCardResource, LinkedList<CardStarting> deckCardStarting, CardObjective firstObjective, CardObjective secondObjective) {
        this.deckCardGold = deckCardGold;
        this.deckCardObjective = deckCardObjective;
        this.deckCardResource = deckCardResource;
        this.deckCardStarting = deckCardStarting;
        this.firstObjective = firstObjective;
        this.secondObjective = secondObjective;
        this.playerList = new ArrayList<Player>();
        this.centralCardsResource = new ArrayList<CardResource>();
        this.centralCardsGold = new ArrayList<CardGold>();

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

    //getting a card from decks
    public CardResource getCardResourceFromDeck(){
        return deckCardResource.pop();
    }
    public CardGold getCardGoldFromDeck(){
        return deckCardGold.pop();
    }

    public CardObjective getCardObjectiveFromDeck(){
        return deckCardObjective.pop();
    }

    public CardStarting getCardStartingFromDeck(){
        return deckCardStarting.pop();
    }   public int getPlayernumber() {
        return playernumber;
    }


    //--------------------SETTER----------------------------


    public void addPlayer(Player p){
        playerList.add(p);
        turnList.add(p.getNickname());
    }

    public void setPlayernumber(int playernumber) {
        this.playernumber = playernumber;
    }


    //--------------------SETTING FASE ENDED----------------------------

    public boolean nicknameChecker(String nickname) {
        for (String name : turnList){
            if (nickname.equals(name))return false;
        }
        return true;
    }

    public CardResource getCentralCardResource(int pos){
        CardResource tmp = centralCardsResource.get(pos);
        centralCardsResource.remove(pos);
        centralCardsResource.add(pos, deckCardResource.pop());
        return tmp;

    }

    public CardGold getCentralCardGold(int pos){
        CardGold tmp = centralCardsGold.get(pos);
        centralCardsResource.remove(pos);
        centralCardsGold.add(pos, deckCardGold.pop());
        return tmp;
    }



    //da aggiungere la exception per player non trovato
    public Player getPlayer(String nickname) throws InterruptedException{
        for(Player player : playerList){
            if (player.getNickname().equals(nickname)){
                return player;
            }
        }
        throw  new InterruptedException();
    }

    // shuffle players
    public void shufflePlayer(){
        Collections.shuffle(turnList);
    }

}

