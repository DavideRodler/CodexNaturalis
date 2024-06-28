package controller;

import Messages.Chat.GlobalChatMessage;
import Messages.Chat.PrivateChatMessage;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.chats.GlobalChat;
import model.PlayingBoard;
import model.PlayingStation;
import model.chats.PrivateChat;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.DirectionEnum;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.ObjectiveAssign;
import model.objectives.ObjectiveDiagonal;
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
        game.addPlayer("isa");
        System.out.println(game.getAvailableToken());
        game.checkTokenAvailability(TokenEnum.GREEN);
        board.setGameState(GameState.SELECT_TOKEN);
        game.selectToken("eric", TokenEnum.GREEN);
        System.out.println(game.getAvailableToken());
        Boolean caught = false;
        try{
            game.selectToken("isa", TokenEnum.GREEN);
        }
        catch (NotValidMoveException e){
            caught = true;
        }
        assertEquals(true, caught);

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


    @Test
    void initializeGame() throws ChangedStateException, NotValidMoveException, RemoteException {
        game.initGameController();
        game.setPlayerNumber(2);
        game.addPlayer("tommy");
        game.addPlayer("davide");
        Boolean caught = false;
        try{
            game.InitializeGame();
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);
    }

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

    @Test
    void setObjectiveOfPlayer() throws ChangedStateException, NotValidMoveException, RemoteException {
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
        Boolean caught = false;
        try{
            game.setObjectiveOfPlayer("tommy", 4);
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);
    }

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
    void addCardFromCentralCardsToPlayerHand() throws ChangedStateException, NotValidMoveException, NotMyTurnException, RemoteException {
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
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        game.getBoard().getPlayer("isa").setStation(station);
        ArrayList<CardResource> hand = new ArrayList<>();
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        CardResource card = new CardResource(1, backTmp, backTmp, SuitEnum.ANIMAL, 2, new ObjectiveAssign());
        CardResource card1 = new CardResource(2, backTmp, backTmp, SuitEnum.ANIMAL, 2, new ObjectiveAssign());
        ArrayList<CardResource> ce = new ArrayList<>();
        ce.add(card1);
        ce.add(card);
        game.getBoard().setCentralCardsResource(ce);
        hand.add(card1);
        hand.add(card);
        game.getBoard().getPlayer("isa").setHand(hand);
        game.getBoard().setGameState(GameState.ADDING_CARD_TO_HAND);
        Boolean caught = false;
        try{
            game.addCardFromCentralCardsToPlayerHand("isa",1 );
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);
        hand.add(card);
        Boolean caught2 = false;
        try{
            game.addCardFromCentralCardsToPlayerHand("isa",1 );
        }
        catch (NotValidMoveException e){
            caught2 = true;
        }
        assertEquals(true, caught2);
        ArrayList<CardResource> cr = new ArrayList<>();
        ArrayList<CardGold> cg = new ArrayList<>();
        game.getBoard().setCentralCardsResource(cr);
        game.getBoard().setCentralCardsGold(cg);
        Boolean caught1 = false;
        try{
            game.addCardFromCentralCardsToPlayerHand("isa",1 );
        }
        catch (NotValidMoveException e){
            caught1 = true;
        }

        assertEquals(true, caught1);
    }

    @Test
    void addCardFromDeckToPlayerHand() throws ChangedStateException, NotValidMoveException, NotMyTurnException, RemoteException {
        GameController game = new GameController();
        game.getBoard().setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("dave");
        game.getBoard().setGameState(GameState.SELECT_TOKEN);
        game.selectToken("isa", TokenEnum.YELLOW);
        game.selectToken("dave", TokenEnum.BLUE);
        game.getBoard().setCurrentPlayer("isa");
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        game.getBoard().getPlayer("isa").setStation(station);
        game.getBoard().setCurrentPlayer("isa");
        ArrayList<CardResource> hand = new ArrayList<>();
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        CardResource card1 = new CardResource(2, backTmp, backTmp, SuitEnum.ANIMAL, 2, new ObjectiveAssign());
        hand.add(card1);
        hand.add(card1);
        game.getBoard().getPlayer("isa").setHand(hand);
        System.out.println(game.getBoard().getPlayer("isa").getNumberOfCardInHand());
        game.getBoard().setGameState(GameState.ADDING_CARD_TO_HAND);
        Boolean caught = false;
        try{
            game.addCardFromDeckToPlayerHand("isa",6);
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);
        hand.add(card1);
        game.getBoard().getPlayer("isa").setHand(hand);
        System.out.println(game.getBoard().getPlayer("isa").getNumberOfCardInHand());
        Boolean caught3 = false;
        try{
            game.addCardFromDeckToPlayerHand("isa",2);
        }
        catch (NotValidMoveException e){
            caught3 = true;
        }
        assertEquals(true, caught3);
    }

    @Test
    void getCurrentPlayer() throws ChangedStateException, NotValidMoveException, RemoteException {
        GameController game = new GameController();
        game.getBoard().setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("dave");
        game.getBoard().setGameState(GameState.SELECT_TOKEN);
        game.selectToken("isa", TokenEnum.YELLOW);
        game.selectToken("dave", TokenEnum.BLUE);
        game.getBoard().setCurrentPlayer("isa");
        assertEquals("isa", game.getCurrentPlayer());
    }

    @Test
    void changeTurn() throws ChangedStateException, NotValidMoveException, RemoteException {
        GameController game = new GameController();
        game.getBoard().setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("dave");
        game.getBoard().setGameState(GameState.SELECT_TOKEN);
        game.selectToken("isa", TokenEnum.YELLOW);
        game.selectToken("dave", TokenEnum.BLUE);
        game.getBoard().setCurrentPlayer("isa");
        game.getBoard().setGameState(GameState.CHANGING_TURN);
        game.changeTurn();
        assertEquals("dave", game.getCurrentPlayer());
    }

    @Test
    void addCardToPlayingStation() throws RemoteException, ChangedStateException, NotValidMoveException, InvalidPlacingCondition {
        GameController game = new GameController();
        game.getBoard().setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("dave");
        game.getBoard().setGameState(GameState.SELECT_TOKEN);
        game.selectToken("isa", TokenEnum.YELLOW);
        game.selectToken("dave", TokenEnum.BLUE);
        game.getBoard().setCurrentPlayer("isa");
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        game.getBoard().getPlayer("isa").setStation(station);
        ArrayList<CardResource> hand = new ArrayList<>();
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        CardResource card = new CardResource(1, backTmp, backTmp, SuitEnum.ANIMAL, 2, new ObjectiveAssign());
        CardResource card1 = new CardResource(2, backTmp, backTmp, SuitEnum.ANIMAL, 2, new ObjectiveAssign());
        hand.add(card1);
        hand.add(card);
        game.getBoard().getPlayer("isa").setHand(hand);
        game.getBoard().setGameState(GameState.PLACING_CARD);
        Boolean caught = false;
        try{
            game.addCardToPlayingStation("isa", 1, false, 30, 30);
        }
        catch (InvalidPlacingCondition e){
            caught = true;
        }
        assertEquals(true, caught);
        Boolean caught3 = false;
        try{
            game.getBoard().setCurrentPlayer("dave");
            game.addCardToPlayingStation("isa", 1, false, 30, 30);
        }
        catch (RuntimeException e){
            caught3 = true;
        }
        assertEquals(true, caught3);
        ArrayList<Integer> co = new ArrayList<>();
        co.add(0, 32);
        co.add(1, 32);
        game.getBoard().getPlayer("isa").getStation().getMap().put(co,card);
        Boolean caught2 = false;
        try{
            game.addCardToPlayingStation("isa", 1, false, 31, 31);
        }
        catch (RuntimeException e){
            caught2 = true;
        }
        assertEquals(true, caught2);

        Boolean caught5 = false;
        try{
            game.getBoard().setGameState(GameState.FINISHED);
            game.addCardToPlayingStation("isa", 1, false, 31, 31);
        }
        catch (RuntimeException e){
            caught5 = true;
        }
        assertEquals(true, caught5);
        Boolean caught4 = false;
        try{
            game.addCardToPlayingStation("isa", 9, false, 31, 31);
        }
        catch (RuntimeException e){
            caught4 = true;
        }
        assertEquals(true, caught4);



    }

    @Test
    void isGamefinished() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.setPlayerNumber(2);
        game.addPlayer("isa");
        game.addPlayer("tommy");
        game.getBoard().setGameState(GameState.FINISHED);
        game.getBoard().getPlayer("isa").setPoints(12);
        game.getBoard().getPlayer("tommy").setPoints(21);
        game.getBoard().setCurrentPlayer("tommy");
        game.setLastTurn(true);
        assertTrue(game.isGamefinished());
        game.setLastTurn(false);
        game.getBoard().setCurrentPlayer("tommy");
        assertFalse(game.isGamefinished());
        game.getBoard().setCurrentPlayer("isa");
        assertFalse(game.isGamefinished());

    }

    @Test
    void getScoreBoard() throws ChangedStateException, NotValidMoveException, RemoteException {
        GameController game = new GameController();
        PlayingBoard board1 = new PlayingBoard();
        game.setBoard(board1);
        ObjectiveDiagonal objectivetmp = new ObjectiveDiagonal(DirectionEnum.LEFT, SuitEnum.ANIMAL);
        CardObjective cardObjectiveTmp = new CardObjective(4, 3, objectivetmp);
        board1.setCommonObjectives(cardObjectiveTmp, cardObjectiveTmp);
        game.getBoard().setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        game.addPlayer("tommy");
        game.getBoard().getPlayer("isa").setPoints(12);
        game.getBoard().getPlayer("tommy").setPoints(7);
        game.getBoard().getPlayer("isa").setSecretObjective(cardObjectiveTmp);
        game.getBoard().getPlayer("tommy").setSecretObjective(cardObjectiveTmp);
        LinkedHashMap<String,ArrayList<Integer>> scoreboard = new LinkedHashMap<>();
        ArrayList<Integer> points1 = new ArrayList<>();
        points1.add(0, 12);
        points1.add(1, 0);
        points1.add(2, 1);
        ArrayList<Integer> points2 = new ArrayList<>();
        points2.add(0, 7);
        points2.add(1, 0);
        points2.add(2, 2);
        scoreboard.put("isa", points1);
        scoreboard.put("tommy", points2);
        assertEquals(scoreboard, game.getScoreBoard());

    }

    @Test
    void assertGameState() throws ChangedStateException, NotValidMoveException {
        GameController game = new GameController();
        game.getBoard().setGameState(GameState.INITIALIZE_GAME);
        Boolean caught = false;
        try{
            game.assertGameState(GameState.SET_PLAYER_NUMBER);
        }
        catch (ChangedStateException e){
            caught = true;
        }
        assertEquals(true, caught);

    }

    @Test
    void isGameState() {
        GameController game = new GameController();
        game.getBoard().setGameState(GameState.INITIALIZE_GAME);
        assertEquals(false, game.isGameState(GameState.FINISHED));
    }

    @Test
    void assertIsMyTurn() throws ChangedStateException, NotValidMoveException, NotMyTurnException {
        GameController game = new GameController();
        game.getBoard().setGameState(GameState.SET_PLAYER_NUMBER);
        game.setPlayerNumber(2);
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("isa");
        game.getBoard().setGameState(GameState.ADD_PLAYERS);
        game.addPlayer("dave");
        game.getBoard().setGameState(GameState.SELECT_TOKEN);
        game.selectToken("isa", TokenEnum.YELLOW);
        game.selectToken("dave", TokenEnum.BLUE);
        game.getBoard().setCurrentPlayer("isa");
        boolean caught = false;
        try{
            game.assertIsMyTurn("dave");
        }
        catch (NotMyTurnException e){
            caught = true;
        }
        assertEquals(true, caught);
        
    }

    @Test
    void addNewPrivateChat() throws RemoteException {
        PrivateChat chat = new PrivateChat("isa", "tommy");
        Boolean caught = false;
        try{
            game.addNewPrivateChat("isa", "tommy");
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);
    }

    @Test
    void addMessageToGlobalChat() {
        GlobalChat chat = new GlobalChat();
        GlobalChatMessage mex = new GlobalChatMessage("GLOBAL", "ciao", "tommy");
        chat.addMessage(mex);
        game.getBoard().setGlobalChat(new GlobalChat());
        game.addMessageToGlobalChat("tommy", "ciao");
        assertEquals(chat.getMessage().getFirst().getMessage(), game.getBoard().getGlobalChat().getMessage().getFirst().getMessage());
    }

    @Test
    void addMessageToPrivateChat() throws RemoteException {
        PrivateChat chat = new PrivateChat("isa", "tommy");
        PrivateChatMessage mex = new PrivateChatMessage("ciaoo", "tommy", "isa");
        chat.addMessage(mex);
        Boolean caught = false;
        try{
            game.addNewPrivateChat("isa", "tommy");
            game.addMessageToPrivateChat("isa", "tommy", "ciaoo");
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);
    }

}