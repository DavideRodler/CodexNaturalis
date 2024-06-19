package Network.Client.RMI;

import Network.Client.ClientToServerCommunication;
import Network.Client.ClientController;
import Network.Server.VirtualServer;

import Socket.Messages.ClientToServer.*;
import Socket.Messages.Message;
import exception.InvalidPlacingCondition;
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
    private BlockingQueue<Message> queue = new ArrayBlockingQueue<>(100);

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

    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }

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
                case "AddCardFromCentralCardsToPlayerHands" -> {
                    AddCardFromCentralCardsToPlayerHandsMessage addCardFromCentralCardsToPlayerHandsMessage = (AddCardFromCentralCardsToPlayerHandsMessage) actionMessage;
                    server.addCardFromCentralCardsToPlayerHand(addCardFromCentralCardsToPlayerHandsMessage.getNickname(), addCardFromCentralCardsToPlayerHandsMessage.getCardId());
                }
                case "AddCardFromDeckToPlayerHand" -> {
                    AddCardFromDeckToPlayerHandMessage addCardFromDeckToPlayerHandMessage = (AddCardFromDeckToPlayerHandMessage) actionMessage;
                    server.addCardFromDeckToPlayerHand(addCardFromDeckToPlayerHandMessage.getNickname(), addCardFromDeckToPlayerHandMessage.getCardId());
                }
                case "StartTurn" -> {
                    server.startTurn();
                }
                default -> throw new RuntimeException("Invalid message type");
            }
        }
    }

    //client To Server Communication
    @Override
    public void connectToServer() throws RemoteException{
        ConnectToServerMessage message = new ConnectToServerMessage();
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void addPlayer(String nickname) {
        AddPlayerMessage message = new AddPlayerMessage(nickname);
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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

    @Override
    public void setStartingCardPlayedBack(boolean playedBack, String nickname, int id){
        SetStartingCardPlayedBackMessage setStartingCardPlayedBackMessage = new SetStartingCardPlayedBackMessage(playedBack,nickname,id);
        try {
            queue.put(setStartingCardPlayedBackMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSecretObjective(String nickname, int id) {
        SetSecretObjectiveMessage setSecretObjectiveMessage = new SetSecretObjectiveMessage(nickname,id);
        try {
            queue.put(setSecretObjectiveMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) throws InvalidPlacingCondition {
        AddCardToStationMessage addCardToStationMessage = new AddCardToStationMessage(nickname,cardid,playedback,x,y);
        try {
            queue.put(addCardToStationMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardid) {
        AddCardFromCentralCardsToPlayerHandsMessage addCardFromCentralCardsToPlayerHandsMessage = new AddCardFromCentralCardsToPlayerHandsMessage(nickname,cardid);
        try {
            queue.put(addCardFromCentralCardsToPlayerHandsMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardid) {
        AddCardFromDeckToPlayerHandMessage addCardFromDeckToPlayerHandMessage = new AddCardFromDeckToPlayerHandMessage(nickname,cardid);
        try {
            queue.put(addCardFromDeckToPlayerHandMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startTurn() {
        StartTurnMessage startTurnMessage = new StartTurnMessage();
        try {
            queue.put(startTurnMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setPlayerNumber(int num) {
        SetPlayerNumberMessage setPlayerNumberMessage = new SetPlayerNumberMessage(num);
        try {
            queue.put(setPlayerNumberMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //server To Client Communication
    @Override
    public void update(Message message) throws RemoteException {
        clientController.updateModel(message);
        }

    @Override
    public void setupOfNickname() {
        clientController.setupOfnickname();
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

    @Override
    public void setupOfToken() throws RemoteException {
        clientController.setupOfToken();
    }
}
