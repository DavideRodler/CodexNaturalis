package model;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import Observers.Observable;
import java.util.*;

public class PlayingBoard extends Observable{

    private LinkedList<CardGold> deckCardGold;
    private LinkedList<CardResource> deckCardResource;
    private LinkedList<CardObjective> deckCardObjective;


    private LinkedList<CardStarting> deckCardStarting;
    private ArrayList<Player> playerList;
    private CardResource CentralFirsResourceCard;
    private CardResource CentralSecondResourceCard;
    private CardGold CentralFristGoldCard;
    private CardGold CentralSecondGoldCard;
    private final CardObjective FirstObjective;
    private final CardObjective SecondObjective;


    public PlayingBoard(CardObjective firstObjective, CardObjective secondObjective, LinkedList<CardGold> deckCardGold, LinkedList<CardObjective> deckCardObjective, LinkedList<CardResource> deckCardResource, LinkedList<CardStarting> deckCardStarting) {
        FirstObjective = firstObjective;
        SecondObjective = secondObjective;
        this.deckCardGold = deckCardGold;
        this.deckCardObjective = deckCardObjective;
        this.deckCardResource = deckCardResource;
        this.deckCardStarting = deckCardStarting;
        this.playerList = new ArrayList<Player>();
    }





    //-------------------GETTER-----------------------------
    public ArrayList<Player> getPlayers(){return playerList;}
    public LinkedList<CardGold> getDeckCardGold() {
        return deckCardGold;
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

    public CardGold getCentralFristGoldCard() {
        return CentralFristGoldCard;
    }

    public CardGold getCentralSecondGoldCard() {
        return CentralSecondGoldCard;
    }

    public CardResource getCentralFirsResourceCard() {
        return CentralFirsResourceCard;
    }

    public CardResource getCentralSecondResourceCard() {
        return CentralSecondResourceCard;
    }

    public CardObjective getFirstObjective() {
        return FirstObjective;
    }

    public CardObjective getSecondObjective() {
        return SecondObjective;
    }
    //--------------------GETTING FASE ENDED----------------------------



    //--------------------SETTER----------------------------
    public void setCentralFirsResourceCard(CardResource centralFirsResourceCard) {
        CentralFirsResourceCard = centralFirsResourceCard;
    }

    public void setCentralFristGoldCard(CardGold centralFristGoldCard) {
        CentralFristGoldCard = centralFristGoldCard;
    }

    public void setCentralSecondResourceCard(CardResource centralSecondResourceCard) {
        CentralSecondResourceCard = centralSecondResourceCard;
    }

    public void setCentralSecondGoldCard(CardGold centralSecondGoldCard) {
        CentralSecondGoldCard = centralSecondGoldCard;
    }

    // shuffle players
    public void shufflePlayer(){
        Collections.shuffle(playerList);
    }
    public void addPlayer(Player p){
        this.playerList.add(p);
    }



    //--------------------SETTING FASE ENDED----------------------------


    /**
     * This method is used to draw two cards from the deck of starting cards
     */

   /* public void drawCardStarting(LinkedList<CardStarting> deckCardStarting, Player player){
        CardStarting card = deckCardStarting.remove();
        player.getStation().addCard(card, 39,39);
    }*/


//Drawing Cards from  the two decks

    public CardGold drawCardGold(){
        return deckCardGold.pop();
    }

    public CardResource drawCardResource(){
        return deckCardResource.pop();
    }


}

