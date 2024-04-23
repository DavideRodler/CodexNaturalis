package Network.Server;

import Network.Client.VirtualView;
import model.PlayingBoard;
import model.cards.CardPlaying;
import model.cards.CardStarting;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void connectClient(VirtualView client) throws RemoteException;

    void addCard(Integer number, Integer X, Integer Y) throws RemoteException;

    void passTurn() throws RemoteException;

    void drawCard(Integer number) throws RemoteException;

    void addNewPlayer(String name) throws RemoteException;

    boolean nicknameCheck(String name) throws RemoteException;

    Integer numberOfPlayer() throws RemoteException;

    void initializeBoard() throws RemoteException;
    
    void inizializeLobby(Integer playernumber) throws RemoteException;

    Integer getPlayerNumber() throws RemoteException;

    CardPlaying getStartingCard(String nickname) throws RemoteException;

    PlayingBoard getServerModel() throws RemoteException;

    boolean allPlayerConnected() throws RemoteException;
}
