package Network.Server;

import Network.Client.RMI.VirtualView;
import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;
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

    private GameController gameController;
    private List<VirtualView> clients;
    private HashMap<String, VirtualView> clientsMap;
    private BlockingQueue<HashMap<String, VirtualView>> queue = new ArrayBlockingQueue<>(100);

    public void serverToClientCall()
    {
        try {
            HashMap<String, VirtualView> map = queue.take();
            switch(map.keySet().toString())
            {
                case "startSetupOfNicknameAndToken" : map.get("startSetupOfNicknameAndToken").setupOfnicknameAndToken();
                case "showFourCentralCardsToPlayers" : map.get("showFourCentralCardsToPlayers").showFourCentralCards();
                case "startSetupOfStartingCard" : map.get("startSetupOfStartingCard").setupOfStartingCard();
                case "firstHandSetup" : map.get("firstHandSetup").reciveMyFirstHand();
                case "setupOfSecretObjective" : map.get("setupOfSecretObjective").setupOfSecretObjective();
                case "notifyAllPlayersConnected" : map.get("notifyAllPlayersConnected").notifyAllPlayersConnected();
            }

        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void startServerToClientCallThread(){
        Thread t = new Thread(() -> {
            while(true)
            {
                serverToClientCall();
            }
        });
        t.start();
    }

    public RmiServer() {
        clients = new ArrayList<>();
        clientsMap = new HashMap<>();

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
    public void addPlayer(String nickname, TokenEnum token, VirtualView Myclient) throws ChangedStateException, NotValidMoveException {
        gameController.addPlayer(nickname, token);
        for (VirtualView client : clients) {
            gameController.getBoard().getPlayer(nickname).addObserver(client);
            gameController.getBoard().getPlayer(nickname).getStation().addObserver(client);
        }
        clientsMap.put(nickname, Myclient);
    }


    @Override
    public void startSetupOfNicknameAndToken() throws RemoteException {
        startServerToClientCallThread();
        for (VirtualView client : clients) {
            queue.add(new HashMap<>() {{
                put("startSetupOfNicknameAndToken", client);
            }});
        }
        initializeGame();
    }

    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) throws ChangedStateException, NotValidMoveException, RemoteException {
        gameController.setCentralCardPlayedBack(playedback, nickname, Id);

    }

    @Override
    public ArrayList<CardResource> getMyHand(String nickname) throws RemoteException {
        return gameController.getBoard().getPlayer(nickname).getHand();
    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException {
        gameController.setObjectiveOfPlayer(nickname, id);

    }

    @Override
    public ArrayList<CardObjective> getSelectableObjectives(String nickname) throws RemoteException {
        return gameController.getBoard().getPlayer(nickname).getSelectibleObjectives();
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
            client.setupOfStartingCard();
        }
        firstHandSetup();
    }

    public void firstHandSetup() throws RemoteException {
        for (VirtualView client : clients) {
            client.reciveMyFirstHand();
        }
        setupOfSecretObjective();
    }

    public void setupOfSecretObjective() throws RemoteException {
        for (VirtualView client : clients) {
            client.setupOfSecretObjective();
        }
    }

    public void notifyAllPlayersConnected() throws RemoteException {
        for (VirtualView client : clients) {
            client.notifyAllPlayersConnected();
        }
    }

    public void handleNewClient(VirtualView client) throws RemoteException {
        //first player to join
        if (clients.isEmpty()) {
            clients.add(client);
            this.gameController = new GameController();
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
}
