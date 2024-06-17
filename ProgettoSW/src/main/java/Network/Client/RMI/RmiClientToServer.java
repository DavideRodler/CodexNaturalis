package Network.Client.RMI;

import Network.Client.ClientToServerCommunication;
import Network.Client.ClientController;
import Network.Server.VirtualServer;

import Socket.Messages.Message;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RmiClientToServer extends UnicastRemoteObject implements ClientToServerCommunication, VirtualView {

    private ClientController clientController;
    private final VirtualServer server;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);

    public RmiClientToServer(VirtualServer server) throws RemoteException{
        this.server = server;
        this.clientController = new ClientController(this);
    }

    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }

    public void ClientToServerCall() throws RemoteException {
        while (true) {
            String action = null;
            try {
                action = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (action) {
                case "connectToServer":
                    this.server.connectClient(this);
                    break;
                case "getAvailableTokens":
                    getAvailableTokens();
                    break;
                case "checkNicknameAvailability":
                    checkNicknameAvailability("nickname");
                    break;
                case "checkTokenAvailability":
                    checkTokenAvailability(TokenEnum.BLACK);
                    break;
                case "addPlayer":
                    addPlayer("nickname", TokenEnum.BLACK);
                    break;
                case "setStartingCardPlayedBack":
                    setStartingCardPlayedBack(true, "nickname", 1);
                    break;
                case "setSecretObjective":
                    setSecretObjective("nickname", 1);
                    break;
                case "addCardToStation":
                    try {
                        addCardToStation("nickname", 1, true, 1, 1);
                    } catch (InvalidPlacingCondition e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "addCardFromCentralCardsToPlayerHand":
                    addCardFromCentralCardsToPlayerHand("nickname", 1);
                    break;
                case "addCardFromDeckToPlayerHand":
                    addCardFromDeckToPlayerHand("nickname", 1);
                    break;
                case "startTurn":
                    startTurn();
                    break;
                case "setPlayerNumber":
                    setPlayerNumber(1);
                    break;
            }
        }
    }
    //client To Server Communication
    @Override
    public void connectToServer() throws RemoteException{
        this.server.connectClient(this);
    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() {
        try {
            return server.getAvailableTokens();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) {
        try {
            return server.checkNicknameAvailability(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) {
        try {
            return server.checkTokenAvailability(token);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addPlayer(String nickname, TokenEnum token)
    {
        try {
            server.addPlayer(nickname, token, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setStartingCardPlayedBack(boolean playedBack, String nickname, int id){
        try {
            server.setStartingCardPlayedBack(playedBack,nickname,id);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
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
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) throws InvalidPlacingCondition {
        try {
            server.addCardToStation(nickname,cardid,playedback,x,y);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromCentralCardsToPlayerHand(nickname,cardid);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotMyTurnException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardid) {
        try {
            server.addCardFromDeckToPlayerHand(nickname,cardid);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (InvalidPlacingCondition e) {
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
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    //server To Client Communication
    @Override
    public void update(Message message) throws RemoteException {
        clientController.updateModel(message);
        }

    @Override
    public void setupOfnicknameAndToken() {
        clientController.setupOfnicknameAndToken();
    }

    @Override
    public void notifyStartSetupOfStartingCard() throws RemoteException {
        clientController.setupOfStartingCard();
    }

    @Override
    public void showFourCentralCards() throws RemoteException {
        clientController.showFourCentralCards();

    }

    @Override
    public void setupOfPlayersNumber() throws RemoteException {
        clientController.setupOfPlayersNumber();

    }

    @Override
    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {
        clientController.notifyAnotherPlayerSettingNumOfPlayers();

    }

    @Override
    public void notifyWaitingForPlayersToJoin() throws RemoteException {
        clientController.notifyWaitingForPlayersToJoin();

    }

    @Override
    public void notifyAllPlayersConnected() throws RemoteException {
        clientController.notifyAllPlayersConnected();

    }

    @Override
    public void notifyGameAlreadyStarted() throws RemoteException {
        clientController.notifyGameAlreadyStarted();
    }

    @Override
    public void notifyItIsYourTurn() throws RemoteException{
        clientController.notifyItIsYourTurn();

    }

    @Override
    public void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) throws RemoteException {
        clientController.notifyGameFinished(scoreBoard);

    }

    @Override
    public void showHandsAndCommonObjectives() throws RemoteException {
        clientController.showHandsAndCommonObjectives();
    }

    @Override
    public void setupOfSecretObjective() throws RemoteException {
        clientController.setupOfSecretObjective();
    }

    @Override
    public void notifyNicknameAlreadyTaken() throws RemoteException {
        clientController.notifyNicknameAlreadyTaken();
    }

    @Override
    public void notifyTokenAlreadyTaken() throws RemoteException {
        clientController.notifyTokenAlreadyTaken();
    }
}
