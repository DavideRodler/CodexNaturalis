package controller;

import Network.Cli2;
import model.cards.CardResource;
import model.enums.TokenEnum;
import org.junit.jupiter.api.Test;
import exception.NotValidMoveException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerTest {

    @Test
    public void testChecknickname() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();
        game.setPlayerNumber(4);
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("davide", TokenEnum.BLUE);
        assertEquals(true ,game.checkNicknameAvailability("isa"));
        game.addPlayer("isa", TokenEnum.YELLOW);
        assertEquals(true ,game.checkNicknameAvailability("giorgio"));
        assertEquals(false ,game.checkNicknameAvailability("isa"));

    }
    @Test
    public void testTokenAvailability() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();
        System.out.println(game.getAvailableToken());
        game.setPlayerNumber(4);
        game.addPlayer("eric", TokenEnum.BLACK);
        System.out.println(game.getAvailableToken());
    }

    @Test
    void setStartingCardForPlayers() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();
        game.setPlayerNumber(2);

        assertEquals(true, game.checkNicknameAvailability("tommy"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLACK));
        game.addPlayer("tommy", TokenEnum.BLACK);

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa", TokenEnum.YELLOW);

        Cli2 cli = new Cli2(null, null);
        cli.showStartingCard(game.getStartingCard("tommy"));
        cli.showStartingCard(game.getStartingCard("isa"));
    }

    @Test
    void setupOfTheGameTest() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();
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

        Cli2 cli = new Cli2(null, null);

        cli.showUpdatedHand(game.getPlayerHand("tommy"));
        cli.showStartingCard(game.getStartingCard("tommy"));
        game.setObjectiveOfPlayer("tommy",game.getObjectiveToChoose("tommy").get(1).getId());
        cli.showObjectiveCards(game.getObjectiveToChoose("tommy"));

        cli.showStartingCard(game.getStartingCard("isa"));
        cli.showStartingCard(game.getStartingCard("isa"));
        game.setObjectiveOfPlayer("isa",game.getObjectiveToChoose("isa").get(1).getId());
        cli.showObjectiveCards(game.getObjectiveToChoose("isa"));

        //ho settato gli obiettivi di tutti i player il gioco inizia in automatico

    }


    @Test
    void GivingCardToPlayersTest() throws Exception {
        GameController game = new GameController(null, null);
        game.initGameController();
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

        Cli2 cli = new Cli2(null, null);

        cli.showUpdatedHand(game.getPlayerHand("tommy"));
        cli.showStartingCard(game.getStartingCard("tommy"));
        game.setObjectiveOfPlayer("tommy",game.getObjectiveToChoose("tommy").get(1).getId());
        cli.showObjectiveCards(game.getObjectiveToChoose("tommy"));

        cli.showStartingCard(game.getStartingCard("isa"));
        cli.showStartingCard(game.getStartingCard("isa"));
        game.setObjectiveOfPlayer("isa",game.getObjectiveToChoose("isa").get(1).getId());
        cli.showObjectiveCards(game.getObjectiveToChoose("isa"));

        //ho settato gli obiettivi di tutti i player il gioco inizia in automatico
        String currentPlayer = game.getCurrentPlayer();
        ArrayList<CardResource> currentPlayerHand = game.getPlayerHand(currentPlayer);
        game.addCardToPlayingStation(currentPlayer, currentPlayerHand.get(0).getId(),41,41);
    }
}