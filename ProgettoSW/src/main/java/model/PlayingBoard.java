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
    private Map<String,Player> playerMap;
    private CardResource CentralFirstCard;
    private CardResource CentralSecondCard;
    private CardResource CentralThirdCard;
    private CardResource CentralFourthCard;
    private final CardObjective FirstObjective;
    private final CardObjective SecondObjective;


    public PlayingBoard(CardObjective firstObjective, CardObjective secondObjective, LinkedList<CardGold> deckCardGold, LinkedList<CardObjective> deckCardObjective, LinkedList<CardResource> deckCardResource, LinkedList<CardStarting> deckCardStarting) {
        FirstObjective = firstObjective;
        SecondObjective = secondObjective;
        this.deckCardGold = deckCardGold;
        this.deckCardObjective = deckCardObjective;
        this.deckCardResource = deckCardResource;
        this.deckCardStarting = deckCardStarting;
        this.playerMap = new HashMap<>();
    }





    //-------------------GETTER-----------------------------
    public Map<String,Player> getPlayers(){return playerMap;}
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

    public CardResource getCentralSecondCard() {
        return CentralSecondCard;
    }

    public CardResource getCentralFourthCard() {
        return CentralFourthCard;
    }

    public CardResource getCentralFirstCard() {
        return CentralFirstCard;
    }

    public CardResource getCentralThirdCard() {
        return CentralThirdCard;
    }

    public CardObjective getFirstObjective() {
        return FirstObjective;
    }

    public CardObjective getSecondObjective() {
        return SecondObjective;
    }
    //--------------------GETTING FASE ENDED----------------------------



    //--------------------SETTER----------------------------
    public void setCentralFirstCard(CardResource centralFirstCard) {
        CentralFirstCard = centralFirstCard;
    }

    public void setCentralThirdCard(CardResource centralThirdCard) {
        CentralThirdCard = centralThirdCard;
    }

    public void setCentralSecondCard(CardResource centralSecondCard) {
        CentralSecondCard = centralSecondCard;
    }

    public void setCentralFourthCard(CardResource centralFourthCard) {
        CentralFourthCard = centralFourthCard;
    }

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
    }



    //--------------------SETTING FASE ENDED----------------------------


    /**
     * This method is used to draw two cards from the deck of starting cards
     * @param deckCardStarting passing deck of starting cards
     */

   /* public void drawCardStarting(LinkedList<CardStarting> deckCardStarting, Player player){
        CardStarting card = deckCardStarting.remove();
        player.getStation().addCard(card, 39,39);
    }*/


    /**
     * This method is used to draw two card from the deck of resource cards
     * @param deckCardObjective passing deck of objective cards
     * @return the two card drawn
     */
    public CardObjective[] pickTwoObjectives(LinkedList<CardObjective> deckCardObjective){
        CardObjective[] objectives = new CardObjective[2];
        for (int i = 0; i < 2; i++) {
            objectives[i] = deckCardObjective.remove();
        }
        return objectives;
    }






}

