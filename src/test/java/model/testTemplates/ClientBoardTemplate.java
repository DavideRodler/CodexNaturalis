package model.testTemplates;

import model.Player;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveCountingGold;

import java.util.ArrayList;

public class ClientBoardTemplate {
    public static ClientBoard createClientBoard(){
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard1 = new CardObjective(0, 2, obj1);
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard2 = new CardObjective(1, 2, obj2);
        GameState gameState = GameState.INITIALIZE_GAME;
        PlayingStation stationisa = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> handisa = new ArrayList<>();
        PlayingStation stationtommy = PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<SuitEnum> handtommy = new ArrayList<>();
        PlayingStation stationgiorgio= PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<SuitEnum> handgiorgio = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.RED, stationisa, 2, handisa);
        ReductPlayer tommy = new ReductPlayer(2, handtommy, "tommy", stationtommy, TokenEnum.YELLOW);
        ReductPlayer giorgio = new ReductPlayer(6, handgiorgio, "giorgio", stationgiorgio, TokenEnum.BLUE);
        ArrayList<ReductPlayer> players = new ArrayList<>();
        players.add(0, tommy);
        players.add(1, giorgio);
        ArrayList<CardResource> centralCardsResource = new ArrayList<>();
        ArrayList<CardGold> centralCardsGold = new ArrayList<>();
        ClientBoard board = new ClientBoard(objcard1, objcard2, players, isa, centralCardsResource, centralCardsGold, gameState);
        return board;
    }

    public static ReductPlayer createReductClient(){
        PlayingStation stationgiorgio= PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<SuitEnum> handgiorgio = new ArrayList<>();
        ReductPlayer giorgio = new ReductPlayer(6, handgiorgio, "giorgio", stationgiorgio, TokenEnum.BLUE);
        return giorgio;
    }

}
