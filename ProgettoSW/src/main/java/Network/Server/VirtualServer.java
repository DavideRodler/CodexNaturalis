package Network.Server;

import Network.Client.RmiClient;
import Network.Client.VirtualView;
import model.PlayingBoard;
import model.PlayingStation;
import model.ReducedBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface VirtualServer extends Remote {
    void connectClient(VirtualView client) throws RemoteException;


    boolean addCard(CardPlaying card, Integer side, Integer X, Integer Y) throws RemoteException;

    CardPlaying drawCard(Integer number) throws RemoteException;

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

    boolean isGameFinished() throws RemoteException;

    void loginThreadsStopper() throws RemoteException, InterruptedException;

    void startTurnNotify() throws RemoteException, InterruptedException;

    void allPlayerReady() throws RemoteException, InterruptedException;


    void notifyMyUpdatedBoard(VirtualView rmiClient, Map<ArrayList<Integer>, CardPlaying> playingStation) throws RemoteException;

    void showedMyBoardNotify() throws RemoteException;

    void nextTurn() throws RemoteException, InterruptedException;

}
