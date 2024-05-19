package Network.Server;

import Network.Client.RMI.VirtualView;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualServer extends Remote {
    void connectClient(VirtualView client) throws RemoteException;

    boolean askFirstPlayertoConnect()  throws RemoteException;
    void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException;

    ArrayList<TokenEnum> getAvailableTokens() throws RemoteException;

    boolean checkNicknameAvailability(String nickname) throws RemoteException;

    boolean checkTokenAvailability(TokenEnum token)throws RemoteException;

    void addPlayer(String nickname, TokenEnum token) throws RemoteException, ChangedStateException, NotValidMoveException;

    void startSetupOfNicknameAndToken() throws RemoteException;

    boolean morePlayersNeeded() throws RemoteException;

    void checkAllPlayersConnected() throws RemoteException;

    void disconnectClient(VirtualView client) throws RemoteException;

    void setStartingCardPlayedBack(boolean playedback, String nickname, int ID) throws ChangedStateException, NotValidMoveException, RemoteException;
}
