package Network.Server;

import Network.Client.VirtualView;
import controller.GameController;
import exception.ChangedStateException;
import exception.NotValidMoveException;

import java.rmi.RemoteException;
import java.util.*;

public class RmiServer implements VirtualServer {

    final GameController gameController;
    private List<VirtualView> clients;


    public RmiServer(GameController gameController) {
        this.gameController = gameController;
        gameController.initGameController();
        clients = new ArrayList<>();

    }

    @Override
    public synchronized void connectClient(VirtualView client) {
            this.clients.add(client);
            System.err.println("new client connected");
            this.gameController.getBoard().addObserver(client);
    }

    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException {
        gameController.setPlayerNumber(playerNumber);
    }

}
