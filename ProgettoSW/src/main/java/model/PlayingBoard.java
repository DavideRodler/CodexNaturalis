package model;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.decktameplate.Deckconstructor;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class PlayingBoard {

    private static LinkedList<CardGold> deckCardGold;
    private static LinkedList<CardResource> deckCardResource;
    private static LinkedList<CardObjective> deckCardObjective;
    private static LinkedList<CardStarting> deckCardStarting;
    private static ArrayList<Player> playerList;
    private CardResource CentralFirstCard;
    private CardResource CentralSecondCard;
    private CardResource CentralThirdCard;
    private CardResource CentralFourthCard;
    private CardObjective FirstObjective;
    private CardObjective SecondObjective;

    private static Deckconstructor deckconstructor;


    //Constructor
    public PlayingBoard(CardResource centralFirstCard, CardResource centralSecondCard, CardResource centralThirdCard, CardResource centralFourthCard, CardObjective firstObjective, CardObjective secondObjective) throws IOException, ParseException {
        deckCardGold = deckconstructor.GoldCardDeck();
        deckCardResource = deckconstructor.ResourceCardDeck();
        deckCardObjective = deckconstructor.ObjectiveCardDeck();
        deckCardStarting = deckconstructor.StartingCardDeck();
        playerList = new ArrayList<>();
        CentralFirstCard = centralFirstCard;
        CentralSecondCard = centralSecondCard;
        CentralThirdCard = centralThirdCard;
        CentralFourthCard = centralFourthCard;
        FirstObjective = firstObjective;
        SecondObjective = secondObjective;
    }


    //-------------------GETTER-----------------------------
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

    //Setter that initializes the player map (randomly selects the first player and assigns turns to all other players clockwise)
    public void setPlayer(Player[] players, int size) {

        // Generating a random number between 0 and the length of the vector
        Random random = new Random();
        int N = random.nextInt(size);

        //Adding players starting from position N-1
        for (int i = N-1; i < size; i++) {
            playerList.add(i - N + 2, players[i]);
        }

        //Adding players from position 0 to position N-2
        for (int i = 0; i < N-1; i++) {
            playerList.add(i + size - N + 1, players[i]);
        }
    }

    //--------------------SETTING FASE ENDED----------------------------




    //CardStarting[] drawCardStarting(){

    //}

    //CardObjective[] pickTwoObjectives(){

    //}



    //MAIN CHE FA DA 'CONTROLLER' E CREA LA PLAYINGBOARD ASSEGNANDOLI I DECK TRAMITE COSTRUTTORI
    public static void main(String[] args) throws IOException, ParseException {
        PlayingBoard board = new PlayingBoard(null, null, null, null, null, null);
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
