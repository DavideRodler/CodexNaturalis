package controller;

import Network.Cli2;
import model.cards.CardResource;
import model.enums.DeckEnum;
import model.enums.TokenEnum;
import org.junit.jupiter.api.Test;
import exception.NotValidMoveException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerTest {

    @Test
    public void testChecknickname() throws NotValidMoveException {
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
    public void testTokenAvailability() throws NotValidMoveException {
        GameController game = new GameController();
        System.out.println(game.getAvailableToken());
        game.setPlayerNumber(4);
        game.addPlayer("eric", TokenEnum.BLACK);
        System.out.println(game.getAvailableToken());
    }

    @Test
    void setStartingCardForPlayers() throws NotValidMoveException {
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

        Cli2 cli = new Cli2(null, null);
        cli.showStartingCard(game.getStartingCard("tommy"));
        cli.showStartingCard(game.getStartingCard("isa"));
    }

    @Test
    void setupOfTheGameTest() throws NotValidMoveException {
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
}