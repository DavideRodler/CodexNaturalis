package Network.Server.RMI;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.VirtualServer;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
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
    public void setPlayerNumber(int playerNumber) throws RemoteException {
        server.setPlayerNumber(playerNumber);
    }

    @Override
    public void addPlayer(String nickname, VirtualView Myclient) throws RemoteException {
        server.addPlayer(nickname, Myclient);
    }

    @Override
    public void setToken(String nickname, TokenEnum token) throws RemoteException{
        server.setToken(nickname, token);
    }


    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) throws RemoteException {
        server.setStartingCardPlayedBack(playedback, nickname, Id);
    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException {
        server.setSecretObjective(nickname, id);
    }


    @Override
    public void addCardToStation(String nickname, int id, boolean playedBack, int x, int y) throws RemoteException {
        server.addCardToStation(nickname, id, playedBack, x, y);

    }


    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int deck) throws RemoteException {
        server.addCardFromDeckToPlayerHand(nickname, deck);
    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardId) throws RemoteException {
        server.addCardFromCentralCardsToPlayerHand(nickname, cardId);
    }

    @Override
    public void startTurn() throws RemoteException {
        server.startTurn();
    }

    @Override
    public void takeGlobalMessage(GlobalChatMessage globalChatMessage) throws RemoteException{
        server.takeGlobalMessage(globalChatMessage);

    }

    @Override
    public void takePrivateMessage(PrivateChatMessage privateChatMessage) throws RemoteException{
        server.takePrivateMessage(privateChatMessage);

    }
}
