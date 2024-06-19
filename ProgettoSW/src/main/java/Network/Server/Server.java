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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server {

    private final GameController gameController;
    private List<VirtualView> clients;
    private HashMap<String, VirtualView> clientsMap;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);

    public void serverToClientCall() throws RemoteException {
        while (true) {
            String action = null;
            try {
                action = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (action) {
                case "setupOfPlayersNumber":
                    try {
                        clients.get(0).setupOfPlayersNumber();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
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
                case "notifyAnotherPlayerSettingNumOfPlayers":
                    synchronized (this.clients) {
                        VirtualView client = clients.get(clients.size() - 1);
                            try {
                                client.notifyAnotherPlayerSettingNumOfPlayers();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        clients.remove(client);
                        }
                    break;
                case "notifyWaitingForPlayersToJoin":
                    synchronized (this.clients) {
                        VirtualView client = clients.get(clients.size() - 1);
                            try {
                                client.notifyWaitingForPlayersToJoin();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    break;
                case "notifyGameAlreadyStarted":
                    synchronized (this.clients) {
                        VirtualView client = clients.get(clients.size() - 1);
                            try {
                                client.notifyGameAlreadyStarted();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        clients.remove(client);
                        }
                    break;
                case "startSetupOfNickname":
                    synchronized (this.clients) {
                        for (VirtualView client : clients) {
                            new Thread(() -> {
                                try {
                                    client.setupOfNickname();
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();
                        }
                    }
                    break;
                case "startSetupOfToken":
                    synchronized (this.clients) {
                        for (VirtualView client : clients) {
                            new Thread(() -> {
                                try {
                                    client.setupOfToken();
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
                    try {
                        clientsMap.get(gameController.getBoard().getCurrentPlayer()).notifyItIsYourTurn();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
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

    public Server() {
        clients = new ArrayList<>();
        clientsMap = new HashMap<>();
        gameController = new GameController();
        startServerToClientCallThread();
    }

    public void connectClient(VirtualView client) {
        handleNewClient(client);
        System.err.println("new client connected");
    }

    private void handleNewClient(VirtualView client) {
        //first player to join
        if (clients.isEmpty()) {
            clients.add(client);
            gameController.getBoard().addObserver(client);
            try {
                queue.put("setupOfPlayersNumber");
                queue.put("notifyWaitingForPlayersToJoin");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //another player is joining while setupping the player number
        else if (gameController.getBoard().getGameState().equals(GameState.SET_PLAYER_NUMBER)) {
            clients.add(client);
            try {
                queue.put("notifyAnotherPlayerSettingNumOfPlayers");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //all players have joined
        else if (clients.size() == gameController.getBoard().getPlayernumber() -1) {
            clients.add(client);
            gameController.getBoard().addObserver(client);
            try {
                queue.put("notifyAllPlayersConnected");
                queue.put("startSetupOfNickname");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //player joining game and waiting for other players
        else if (clients.size() < gameController.getBoard().getPlayernumber() -1) {
            clients.add(client);
            gameController.getBoard().addObserver(client);
            try {
                queue.put("notifyWaitingForPlayersToJoin");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            clients.add(client);
            try {
                queue.put("notifyGameAlreadyStarted");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setPlayerNumber(int playerNumber){
        try {
            gameController.setPlayerNumber(playerNumber);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPlayer(String nickname, VirtualView Myclient) throws RemoteException {
        try {
            gameController.addPlayer(nickname);
        } catch (NotValidMoveException | ChangedStateException e) {
            if (e.getMessage().equals("Nickname already taken")) {
                Myclient.notifyNicknameAlreadyTaken();
                return;
            }
            else {
                throw new RuntimeException(e);
            }
        }
        clientsMap.put(nickname, Myclient);
        if (gameController.isGameState(GameState.INITIALIZE_GAME)){
            initializeGame();
        }
    }

    public void setToken(String nickname, TokenEnum token) throws RemoteException {
        synchronized (gameController) {
            try {
                gameController.selectToken(nickname, token);
            }
            catch (NotValidMoveException e){
                if (e.getMessage().equals("Token already taken")){
                    clientsMap.get(nickname).notifyTokenAlreadyTaken();
                    return;
                }
                else {
                    throw new RuntimeException(e);
                }
            } catch (ChangedStateException e) {
                throw new RuntimeException(e);
            }
        }
        if(gameController.getBoard().getGameState().equals(GameState.SELECT_STARTINGCARDFACE)){
            showFourCentralCardsToPlayers();
        }
    }


    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) {
        synchronized (gameController) {
            try {
                gameController.setCentralCardPlayedBack(playedback, nickname, Id);
            } catch (NotValidMoveException e) {
                throw new RuntimeException(e);
            } catch (ChangedStateException e) {
                throw new RuntimeException(e);
            }
            if (gameController.getBoard().getGameState().equals(GameState.SELECT_OBJECTIVE)) {
                showHandsAndCommonObjectives();
            }
        }
    }

    public void setSecretObjective(String nickname, Integer id) {
        synchronized (gameController){
            try {
                gameController.setObjectiveOfPlayer(nickname, id);
            } catch (NotValidMoveException e) {
                throw new RuntimeException(e);
            } catch (ChangedStateException e) {
                throw new RuntimeException(e);
            }
        }
        if(gameController.getBoard().getGameState().equals(GameState.PLACING_CARD)){
            startTurn();
        }
    }


    public void addCardToStation(String nickname,int id, boolean playedBack, int x, int y) {
        try {
            gameController.addCardToPlayingStation(nickname, id, playedBack, x, y);
        } catch (InvalidPlacingCondition e) {
            throw new RuntimeException(e);
        }

    }


    public void addCardFromDeckToPlayerHand(String nickname, int deck){
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

    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardId){
        try {
            gameController.addCardFromCentralCardsToPlayerHand(nickname, cardId);
        } catch (NotValidMoveException | NotMyTurnException | ChangedStateException e) {
            throw new RuntimeException(e);
        }
    }

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

    private void initializeGame() {
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
        try {
            queue.put("startSetupOfToken");
        }
        catch (InterruptedException e ){
            throw new RuntimeException(e);
        }
    }

    private void showFourCentralCardsToPlayers()  {
        try {
            queue.put("showFourCentralCardsToPlayers");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startSetupOfStartingCard();
    }


    private void startSetupOfStartingCard()  {
        try {
            queue.put("startSetupOfStartingCard");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void showHandsAndCommonObjectives()  {
        try {
            queue.put("showHandsAndCommonObjectives");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setupOfSecretObjective();
    }

    private void setupOfSecretObjective()  {
        try {
            queue.put("setupOfSecretObjective");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
