package model.testTemplates;

import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardStarting;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.TokenEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveCountingGold;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlayingBoardTemplate {

    // CardObjective firstObjective, CardObjective secondObjective, int playernumber, ArrayList<Player> playerList,
    // LinkedList<CardStarting> deckCardStarting, LinkedList<CardResource> deckCardResource,
    // LinkedList<CardObjective> deckCardObjective, LinkedList<CardGold> deckCardGold, String currentPlayer,
    // ArrayList<CardResource> centralCardsResource, ArrayList<CardGold> centralCardsGold, GameState gameState)
    public static PlayingBoard createPlayingBoard(){
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard1 = new CardObjective(0, 2, obj1);
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard2 = new CardObjective(1, 2, obj2);
        PlayingStation stationisa = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> handisa = new ArrayList<>();
        PlayingStation stationtommy = PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<CardResource> handtommy = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.RED, stationisa, 2, handisa);
        Player tommy = new Player("tommy", TokenEnum.YELLOW, stationtommy, 3, handtommy);
        ArrayList<Player> players = new ArrayList<>();
        players.add(0, isa);
        players.add(1, tommy);
        LinkedList<CardStarting> deckCardStarting = new LinkedList<>();
        LinkedList<CardResource> deckCardResource = new LinkedList<>();
        LinkedList<CardObjective> deckCardObjective = new LinkedList<>();
        LinkedList<CardGold> deckCardGold = new LinkedList<>();
        ArrayList<CardResource> centralCardsResource = new ArrayList<>();
        ArrayList<CardGold> centralCardsGold = new ArrayList<>();
        GameState gameState = GameState.INITIALIZE_GAME;
        PlayingBoard board = new PlayingBoard(objcard1, objcard2, 2, players, deckCardStarting, deckCardResource, deckCardObjective,
                deckCardGold, "isa", centralCardsResource, centralCardsGold, gameState);

        return board;
    }
}
