package Network.Client.Socket;

import Network.Client.ClientController;
import Network.Client.ClientToServerCommunication;
import Socket.Messages.Message;
import Socket.Messages.ServerToClient.ResultOfCardAddedToStationMessage;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SocketClient implements ClientToServerCommunication{
    final ObjectInputStream input;
    final VirtualSocketServer server;
    private ClientController clientController;

    public SocketClient(ObjectInputStream input, ObjectOutputStream output) {
        this.input = input;
        this.server = new VirtualSocketServer(output);
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
    public void run() throws RemoteException{
        new Thread( () -> {
            try {
                runVirtualServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

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

                case "notifyItIsYourTurn" -> clientController.notifyItIsYourTurn();

                default -> clientController.updateModel(message);
            }
        }
    }

    @Override
    public void setToken(String nickname, TokenEnum token) {
        try {
            server.setToken(nickname, token);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void connectToServer() throws Exception {
        server.connectClient(null);
    }



    @Override
    public void addPlayer(String nickname) {
        try {
            server.addPlayer(nickname, null);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setStartingCardPlayedBack(boolean playedBack, String nickname, int id) {
        try {
            server.setStartingCardPlayedBack(playedBack, nickname, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void setSecretObjective(String nickname, int id) {
        try {
            server.setSecretObjective(nickname, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) {
        try {
            server.addCardToStation(nickname, cardid, playedback, x, y);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromCentralCardsToPlayerHand(nickname, cardid);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromDeckToPlayerHand(nickname, cardid);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finishTurn() {
        try {
            server.startTurn();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void setPlayerNumber(int num) {
        try {
            server.setPlayerNumber(num);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
