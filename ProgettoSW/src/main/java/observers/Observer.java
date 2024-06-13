package observers;

import Socket.Messages.Message;
import exception.NonePlayerFoundException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
   void update(Message message) throws RemoteException, NonePlayerFoundException;
}
