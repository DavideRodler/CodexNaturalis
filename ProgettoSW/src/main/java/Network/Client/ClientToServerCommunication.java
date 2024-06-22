package Network.Client;

import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import model.enums.TokenEnum;

public interface ClientToServerCommunication {
    void setToken(String nickname, TokenEnum token);

    void connectToServer() throws Exception;

    void addPlayer(String nickname);

    void setStartingCardPlayedBack(boolean playedBack, String nickname, int id);

    void setSecretObjective(String nickname, int id);

    void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y);

    void addCardFromCentralCardsToPlayerHand(String nickname, int cardid);

    void addCardFromDeckToPlayerHand(String nickname, int cardid);

    void finishTurn();

    void setPlayerNumber(int num);

    void sendGlobalMessage(GlobalChatMessage global);

    void sendPrivateMessage(PrivateChatMessage privateMessage);
}
