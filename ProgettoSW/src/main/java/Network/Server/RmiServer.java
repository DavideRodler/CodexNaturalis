package Network.Server;

import Network.Client.RMI.VirtualView;
import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.Player;
import model.enums.TokenEnum;
import socket.Messages.Message;
import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;
import java.util.*;

public class RmiServer implements VirtualServer {

    final GameController gameController;
    private List<VirtualView> clients;


    public RmiServer(GameController gameController) {
        this.gameController = gameController;
        gameController.initGameController();
        clients = new ArrayList<>();

    }

    @Override
    public synchronized void connectClient(VirtualView client) {
            this.clients.add(client);
            System.err.println("new client connected");
            this.gameController.getBoard().addObserver(client);
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
            //check if there are more clients connected than the number of players
            if(clients.indexOf(client) < gameController.getBoard().getPlayernumber()) {
                client.setupOfnicknameAndToken();
            }
        }
//        notifyAllClientsOfPlayersAdded();
    }

}


