package View;

import model.PlayingBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.Map;

public interface UI {
    void showStartingCard(CardStarting cardStarting);

    void showGameTitle();

    String askNickname();

    int askPlayerNumber();


    void printErrorMessage(Exception e);
    boolean askStartingCardPlayedBack();

    void showObjectiveCards();

    Integer askObjectiveCard();
    
    void showUpdatedBoard();
    
    void showUpdatedStation();

    void showUpdatedHand();

    void showMyUpdatedBoard(Map<ArrayList<Integer>, CardPlaying> playingStation, String name);

    Integer[] askCoordinatesOfCards();

    Integer askDrawingCard();

    TokenEnum askToken(ArrayList<TokenEnum> availableTokens);

    void waitingForOtherPlayers();

    void showErrorMessage(String message);

    void printWaitingForPlayerToSetPlayerNumber();

    void printAvailableTokens(ArrayList<TokenEnum> availableTokens);
}
