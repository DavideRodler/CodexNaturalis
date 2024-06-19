package Network.Client.RMI;

import observers.Observer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface VirtualView extends Remote, Observer {

    void setupOfNickname() throws RemoteException;

    void notifyStartSetupOfStartingCard() throws RemoteException;

    void showFourCentralCards()throws RemoteException;

    void setupOfPlayersNumber() throws RemoteException;

    void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException;

    void notifyWaitingForPlayersToJoin() throws RemoteException;

    void notifyAllPlayersConnected() throws RemoteException;

    void notifyGameAlreadyStarted() throws RemoteException;

    void notifyItIsYourTurn() throws RemoteException;

    void notifyResultOfCardAddedToStation(boolean result, String message) throws RemoteException;

    void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) throws RemoteException;

    void showHandsAndCommonObjectives() throws RemoteException;

    void setupOfSecretObjective() throws RemoteException;

    void notifyNicknameAlreadyTaken() throws RemoteException;

    void notifyTokenAlreadyTaken() throws RemoteException;

    void setupOfToken() throws RemoteException;
}
