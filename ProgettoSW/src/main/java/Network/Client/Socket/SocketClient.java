package Network.Client.Socket;

import Network.Client.ClientController;
import Network.Client.ClientToServerCommunication;
import Network.Client.RMI.VirtualView;
import Network.Server.VirtualServer;
import Socket.Messages.Message;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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

                case "setupOfNicknameAndToken" -> clientController.setupOfnicknameAndToken();
                case "showFourCentralCards" -> clientController.showFourCentralCards();
                case "setupOfSecretObjective" -> clientController.setupOfSecretObjective();

                case "notifyStartSetupOfStartingCard" -> clientController.setupOfStartingCard();
                case "notifyIsYourTurn" -> clientController.notifyItIsYourTurn();

                default -> clientController.updateModel(message);
            }
        }
    }

    @Override
    public void connectToServer() throws Exception {

    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() {
        return null;
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) {
        return false;
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) {
        return false;
    }



    @Override
    public void addPlayer(String nickname, TokenEnum token) {
        try {
            server.addPlayer(nickname, token, null);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setStartingCardPlayedBack(boolean playedBack, String nickname, int id) {
        try {
            server.setStartingCardPlayedBack(playedBack, nickname, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void setSecretObjective(String nickname, int id) {
        try {
            server.setSecretObjective(nickname, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) throws InvalidPlacingCondition {
        try {
            server.addCardToStation(nickname, cardid, playedback, x, y);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (InvalidPlacingCondition e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromCentralCardsToPlayerHand(nickname, cardid);
        } catch (RemoteException | NotMyTurnException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromDeckToPlayerHand(nickname, cardid);
        } catch (RemoteException | InvalidPlacingCondition e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startTurn() {
        try {
            server.startTurn();
        } catch (RemoteException | NotMyTurnException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void setPlayerNumber(int num) {
        try {
            server.setPlayerNumber(num);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }

    }
}
