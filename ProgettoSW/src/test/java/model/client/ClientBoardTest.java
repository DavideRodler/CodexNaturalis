package model.client;

import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import model.*;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveCountingGold;
import model.testTemplates.ClientBoardTemplate;
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientBoardTest {
ClientBoard board = ClientBoardTemplate.createClientBoard();
    @Test
    void getCentralCardsGold() {
        ArrayList<CardGold> centralCardsGold = new ArrayList<>();
        board.setCentralCardsGold(centralCardsGold);
        assertEquals(centralCardsGold, board.getCentralCardsGold());
    }

    @Test
    void getCentralCardsResource() {
        ArrayList<CardResource> centralCardsResource = new ArrayList<>();
        board.setCentralCardsResource(centralCardsResource);
        assertEquals(centralCardsResource, board.getCentralCardsResource());
    }

    @Test
    void getFirstObjective() {
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard1 = new CardObjective(0, 2, obj1);
        board.setFirstObjective(objcard1);
        assertEquals(objcard1, board.getFirstObjective());
    }

    @Test
    void getMyplayer() {
        PlayingStation stationisa = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> handisa = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.RED, stationisa, 2, handisa);
        board.setMyplayer(isa);
        assertEquals(isa, board.getMyplayer());
    }

    @Test
    void getOtherplayers() {
        PlayingStation stationtommy = PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<SuitEnum> handtommy = new ArrayList<>();
        PlayingStation stationgiorgio= PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<SuitEnum> handgiorgio = new ArrayList<>();
        ReductPlayer tommy = new ReductPlayer(2, handtommy, "tommy", stationtommy, TokenEnum.YELLOW);
        ReductPlayer giorgio = new ReductPlayer(6, handgiorgio, "giorgio", stationgiorgio, TokenEnum.BLUE);
        ArrayList<ReductPlayer> players = new ArrayList<>();
        players.add(0, tommy);
        players.add(1, giorgio);
        board.setOtherplayers(players);
        assertEquals(players, board.getOtherplayers());
    }

    @Test
    void getSecondObjective() {
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard2 = new CardObjective(1, 2, obj2);
        board.setSecondObjective(objcard2);
        assertEquals(objcard2, board.getSecondObjective());
    }

    @Test
    void getGameState() {
        GameState gameState = GameState.INITIALIZE_GAME;
        board.setGameState(gameState);
        assertEquals(gameState, board.getGameState());
    }

    @Test
    void getOtherPlayer(){
        PlayingStation stationtommy = PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<SuitEnum> handtommy = new ArrayList<>();
        PlayingStation stationgiorgio= PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<SuitEnum> handgiorgio = new ArrayList<>();
        ReductPlayer tommy = new ReductPlayer(2, handtommy, "tommy", stationtommy, TokenEnum.YELLOW);
        ReductPlayer giorgio = new ReductPlayer(6, handgiorgio, "giorgio", stationgiorgio, TokenEnum.BLUE);
        ArrayList<ReductPlayer> players = new ArrayList<>();
        players.add(0, tommy);
        players.add(1, giorgio);
        board.setOtherplayers(players);
        assertEquals(tommy, board.getOtherPlayer("tommy"));

    }


    @Test
    void getGlobalChat() {
        GlobalChat globalChat = new GlobalChat();
        board.setGlobalChat(globalChat);
        assertEquals(globalChat, board.getGlobalChat());

    }

        @Test
   void getPrivateChat() {
        PrivateChatMessage mex = new PrivateChatMessage("ciao", "tommy", "isa");
        PrivateChat chat = new PrivateChat("isa", "tommy");
        chat.addMessage(mex);
        board.addNewPrivateChat("isa", "tommy");
        board.updatePrivateChat("PRIVATE", "tommy", "isa", "ciao");
        assertEquals(chat.getMessage().size(), board.getPrivateChat("isa", "tommy").size());

    }


    @Test
    void getCurrentPlayer() {
        board.setCurrentPlayer("isa");
        assertEquals("isa", board.getCurrentPlayer());
    }


    @Test
    void getBackOfResourceDeck() {
        board.setBackOfResourceDeck(SuitEnum.ANIMAL);
        assertEquals(SuitEnum.ANIMAL, board.getBackOfResourceDeck());
    }

    @Test
    void getBackOfGoldDeck() {
        board.setBackOfGoldDeck(SuitEnum.INSECT);
        assertEquals(SuitEnum.INSECT, board.getBackOfGoldDeck());
    }


    @Test
    void getAvailableTokens() {
        ArrayList<TokenEnum> tokens = new ArrayList<>();
        tokens.add(TokenEnum.RED);
        tokens.add(TokenEnum.GREEN);
        assertEquals(tokens, board.getAvailableTokens());
    }

    @Test
    void updatePrivateChat() {
        PrivateChat chat = new PrivateChat("isa", "tommy");
        PrivateChatMessage mex = new PrivateChatMessage("ciao", "isa", "tommy");
        chat.addMessage(mex);
        board.addNewPrivateChat("isa", "tommy");
        board.updatePrivateChat("PRIVATE", "isa", "tommy", "ciao");;
        assertEquals(chat.getNickname1(), board.getPrivateChats().getFirst().getNickname1());
        assertEquals(chat.getNickname2(), board.getPrivateChats().getFirst().getNickname2());
        assertEquals(chat.getMessage().getFirst().getMessage(), board.getPrivateChat("isa", "tommy").getFirst().getMessage());
    }

    @Test
    void updateGlobalChat(){
        GlobalChat chat = new GlobalChat();
        GlobalChatMessage mex = new GlobalChatMessage("GLOBAL", "ciao", "tommy");
        chat.addMessage(mex);
        board.setGlobalChat(new GlobalChat());
        board.updateGlobalChat("GLOBAL", "tommy", "ciao");
        assertEquals(chat.getMessage().getFirst().getMessage(), board.getGlobalChat().getMessage().getFirst().getMessage());
    }

}