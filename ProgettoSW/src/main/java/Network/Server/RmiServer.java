package Network.Server;

import Network.Client.VirtualView;
import controller.GameController;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RmiServer implements VirtualServer{

    final GameController gameController;
    final List<VirtualView> clients;
    private Integer playerNumber;

    public RmiServer(GameController gameController) {
        this.gameController = gameController;
        clients = new ArrayList<>();
    }


    @Override
    public void connectClient(VirtualView client) {
            this.clients.add(client);
    }

    @Override
    public synchronized void addCard(Integer number, Integer X, Integer Y) {

    }

    @Override
    public synchronized void passTurn() {

    }

    @Override
    public synchronized void drawCard(Integer number) {

    }

    @Override
    public synchronized void addNewPlayer(String name) {
        gameController.addPlayer(name);
    }

    @Override
    public synchronized boolean nicknameCheck(String name) {
        return gameController.takenNickname(name);
    }

    @Override
    public Integer numberOfPlayer() throws RemoteException {
        return gameController.numberOfPlayer();
    }

    @Override
    public void initializeBoard() throws RemoteException {
        gameController.initGameController();
    }

    @Override
    public void inizializeLobby(Integer playerNumber) throws RemoteException {
        this.playerNumber = playerNumber;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public void addStartingCard(String nickname) throws RemoteException {
        gameController.addStartingCard(nickname);
        for(var c : this.clients){

            c.showUpdatedHand();
        }
    }
}
