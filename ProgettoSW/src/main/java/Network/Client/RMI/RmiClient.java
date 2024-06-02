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
    public void notifyStartSetupOfStartingCard() throws RemoteException {
        clientController.setupOfStartingCard();
    }

    @Override
    public void showFourCentralCards() throws RemoteException {
        clientController.showFourCentralCards();

    }

    @Override
    public void setupOfPlayersNumber() throws RemoteException {
        clientController.setupOfPlayersNumber();

    }

    @Override
    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {
        clientController.notifyAnotherPlayerSettingNumOfPlayers();

    }

    @Override
    public void notifyWaitingForPlayersToJoin() throws RemoteException {
        clientController.notifyWaitingForPlayersToJoin();

    }

    @Override
    public void notifyAllPlayersConnected() throws RemoteException {
        clientController.notifyAllPlayersConnected();

    }

    @Override
    public void notifyGameAlreadyStarted() throws RemoteException {
        clientController.notifyGameAlreadyStarted();
    }

    @Override
    public void notifyItIsYourTurn() throws RemoteException{
        clientController.notifyItIsYourTurn();

    }
}
