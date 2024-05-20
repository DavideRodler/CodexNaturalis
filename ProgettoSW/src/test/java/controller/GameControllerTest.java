package controller;

import Network.Cli2;
import exception.ChangedStateException;
import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.*;
import model.objectives.ObjectiveDiagonal;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.jupiter.api.Test;
import exception.NotValidMoveException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerTest {

    @Test
    public void testChecknickname() throws NotValidMoveException, ChangedStateException {
        GameController game = new GameController();
        game.setPlayerNumber(4);
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("davide", TokenEnum.BLUE);
        assertEquals(true ,game.checkNicknameAvailability("isa"));
        game.addPlayer("isa", TokenEnum.YELLOW);
        assertEquals(true ,game.checkNicknameAvailability("giorgio"));
        assertEquals(false ,game.checkNicknameAvailability("isa"));

    }
    @Test
    public void testTokenAvailability() throws NotValidMoveException, ChangedStateException {
        GameController game = new GameController();
        System.out.println(game.getAvailableToken());
        game.setPlayerNumber(4);
        game.addPlayer("eric", TokenEnum.BLACK);
        System.out.println(game.getAvailableToken());
    }

  //*  @Test
    void setStartingCardForPlayers() throws NotValidMoveException, ChangedStateException {
        GameController game = new GameController();
        game.setPlayerNumber(2);

        assertEquals(true, game.checkNicknameAvailability("tommy"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLACK));
        game.addPlayer("tommy", TokenEnum.BLACK);

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa", TokenEnum.YELLOW);

        Cli2 cli = new Cli2();
    //    cli.showStartingCard(game.getBoard().getPlayer("tommy").getStation().getCardStarting());
    //    cli.showStartingCard(game.getStartingCard("isa"));
    }

    @Test
    void setupOfTheGameTest() throws NotValidMoveException, ChangedStateException {
        GameController game = new GameController();
        game.setPlayerNumber(2);

        //numero di player settato, si mescolano in automatico e posso aggiungere nickname e token

        assertEquals(true, game.checkNicknameAvailability("tommy"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLACK));
        game.addPlayer("tommy", TokenEnum.BLACK);

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa", TokenEnum.YELLOW);

        //tutti i giocatori sono salvati, gli obiettivi e le carte Starting sono distribuite in automatico

        Cli2 cli = new Cli2();

        //cli.showUpdatedHand(game.getPlayerHand("tommy"));
    //    cli.showStartingCard(game.getStartingCard("tommy"));
     //   game.setObjectiveOfPlayer("tommy",game.getObjectiveToChoose("tommy").get(1).getId());
    //    cli.showObjectiveCards(game.getObjectiveToChoose("tommy"));

    //    cli.showStartingCard(game.getStartingCard("isa"));
    //    cli.showStartingCard(game.getStartingCard("isa"));
    //    game.setObjectiveOfPlayer("isa",game.getObjectiveToChoose("isa").get(1).getId());
    //    cli.showObjectiveCards(game.getObjectiveToChoose("isa"));

        //ho settato gli obiettivi di tutti i player il gioco inizia in automatico

    }


    @Test
    void TestOfFourTurns() throws Exception {
        GameController game = new GameController();
        game.setPlayerNumber(2);

        //numero di player settato, si mescolano in automatico e posso aggiungere nickname e token

        assertEquals(true, game.checkNicknameAvailability("tommy"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLACK));
        game.addPlayer("tommy", TokenEnum.BLACK);

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa", TokenEnum.YELLOW);

        //tutti i giocatori sono salvati, gli obiettivi e le carte Starting sono distribuite in automatico

        Cli2 cli = new Cli2();

        //cli.showUpdatedHand(game.getPlayerHand("tommy"));
    //    cli.showStartingCard(game.getStartingCard("tommy"));
    //    game.setObjectiveOfPlayer("tommy",game.getObjectiveToChoose("tommy").get(1).getId());
    //    cli.showObjectiveCards(game.getObjectiveToChoose("tommy"));

    //    cli.showStartingCard(game.getStartingCard("isa"));
    //    cli.showStartingCard(game.getStartingCard("isa"));
    //    game.setObjectiveOfPlayer("isa",game.getObjectiveToChoose("isa").get(1).getId());
    //    cli.showObjectiveCards(game.getObjectiveToChoose("isa"));

        //ho settato gli obiettivi di tutti i player il gioco inizia in automatico
        String Player1 = game.getCurrentPlayer();
        ArrayList<CardResource> Player1Hand = game.getPlayerHand(Player1);
        game.addCardToPlayingStation(Player1, Player1Hand.get(0).getId(),39,39);
        game.addCardFromCentralCardsToPlayerHand(Player1,game.getBoard().getCentralCardsGold().get(0).getId());

        String Player2 = game.getCurrentPlayer();
        ArrayList<CardResource> Player2Hand = game.getPlayerHand(Player2);
        game.addCardToPlayingStation(Player2, Player2Hand.get(0).getId(),39,39);
        game.addCardFromCentralCardsToPlayerHand(Player2,game.getBoard().getCentralCardsGold().get(0).getId());

        game.addCardToPlayingStation(Player1, Player1Hand.get(0).getId(),38,38);
        game.addCardFromDeckToPlayerHand(Player1, DeckEnum.DECK_GOLD);

        game.addCardToPlayingStation(Player2, Player2Hand.get(0).getId(),38,38);
        game.addCardFromDeckToPlayerHand(Player2, DeckEnum.DECK_GOLD);
    }


    @Test
    void getPlayerNumber() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        assertEquals(2, game.getPlayerNumber());

    }

    @Test
    void getAvailableToken() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(3);
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("isa", TokenEnum.YELLOW);
        game.addPlayer("eric", TokenEnum.BLUE);
        System.out.println(game.getAvailableToken());
    }


    @Test
    void getStartingCard() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.YELLOW);
        game.addPlayer("dave", TokenEnum.BLUE);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        game.getBoard().getPlayer("isa").setStation(station);
    //    assertEquals(3,game.getStartingCard("isa").getId());

    }

    @Test
    void getObjectiveToChoose() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.YELLOW);
        game.addPlayer("dave", TokenEnum.BLUE);
        Cli2 cli = new Cli2();
    //    System.out.println(game.getObjectiveToChoose("isa"));
    //    cli.showObjectiveCards(game.getObjectiveToChoose("isa"));
    }

    @Test
    void getPlayerHand() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("isa", TokenEnum.YELLOW);
        Cli2 cli = new Cli2();
        System.out.println(game.getPlayerHand("isa"));
        //cli.showUpdatedHand(game.getPlayerHand("isa"));

    }

    @Test
    void getCurrentPlayer() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("isa", TokenEnum.YELLOW);
        game.getBoard().setCurrentPlayer("isa");
        assertEquals("isa", game.getCurrentPlayer());

    }

    @Test
    void isGamefinished() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa", TokenEnum.BLACK);
        game.addPlayer("tommy", TokenEnum.YELLOW);
        game.getBoard().getPlayer("isa").setPoints(12);
        game.getBoard().getPlayer("isa").setPoints(21);
        game.getBoard().setCurrentPlayer("tommy");
