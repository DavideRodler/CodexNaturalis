package Network.Client.RMI;

import Network.Client.ClientToServerCommunication;
import Network.Client.ClientController;
import Network.Server.VirtualServer;

import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import Socket.Messages.ClientToServer.*;
import Socket.Messages.Message;
import model.enums.TokenEnum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class is the RMI implementation of the client to server communication.
 * It extends UnicastRemoteObject and implements the ClientToServerCommunication and VirtualView interfaces.
 * It has a queue of messages that is used to send messages to the server to make the
 * Rmi connection async.
 * It also recive messages from the server and send them to the client controller to update the model or
 * perform different action.
 */
public class RmiClientToServer extends UnicastRemoteObject implements ClientToServerCommunication, VirtualView {

    private ClientController clientController;
    private final VirtualServer server;
    private BlockingQueue<Message> queue = new ArrayBlockingQueue<>(100);

    /**
     * Constructor of the class
     * Create a new thread that call the ClientToServerCall method
     * that is used to read the messages from the queue and send them to the server
     * @param server
     * @throws RemoteException
     */
    public RmiClientToServer(VirtualServer server) throws RemoteException{
        this.server = server;
        new Thread(() ->{
            try {
                ClientToServerCall();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * Set the client controller
     * @param clientController
     */
    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }

    /**
     * This method is used to read the messages from the queue and send them to the server
     * @throws RemoteException
     */
    public void ClientToServerCall() throws RemoteException {
        while (true) {
            Message actionMessage = null;
            try {
                actionMessage = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (actionMessage.getType()) {
                case "ConnectToServer"-> {
                    this.server.connectClient(this);
                }
                case "SetPlayerNumber"-> {
                    SetPlayerNumberMessage setPlayerNumberMessage = (SetPlayerNumberMessage) actionMessage;
                    server.setPlayerNumber(setPlayerNumberMessage.getNumber());
                }
                case "AddPlayer"-> {
                    AddPlayerMessage addPlayerMessage = (AddPlayerMessage) actionMessage;
                    server.addPlayer(addPlayerMessage.getNickname(), this);
                }
                case "SetToken"-> {
                    SetTokenMessage setTokenMessage = (SetTokenMessage) actionMessage;
                    server.setToken(setTokenMessage.getNickname(), setTokenMessage.getToken());
                }
                case "SetStartingCardPlayedBack" -> {
                    SetStartingCardPlayedBackMessage setStartingCardPlayedBackMessage = (SetStartingCardPlayedBackMessage) actionMessage;
                    server.setStartingCardPlayedBack(setStartingCardPlayedBackMessage.isPlayedBack(), setStartingCardPlayedBackMessage.getNickname(), setStartingCardPlayedBackMessage.getId());

                }
                case "SetSecretObjective" -> {
                    SetSecretObjectiveMessage setSecretObjectiveMessage = (SetSecretObjectiveMessage) actionMessage;
                    server.setSecretObjective(setSecretObjectiveMessage.getNickname(), setSecretObjectiveMessage.getId());
                }
                case "AddCardToStation" -> {
                    AddCardToStationMessage addCardToStationMessage = (AddCardToStationMessage) actionMessage;
                    server.addCardToStation(addCardToStationMessage.getNickname(), addCardToStationMessage.getCardId(), addCardToStationMessage.isPlayedBack(), addCardToStationMessage.getX(), addCardToStationMessage.getY());
                    }
                case "AddCardFromCentralCardsToPlayerHand" -> {
                    AddCardFromCentralCardsToPlayerHandMessage addCardFromCentralCardsToPlayerHandMessage = (AddCardFromCentralCardsToPlayerHandMessage) actionMessage;
                    server.addCardFromCentralCardsToPlayerHand(addCardFromCentralCardsToPlayerHandMessage.getNickname(), addCardFromCentralCardsToPlayerHandMessage.getCardId());
                }
                case "AddCardFromDeckToPlayerHand" -> {
                    AddCardFromDeckToPlayerHandMessage addCardFromDeckToPlayerHandMessage = (AddCardFromDeckToPlayerHandMessage) actionMessage;
                    server.addCardFromDeckToPlayerHand(addCardFromDeckToPlayerHandMessage.getNickname(), addCardFromDeckToPlayerHandMessage.getCardId());
                }
                case "GLOBAL" -> {
                    GlobalChatMessage globalChatMessage = (GlobalChatMessage) actionMessage;
                    server.takeGlobalMessage(globalChatMessage);
                }
                case "PRIVATE" -> {
                    PrivateChatMessage privateChatMessage = (PrivateChatMessage) actionMessage;
                    server.takePrivateMessage(privateChatMessage);
                }
                default -> throw new RuntimeException("Invalid message type");
            }
        }
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void connectToServer() throws RemoteException {
        ConnectToServerMessage message = new ConnectToServerMessage();
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            try {
                startHeartbeat();
            } catch (RemoteException e) {
                System.out.println("Server disconnected");
                new Thread(() -> {
                    clientController.tryToReconnect();
                }).start();

            }
        }
        ).start();
    }

        public void startHeartbeat() throws RemoteException{
                while (true) {
                        server.ping();
                        try {
                        Thread.sleep(1000);  // Aspetta 1 secondo prima del prossimo ping
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }


    /**
     * This method is used to send the message to the server to add a player
     * @param nickname
     */
    @Override
    public void addPlayer(String nickname) {
        AddPlayerMessage message = new AddPlayerMessage(nickname);
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to send the message to the server to set the token of a player
     * @param nickname
     * @param token
     */
    @Override
    public void setToken(String nickname, TokenEnum token) {
        SetTokenMessage message = new SetTokenMessage(nickname,token);
        try {
            queue.put(message);
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to send the message to the server to set the starting card played back
     * @param playedBack
     * @param nickname
     * @param id
     */
    @Override
    public void setStartingCardPlayedBack(boolean playedBack, String nickname, int id){
        SetStartingCardPlayedBackMessage setStartingCardPlayedBackMessage = new SetStartingCardPlayedBackMessage(playedBack,nickname,id);
        try {
            queue.put(setStartingCardPlayedBackMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to send the message to the server to set the secret objective of a player
     * @param nickname
     * @param id
     */
    @Override
    public void setSecretObjective(String nickname, int id) {
        SetSecretObjectiveMessage setSecretObjectiveMessage = new SetSecretObjectiveMessage(nickname,id);
        try {
            queue.put(setSecretObjectiveMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to send the message to the server to add a card to a station
     * @param nickname
     * @param cardid
     * @param playedback
     * @param x
     * @param y
     */
    @Override
    public void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y){
        AddCardToStationMessage addCardToStationMessage = new AddCardToStationMessage(nickname,cardid,playedback,x,y);
        try {
            queue.put(addCardToStationMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to send the message to the server to add a card from the central cards to a player hand
     * @param nickname
     * @param cardid
     */
    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardid) {
        AddCardFromCentralCardsToPlayerHandMessage addCardFromCentralCardsToPlayerHandMessage = new AddCardFromCentralCardsToPlayerHandMessage(nickname,cardid);
        try {
            queue.put(addCardFromCentralCardsToPlayerHandMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to send the message to the server to add a card from the deck to a player hand
     * @param nickname
     * @param cardid
     */
    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardid) {
        AddCardFromDeckToPlayerHandMessage addCardFromDeckToPlayerHandMessage = new AddCardFromDeckToPlayerHandMessage(nickname,cardid);
        try {
            queue.put(addCardFromDeckToPlayerHandMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method is used to send the message to the server to set the player number
     * @param num
     */
    @Override
    public void setPlayerNumber(int num) {
        SetPlayerNumberMessage setPlayerNumberMessage = new SetPlayerNumberMessage(num);
        try {
            queue.put(setPlayerNumberMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to send the message to the server to send a global message
     * @param global
     */
    @Override
    public void sendGlobalMessage(GlobalChatMessage global) {
        try {
            queue.put(global);
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to send the message to the server to send a private message
     * @param privateMessage
     */
    @Override
    public void sendPrivateMessage(PrivateChatMessage privateMessage) {
        try {
            queue.put(privateMessage);
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reconnectToServer(String nickname) {
        try {
            server.reconnect(nickname, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    ///////////////////////////VIRTUAL VIEW METHODS//////////////////////////

    /**
     * This method is used to update the client controller with the message received from the server
     * @param message
     */
    @Override
    public void update(Message message) throws RemoteException {
        clientController.updateModel(message);
        }


    /**
     * This method is used to notify the client controller that the server is ready to start the setup of the nickname
     * @throws RemoteException
     */
    @Override
    public void setupOfNickname() {
        clientController.setupOfnickname();
    }

    /**
     * This method is used to notify the client controller that the server is ready to start the setup of the starting card
     * @throws RemoteException
     */
    @Override
    public void notifyStartSetupOfStartingCard() throws RemoteException {
        clientController.setupOfStartingCard();
    }

    /**
     * This method is used to notify the client controller that the server is ready to start the setup of the secret objective
     * @throws RemoteException
     */
    @Override
    public void showFourCentralCards() throws RemoteException {
        clientController.showFourCentralCards();

    }

    /**
     * This method is used to notify the client controller that the server is ready to start the setup of the number of players
     * @throws RemoteException
     */
    @Override
    public void setupOfPlayersNumber() throws RemoteException {
        clientController.setupOfPlayersNumber();

    }

    /**
     * This method is used to notify the client controller that the server is waiting for another player to set the number of players
     * @throws RemoteException
     */
    @Override
    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {
        clientController.notifyAnotherPlayerSettingNumOfPlayers();

    }

    /**
     * This method is used to notify the client controller that the server is waiting for all the players to join
     * @throws RemoteException
     */
    @Override
    public void notifyWaitingForPlayersToJoin() throws RemoteException {
        clientController.notifyWaitingForPlayersToJoin();

    }

    /**
     * This method is used to notify the client controller that all the players needet to start the game are connected
     * @throws RemoteException
     */
    @Override
    public void notifyAllPlayersConnected() throws RemoteException {
        clientController.notifyAllPlayersConnected();

    }

    /**
     * This method is used to notify the client controller that the game is already started
     * @throws RemoteException
     */
    @Override
    public void notifyGameAlreadyStarted() throws RemoteException {
        clientController.notifyGameAlreadyStarted();
    }


    /**
     * This method is used to notify the client controller that the game is started
     * @throws RemoteException
     */
    @Override
    public void StartGame() throws RemoteException{
        clientController.notifyItIsYourTurn();
    }

    /**
     * This method is used to notify the client controller that the result of the card added to the station is received
     * @param result is the result of the operation of adding a card to the station
     * @param message  is the message if the card was not added.
     */
    @Override
    public void notifyResultOfCardAddedToStation(boolean result, String message) throws RemoteException {
        clientController.handleResultOfCardAdded(result, message);

    }

    /**
     * This method is used to notify the client controller that the game is finished
     * @param scoreBoard is the score board of the game
     */
    @Override
    public void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) throws RemoteException {
        clientController.notifyGameFinished(scoreBoard);

    }

    /**
     * This method is used to notify the client controller that the hands and the common objectives are ready to be shown
     * @throws RemoteException
     */
    @Override
    public void showHandsAndCommonObjectives() throws RemoteException {
        clientController.showHandsAndCommonObjectives();
    }

    /**
     * This method is used to notify the client controller that the setup of the secret objective is ready to start
     * @throws RemoteException
     */
    @Override
    public void setupOfSecretObjective() throws RemoteException {
        clientController.setupOfSecretObjective();
    }

    /**
     * This method is used to notify the client controller that the nickname is already taken
     * @throws RemoteException
     */
    @Override
    public void notifyNicknameAlreadyTaken() throws RemoteException {
        clientController.notifyNicknameAlreadyTaken();
    }

    /**
     * This method is used to notify the client controller that the token is already taken
     * @throws RemoteException
     */
    @Override
    public void notifyTokenAlreadyTaken() throws RemoteException {
        clientController.notifyTokenAlreadyTaken();
    }

    /**
     * This method is used to notify the client controller that the setup of the token is ready to start
     * @throws RemoteException
     */
    @Override
    public void setupOfToken() throws RemoteException {
        clientController.setupOfToken();
    }
}
