package model;

import Network.Client.RMI.RmiClientToServer;
import Network.Server.RMI.RmiServer;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveCountingGold;
import model.testTemplates.PlayingBoardTemplate;
import model.testTemplates.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    PlayingBoard board = PlayingBoardTemplate.createPlayingBoard();
/**    @Test
    void getHand() throws RemoteException {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
        VirtualServer server = new RmiServer();
        VirtualView client = new RmiClient(server, "TUI");
        HashMap<String, Observer> observerHashMap = new HashMap<>();
        observerHashMap.put("isa", client);
        ObservableModel model = new ObservableModel();
        model.addSpecificObserver("isa",client);
        isa.addCardToHandWithObserver(cardAnimal1);
        isa.addCardToHandWithObserver(cardAnimal2);
        assertEquals(hand, isa.getHand());

    } **/

    @Test
    void getToken() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals(TokenEnum.YELLOW, isa.getToken());
    }

    @Test
    void getNickname() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals("isa", isa.getNickname());
    }

    @Test
    void getPoints() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals(2, isa.getPoints());
    }

    @Test
    void getSelectibleObjectives() {
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard1 = new CardObjective(0, 2, obj1);
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard2 = new CardObjective(1, 2, obj2);
        ArrayList<CardObjective> cards = new ArrayList<>();
        cards.add(0,objcard1);
        cards.add(1,objcard2);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        //isa.setSelectibleObjectivesWithObserver(cards);
        isa.setSelectibleObjectives(cards);
        assertEquals(cards, isa.getSelectibleObjectives());

    }

    @Test
    void getStation() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals(station, isa.getStation());
    }

    @Test
    void getSecretObjective() {
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard1 = new CardObjective(0, 2, obj1);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        //isa.setSecretObjectiveWithObs(objcard1);
        isa.setSecretObjective(objcard1);
        assertEquals(objcard1, isa.getSecretObjective());
    }

    @Test
    void removeCardFromHandWithObs() throws Exception {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.INKWELL), new Corner(SuitEnum.ANIMAL));
        CardResource card1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card3 = new CardResource(2, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        hand.add(0, card1);
        hand.add(1, card2);
        hand.add(2, card3);
        ArrayList<CardResource> handexpected = new ArrayList<>();
        handexpected.add(0, card1);
        handexpected.add(1, card3);
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        //isa.removeCardFromHandWithObs(1);
        isa.removeCardFromHand(1);
        assertEquals(hand, handexpected);

    }

    @Test
    void getNumberOfCardInHand() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.INKWELL), new Corner(SuitEnum.ANIMAL));
        CardResource card1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        hand.add(0, card1);
        hand.add(1, card2);
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals(2, isa.getNumberOfCardInHand());
    }

    /**    @Test
    void addCardToHand() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.QUILL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.INKWELL), new Corner(SuitEnum.ANIMAL));
        CardResource card1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        CardResource card3 = new CardResource(2, frontTmp, backTmp, SuitEnum.ANIMAL, 0, null);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        hand.add(0, card1);
        hand.add(1, card2);
        ArrayList<CardResource> handexpected = new ArrayList<>();
        handexpected.add(0, card1);
        handexpected.add(1, card2);
        handexpected.add(2, card3);
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        isa.addCardToHandWithObserver(card3);
        assertEquals(hand, handexpected);
    } **/
@Test
    void setPoints(){
    PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
    ArrayList<CardResource> hand = new ArrayList<>();
    Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
    isa.setPoints(5);
    assertEquals(5, isa.getPoints());
    }
    @Test
    void setStation(){
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        isa.setStation(station);
        assertEquals(station, isa.getStation());
    }
    /**    @Test
    void setSelectibleObjectivesWithObserver() throws RemoteException {
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard1 = new CardObjective(0, 2, obj1);
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard2 = new CardObjective(1, 2, obj2);
        ArrayList<CardObjective> objs = new ArrayList<>();
        objs.add(0,objcard1);
        objs.add(1,objcard2);
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        VirtualServer server = new RmiServer();
        VirtualView client = new RmiClient(server, "TUI");
        HashMap<String, Observer> observerHashMap = new HashMap<>();
        observerHashMap.put("isa", client);
        ObservableModel model = new ObservableModel();
        model.addSpecificObserver("isa",client);
        isa.setSelectibleObjectivesWithObserver(objs);
        assertEquals(objs, isa.getSelectibleObjectives());

    }**/
    @Test
    void setToken(){
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        isa.setToken(TokenEnum.BLUE);
        assertEquals(TokenEnum.BLUE, isa.getToken());
    }
    @Test
    void setSecretObjectiveWithObs(){


    }

}