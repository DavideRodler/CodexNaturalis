package controller;

import View.CLI.Cli2;
import exception.ChangedStateException;
import model.PlayingStation;
import model.cards.CardResource;
import model.enums.*;
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;
import exception.NotValidMoveException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerTest {

    @Test
    public void testChecknickname() throws NotValidMoveException, ChangedStateException {
        GameController game = new GameController();
        game.setPlayerNumber(4);
        game.addPlayer("tommy");
        game.addPlayer("davide");
        assertEquals(true ,game.checkNicknameAvailability("isa"));
        game.addPlayer("isa");
        assertEquals(true ,game.checkNicknameAvailability("giorgio"));
        assertEquals(false ,game.checkNicknameAvailability("isa"));

    }
    @Test
    public void testTokenAvailability() throws NotValidMoveException, ChangedStateException {
        GameController game = new GameController();
        System.out.println(game.getAvailableToken());
        game.setPlayerNumber(4);
        game.addPlayer("eric");
        System.out.println(game.getAvailableToken());
    }

  //*  @Test
    void setStartingCardForPlayers() throws NotValidMoveException, ChangedStateException {
        GameController game = new GameController();
        game.setPlayerNumber(2);

        assertEquals(true, game.checkNicknameAvailability("tommy"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLUE));
        game.addPlayer("tommy");

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa");

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
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLUE));
        game.addPlayer("tommy");

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa");

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
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLUE));
        game.addPlayer("tommy");

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa");
        game.InitializeGame();

        //tutti i giocatori sono salvati, gli obiettivi e le carte Starting sono distribuite in automatico

        Cli2 cli = new Cli2();

    //    cli.showUpdatedHand(game.getPlayerHand("tommy"));
    //    cli.showStartingCard(game.getStartingCard("tommy"));
        game.setObjectiveOfPlayer("tommy",game.getBoard().getPlayer("tommy").getSelectibleObjectives().get(1).getId());
    //    cli.showObjectiveCards(game.getObjectiveToChoose("tommy"));

    //    cli.showStartingCard(game.getStartingCard("isa"));
    //    cli.showStartingCard(game.getStartingCard("isa"));
        game.setObjectiveOfPlayer("isa",game.getBoard().getPlayer("isa").getSelectibleObjectives().get(1).getId());
    //    cli.showObjectiveCards(game.getObjectiveToChoose("isa"));

        //ho settato gli obiettivi di tutti i player il gioco inizia in automatico
        String Player1 = game.getCurrentPlayer();
        ArrayList<CardResource> Player1Hand = game.getPlayerHand(Player1);
        game.addCardToPlayingStation(Player1, Player1Hand.get(0).getId(),true,39,39);
        game.addCardFromCentralCardsToPlayerHand(Player1,game.getBoard().getCentralCardsGold().get(0).getId());

        String Player2 = game.getCurrentPlayer();
        ArrayList<CardResource> Player2Hand = game.getPlayerHand(Player2);
        game.addCardToPlayingStation(Player2, Player2Hand.get(0).getId(),true,39,39);
        game.addCardFromCentralCardsToPlayerHand(Player2,game.getBoard().getCentralCardsGold().get(0).getId());

        game.addCardToPlayingStation(Player1, Player1Hand.get(0).getId(),true,38,38);
        // game.addCardFromDeckToPlayerHand(Player1, DeckEnum.DECK_GOLD);

        game.addCardToPlayingStation(Player2, Player2Hand.get(0).getId(),true,38,38);
       // game.addCardFromDeckToPlayerHand(Player2, DeckEnum.DECK_GOLD);

    }




    @Test
    void getAvailableToken() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(3);
        game.addPlayer("tommy");
        game.addPlayer("isa");
        game.addPlayer("eric");
        System.out.println(game.getAvailableToken());
    }


    @Test
    void getStartingCard() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa");
        game.addPlayer("dave");
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        game.getBoard().getPlayer("isa").setStation(station);
        assertEquals(3,game.getBoard().getPlayer("isa").getStation().getCardStarting().getId());

    }

    @Test
    void getObjectiveToChoose() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa");
        game.addPlayer("dave");
        Cli2 cli = new Cli2();
    //    System.out.println(game.getObjectiveToChoose("isa"));
    //    cli.showObjectiveCards(game.getObjectiveToChoose("isa"));
    }

    @Test
    void getPlayerHand() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("tommy");
        game.addPlayer("isa");
        Cli2 cli = new Cli2();
        System.out.println(game.getPlayerHand("isa"));
        //cli.showUpdatedHand(game.getPlayerHand("isa"));

    }

    @Test
    void getCurrentPlayer() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("tommy");
        game.addPlayer("isa");
        game.getBoard().setCurrentPlayer("isa");
        assertEquals("isa", game.getCurrentPlayer());

    }



    @Test
    void gamefinishCondition() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa");
        game.addPlayer("tommy");
        game.getBoard().setGameState(GameState.FINISHED);

        game.getBoard().getPlayer("isa").setPoints(12);
        game.getBoard().getPlayer("tommy").setPoints(21);

        game.getBoard().setCurrentPlayer("tommy");
        assertTrue(game.isGamefinished());

        game.getBoard().setCurrentPlayer("isa");
        assertFalse(game.isGamefinished());

    }
}