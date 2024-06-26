package Network.Server;

import Network.Client.RMI.VirtualView;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.enums.TokenEnum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualServer extends Remote {
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
}
