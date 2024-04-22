package Network.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {

    void showUpdatedBoard() throws RemoteException;

    void showUpdatedHand() throws RemoteException;
}
