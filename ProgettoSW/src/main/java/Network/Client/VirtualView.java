package Network.Client;

import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardStarting;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface VirtualView extends Remote {


    void showStartingCard(CardStarting cardStarting) throws RemoteException;

    void Loginupdate() throws RemoteException ;

    void showObjectiveCards(CardObjective[] cardObjective) throws RemoteException;

    void StartGameTurns() throws RemoteException, InterruptedException;

    void gameSituationUpdate() throws RemoteException;

    void showMyUpdatedBoard(Map<ArrayList<Integer>, CardPlaying> playingStation, String name) throws RemoteException;

    void playerTurn() throws RemoteException, InterruptedException;
}
