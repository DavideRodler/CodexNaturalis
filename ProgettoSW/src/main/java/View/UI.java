package View;

import model.PlayingStation;
import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface UI {
    void showStartingCard();

    void showGameTitle();

    String askNickname();

    int askPlayerNumber();


    boolean askStartingCardPlayedBack();

    void printCommonObjectives();

    void printSecretObjective();

    void printSelectableObjectives();

    int askObjectiveCard();

    //this method only shows the 4 Central cards at the beginning of the game
    void print4CentralCards();
    
    void printPlayerStation(PlayingStation playingStation);

    //this mehtod only print the hand and is used at the beginning of the game
    void printSetupPlayerHand();

    void printPlayerHand();

    Integer[] askCoordinatesOfCards();

    Integer askWhichCardToDraw();

    //ToDo

    // this method print the 4 central cards, the hand , the playingstation, the common and private objectives
    // and the other players name and points;
   void printStartOfPlayerTurn();

   // printa solo la staion aggiornata più risorse e più punti
   void printStationAfterCardHasBeenAdded();

   void printOtherPlayersStation(String nickname);

    void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map);

    TokenEnum askToken(ArrayList<TokenEnum> availableTokens);

    void printPlayerToken();

    void waitingForOtherPlayers();

    void showErrorMessage(String message);

    void printAvailableTokens(ArrayList<TokenEnum> availableTokens);


    void printCardAddedSuccessfully();

    void printCardNotAdded(String message);
}
