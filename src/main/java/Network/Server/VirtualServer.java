package Network.Server;

import Network.Client.RMI.VirtualView;
import Messages.Chat.GlobalChatMessage;
import Messages.Chat.PrivateChatMessage;
import model.enums.TokenEnum;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void ping() throws RemoteException;

    void connectClient(VirtualView client) throws RemoteException;

    void setPlayerNumber(int playerNumber) throws RemoteException;

    void addPlayer(String nickname, VirtualView Myclient) throws RemoteException;

    void setToken(String nickname, TokenEnum token) throws RemoteException;

    void setStartingCardPlayedBack(boolean playedback, String nickname, int ID) throws RemoteException;

    void setSecretObjective(String nickname, Integer id) throws RemoteException;

    void addCardToStation(String nickname,int cardId, boolean playedBack, int x, int y) throws RemoteException;

    void addCardFromDeckToPlayerHand(String nickname, int cardId) throws RemoteException;

    void addCardFromCentralCardsToPlayerHand(String nickname, int id) throws RemoteException;

    void takeGlobalMessage(GlobalChatMessage globalChatMessage) throws RemoteException;

    void takePrivateMessage(PrivateChatMessage privateChatMessage) throws RemoteException;

    void reconnect(String nickname, VirtualView client) throws RemoteException;
}
