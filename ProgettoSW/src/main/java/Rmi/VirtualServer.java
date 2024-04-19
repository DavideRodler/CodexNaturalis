package Rmi;

import View.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public void connect(VirtualView client) throws RemoteException;
}

