package Network.Client.RMI;

import Network.Client.ClientController;
import Network.Server.VirtualServer;

import Socket.Messages.Message;

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

    @Override
    public void setupOfStartingCard() throws RemoteException {
        clientController.setupOfStartingCard();

    }

    @Override
    public void showFourCentralCards() throws RemoteException {
        clientController.showFourCentralCards();

    }

    @Override
    public void reciveMyFirstHand() throws RemoteException {
        clientController.reciveMyFirstHand();

    }

    @Override
    public void setupOfSecretObjective() throws RemoteException {
        clientController.setupOfSecretObjective();

    }
}
