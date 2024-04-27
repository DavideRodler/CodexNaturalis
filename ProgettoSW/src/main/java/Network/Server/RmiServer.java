package Network.Server;

import Network.Client.RmiClient;
import Network.Client.VirtualView;
import Observers.Observable;
import java.util.concurrent.CountDownLatch;

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
import java.util.concurrent.*;

public class RmiServer extends Observable implements VirtualServer {

    final GameController gameController;
    final BlockingQueue<VirtualView> blockingQueue = new ArrayBlockingQueue<>(100);
    private List<VirtualView> clients;
    private Map<String, VirtualView> clientsMapNicknamesKey;
    private Map<VirtualView, String> clientsMapClientsKey;
    private Integer CurrentTurn;
    private Integer playerReady;
    private Integer playerNumber;
    private Boolean showedBoard = false;


    public RmiServer(GameController gameController) {
        this.gameController = gameController;
        clients = new ArrayList<>();
        clientsMapNicknamesKey = new HashMap<>();
        clientsMapClientsKey = new HashMap<>();
        this.CurrentTurn = 0;
        this.playerReady = 0;
    }

    private void broadcastUpdateThread() throws InterruptedException, RemoteException {

        while(true)
        {
            VirtualView client = blockingQueue.take();
            client.StartGameTurns();
        }
    }

    public void setShowedBoard(Boolean showedBoard) {
        this.showedBoard = showedBoard;
    }

    @Override
    public synchronized void connectClient(VirtualView client) {

            this.clients.add(client);
            System.err.println("new client connected");
            try{
                this.addLoginObserver(client);
                this.addBoardObserver(client);
                this.addMyBoardObserver(client);
                //this.addStartingTurnObserver(client);
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
                List<String> playersNickname = gameController.getBoard().PlayerTurnOrder();
                // Crea nuove strutture dati
                List<VirtualView> newClients = new ArrayList<>();
                Map<String, VirtualView> newClientsMapNicknamesKey = new HashMap<>();
                Map<VirtualView, String> newClientsMapClientsKey = new HashMap<>();

                // Popola le nuove strutture dati nell'ordine corretto
                for (String nickname : playersNickname) {
                    VirtualView client = clientsMapNicknamesKey.get(nickname);
                    newClients.add(client);
                    newClientsMapNicknamesKey.put(nickname, client);
                    newClientsMapClientsKey.put(client, nickname);
                }

                // Sostituisci le vecchie strutture dati con le nuove
                clients = newClients;
                clientsMapNicknamesKey = newClientsMapNicknamesKey;
                clientsMapClientsKey = newClientsMapClientsKey;
                this.notifyLoginObservers();
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

    public synchronized Integer getCurrentTurn() {
        return CurrentTurn;
    }

    public synchronized void setCurrentTurn(Integer currentTurn) {
        CurrentTurn = currentTurn;
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
    public synchronized boolean isGameFinished() throws RemoteException {
        return false;
    }

    @Override
    public synchronized boolean isMyTurn(VirtualView rmiClient) {
        return getCurrentTurn() == clients.indexOf(rmiClient);
    }

    @Override
    public synchronized String getClientNickname(VirtualView client) {
            return clientsMapClientsKey.get(client);

    }

    @Override
    public synchronized void loginThreadsStopper() throws RemoteException, InterruptedException {
        stopAllLoginThreads();
        playerToNextTurn(0);
        new Thread(() -> {
            try {
                broadcastUpdateThread();
            } catch (InterruptedException | RemoteException e) {
                e.printStackTrace();
            }
        }).start();
        notifyStartingTurnObservers();
    }

    @Override
    public synchronized void startTurnNotify() throws RemoteException {
        notifyBoardObservers();
    }

    @Override
    public synchronized void allPlayerReady() throws RemoteException, InterruptedException {
        addingPlayerReady();
        if(getPlayerReady() == clients.size()){
            this.loginThreadsStopper();
        }
    }

    @Override
    public synchronized void showedBoardNotify() throws RemoteException {
        stopAllBoardThreads();
        getClientIstance(getClientNickname(clients.get(getCurrentTurn()))).decrementLatch();
    }

    @Override
    public synchronized void notifyMyUpdatedBoard(VirtualView rmiClient) {
        System.out.println("FLAG1");
        notifyMyBoardObservers(getClientNickname(rmiClient));
    }

    @Override
    public synchronized void showedMyBoardNotify() throws RemoteException {
        stopAllMyBoardThreads();
        setShowedBoard(true);
    }

    @Override
    public synchronized void nextTurn() throws RemoteException, InterruptedException {
        if(getCurrentTurn()< (clients.size()-1))
            setCurrentTurn(getCurrentTurn()+1);
        else
            setCurrentTurn(0);
        playerToNextTurn(getCurrentTurn());

    }

    @Override
    public boolean getShowedBoardNotify() throws RemoteException {
        return this.showedBoard;
    }

    private synchronized void addingPlayerReady() {
        this.playerReady ++;
    }

    public synchronized Integer getPlayerReady() {
        return this.playerReady;
    }

    public synchronized void playerToNextTurn(Integer index) throws InterruptedException {
        blockingQueue.put(clients.get(index));
    }
}
