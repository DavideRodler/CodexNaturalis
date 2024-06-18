package Network.Client.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.VirtualServer;
import Socket.Messages.ClientToServer.*;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VirtualSocketServer implements VirtualServer {
    private ObjectOutputStream output;

    public VirtualSocketServer(ObjectOutputStream output) {
        this.output = output;
    }

    @Override
    public void connectClient(VirtualView client) throws RemoteException {
        ConnectClientMessage message = new ConnectClientMessage();
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException {
        SetPlayerNumberMessage message = new SetPlayerNumberMessage(playerNumber);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPlayer(String nickname, VirtualView Client) throws RemoteException, ChangedStateException, NotValidMoveException {
        AddPlayerMessage message = new AddPlayerMessage(nickname);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setToken(String nickname, TokenEnum token) throws RemoteException {
        SetTokenMessage message = new SetTokenMessage(nickname, token);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() throws RemoteException {
        return null;
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) throws RemoteException {
        return false;
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) throws RemoteException {
        return false;
    }

    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int ID) throws ChangedStateException, NotValidMoveException, RemoteException {
        SetStartingCardPlayedBackMessage message = new SetStartingCardPlayedBackMessage(playedback, nickname, ID);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException {
        SetSecretObjectiveMessage message = new SetSecretObjectiveMessage(nickname, id);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardToStation(String nickname, int cardId, boolean playedBack, int x, int y) throws RemoteException, InvalidPlacingCondition {

    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardId) throws RemoteException, InvalidPlacingCondition {

    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int id) throws RemoteException, NotMyTurnException {

    }

    @Override
    public void startTurn() throws RemoteException, NotMyTurnException {

    }
}
