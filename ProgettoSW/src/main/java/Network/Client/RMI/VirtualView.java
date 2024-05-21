package Network.Client.RMI;

import observers.Observer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote, Observer {

    void setupOfnicknameAndToken() throws RemoteException;

    void setupOfStartingCard() throws RemoteException;

    void showFourCentralCards()throws RemoteException;

    void reciveMyFirstHand() throws RemoteException;

    void setupOfSecretObjective() throws RemoteException;

    void setupOfPlayersNumber() throws RemoteException;

    void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException;

    void notifyWaitingForPlayersToJoin() throws RemoteException;

    void notifyAllPlayersConnected() throws RemoteException;

    void notifyGameAlreadyStarted() throws RemoteException;
}
