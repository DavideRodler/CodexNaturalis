package Network.Server.RMI;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.VirtualServer;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;
//import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;
import java.util.*;

public class RmiServer implements VirtualServer {
    private Server server;

    public RmiServer(Server server) {
        this.server = server;
    }


    @Override
    public void connectClient(VirtualView client) throws RemoteException {
        server.connectClient(client);
    }


    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException {
        server.setPlayerNumber(playerNumber);
    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() throws RemoteException {
        return server.getAvailableTokens();
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) throws RemoteException {
        return server.checkNicknameAvailability(nickname);
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) throws RemoteException {
        return server.checkTokenAvailability(token);
    }

    @Override
    public void addPlayer(String nickname, VirtualView Myclient) throws ChangedStateException, NotValidMoveException, RemoteException {
        server.addPlayer(nickname, Myclient);
    }

    @Override
    public void setToken(String nickname, TokenEnum token) throws RemoteException, ChangedStateException, NotValidMoveException {
        server.setToken(nickname, token);
    }


    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) throws ChangedStateException, NotValidMoveException, RemoteException {
        server.setStartingCardPlayedBack(playedback, nickname, Id);
    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException {
        server.setSecretObjective(nickname, id);
    }


    @Override
    public void addCardToStation(String nickname, int id, boolean playedBack, int x, int y) throws RemoteException, InvalidPlacingCondition {
        server.addCardToStation(nickname, id, playedBack, x, y);

    }


    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int deck) throws RemoteException, InvalidPlacingCondition {
        server.addCardFromDeckToPlayerHand(nickname, deck);
    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardId) throws RemoteException, NotMyTurnException {
        server.addCardFromCentralCardsToPlayerHand(nickname, cardId);
    }

    @Override
    public void startTurn() throws RemoteException, NotMyTurnException {
        server.startTurn();
    }
}
