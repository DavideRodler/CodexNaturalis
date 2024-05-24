package Network.Server;

import Network.Client.RMI.VirtualView;
import exception.ChangedStateException;
import exception.NotValidMoveException;
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

    ArrayList<CardResource> getMyHand(String nickname) throws RemoteException;

    void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException;

    ArrayList<CardObjective> getSelectableObjectives(String nickname) throws RemoteException;

    void addCardToStation(String nickname,int cardId, boolean front, int x, int y) throws RemoteException;
}
