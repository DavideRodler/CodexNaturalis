package Network.Client;

import Network.Client.RMI.VirtualView;
import exception.InvalidPlacingCondition;
import model.enums.TokenEnum;

import java.util.ArrayList;

public interface ClientCommunication extends VirtualView {
    void connectToServer() throws Exception;

    ArrayList<TokenEnum> getAvailableTokens();

    boolean checkNicknameAvailability(String nickname);

    boolean checkTokenAvailability(TokenEnum token);

    void addPlayer(String nickname, TokenEnum token);

    void setStartingCardPlayedBack(boolean playedBack, String nickname, int id);

    void setSecretObjective(String nickname, int id);

    void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) throws InvalidPlacingCondition;

    void addCardFromCentralCardsToPlayerHand(String nickname, int cardid);

    void addCardFromDeckToPlayerHand(String nickname, int cardid);

    void startTurn();

    void setPlayerNumber(int num);
}
