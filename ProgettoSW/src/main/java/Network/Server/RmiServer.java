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

public class RmiServer implements VirtualServer {

    final GameController gameController;
    private List<VirtualView> clients;
    private HashMap<String, VirtualView> clientsMap;


    public RmiServer(GameController gameController) {
        this.gameController = gameController;
        gameController.initGameController();
        clients = new ArrayList<>();
        clientsMap = new HashMap<>();

    }

    @Override
    public synchronized void connectClient(VirtualView client) {
            this.clients.add(client);
        gameController.getBoard().addObserver(client);
            System.err.println("new client connected");
    }

    @Override
    public boolean askFirstPlayertoConnect() throws RemoteException {
        if (clients.size() == 1) {
            return true;
        }
        else {
            return false;
        }
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
        gameController.addPlayer(nickname,token);
        for(VirtualView client : clients) {
            gameController.getBoard().getPlayer(nickname).addObserver(client);
            gameController.getBoard().getPlayer(nickname).getStation().addObserver(client);
        }
        clientsMap.put(nickname, Myclient);
    }



    @Override
    public void startSetupOfNicknameAndToken() throws RemoteException {
        for (VirtualView client : clients) {
            client.setupOfnicknameAndToken();
        }
        initializeGame();
    }
 

    @Override
    public boolean morePlayersNeeded() throws RemoteException {
        if (clients.size() <= gameController.getBoard().getPlayernumber() || gameController.getBoard().getGameState().equals(GameState.SET_PLAYER_NUMBER)) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public void checkAllPlayersConnected() throws RemoteException {
        if(clients.size() == (gameController.getBoard().getPlayernumber())){
            startSetupOfNicknameAndToken();
        }
    }

    @Override
    public void disconnectClient(VirtualView client) throws RemoteException{
        for (int i = 0; i < clients.size(); i++) {
            clients.remove(clients.get(i));
            System.err.println("client disconnected");
        }
    }

    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) throws ChangedStateException, NotValidMoveException, RemoteException{
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

    public  void initializeGame() throws RemoteException {
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

}


