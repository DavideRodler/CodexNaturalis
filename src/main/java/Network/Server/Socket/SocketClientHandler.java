package Network.Server.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Messages.Chat.GlobalChatMessage;
import Messages.Chat.PrivateChatMessage;
import Messages.ClientToServer.*;
import Messages.Message;
import Messages.ServerToClient.ActionMessage;
import Messages.ServerToClient.GameFinishedMessage;
import Messages.ServerToClient.ResultOfCardAddedToStationMessage;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SocketClientHandler implements VirtualView {
    final ObjectInputStream input;
    final ObjectOutputStream output;
    final Server server;

    public SocketClientHandler( ObjectOutputStream output,ObjectInputStream input, Server server) {
        this.input = input;
        this.output = output;
        this.server = server;
    }

    /**
     * This method is used to run the virtual view
     * It reads the messages from the input stream and handles them
     * @throws IOException if an I/O error occurs
     */
    public void runVirtualView() throws IOException {
        Message message;
        while (true) {
            try {
                if (((message = (Message) input.readObject()) == null)) break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            switch (message.getType()) {
                case "ConnectToServer" -> {
                    server.connectClient(this);
                }
                case "SetPlayerNumber" -> {
                    SetPlayerNumberMessage setPlayerNumberMessage = (SetPlayerNumberMessage) message;
                    server.setPlayerNumber(setPlayerNumberMessage.getNumber());
                }
                case "AddPlayer" -> {
                    AddPlayerMessage addPlayerMessage = (AddPlayerMessage) message;
                    server.addPlayer(addPlayerMessage.getNickname(), this);
                }
                case "SetStartingCardPlayedBack" -> {
                    SetStartingCardPlayedBackMessage setStartingCardPlayedBackMessage = (SetStartingCardPlayedBackMessage) message;
                    server.setStartingCardPlayedBack(setStartingCardPlayedBackMessage.isPlayedBack(), setStartingCardPlayedBackMessage.getNickname(), setStartingCardPlayedBackMessage.getId());
                    }
                case "SetSecretObjective" -> {
                    SetSecretObjectiveMessage setSecretObjectiveMessage = (SetSecretObjectiveMessage) message;
                    server.setSecretObjective(setSecretObjectiveMessage.getNickname(), setSecretObjectiveMessage.getId());
                    }
                case "SetToken"->{
                    SetTokenMessage setTokenMessage = (SetTokenMessage) message;
                        server.setToken(setTokenMessage.getNickname(), setTokenMessage.getToken());
                    }
                case "AddCardToStation" -> {
                    AddCardToStationMessage addCardToStationMessage = (AddCardToStationMessage) message;
                    server.addCardToStation(addCardToStationMessage.getNickname(), addCardToStationMessage.getCardId(), addCardToStationMessage.isPlayedBack(), addCardToStationMessage.getX(), addCardToStationMessage.getY());
                }
                case "AddCardFromDeckToPlayerHand" -> {
                    AddCardFromDeckToPlayerHandMessage addCardFromDeckToPlayerHandMessage = (AddCardFromDeckToPlayerHandMessage) message;
                    server.addCardFromDeckToPlayerHand(addCardFromDeckToPlayerHandMessage.getNickname(), addCardFromDeckToPlayerHandMessage.getCardId());
                }
                case "AddCardFromCentralCardsToPlayerHand" -> {
                    AddCardFromCentralCardsToPlayerHandMessage addCardFromCentralCardsToPlayerHandMessage = (AddCardFromCentralCardsToPlayerHandMessage) message;
                    server.addCardFromCentralCardsToPlayerHand(addCardFromCentralCardsToPlayerHandMessage.getNickname(), addCardFromCentralCardsToPlayerHandMessage.getCardId());
                }
                case "GLOBAL" -> {
                    GlobalChatMessage globalChatMessage = (GlobalChatMessage) message;
                    server.takeGlobalMessage(globalChatMessage);
                }
                case "PRIVATE" -> {
                    PrivateChatMessage privateChatMessage = (PrivateChatMessage) message;
                    server.takePrivateMessage(privateChatMessage);
                }
                case "ReconnectMessage" -> {
                    ReconnectMessage reconnectMessage = (ReconnectMessage) message;
                    server.reconnect(this, reconnectMessage.getNickname());
                }

                default -> System.out.println("invalid message");
                // handle the message
            }
        }
    }




    /**
     * This method is used to set the players number
     */
    @Override
    public void setupOfPlayersNumber() throws RemoteException {
        Message message = new ActionMessage("setupOfPlayersNumber");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to notify another player that a player is setting the number of players
     */
    @Override
    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {
        Message message = new ActionMessage("notifyAnotherPlayerSettingNumOfPlayers");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to notify that the game is waiting for players to join
     */
    @Override
    public void notifyWaitingForPlayersToJoin() throws RemoteException {
        Message message = new ActionMessage("notifyWaitingForPlayersToJoin");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to notify that all players are connected
     */
    @Override
    public void notifyAllPlayersConnected() throws RemoteException {
        Message message = new ActionMessage("notifyAllPlayersConnected");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to notify that the game is already started
     */
    @Override
    public void notifyGameAlreadyStarted() throws RemoteException {
        Message message = new ActionMessage("notifyGameAlreadyStarted");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to start the  setup the nickname
     */
    @Override
    public void setupOfNickname() throws RemoteException {
        Message message = new ActionMessage("setupOfNicknameAndToken");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to notify the start of the setup of the starting card
     */
    @Override
    public void notifyStartSetupOfStartingCard() throws RemoteException {
        Message message = new ActionMessage("notifyStartSetupOfStartingCard");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to show the four central cards
     */
    @Override
    public void showFourCentralCards() throws RemoteException {
        Message message = new ActionMessage("showFourCentralCards");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to notify the player that the common objectives are shown
     * and the hands are shown
     */
    @Override
    public void showHandsAndCommonObjectives() throws RemoteException {
        Message message = new ActionMessage("showHandsAndCommonObjectives");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to setup the secret objective
     */
    @Override
    public void setupOfSecretObjective() throws RemoteException {
        Message message = new ActionMessage("setupOfSecretObjective");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to notify that the nickname is already taken
     */
    @Override
    public void notifyNicknameAlreadyTaken() throws RemoteException {
        Message message = new ActionMessage("notifyNicknameAlreadyTaken");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to notify that the token is already taken
     */
    @Override
    public void notifyTokenAlreadyTaken() throws RemoteException {
        Message message = new ActionMessage("notifyTokenAlreadyTaken");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to start the setup the token
     */
    @Override
    public void setupOfToken() throws RemoteException {
        Message message = new ActionMessage("setupOfToken");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to start the game
     */
    @Override
    public void StartGame() throws RemoteException {
        Message message = new ActionMessage("startGame");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to notify the result of the card added to the station
     * @param result the result of the card added to the station
     * @param message the message to show
     */
    @Override
    public void notifyResultOfCardAddedToStation(boolean result, String message) throws RemoteException {
        ResultOfCardAddedToStationMessage resultOfCardAddedToStationMessage = new ResultOfCardAddedToStationMessage(result, message);
        try {
            this.output.writeObject(resultOfCardAddedToStationMessage);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to notify the game is finished
     * @param scoreBoard the score board of the game
     */
    @Override
    public void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) throws RemoteException {
        GameFinishedMessage gameFinishedMessage = new GameFinishedMessage(scoreBoard);
        try {
            this.output.writeObject(gameFinishedMessage);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to send an update of the model
     * @param message the global message to send
     */
    @Override
    public void update(Message message) throws RemoteException {
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