//        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
//        CardObjective cardObjectiveTmp1 = new CardObjective(4, 3, objectivetmp);
//        CardObjective cardObjectiveTmp2 = new CardObjective(4, 3, objectivetmp);
//        ArrayList<Player> players = new ArrayList<>();
//        Player isa = new Player("isa", TokenEnum.BLUE, new PlayingStation(new HashMap<>()), 21, new ArrayList<>());
//        Player tommy = new Player("tommy", TokenEnum.YELLOW, new PlayingStation(new HashMap<>()), 12, new ArrayList<>());
//        players.add(0, tommy);
//        players.add(1, isa);
//       LinkedList<CardStarting> deckCardStarting = new LinkedList<CardStarting>();
//        LinkedList<CardObjective> deckCardObjective = new LinkedList<CardObjective>();
//        LinkedList<CardResource> deckCardResource = new LinkedList<CardResource>();
//        LinkedList<CardGold> deckCardGold = new LinkedList<CardGold>();
//        ArrayList<CardResource> deckCardResource1 = new ArrayList<CardResource>();
//        ArrayList<CardGold> deckCardGold1 = new ArrayList<CardGold>();
//        GameState stato = GameState.FINISHED;
//        PlayingBoard board = new PlayingBoard(cardObjectiveTmp1, cardObjectiveTmp2, 2, players, deckCardStarting, deckCardResource, deckCardObjective, deckCardGold, "isa", deckCardResource1, deckCardGold1, stato);
        assertTrue(game.isGamefinished());
    }

    @Test
    void win() {

    }
}