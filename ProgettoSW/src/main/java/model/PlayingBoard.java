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
    private Map<String,Player> playerMap;
    private CardResource[] centralCards = new CardResource[4];
    private final CardObjective FirstObjective;
    private final CardObjective SecondObjective;


    public PlayingBoard(CardObjective firstObjective, CardObjective secondObjective, LinkedList<CardGold> deckCardGold, LinkedList<CardObjective> deckCardObjective, LinkedList<CardResource> deckCardResource, LinkedList<CardStarting> deckCardStarting) {
        this.FirstObjective = firstObjective;
        this.SecondObjective = secondObjective;
        this.deckCardResource = deckCardResource;
        this.deckCardObjective = deckCardObjective;
        this.deckCardGold = deckCardGold;
        this.deckCardStarting = deckCardStarting;
        this.centralCards[0] = (deckCardResource.pop());
        this.centralCards[1] = (deckCardResource.pop());
        this.centralCards[2] = (deckCardGold.pop());
        this.centralCards[3] = (deckCardGold.pop());
        this.playerMap = new HashMap<>();
    }





    //-------------------GETTER-----------------------------
    public Map<String,Player> getPlayers(){
        return playerMap;
    }
    public LinkedList<CardGold> getDeckCardGold() {
        return deckCardGold;
    }

    public CardResource[] getCentralCards() {
        return centralCards;
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

    public CardObjective getFirstObjective() {
        return FirstObjective;
    }

    public CardObjective getSecondObjective() {
        return SecondObjective;
    }
    //--------------------GETTING FASE ENDED----------------------------



    //--------------------SETTER----------------------------

    // shuffle players
    public void shufflePlayer(){
        List<Player> players = new ArrayList<>(playerMap.values());
        Collections.shuffle(players);
        playerMap.clear();
        for (Player player : players) {
            playerMap.put(player.getNickname(), player);
        }
    }

    public void addPlayer(Player p){
        this.playerMap.put(p.getNickname(),p);
        ArrayList<CardPlaying> hand = new ArrayList<>();
        hand.add(drawCardResource());
        hand.add(drawCardResource());
        hand.add(drawCardGold());
        p.setHand(hand);
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


    public List<String> PlayerTurnOrder() {
        shufflePlayer();
        List<String> players = new ArrayList<>(playerMap.keySet().stream().toList());
        return players;
    }
}

