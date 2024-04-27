package socket.client;

import socket.Messages.Message;

import java.rmi.RemoteException;

public interface VirtualViewSocket {
    void sendMessageToServer(Message m) throws RemoteException;
}
