package Network.Server.RMI;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.VirtualServer;
import Messages.Chat.GlobalChatMessage;
import Messages.Chat.PrivateChatMessage;
import model.enums.TokenEnum;
//import socket.Messages.ModelUpdates.PlayersInfoMessage;

import java.rmi.RemoteException;

public class RmiServer implements VirtualServer {
    private Server server;

    public RmiServer(Server server) {
        this.server = server;
    }


    /**
     * This method is used to ping the server
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void ping() throws RemoteException {
    }

    /**
     * This method is used to connect the client to the server
     * @param client the client to connect
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void connectClient(VirtualView client) throws RemoteException {
        server.connectClient(client);
    }


    /**
     * This method is used to set the player number
     * @param playerNumber the player number
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException {
        server.setPlayerNumber(playerNumber);
    }

    /**
     * This method is used to add a player
     * @param nickname the nickname of the player
     * @param Myclient the client to add
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void addPlayer(String nickname, VirtualView Myclient) throws RemoteException {
        server.addPlayer(nickname, Myclient);
    }

    /**
     * This method is used to set the token of a player
     * @param nickname the nickname of the player
     * @param token the token to set
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void setToken(String nickname, TokenEnum token) throws RemoteException{
        server.setToken(nickname, token);
    }


    /**
     * This method is used to set the starting card played back
     * @param playedback the boolean value of the card
     * @param nickname the nickname of the player
     * @param Id the id of the card
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int Id) throws RemoteException {
        server.setStartingCardPlayedBack(playedback, nickname, Id);
    }

    /**
     * This method is used to set the secret objective of a player
     * @param nickname the nickname of the player
     * @param id the id of the secret objective
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException {
        server.setSecretObjective(nickname, id);
    }


    /**
     * This method is used to add a card to a station
     * @param nickname the nickname of the player
     * @param id the id of the card
     * @param playedBack the boolean value of the card
     * @param x the x coordinate of the card
     * @param y the y coordinate of the card
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void addCardToStation(String nickname, int id, boolean playedBack, int x, int y) throws RemoteException {
        server.addCardToStation(nickname, id, playedBack, x, y);

    }


    /**
     * This method is used to add a card from the deck to a player hand
     * @param nickname the nickname of the player
     * @param deck the deck to take the card from
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int deck) throws RemoteException {
        server.addCardFromDeckToPlayerHand(nickname, deck);
    }

    /**
     * This method is used to add a card from the central cards to a player hand
     * @param nickname the nickname of the player
     * @param cardId the id of the card
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardId) throws RemoteException {
        server.addCardFromCentralCardsToPlayerHand(nickname, cardId);
    }


    /**
     * This method is used to send a global message
     * @param globalChatMessage the global message to send
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void takeGlobalMessage(GlobalChatMessage globalChatMessage) throws RemoteException{
        server.takeGlobalMessage(globalChatMessage);

    }

    /**
     * This method is used to send a private message
     * @param privateChatMessage the private message to send
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void takePrivateMessage(PrivateChatMessage privateChatMessage) throws RemoteException{
        server.takePrivateMessage(privateChatMessage);

    }

    /**
     * This method is used to reconnect a player
     * @param nickname the nickname of the player
     * @param client the client to reconnect
     * @throws RemoteException if a remote error occurs
     */
    @Override
    public void reconnect(String nickname, VirtualView client) throws RemoteException {
        server.reconnect(client, nickname);
    }
}
