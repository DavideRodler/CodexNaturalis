package Network.Server;

import Network.Client.VirtualView;
import Observers.LoginObservable;

import controller.GameController;
import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.ReducedBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.cards.CardStarting;

import java.rmi.RemoteException;
import java.util.*;

public class RmiServer extends LoginObservable implements VirtualServer {

    final GameController gameController;
    final List<VirtualView> clients;
    final Map<String, VirtualView> clientsMapNicknamesKey;
    final Map<VirtualView, String> clientsMapClientsKey;
    private Integer playerNumber;

    public RmiServer(GameController gameController) {
        this.gameController = gameController;
        clients = new ArrayList<>();
        clientsMapNicknamesKey = new HashMap<>();
        clientsMapClientsKey = new HashMap<>();
    }


    @Override
    public synchronized void connectClient(VirtualView client) {

            this.clients.add(client);
            System.err.println("new client connected");
            try{
                this.addObserver(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void addCard(CardResource card, Integer X, Integer Y) {

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
                clientsMapNicknamesKey.put(name, clients.get(0));
                clientsMapClientsKey.put(clients.get(0), name);
                break;
            case 2:
                clientsMapNicknamesKey.put(name, clients.get(1));
                clientsMapClientsKey.put(clients.get(1), name);
                break;
            case 3:
                clientsMapNicknamesKey.put(name, clients.get(2));
                clientsMapClientsKey.put(clients.get(2), name);
                break;
            case 4:
                clientsMapNicknamesKey.put(name, clients.get(3));
                clientsMapClientsKey.put(clients.get(3), name);
                break;
        }
        System.err.println("New player added" + name);
        try{
            if(this.allPlayerConnected()){
                this.notifyObservers();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean nicknameCheck(String name) {
        return gameController.takenNickname(name);
    }

    @Override
    public synchronized Integer numberOfPlayer() throws RemoteException {
        return gameController.numberOfPlayer();
    }

    @Override
    public synchronized void initializeBoard() throws RemoteException {
        gameController.initGameController();
    }

    @Override
    public synchronized void inizializeLobby(Integer playerNumber) throws RemoteException {
        this.playerNumber = playerNumber;
    }

    public synchronized Integer getPlayerNumber() {
        return playerNumber;
    }






    /**
     * Add a starting card to the player
     * @param nickname the nickname of the player
     * @throws RemoteException
     */

    @Override
    public synchronized CardStarting getStartingCard(String nickname) throws RemoteException {
        CardStarting cardStarting = gameController.getStartingCard(nickname);
        clientsMapNicknamesKey.get(nickname).showStartingCard(cardStarting);
        return cardStarting;
    }

    @Override
    public synchronized boolean allPlayerConnected() throws RemoteException {
        return Objects.equals(this.numberOfPlayer(), this.getPlayerNumber());
    }

    @Override
    public synchronized PlayingBoard getServerModel() throws RemoteException {
        return gameController.getBoard();
    }

    public synchronized void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public synchronized VirtualView getClientIstance(String nickname) {
            return clientsMapNicknamesKey.get(nickname);
    }

    @Override
    public synchronized ReducedBoard getReducedBoard(VirtualView rmiClient) {
        return this.gameController.getReducedBoard(this.getClientNickname(rmiClient));
    }

    @Override
    public synchronized CardObjective[] getObjectiveCards(String nickname) throws RemoteException {
        CardObjective[] cardObjective = gameController.getObjectiveCards(nickname);
        clientsMapNicknamesKey.get(nickname).showObjectiveCards(cardObjective);
        return cardObjective;
    }

    @Override
    public synchronized PlayingStation inizializePlayingStation(String clientNickname, CardPlaying startingCard, Integer choice, CardObjective cardObjective) {
            Player player = gameController.getBoard().getPlayers().get(clientNickname);
            PlayingStation station = new PlayingStation(player, startingCard, cardObjective);
            player.setStation(station);
            return station;
    }

    @Override
    public synchronized String getClientNickname(VirtualView client) {
            return clientsMapClientsKey.get(client);

    }


}
