package Network.Server;

import Network.Client.RMI.VirtualView;
import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.GameState;
import model.enums.TokenEnum;
//import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RmiServer implements VirtualServer {

    private final GameController gameController;
    private List<VirtualView> clients;
    private HashMap<String, VirtualView> clientsMap;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);

    public void serverToClientCall() throws RemoteException {

        while (true) {
            String action = null;
            try {
                action = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (action) {
                case "startSetupOfNicknameAndToken":
                    synchronized (this.clients) {
                        for (VirtualView client : clients) client.setupOfnicknameAndToken();
                    }
                    break;
//            case "showFourCentralCardsToPlayers":
//                map.get("showFourCentralCardsToPlayers").showFourCentralCards();
//                break;
//            case "startSetupOfStartingCard":
//                map.get("startSetupOfStartingCard").setupOfStartingCard();
//                break;
//            case "firstHandSetup":
//                map.get("firstHandSetup").reciveMyFirstHand();
//                break;
//            case "setupOfSecretObjective":
//                map.get("setupOfSecretObjective").setupOfSecretObjective();
//                break;
//            case "notifyAllPlayersConnected":
//                map.get("notifyAllPlayersConnected").notifyAllPlayersConnected();
//                break;
            }

        }
    }

    public void startServerToClientCallThread(){
        Thread t = new Thread(() -> {
            while(true)
            {
                try {
                    serverToClientCall();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }

    public RmiServer() {
        clients = new ArrayList<>();
        clientsMap = new HashMap<>();
        gameController = new GameController();
    }

    @Override
    public void connectClient(VirtualView client) throws RemoteException {
        handleNewClient(client);
        System.err.println("new client connected");
    }


    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException {
        gameController.setPlayerNumber(playerNumber);
    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() throws RemoteException {
        return gameController.getAvailableToken();
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) throws RemoteException {
        return gameController.checkNicknameAvailability(nickname);
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) throws RemoteException {
        return gameController.checkTokenAvailability(token);
    }

    @Override
    public void addPlayer(String nickname, TokenEnum token, VirtualView Myclient) throws ChangedStateException, NotValidMoveException, RemoteException {
        gameController.addPlayer(nickname, token);
        for (VirtualView client : clients) {
            gameController.getBoard().getPlayer(nickname).addObserver(client);
            gameController.getBoard().getPlayer(nickname).getStation().addObserver(client);
        }
        clientsMap.put(nickname, Myclient);
        if (gameController.isGameState(GameState.INITIALIZE_GAME)){
            initializeGame();
        }
    }


    public void startSetupOfNicknameAndToken() throws RemoteException {
        for (VirtualView client : clients) {
            new Thread(() -> {
                try {
                    client.setupOfnicknameAndToken();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) throws ChangedStateException, NotValidMoveException, RemoteException {
        synchronized (gameController){
        gameController.setCentralCardPlayedBack(playedback, nickname, Id);}

    }

    @Override
    public ArrayList<CardResource> getMyHand(String nickname) throws RemoteException {
        synchronized (gameController){
        return gameController.getBoard().getPlayer(nickname).getHand();}
    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException {
        synchronized (gameController){
        gameController.setObjectiveOfPlayer(nickname, id);}

    }

    @Override
    public ArrayList<CardObjective> getSelectableObjectives(String nickname) throws RemoteException {
        synchronized (gameController){
        return gameController.getBoard().getPlayer(nickname).getSelectibleObjectives();}
    }

    @Override
    public void addCardToStation(String nickname,int id, boolean playedBack, int x, int y) throws RemoteException {
        try {
            gameController.addCardToPlayingStation(nickname, id, playedBack, x, y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void drawCard(String nickname, int cardToDraw, CardResource card) throws RemoteException {
        try {
            if(cardToDraw>=1 && cardToDraw<=4)
                gameController.addCardFromCentralCardsToPlayerHand(nickname, card.getId());
            else
                gameController.addCardFromDeckToPlayerHand(nickname, cardToDraw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CardGold getCardFromGoldDeck() throws RemoteException {
        return gameController.getBoard().getDeckCardGold().getLast();
    }

    @Override
    public CardResource getCardFromResourceDeck() throws RemoteException {
        return gameController.getBoard().getDeckCardResource().getLast();
    }

    public void initializeGame() throws RemoteException {
        try {
            gameController.InitializeGame();
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }
        showFourCentralCardsToPlayers();
    }

    public void showFourCentralCardsToPlayers() throws RemoteException {
        for (VirtualView client : clients) {
            client.showFourCentralCards();
        }
        startSetupOfStartingCard();
    }

    public void startSetupOfStartingCard() throws RemoteException {
        for (VirtualView client : clients) {
            new Thread(() -> {
                try {
                    client.notifyStartSetupOfStartingCard();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        while(true)
        {
            synchronized (gameController){
            if(gameController.getBoard().getGameState().equals(GameState.PLACING_CARD))
            {
                break;
            }}
        }
        startGame();
    }

    /*public void firstHandSetup() throws RemoteException {
        for (VirtualView client : clients) {
            client.reciveMyFirstHand();
        }
        setupOfSecretObjective();
    }

    public void setupOfSecretObjective() throws RemoteException {
        for (VirtualView client : clients) {
            client.setupOfSecretObjective();
        }
        startGame();
    }*/

    public void notifyAllPlayersConnected() throws RemoteException {
        for (VirtualView client : clients) {
            client.notifyAllPlayersConnected();
        }
    }

    public void handleNewClient(VirtualView client) throws RemoteException {
        //first player to join
        if (clients.isEmpty()) {
            clients.add(client);
            gameController.getBoard().addObserver(client);
            client.setupOfPlayersNumber();
            client.notifyWaitingForPlayersToJoin();
        }
        //another player is joining while setupping the player number
        else if (gameController.getBoard().getGameState().equals(GameState.SET_PLAYER_NUMBER)) {
            client.notifyAnotherPlayerSettingNumOfPlayers();
        }
        //all players have joined
        else if (clients.size() == gameController.getBoard().getPlayernumber() -1) {
            clients.add(client);
            gameController.getBoard().addObserver(client);
            notifyAllPlayersConnected();
            startSetupOfNicknameAndToken();
        }
        //player joining game and waiting for other players
        else if (clients.size() < gameController.getBoard().getPlayernumber() -1){
            clients.add(client);
            gameController.getBoard().addObserver(client);
            client.notifyWaitingForPlayersToJoin();
        }
        else{
            client.notifyGameAlreadyStarted();
        }
    }
    public  void startGame(){
        while (!gameController.isGamefinished()){
            try {
                clientsMap.get(gameController.getBoard().getCurrentPlayer()).notifyItIsYourTurn();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
