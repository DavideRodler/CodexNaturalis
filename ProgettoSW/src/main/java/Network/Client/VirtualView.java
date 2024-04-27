package Network.Client;

import model.cards.CardObjective;
import model.cards.CardStarting;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {

    void showUpdatedBoard() throws RemoteException;

    void showUpdatedHand() throws RemoteException;

    //void showObjectivetochoose(CardObjective firsrobj, CardObjective secondobj) throws  RemoteException;

    void showStartingCard(CardStarting cardStarting) throws RemoteException;

    void Loginupdate() throws RemoteException ;

    void showObjectiveCards(CardObjective[] cardObjective) throws RemoteException;

    void StartGameTurns() throws RemoteException, InterruptedException;

    void gameSituationUpdate() throws RemoteException;

    void showMyUpdatedBoard(String name) throws RemoteException;

    void decrementLatch() throws RemoteException;
}
