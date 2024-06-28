package observers;

import Messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
   /**
    * This method is used to update the observer
    * @param message the message to update
    * @throws RemoteException if a remote error occurs
    */
   void update(Message message) throws RemoteException;
}
