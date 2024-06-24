package controller;

import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.DirectionEnum;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.ObjectiveDiagonal;
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    PlayingBoard board = new PlayingBoard();
    GameController game = new GameController();
    @Test
    void getBoard() {
        game.setBoard(board);
        assertEquals(board,game.getBoard());
    }

    @Test
    void initGameController() {
        game.initGameController();
        assertEquals(GameState.SET_PLAYER_NUMBER, game.getBoard().getGameState());
    }

    @Test
    void setPlayerNumber() throws ChangedStateException, NotValidMoveException {
        game.initGameController();
        game.setPlayerNumber(2);
        assertEquals(2, game.getBoard().getPlayernumber());

    }

    @Test
    void getAvailableToken() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setBoard(board);
        board.setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        board.setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("eric");
        System.out.println(game.getAvailableToken());
        game.checkTokenAvailability(TokenEnum.GREEN);
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("eric", TokenEnum.GREEN);
        System.out.println(game.getAvailableToken());
    }


    @Test
    void checkNicknameAvailability() throws ChangedStateException, NotValidMoveException {
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
    void addPlayer() throws ChangedStateException, NotValidMoveException {
        game.initGameController();
        game.setPlayerNumber(2);
        game.addPlayer("tommy");
        game.addPlayer("davide");
        assertEquals(2, game.getBoard().getPlayers().size());
    }


/*    @Test
    void initializeGame() throws ChangedStateException, NotValidMoveException {
        game.initGameController();
        game.setPlayerNumber(2);
        game.addPlayer("tommy");
        game.addPlayer("davide");
        game.InitializeGame();
        assertEquals(GameState.SELECT_TOKEN, game.getBoard().getGameState());
    } */

    @Test
    void setCentralCardPlayedBack() throws ChangedStateException, NotValidMoveException {
        ArrayList<SuitEnum> suitList = new ArrayList<SuitEnum>();
        suitList.add(SuitEnum.ANIMAL);
        suitList.add(SuitEnum.PLANT);
        suitList.add(SuitEnum.INSECT);
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        CardStarting cardStarting = new CardStarting(2, frontTmp, backTmp, suitList);
        game.setBoard(board);
        board.setGameState(GameState. SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.addPlayer("tommy");
        game.addPlayer("davide");
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("tommy", TokenEnum.GREEN);
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("davide", TokenEnum.BLUE);
        board.setGameState(GameState.SELECT_STARTINGCARDFACE);
        game.getBoard().getPlayer("tommy").getStation().setCardStarting(cardStarting,"tommy");
        game.getBoard().getPlayer("davide").getStation().setCardStarting(cardStarting,"davide");
        game.setCentralCardPlayedBack(false, "tommy", 2);
        game.setCentralCardPlayedBack(false, "davide", 2);
        assertEquals(2, game.getBoard().getPlayer("tommy").getStation().getCardStarting().getId());
        assertEquals(false, game.getBoard().getPlayer("tommy").getStation().getCardStarting().getPlayingBack());

    }

/*    @Test
    void setObjectiveOfPlayer() throws ChangedStateException, NotValidMoveException {
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        CardObjective card1 = new CardObjective(7, 3, objectivetmp);
        ArrayList<CardObjective> objs = new ArrayList<>();
        objs.add(card1);
        objs.add(cardObjectiveTmp);
        game.setBoard(board);
        board.setGameState(GameState. SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.addPlayer("tommy");
        game.addPlayer("davide");
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("tommy", TokenEnum.GREEN);
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("davide", TokenEnum.BLUE);
        game.getBoard().setGameState(GameState.SELECT_OBJECTIVE);
        game.getBoard().getPlayer("tommy").setSelectibleObjectives(objs);
        game.setObjectiveOfPlayer("tommy", 4);
        assertEquals(4, game.getBoard().getPlayer("tommy").getSecretObjective().getId());
    } */

    @Test
    void getPlayerHand() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setBoard(board);
        board.setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        board.setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("isa", TokenEnum.YELLOW);
        board.setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("dave");
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("dave", TokenEnum.BLUE);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        game.getBoard().getPlayer("isa").setStation(station);
        ArrayList<CardResource> hand = new ArrayList<>();
        game.getBoard().getPlayer("isa").setHand(hand);
        assertEquals(hand,game.getPlayerHand("isa"));
    }

    @Test
    void addCardFromCentralCardsToPlayerHand() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setBoard(board);

        board.setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        board.setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("isa", TokenEnum.YELLOW);
        board.setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("dave");
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("dave", TokenEnum.BLUE);
        game.getBoard().setCurrentPlayer("isa");

    }

    @Test
    void addCardFromDeckToPlayerHand() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void changeTurn() {
    }

    @Test
    void addCardToPlayingStation() {
    }

    @Test
    void isGamefinished() {
    }

    @Test
    void getScoreBoard() {
    }

    @Test
    void assertGameState() {
    }

    @Test
    void isGameState() {
    }

    @Test
    void assertIsMyTurn() {
    }

    @Test
    void addNewPrivateChat() {
    }

    @Test
    void addMessageToGlobalChat() {
    }

    @Test
    void addMessageToPrivateChat() {
    }

    @Test
    void setBoard() {
    }
}