package model;

import Network.Client.RMI.RmiClientToServer;
import Network.Server.RMI.RmiServer;
import Network.Server.Server;
import Network.Server.VirtualServer;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.chats.GlobalChat;
import model.chats.PrivateChat;
import model.enums.GameState;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import model.objectives.ObjectiveCountingGold;
import model.objectives.Points;
import model.testTemplates.PlayingBoardTemplate;
import model.testTemplates.PlayingStationTemplate;
import observers.ObservableModel;
import observers.Observer;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PlayingBoardTest {
    PlayingBoard board = PlayingBoardTemplate.createPlayingBoard_withdecks();

    @Test
    void getCommonObjectives() {
        Objective obj1 = new ObjectiveCountingGold(2,0,0);
        CardObjective objcard1 = new CardObjective(0, 2, obj1);
        Objective obj2 = new ObjectiveCountingGold(1,1,1);
        CardObjective objcard2 = new CardObjective(1, 2, obj2);
        board.setCommonObjectives(objcard1, objcard2);
        assertEquals(objcard1, board.getFirstObjective());
        assertEquals(objcard2, board.getSecondObjective());
    }
    @Test
    void getPlayernumber() {
        board.setPlayernumber(2);
        assertEquals(2, board.getPlayernumber());
    }

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
    void getCurrentPlayer() {
        board.setCurrentPlayer("isa");
        assertEquals("isa", board.getCurrentPlayer());
    }

    @Test
    void getDeckCardObjective() {
        LinkedList<CardObjective> deckCardObjective = new LinkedList<>();
        board.setDeckCardObjective(deckCardObjective);
        assertEquals(deckCardObjective, board.getDeckCardObjective());
    }

    @Test
    void getDeckCardStarting() {
        LinkedList<CardStarting> deckCardStarting = new LinkedList<>();
        board.setDeckCardStarting(deckCardStarting);
        assertEquals(deckCardStarting, board.getDeckCardStarting());
    }


    @Test
    void getPlayers() {
        PlayingStation stationisa = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> handisa = new ArrayList<>();
        PlayingStation stationtommy = PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<CardResource> handtommy = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.RED, stationisa, 2, handisa);
        Player tommy = new Player("tommy", TokenEnum.YELLOW, stationtommy, 3, handtommy);
        ArrayList<Player> players = new ArrayList<>();
        players.add(0, isa);
        players.add(1, tommy);
        board.setPlayers(players);
        assertEquals(players, board.getPlayers());
    }

    @Test
    void getGameState() {
        board.setGameState(GameState.FINISHED);
        assertEquals(GameState.FINISHED, board.getGameState());
    }

    @Test
    void addPlayer() {
        PlayingStation stationisa = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> handisa = new ArrayList<>();
        PlayingStation stationtommy = PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<CardResource> handtommy = new ArrayList<>();
        PlayingStation stationgiorgio = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> handgiorgio = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.RED, stationisa, 2, handisa);
        Player tommy = new Player("tommy", TokenEnum.YELLOW, stationtommy, 3, handtommy);
        Player giorgio = new Player("giorgio", TokenEnum.BLUE, stationgiorgio, 20, handgiorgio);
        ArrayList<Player> players = new ArrayList<>();
        players.add(0, isa);
        players.add(1, tommy);
        ArrayList<Player> playersExpected = new ArrayList<>();
        playersExpected.add(0, isa);
        playersExpected.add(1, tommy);
        playersExpected.add(2, giorgio);
        board.setPlayers(players);
        board.addPlayer(giorgio);
        assertEquals(playersExpected, board.getPlayers());

    }

    @Test
    void getPlayer() {
        PlayingStation stationisa = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> handisa = new ArrayList<>();
        PlayingStation stationtommy = PlayingStationTemplate.test_3Cards_1Positioning();
        ArrayList<CardResource> handtommy = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.RED, stationisa, 2, handisa);
        Player tommy = new Player("tommy", TokenEnum.YELLOW, stationtommy, 3, handtommy);
        ArrayList<Player> players = new ArrayList<>();
        players.add(0, isa);
        players.add(1, tommy);
        board.setPlayers(players);
        assertEquals(isa, board.getPlayer("isa"));
    }

    @Test
    void getCardResource() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, null);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, null);
        ArrayList<CardResource> centralCardsResource = new ArrayList<>();
        centralCardsResource.add(0, cardAnimal1);
        centralCardsResource.add(1, cardAnimal2);
        board.setCentralCardsResource(centralCardsResource);
        assertEquals(cardAnimal1, board.getCardResource(0));
    }

    @Test
    void getCardGold() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveCountingGold(0,1,0);
        CardGold cardAnimal1 = new CardGold(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1,2,1,2,1,obj );
        CardGold cardAnimal2 = new CardGold(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1,1,2,1,2, obj);
        ArrayList<CardGold> centralCardsGold = new ArrayList<>();
        centralCardsGold.add(0, cardAnimal1);
        centralCardsGold.add(1, cardAnimal2);
        board.setCentralCardsGold(centralCardsGold);
        assertEquals(cardAnimal1, board.getCardGold(0));
    }

    @Test
    void addCardResource() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, null);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, null);
        ArrayList<CardResource> centralCardsResource = new ArrayList<>();
        centralCardsResource.add(0, cardAnimal1);
        board.setCentralCardsResource(centralCardsResource);
        ArrayList<CardResource> centralCardsResourceExpected = new ArrayList<>();
        centralCardsResourceExpected.add(0, cardAnimal1);
        centralCardsResourceExpected.add(1, cardAnimal2);
        board.addCardResource(cardAnimal2);
        assertEquals(centralCardsResourceExpected, board.getCentralCardsResource());
    }

    @Test
    void addCardGold() {
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        Points obj = new ObjectiveCountingGold(0,1,0);
        CardGold cardAnimal1 = new CardGold(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1,2,1,2,1,obj );
        CardGold cardAnimal2 = new CardGold(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1,1,2,1,2, obj);
        ArrayList<CardGold> centralCardsGold = new ArrayList<>();
        centralCardsGold.add(0, cardAnimal1);
        board.setCentralCardsGold(centralCardsGold);
        ArrayList<CardGold> centralCardsGoldExpected = new ArrayList<>();
        centralCardsGoldExpected.add(0, cardAnimal1);
        centralCardsGoldExpected.add(1, cardAnimal2);
        board.addCardGold(cardAnimal2);
        assertEquals(centralCardsGoldExpected, board.getCentralCardsGold());
    }


    @Test
    void getnextPlayer() {
        assertEquals("tommy", board.getnextPlayer());
    }

    @Test
    void  getCardFromGoldDeck(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardGold cardAnimal1 = new CardGold(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, 1,1,1,1,obj);
        assertEquals(cardAnimal1.getClass(), board.getCardFromGoldDeck().getClass());
    }
    @Test
    void getCardFromResourceDeck(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0,obj);
        assertEquals(cardAnimal1.getClass(), board.getCardFromResourceDeck().getClass());
    }
    @Test
    void removeCentralCard(){
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp1 = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT), new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.PLANT));
        Points obj = new ObjectiveAssign();
        CardGold cardAnimal1 = new CardGold(0, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, 1,1,1,1,obj);
        CardGold cardAnimal2 = new CardGold(1, frontTmp1, backTmp, SuitEnum.ANIMAL, 0, 1,1,1,1,obj);
        ArrayList<CardGold> cards= new ArrayList<>();
        cards.add(cardAnimal1);
        cards.add(cardAnimal2);
        board.setCentralCardsGold(cards);
        ArrayList<CardGold> cardspost= new ArrayList<>();
        cardspost.add(cardAnimal2);
        board.removeCentralCard(0);
        assertEquals(cardspost, board.getCentralCardsGold());

    }
    @Test
    void shufflePlayer(){
        PlayingStation station = PlayingStationTemplate.test_7Cards_2Diagonal_c();
        Player isa = new Player("isa", TokenEnum.RED, station , 2, new ArrayList<>());
        Player tommy = new Player("tommy", TokenEnum.YELLOW, station, 3,  new ArrayList<>());
        Player dave = new Player("dave", TokenEnum.GREEN, station , 2, new ArrayList<>());
        Player eric = new Player("eric", TokenEnum.BLUE, station, 3,  new ArrayList<>());
        ArrayList<Player> players = new ArrayList<>();
        players.add(0, isa);
        players.add(1, tommy);
        players.add(2, dave);
        players.add(3, eric);
        PlayingBoard b = new PlayingBoard();
        b.addPlayer(isa);
        b.addPlayer(tommy);
        b.addPlayer(dave);
        b.addPlayer(eric);
        b.shufflePlayer();
        assertEquals(players.size(), b.getPlayers().size());

    }

        @Test
   void addNewPrivateChat() throws RemoteException {
        PrivateChat chat = new PrivateChat("isa", "tommy");
            Server server = new Server();
            VirtualServer server1 = new RmiServer(server);
            RmiClientToServer client = new RmiClientToServer(server1);
            HashMap<String, Observer> observerHashMap = new HashMap<>();
            observerHashMap.put("isa", client);
            ObservableModel model = new ObservableModel();
            model.addSpecificObserver("isa",client);
            Boolean caught = false;
            try{
                board.addNewPrivateChat("isa", "tommy");
            }
            catch (NullPointerException e){
                caught = true;
            }
            assertEquals(true, caught);
    }

    @Test
    void addMessageToPrivateChat() throws RemoteException {
        PrivateChat chat = new PrivateChat("isa", "tommy");
        PrivateChatMessage mex = new PrivateChatMessage("ciaoo", "tommy", "isa");
        chat.addMessage(mex);
        Server server = new Server();
        VirtualServer server1 = new RmiServer(server);
        RmiClientToServer client = new RmiClientToServer(server1);
        HashMap<String, Observer> observerHashMap = new HashMap<>();
        observerHashMap.put("isa", client);
        ObservableModel model = new ObservableModel();
        model.addSpecificObserver("isa",client);
        Boolean caught = false;
        try{
            board.addNewPrivateChat("isa", "tommy");
            board.addMessageToPrivateChat("isa", "tommy", "ciaoo");
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);

    }
    @Test
   void getPrivateChat() throws RemoteException {
        PrivateChat chat = new PrivateChat("isa", "tommy");
        PrivateChatMessage mex = new PrivateChatMessage("ciaoo", "tommy", "isa");
        chat.addMessage(mex);
        Server server = new Server();
        VirtualServer server1 = new RmiServer(server);
        RmiClientToServer client = new RmiClientToServer(server1);
        HashMap<String, Observer> observerHashMap = new HashMap<>();
        observerHashMap.put("isa", client);
        ObservableModel model = new ObservableModel();
        model.addSpecificObserver("isa",client);
        Boolean caught = false;
        try{
            board.addNewPrivateChat("isa", "tommy");
            board.addMessageToPrivateChat("isa", "tommy", "ciaoo");
            PrivateChat chatpriv = board.getPrivateChat("isa", "tommy");
        }
        catch (NullPointerException e){
            caught = true;
        }
        assertEquals(true, caught);
    }
    @Test
    void addMessageToGlobalChat(){
        GlobalChat chat = new GlobalChat();
        GlobalChatMessage mex = new GlobalChatMessage("GLOBAL", "ciao", "tommy");
        chat.addMessage(mex);
        board.setGlobalChat(new GlobalChat());
        board.addMessageToGlobalChat("tommy", "ciao");
        assertEquals(chat.getMessage().getFirst().getMessage(), board.getGlobalChat().getMessage().getFirst().getMessage());
    }


}

