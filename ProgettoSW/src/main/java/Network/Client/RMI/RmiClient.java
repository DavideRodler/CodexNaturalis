package Network.Client.RMI;

import Network.Client.ClientController;
import Network.Server.VirtualServer;

import Socket.Messages.Message;
import exception.NonePlayerFoundException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
    public void update(Message message) throws RemoteException, NonePlayerFoundException {
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

    @Override
    public void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) throws RemoteException {
        clientController.notifyGameFinished(scoreBoard);

    }

    @Override
    public void showHandsAndCommonObjectives() throws RemoteException {
        clientController.showHandsAndCommonObjectives();
    }

    @Override
    public void setupOfSecretObjective() throws RemoteException {
        clientController.setupOfSecretObjective();
    }

    @Override
    public void notifyIsNotYourTurn(String currentPlayer) throws RemoteException {
        clientController.notifyIsNotYourTurn(currentPlayer);
    }
}
