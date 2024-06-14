package Network.Server;

import Network.Client.RMI.VirtualView;
import controller.GameController;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.Player;
import model.enums.GameState;
import model.enums.TokenEnum;
//import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RmiServer implements VirtualServer {

    private final GameController gameController;
    private List<VirtualView> clients;
    private HashMap<String, VirtualView> clientsMap;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    private int menuCounter=0;

    public int getMenuCounter(){
        return this.menuCounter;
    }

    public void setMenuCounter(int menuCounter){
        this.menuCounter=menuCounter;
    }


    public void serverToClientCall() throws RemoteException {
        while (true) {
            String action = null;
            try {
                action = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (action) {
                case "notifyAllPlayersConnected":
                    synchronized (this.clients) {
                        for (VirtualView client : clients) {
                                try {
                                    client.notifyAllPlayersConnected();
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                        }
                }
                break;

                case "startSetupOfNicknameAndToken":
                    synchronized (this.clients) {
                        for (VirtualView client : clients) {
                            new Thread(() -> {
                                try {
                                    client.setupOfnicknameAndToken();
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();
                        }
                    }
                    break;

            case "showFourCentralCardsToPlayers":
                synchronized (this.clients) {
                    for (VirtualView client : clients) {
                        client.showFourCentralCards();
                    }
                }
                break;
            case "startSetupOfStartingCard":
                synchronized (this.clients) {
                    for (VirtualView client : clients) {
                        new Thread(() -> {
                            try {
                                client.notifyStartSetupOfStartingCard();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    }
                }
                break;
            case "showHandsAndCommonObjectives":
                synchronized (this.clients) {
                    for (VirtualView client : clients) {
                        new Thread(() -> {
                            try {
                                client.showHandsAndCommonObjectives();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    }
                }
                break;
            case "setupOfSecretObjective":
                synchronized (this.clients) {
                    for (VirtualView client : clients) {
                        new Thread(() -> {
                            try {
                                client.setupOfSecretObjective();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    }
                }
                break;

            case "startTurn":
                    System.out.println("startTurn");
                    for(VirtualView client : clients) {
                        if (!client.equals(clientsMap.get(gameController.getBoard().getCurrentPlayer()))) {
                            while(true){
                                if(menuChecker()){
                                    new Thread(() -> {
                                        try {
                                            client.notifyIsNotYourTurn(gameController.getBoard().getCurrentPlayer());
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).start();
                                    break;
                                }
                            }
                        }
                        else {
                            while (true) {
                                if (menuChecker()) {
                                    new Thread(() -> {
                                        try {
                                            client.notifyItIsYourTurn();
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).start();
                                    break;
                                }
                            }
                        }
                    }

                    this.setMenuCounter(0);
            break;
            case "gameFinished":
                synchronized (this.clients) {
                    for (VirtualView client : clients) {
                        try {
                            client.notifyGameFinished(gameController.getScoreBoard());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            break;


            }

        }
    }

    public void startServerToClientCallThread(){
        Thread t = new Thread(() -> {
            while(true)
            {
                try {
                    serverToClientCall();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }

    public RmiServer() {
        clients = new ArrayList<>();
        clientsMap = new HashMap<>();
        gameController = new GameController();
        startServerToClientCallThread();
    }

    @Override
    public void connectClient(VirtualView client) throws RemoteException {
        handleNewClient(client);
        this.setMenuCounter(this.getMenuCounter()+1);
        System.err.println("new client connected");
    }

    @Override
    public synchronized boolean menuChecker(){
        return getMenuCounter() == this.clients.size();
    }

    @Override
    public synchronized void updatePlayerReadyForNewMenu(int i) throws RemoteException {
        if (i==1)
            this.setMenuCounter(this.getMenuCounter()+1);
    }

    @Override
    public synchronized void unlockAllThreadsForNewMenu() throws RemoteException {
            for(VirtualView client : clients){
                client.unlockThread();
                System.out.println("FLAG");
            }

    }


    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException {
        gameController.setPlayerNumber(playerNumber);
    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() throws RemoteException {
        return gameController.getAvailableToken();
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) throws RemoteException {
        return gameController.checkNicknameAvailability(nickname);
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) throws RemoteException {
        return gameController.checkTokenAvailability(token);
    }

    @Override
    public void addPlayer(String nickname, TokenEnum token, VirtualView Myclient) throws ChangedStateException, NotValidMoveException, RemoteException {
        gameController.addPlayer(nickname, token);
        clientsMap.put(nickname, Myclient);
        if (gameController.isGameState(GameState.INITIALIZE_GAME)){
            initializeGame();
        }
    }




    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) throws ChangedStateException, NotValidMoveException, RemoteException {
        synchronized (gameController) {
            gameController.setCentralCardPlayedBack(playedback, nickname, Id);
            if (gameController.getBoard().getGameState().equals(GameState.SELECT_OBJECTIVE)) {
                showHandsAndCommonObjectives();
            }
        }
    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException {
        synchronized (gameController){
        gameController.setObjectiveOfPlayer(nickname, id);}
        if(gameController.getBoard().getGameState().equals(GameState.PLACING_CARD)){
            startTurn();
        }
    }


    @Override
    public void addCardToStation(String nickname,int id, boolean playedBack, int x, int y) throws RemoteException, InvalidPlacingCondition {
        gameController.addCardToPlayingStation(nickname, id, playedBack, x, y);

    }


    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int deck) throws RemoteException, InvalidPlacingCondition {
        try {
            gameController.addCardFromDeckToPlayerHand(nickname, deck);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (NotMyTurnException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardId) throws RemoteException, NotMyTurnException {
        try {
            gameController.addCardFromCentralCardsToPlayerHand(nickname, cardId);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }
    }

    public void startSetupOfNicknameAndToken() throws RemoteException {
        try {
            queue.put("startSetupOfNicknameAndToken");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void initializeGame() throws RemoteException {
        for (Player p : gameController.getBoard().getPlayers()){
            //adding  normal observers to players and stations
            for(VirtualView client : clients){
                p.addObserver(client);
                p.getStation().addObserver(client);
            }
            //adding specific observers to players and stations
            for (Map.Entry<String, VirtualView> entry : clientsMap.entrySet()) {
                p.addSpecificObserver(entry.getKey(), entry.getValue());
                p.getStation().addSpecificObserver(entry.getKey(), entry.getValue());
            }
        }
        try {
            //initialize gameController
            gameController.InitializeGame();
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }
        showFourCentralCardsToPlayers();
    }

    public void showFourCentralCardsToPlayers() throws RemoteException {
        try {
            queue.put("showFourCentralCardsToPlayers");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startSetupOfStartingCard();
    }

    public void startSetupOfStartingCard() throws RemoteException {
        try {
            queue.put("startSetupOfStartingCard");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void showHandsAndCommonObjectives() throws RemoteException {
       try {
              queue.put("showHandsAndCommonObjectives");
         } catch (InterruptedException e) {
              throw new RuntimeException(e);
       }
      setupOfSecretObjective();
    }

    public void setupOfSecretObjective() throws RemoteException {
        try {
            queue.put("setupOfSecretObjective");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyAllPlayersConnected() throws RemoteException {
        try {
            queue.put("notifyAllPlayersConnected");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public void handleNewClient(VirtualView client) throws RemoteException {
        //first player to join
        if (clients.isEmpty()) {
            clients.add(client);
            gameController.getBoard().addObserver(client);
            client.setupOfPlayersNumber();
            client.notifyWaitingForPlayersToJoin();
        }
        //another player is joining while setupping the player number
        else if (gameController.getBoard().getGameState().equals(GameState.SET_PLAYER_NUMBER)) {
            client.notifyAnotherPlayerSettingNumOfPlayers();
        }
        //all players have joined
        else if (clients.size() == gameController.getBoard().getPlayernumber() -1) {
            clients.add(client);
            gameController.getBoard().addObserver(client);
            notifyAllPlayersConnected();
            startSetupOfNicknameAndToken();
        }
        //player joining game and waiting for other players
        else if (clients.size() < gameController.getBoard().getPlayernumber() -1){
            clients.add(client);
            gameController.getBoard().addObserver(client);
            client.notifyWaitingForPlayersToJoin();
        }
        else{
            client.notifyGameAlreadyStarted();
        }
    }

    @Override
    public  void startTurn(){
        if (!gameController.getBoard().getGameState().equals(GameState.FINISHED)){
            try {
                queue.put("startTurn");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                queue.put("gameFinished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}

