package soket.client;

import soket.Messages.Message;

import java.rmi.RemoteException;

public interface VirtualViewSocket {
    void sendMessageToServer(Message m) throws RemoteException;
}
