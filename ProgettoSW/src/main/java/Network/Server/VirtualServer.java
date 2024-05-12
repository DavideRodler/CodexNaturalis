package Network.Server;

import Network.Client.RMI.VirtualView;
import exception.ChangedStateException;
import exception.NotValidMoveException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void connectClient(VirtualView client) throws RemoteException;

    void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException;
}
