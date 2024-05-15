package Network.Server;

import Network.Client.RMI.VirtualView;
import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.Player;
import model.enums.GameState;
import model.enums.TokenEnum;
import socket.Messages.Message;
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
    public void addPlayer(String nickname, TokenEnum token) throws ChangedStateException, NotValidMoveException {
        gameController.addPlayer(nickname,token);
        for(VirtualView client : clients) {
            gameController.getBoard().getPlayer(nickname).addObserver(client);
            gameController.getBoard().getPlayer(nickname).getStation().addObserver(client);
        }
    }



    @Override
    public void startSetupOfNicknameAndToken() throws RemoteException {
        for (VirtualView client : clients) {
            client.setupOfnicknameAndToken();
        }
        startSetupOfStartingCard();
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

    public void startSetupOfStartingCard() throws RemoteException {
        try {
            gameController.InitializeGame();
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }
        for (VirtualView client : clients) {
            client.setupOfStartingCard();
        }
    }

}


