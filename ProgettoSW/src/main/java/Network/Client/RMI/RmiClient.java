package Network.Client.RMI;

import Network.Cli2;
import Network.Client.ClientController;
import Network.Server.VirtualServer;

import exception.NotValidMoveException;
import model.client.ClientBoard;
import observers.Observer;
import socket.Messages.ChangeStateMessage;
import socket.Messages.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends UnicastRemoteObject implements VirtualView {

    final VirtualServer server;
    private ClientController clientController;

    public RmiClient(VirtualServer server) throws RemoteException{
        this.server = server;
        this.clientController = new ClientController(server,this);
    }

    public void connectToServer() throws RemoteException{
        this.server.connectClient(this);
        clientController.StartGame();
        }

    @Override
    public void update(Message message) throws RemoteException {
        clientController.updateModel(message);
        }

    @Override
    public void setupOfnicknameAndToken() {
        clientController.setupOfnicknameAndToken();
    }
}
