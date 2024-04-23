package Network.Server;

import Network.Client.RmiClient;
import Network.Client.VirtualView;
import controller.GameController;
import model.PlayingBoard;
import model.cards.CardStarting;

import java.rmi.RemoteException;
import java.util.*;

public class RmiServer implements VirtualServer{

    final GameController gameController;
    final List<VirtualView> clients;
    final Map<String, VirtualView> clientsMap;
    private Integer playerNumber;

    public RmiServer(GameController gameController) {
        this.gameController = gameController;
        clients = new ArrayList<>();
        clientsMap = new HashMap<>();
    }


    @Override
    public synchronized void connectClient(VirtualView client) {

            this.clients.add(client);
            System.err.println("new client connected");
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


    /**
     * Add a new player to the game
     * @param name the nickname of the player
     *Add new player to the model and also add clients with theirs nickname to server
     */
    @Override
    public synchronized void addNewPlayer(String name) {
        gameController.addPlayer(name);
        switch(this.clients.size())
        {
            case 1:
                clientsMap.put(name, clients.get(0));
                break;
            case 2:
                clientsMap.put(name, clients.get(1));
                break;
            case 3:
                clientsMap.put(name, clients.get(2));
                break;
            case 4:
                clientsMap.put(name, clients.get(3));
                break;
        }
        System.err.println("New player added" + name);
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


    /**
     * Add a starting card to the player
     * @param nickname the nickname of the player
     * @throws RemoteException
     */

    @Override
    public CardStarting getStartingCard(String nickname) throws RemoteException {
        CardStarting cardStarting = gameController.getStartingCard(nickname);
        clientsMap.get(nickname).showStartingCard(cardStarting);
        return cardStarting;
    }

    @Override
    public boolean allPlayerConnected() throws RemoteException {
        return Objects.equals(this.numberOfPlayer(), this.getPlayerNumber());
    }

    @Override
    public PlayingBoard getServerModel() throws RemoteException {
        return gameController.getBoard();
    }
}
