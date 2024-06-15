package Network.Client.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.VirtualServer;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VirtualSocketServer implements VirtualServer {
    private PrintWriter output;

    public VirtualSocketServer(PrintWriter output) {
        this.output = output;
    }

    @Override
    public void connectClient(VirtualView client) throws RemoteException {

    }

    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException {
        output.println("setPlayerNumber");
        output.println(playerNumber);
        output.flush();

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
    public void addPlayer(String nickname, TokenEnum token, VirtualView Myclient) throws RemoteException, ChangedStateException, NotValidMoveException {

    }

    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int ID) throws ChangedStateException, NotValidMoveException, RemoteException {

    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException {

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
