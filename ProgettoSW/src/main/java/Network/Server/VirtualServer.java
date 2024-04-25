package Network.Server;

import Network.Client.ClientModel;
import Network.Client.RmiClient;
import Network.Client.VirtualView;
import model.PlayingBoard;
import model.PlayingStation;
import model.ReducedBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.cards.CardStarting;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void connectClient(VirtualView client) throws RemoteException;

    void addCard(CardResource card, Integer X, Integer Y) throws RemoteException;

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


    String getClientNickname(VirtualView client) throws RemoteException;

    VirtualView getClientIstance(String nickname) throws RemoteException;

    ReducedBoard getReducedBoard(VirtualView rmiClient) throws RemoteException;

    CardObjective[] getObjectiveCards(String clientNickname) throws RemoteException;

    PlayingStation inizializePlayingStation(String clientNickname, CardPlaying startingCard, Integer choice, CardObjective cardObjective) throws RemoteException;
}
