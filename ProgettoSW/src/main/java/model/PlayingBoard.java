package model;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class PlayingBoard {

    private static LinkedList<CardGold> deckCardGold;
    private static LinkedList<CardResource> deckCardResource;
    private static LinkedList<CardObjective> deckCardObjective;
    private static LinkedList<CardStarting> deckCardStarting;
    private ArrayList<Player> playerList;
    private CardResource CentralFirstCard;
    private CardResource CentralSecondCard;
    private CardResource CentralThirdCard;
    private CardResource CentralFourthCard;
    private final CardObjective FirstObjective;
    private final CardObjective SecondObjective;




    //Constructor
    public PlayingBoard( CardObjective firstObjective, CardObjective secondObjective) {
        playerList = new  ArrayList<Player>();
        FirstObjective = firstObjective;
        SecondObjective = secondObjective;
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
        Collections.shuffle(playerList);
    }
    public void addPlayer(Player p){
        this.playerList.add(p);
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



    //MAIN CHE FA DA 'CONTROLLER' E CREA LA PLAYINGBOARD ASSEGNANDOLI I DECK TRAMITE COSTRUTTORI
    public static void main(String[] args) throws IOException, ParseException {
        PlayingBoard board = new PlayingBoard(null, null);
        for (CardStarting c : deckCardStarting) {
            System.out.println(c.getFront().getUpRight().getDrawing());
            System.out.println(c.getFront().getUpLeft().getDrawing());
            System.out.println(c.getFront().getDownRight().getDrawing());
            System.out.println(c.getFront().getDownLeft().getDrawing());
            System.out.println(c.getBack().getUpRight().getDrawing());
            System.out.println(c.getBack().getUpLeft().getDrawing());
            System.out.println(c.getBack().getDownRight().getDrawing());
            System.out.println(c.getBack().getDownLeft().getDrawing());
            System.out.println(c.getSymbols());
            System.out.println("------------------------------------------------------");
        }
        System.out.println("*******************************************************");
        for (CardResource c : deckCardResource) {
            System.out.println(c.getFront().getUpRight().getDrawing());
            System.out.println(c.getFront().getUpLeft().getDrawing());
            System.out.println(c.getFront().getDownRight().getDrawing());
            System.out.println(c.getFront().getDownLeft().getDrawing());
            System.out.println(c.getBack().getUpRight().getDrawing());
            System.out.println(c.getBack().getUpLeft().getDrawing());
            System.out.println(c.getBack().getDownRight().getDrawing());
            System.out.println(c.getBack().getDownLeft().getDrawing());
            System.out.println(c.getSymbol());
            System.out.println(c.getPoints());
            System.out.println("------------------------------------------------------");
        }
        System.out.println("*******************************************************");
        for (CardGold c : deckCardGold) {
            System.out.println(c.getFront().getUpRight().getDrawing());
            System.out.println(c.getFront().getUpLeft().getDrawing());
            System.out.println(c.getFront().getDownRight().getDrawing());
            System.out.println(c.getFront().getDownLeft().getDrawing());
            System.out.println(c.getBack().getUpRight().getDrawing());
            System.out.println(c.getBack().getUpLeft().getDrawing());
            System.out.println(c.getBack().getDownRight().getDrawing());
            System.out.println(c.getBack().getDownLeft().getDrawing());
            System.out.println(c.getSymbol());
            System.out.println(c.getPoints());
            System.out.println(c.getCostAnimal());
            System.out.println(c.getCostFungi());
            System.out.println(c.getCostInsect());
            System.out.println(c.getCostPlant());
            System.out.println("------------------------------------------------------");
        }
        System.out.println("*******************************************************");
        for (CardObjective c : deckCardObjective){
            System.out.println(c.getPoints());
            System.out.println(c.getObjective());
            System.out.println("------------------------------------------------------");
        }


    }

}
