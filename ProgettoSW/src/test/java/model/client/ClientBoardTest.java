package model.client;

import model.Player;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveCountingGold;
import model.testsTemplate.ClientBoardTemplate;
import model.testsTemplate.PlayingStationTemplate;
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
    void getOtherPlayer() {
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
}