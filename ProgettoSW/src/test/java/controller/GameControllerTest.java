package controller;

import Network.Cli2;
import model.enums.TokenEnum;
import org.junit.jupiter.api.Test;
import exception.NotValidMoveException;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerTest {

    @Test
    public void testChecknickname() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();
        game.addPlayer("tommy", TokenEnum.BLACK);
        game.addPlayer("davide", TokenEnum.BLUE);
        assertEquals(true ,game.checkNicknameAvailability("isa"));
        game.addPlayer("isa", TokenEnum.YELLOW);
        game.addPlayer("eric", TokenEnum.BLACK);
        assertEquals(true ,game.checkNicknameAvailability("giorgio"));
        assertEquals(false ,game.checkNicknameAvailability("isa"));
        game.shufflePlayer();

    }
    @Test
    public void testTokenAvailability() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();
        System.out.println(game.getAvailableToken());
        game.addPlayer("eric", TokenEnum.BLACK);
        System.out.println(game.getAvailableToken());
    }

    @Test
    void setStartingCardForPlayers() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();

        assertEquals(true, game.checkNicknameAvailability("tommy"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLACK));
        game.addPlayer("tommy", TokenEnum.BLACK);

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa", TokenEnum.YELLOW);

        game.shufflePlayer();

        game.setStartingCardForPlayers();

        Cli2 cli = new Cli2(null, null);
        cli.showStartingCard(game.getStartingCard("tommy"));
        cli.showStartingCard(game.getStartingCard("isa"));
    }

    @Test
    void setCentralCardPlayedBack() throws NotValidMoveException {
        GameController game = new GameController(null, null);
        game.initGameController();

        assertEquals(true, game.checkNicknameAvailability("tommy"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.BLACK));
        game.addPlayer("tommy", TokenEnum.BLACK);

        assertEquals(true, game.checkNicknameAvailability("isa"));
        System.out.println(game.getAvailableToken());
        assertEquals(true, game.checkTokenAvailability(TokenEnum.YELLOW));
        game.addPlayer("isa", TokenEnum.YELLOW);

        game.shufflePlayer();

        game.setStartingCardForPlayers();

        Cli2 cli = new Cli2(null, null);
        cli.showStartingCard(game.getStartingCard("tommy"));
        cli.showStartingCard(game.getStartingCard("isa"));

        game.setCentralCardPlayedBack(true,"tommy");
        game.setCentralCardPlayedBack(false,"isa");

        cli.showObjectiveCards(game.getCardObjectiveToChoose());
    }

    @Test
    void getStartingCard() {
    }

    @Test
    void getObjectiveCards() {
    }
}