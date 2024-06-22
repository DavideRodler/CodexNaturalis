package Network.Client.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.VirtualServer;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import Socket.Messages.ClientToServer.*;
import model.enums.GameState;
import model.enums.TokenEnum;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

public class VirtualSocketServer implements VirtualServer {
    private ObjectOutputStream output;

    public VirtualSocketServer(ObjectOutputStream output) {
        this.output = output;
    }

    @Override
    public void connectClient(VirtualView client) throws RemoteException {
        ConnectToServerMessage message = new ConnectToServerMessage();
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException {
        SetPlayerNumberMessage message = new SetPlayerNumberMessage(playerNumber);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPlayer(String nickname, VirtualView Client) throws RemoteException {
        AddPlayerMessage message = new AddPlayerMessage(nickname);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setToken(String nickname, TokenEnum token) throws RemoteException {
        SetTokenMessage message = new SetTokenMessage(nickname, token);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int ID) throws RemoteException{
        SetStartingCardPlayedBackMessage message = new SetStartingCardPlayedBackMessage(playedback, nickname, ID);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException {
        SetSecretObjectiveMessage message = new SetSecretObjectiveMessage(nickname, id);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardToStation(String nickname, int cardId, boolean playedBack, int x, int y) throws RemoteException{
        AddCardToStationMessage message = new AddCardToStationMessage(nickname, cardId, playedBack, x, y);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardId) throws RemoteException {
        AddCardFromDeckToPlayerHandMessage message = new AddCardFromDeckToPlayerHandMessage(nickname, cardId);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int id) throws RemoteException {
        AddCardFromCentralCardsToPlayerHandMessage message = new AddCardFromCentralCardsToPlayerHandMessage(nickname, id);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startTurn() throws RemoteException {
        StartTurnMessage message = new StartTurnMessage();
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void takeGlobalMessage(GlobalChatMessage globalChatMessage) {
        try {
            output.writeObject(globalChatMessage);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void takePrivateMessage(PrivateChatMessage privateChatMessage) {
        try {
            output.writeObject(privateChatMessage);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
