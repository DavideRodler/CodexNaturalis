package View;

import model.cards.CardPlaying;
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

    //this method only shows the 4 Central cards at the beginning of the game
    void print4CentralCards();
    
    void showUpdatedStation();

    //this mehtod only print the hand and is used at the beginning of the game
    void printMyHand();

    void showMyUpdatedBoard(Map<ArrayList<Integer>, CardPlaying> playingStation, String name);

    Integer[] askCoordinatesOfCards();

    Integer askDrawingCard();

    //ToDo

    // this method print the 4 central cards, the hand , the playingstation, the common and private objectives
    // and the other players name and points;
   void printMyboard();

    TokenEnum askToken(ArrayList<TokenEnum> availableTokens);

    void waitingForOtherPlayers();

    void showErrorMessage(String message);

    void printWaitingForPlayerToSetPlayerNumber();

    void printAvailableTokens(ArrayList<TokenEnum> availableTokens);
}
