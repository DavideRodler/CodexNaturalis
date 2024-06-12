package Network.Server;

import Network.Client.RMI.VirtualView;
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

    void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException;

    ArrayList<TokenEnum> getAvailableTokens() throws RemoteException;

    boolean checkNicknameAvailability(String nickname) throws RemoteException;

    boolean checkTokenAvailability(TokenEnum token)throws RemoteException;

    void addPlayer(String nickname, TokenEnum token, VirtualView Myclient) throws RemoteException, ChangedStateException, NotValidMoveException;

    void setStartingCardPlayedBack(boolean playedback, String nickname, int ID) throws ChangedStateException, NotValidMoveException, RemoteException;

    void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException;

    void addCardToStation(String nickname,int cardId, boolean playedBack, int x, int y) throws RemoteException, InvalidPlacingCondition;

    void addCardFromDeckToPlayerHand(String nickname, int cardId) throws RemoteException, InvalidPlacingCondition;

    void addCardFromCentralCardsToPlayerHand(String nickname, int id) throws RemoteException, NotMyTurnException;

    void startTurn() throws RemoteException, NotMyTurnException;

}
