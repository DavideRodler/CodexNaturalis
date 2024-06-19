package Network.Client;

import exception.InvalidPlacingCondition;
import model.enums.TokenEnum;

import java.util.ArrayList;

public interface ClientToServerCommunication {
    void setToken(String nickname, TokenEnum token);

    void connectToServer() throws Exception;

    void addPlayer(String nickname);

    void setStartingCardPlayedBack(boolean playedBack, String nickname, int id);

    void setSecretObjective(String nickname, int id);

    void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) throws InvalidPlacingCondition;

    void addCardFromCentralCardsToPlayerHand(String nickname, int cardid);

    void addCardFromDeckToPlayerHand(String nickname, int cardid);

    void startTurn();

    void setPlayerNumber(int num);
}
