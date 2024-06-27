package Network.Client.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.VirtualServer;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import Socket.Messages.ClientToServer.*;
import model.enums.TokenEnum;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

/**
 * This class is the implementation of the VirtualServer interface for the Socket connection
 * It is used to represet a Server.
 */
public class VirtualSocketServer implements VirtualServer {
    private ObjectOutputStream output;

    /**
     * This is the constructor of the class
     * @param output the output stream
     */
    public VirtualSocketServer(ObjectOutputStream output) {
        this.output = output;
    }

    @Override
    public void ping() throws RemoteException {

    }

    /**
     * This method connect the client to the server
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method sets the player number
     * @param playerNumber the player number
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method adds a player to the server
     * @param nickname the nickname of the player
     * @param Client the client
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method sets the token of a player
     * @param nickname the nickname of the player
     * @param token the token of the player
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method sets the starting card played back
     * @param playedback the boolean value of the played back
     * @param nickname the nickname of the player
     * @param ID the id of the card
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method sets the secret objective of a player
     * @param nickname the nickname of the player
     * @param id the id of the objective
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method adds a card to a station
     * @param nickname the nickname of the player
     * @param cardId the id of the card
     * @param playedBack the boolean value of the played back
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method adds a card from the deck to the player hand
     * @param nickname the nickname of the player
     * @param cardId the id of the card
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method adds a card from the central cards to the player hand
     * @param nickname the nickname of the player
     * @param id the id of the card
     * @throws RemoteException if a remote error occurs
     */
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

    /**
     * This method starts the turn
     * @throws RemoteException if a remote error occurs
     */

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

    @Override
    public void reconnect(String nickname, VirtualView client) throws RemoteException {
        ReconnectMessage message = new ReconnectMessage(nickname);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
