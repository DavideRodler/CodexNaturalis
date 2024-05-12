package Network.Server;

import Network.Client.RmiClient;
import Network.Client.VirtualView;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface VirtualServer extends Remote {
    void connectClient(VirtualView client) throws RemoteException;

    void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException;
}
