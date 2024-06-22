package Network.Server;

import Network.Client.RMI.VirtualView;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import Socket.Messages.Message;
import Socket.Messages.ServerToClient.ActionMessage;
import Socket.Messages.ServerToClient.ResultOfCardAddedToStationMessage;
import Socket.Messages.queqe.QueueActionWithClientMessage;
import Socket.Messages.queqe.QueueResultOfCardAddedToStationMessage;
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
    private final List<VirtualView> clients;
    private HashMap<String, VirtualView> clientsMap;
    private BlockingQueue<Message> queue = new ArrayBlockingQueue<>(100);

    public void serverToClientCall() throws RemoteException {
        while (true) {
            Message actionMessage = null;
            try {
                actionMessage = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (actionMessage.getType()) {
                case "setupOfPlayersNumber":
                    synchronized (this.clients) {
                    VirtualView client = ((QueueActionWithClientMessage) actionMessage).getClient();
                            new Thread(() -> {
                                try {
                                    client.setupOfPlayersNumber();
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();
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
                    synchronized (this.clients){
                        VirtualView client = ((QueueActionWithClientMessage) actionMessage).getClient();
                        new Thread(() -> {
                            try {
                                client.notifyAnotherPlayerSettingNumOfPlayers();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                        }
                    break;
                case "notifyWaitingForPlayersToJoin":
                    synchronized (this.clients) {
                        VirtualView client = ((QueueActionWithClientMessage) actionMessage).getClient();
                            try {
                                client.notifyWaitingForPlayersToJoin();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    break;
                case "notifyGameAlreadyStarted":
                    synchronized (this.clients) {
                        VirtualView client = ((QueueActionWithClientMessage) actionMessage).getClient();
                            try {
                                client.notifyGameAlreadyStarted();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
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
                case "notifyNicknameAlreadyTaken":
                    synchronized (this.clients) {
                        VirtualView client = ((QueueActionWithClientMessage) actionMessage).getClient();
                        try {
                            client.notifyNicknameAlreadyTaken();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
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
                case "notifyTokenAlreadyTaken":
                    synchronized (this.clients) {
                        VirtualView client = ((QueueActionWithClientMessage) actionMessage).getClient();
                        try {
                            client.notifyTokenAlreadyTaken();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
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
                case "QueueResultOfCardAddedToStation":
                    synchronized (this.clients) {
                        QueueResultOfCardAddedToStationMessage message = (QueueResultOfCardAddedToStationMessage) actionMessage;
                        new Thread(() -> {
                            try {
                                message.getClient().notifyResultOfCardAddedToStation(message.isAdded(), message.getMessage());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
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
                default:
                    throw new RuntimeException("Message type not recognized");
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
    }

    private void handleNewClient(VirtualView client) {
        //first player to join
        if (clients.isEmpty()) {
            clients.add(client);
            System.err.println("new client connected");
            gameController.getBoard().addObserver(client);
            try {
                queue.put(new QueueActionWithClientMessage("setupOfPlayersNumber", client));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //another player is joining while setupping the player number
        else if (gameController.getBoard().getGameState().equals(GameState.SET_PLAYER_NUMBER)) {
            try {
                queue.put(new QueueActionWithClientMessage("notifyAnotherPlayerSettingNumOfPlayers", client));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //all players have joined
        else if (clients.size() == gameController.getBoard().getPlayernumber() -1) {
            clients.add(client);
            System.err.println("new client connected");
            gameController.getBoard().addObserver(client);
            try {
                queue.put(new ActionMessage("notifyAllPlayersConnected"));
                queue.put(new ActionMessage("startSetupOfNickname"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //player joining game and waiting for other players
        else if (clients.size() < gameController.getBoard().getPlayernumber() -1) {
            clients.add(client);
            System.err.println("new client connected");
            gameController.getBoard().addObserver(client);
            try {
                queue.put(new QueueActionWithClientMessage("notifyWaitingForPlayersToJoin", client));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                queue.put(new QueueActionWithClientMessage("notifyGameAlreadyStarted",client));
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
                try {
                    queue.put(new QueueActionWithClientMessage("notifyNicknameAlreadyTaken", Myclient));
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
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
                    try {
                        queue.put(new QueueActionWithClientMessage("notifyTokenAlreadyTaken", clientsMap.get(nickname)));
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
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
            String message = "NO MESSAGE";
            message = e.getMessage();
            try {
                queue.put(new QueueResultOfCardAddedToStationMessage(false, message, clientsMap.get(nickname)));
                return;
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            queue.put(new QueueResultOfCardAddedToStationMessage(true, "Card added to station", clientsMap.get(nickname)));
        } catch (InterruptedException e) {
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
                queue.put(new ActionMessage("startTurn"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                queue.put(new ActionMessage("gameFinished"));
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
        for (Map.Entry<String, VirtualView> entry : clientsMap.entrySet()) {
            gameController.getBoard().addSpecificObserver(entry.getKey(), entry.getValue());
        }
        for(var c : gameController.getBoard().getPlayers()){
            for(var d : gameController.getBoard().getPlayers()){
                if(!c.getNickname().equals(d.getNickname())){
                    gameController.addNewPrivateChat(c.getNickname(), d.getNickname());
                }
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
            queue.put(new ActionMessage("startSetupOfToken"));
        }
        catch (InterruptedException e ){
            throw new RuntimeException(e);
        }
    }

    private void showFourCentralCardsToPlayers()  {
        try {
            queue.put(new ActionMessage("showFourCentralCardsToPlayers"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startSetupOfStartingCard();
    }


    private void startSetupOfStartingCard()  {
        try {
            queue.put(new ActionMessage("startSetupOfStartingCard"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void showHandsAndCommonObjectives()  {
        try {
            queue.put(new ActionMessage("showHandsAndCommonObjectives"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setupOfSecretObjective();
    }

    private void setupOfSecretObjective()  {
        try {
            queue.put(new ActionMessage("setupOfSecretObjective"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public synchronized void takeGlobalMessage(GlobalChatMessage message){
        gameController.addMessageToGlobalChat(message.getNickname(), message.getMessage());
    }


    public void takePrivateMessage(PrivateChatMessage message) {
        gameController.addMessageToPrivateChat(message.getNicknameReceiver(), message.getNicknameSender(), message.getMessage());
    }



}
