package Network.Client.RMI;

import observers.Observer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote, Observer {

    void setupOfnicknameAndToken() throws RemoteException;

    void notifyStartSetupOfStartingCard() throws RemoteException;

    void showFourCentralCards()throws RemoteException;

    void setupOfPlayersNumber() throws RemoteException;

    void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException;

    void notifyWaitingForPlayersToJoin() throws RemoteException;

    void notifyAllPlayersConnected() throws RemoteException;

    void notifyGameAlreadyStarted() throws RemoteException;

    void notifyItIsYourTurn() throws RemoteException;
}
