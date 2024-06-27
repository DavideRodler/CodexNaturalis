package Network.Server.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.ServerToClientCommunication;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import Socket.Messages.ClientToServer.*;
import Socket.Messages.Message;
import Socket.Messages.ServerToClient.ActionMessage;
import Socket.Messages.ServerToClient.GameFinishedMessage;
import Socket.Messages.ServerToClient.ResultOfCardAddedToStationMessage;
import exception.ChangedStateException;
import exception.NotValidMoveException;

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
