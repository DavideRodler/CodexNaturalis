package Network.Client.Socket;

import Network.Client.ClientController;
import Network.Client.ClientToServerCommunication;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import Socket.Messages.Message;
import Socket.Messages.ServerToClient.GameFinishedMessage;
import Socket.Messages.ServerToClient.ResultOfCardAddedToStationMessage;
import model.enums.TokenEnum;

import java.io.*;
import java.rmi.RemoteException;

/**
 * This class is the implementation of the ClientToServerCommunication interface for the Socket connection
 */

public class SocketClient implements ClientToServerCommunication{
    final ObjectInputStream input;
    final VirtualSocketServer server;
    private ClientController clientController;

    public SocketClient(ObjectInputStream input, ObjectOutputStream output) {
        this.input = input;
        this.server = new VirtualSocketServer(output);
    }

    /**
     * This method sets the clientController
     * @param clientController the clientController to be set
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * This method starts the virtual server
     */
    public void run() {
        new Thread( () -> {
            try {
                runVirtualServer();
            } catch (IOException e) {
                if (e.getMessage() == null || e.getMessage().equals("Connection reset")  || e.getMessage().equals("Connection reset by peer")) {
                    System.out.println("Server disconnected");
                    clientController.tryToReconnect();
                }
                else {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /**
     * This method runs the virtual server
     * If the message is an update of the model it sends it to a client controller update function
     * else id performs the action requested by the message by
     * calling the corresponding function in the client controller
     * @throws IOException if an I/O error occurs
     */
    private void runVirtualServer() throws IOException {
        Message message;
        // Read message type
        while (true) {
            try {
                if (((message =(Message) input.readObject()) == null)) break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // Read message and perform action
            switch (message.getType()) {
                case "setupOfPlayersNumber" -> clientController.setupOfPlayersNumber();
                case "notifyAnotherPlayerSettingNumOfPlayers" -> clientController.notifyAnotherPlayerSettingNumOfPlayers();
                case "notifyWaitingForPlayersToJoin" -> clientController.notifyWaitingForPlayersToJoin();
                case "notifyAllPlayersConnected" -> clientController.notifyAllPlayersConnected();
                case "notifyGameAlreadyStarted" -> clientController.notifyGameAlreadyStarted();

                case "setupOfNicknameAndToken" -> clientController.setupOfnickname();
                case "notifyNicknameAlreadyTaken" -> clientController.notifyNicknameAlreadyTaken();

                case "setupOfToken" -> clientController.setupOfToken();
                case "notifyTokenAlreadyTaken" -> clientController.notifyTokenAlreadyTaken();

                case "showFourCentralCards" -> clientController.showFourCentralCards();
                case "notifyStartSetupOfStartingCard" -> clientController.setupOfStartingCard();

                case "showHandsAndCommonObjectives" -> clientController.showHandsAndCommonObjectives();
                case "setupOfSecretObjective" -> clientController.setupOfSecretObjective();

                case "ResultOfCardAddedToStation" -> {
                    ResultOfCardAddedToStationMessage resultOfCardAddedToStationMessage = (ResultOfCardAddedToStationMessage) message;
                    clientController.handleResultOfCardAdded(resultOfCardAddedToStationMessage.isAdded(), resultOfCardAddedToStationMessage.getMessage());
                }
                case "GameFinished" -> {
                    GameFinishedMessage gameFinishedMessage = (GameFinishedMessage) message;
                    clientController.notifyGameFinished(gameFinishedMessage.getScoreBoard());
                }

                case "startGame" -> new Thread(() -> {
                        clientController.startGameLoop();
                }).start();

                default -> clientController.updateModel(message);
            }
        }
    }


    /**
     * This method sets the token of a player
     * @param nickname the nickname of the player
     * @param token the token to be set
     */
    @Override
    public void setToken(String nickname, TokenEnum token) {
        try {
            server.setToken(nickname, token);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method connects the client to the server
     * @throws Exception if an error occurs
     */
    @Override
    public void connectToServer() {
        try {
            server.connectClient(null);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * This method adds a player to the game
     * @param nickname the nickname of the player to be added
     */
    @Override
    public void addPlayer(String nickname) {
        try {
            server.addPlayer(nickname, null);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method sets the starting card played back
     * @param playedBack the boolean value of the played back
     * @param nickname the nickname of the player
     * @param id the id of the card
     */
    @Override
    public void setStartingCardPlayedBack(boolean playedBack, String nickname, int id) {
        try {
            server.setStartingCardPlayedBack(playedBack, nickname, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * This method sets the secret objective of a player
     * @param nickname the nickname of the player
     * @param id the id of the secret objective
     */
    @Override
    public void setSecretObjective(String nickname, int id) {
        try {
            server.setSecretObjective(nickname, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method adds a card to a station
     * @param nickname the nickname of the player
     * @param cardid the id of the card
     * @param playedback the boolean value of the played back
     * @param x the x coordinate of the card
     * @param y the y coordinate of the card
     */
    @Override
    public void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) {
        try {
            server.addCardToStation(nickname, cardid, playedback, x, y);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method adds a card from the central cards to the player hand
     * @param nickname the nickname of the player
     * @param cardid the id of the card
     */
    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromCentralCardsToPlayerHand(nickname, cardid);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method adds a card from the deck to the player hand
     * @param nickname the nickname of the player
     * @param cardid the id of the card
     */
    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromDeckToPlayerHand(nickname, cardid);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method sets the player number
     * @param num the number of the player
     */
    @Override
    public void setPlayerNumber(int num) {
        try {
            server.setPlayerNumber(num);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method sends a global message
     * @param global the global message to be sent
     */
    @Override
    public void sendGlobalMessage(GlobalChatMessage global) {
        server.takeGlobalMessage(global);
    }

    /**
     * This method sends a private message
     * @param privateMessage the private message to be sent
     */
    @Override
    public void sendPrivateMessage(PrivateChatMessage privateMessage) {
        server.takePrivateMessage(privateMessage);
    }

    @Override
    public void reconnectToServer(String nickname) {
        try {
            server.reconnect(nickname, null);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
