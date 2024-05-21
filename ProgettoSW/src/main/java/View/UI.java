package View;

import model.enums.TokenEnum;

import java.util.ArrayList;

public interface UI {
    void showStartingCard();

    void showGameTitle();

    String askNickname();

    int askPlayerNumber();


    boolean askStartingCardPlayedBack();

    void printCommonObjective();

    void printSecretObjective();

    void printSelectableObjectives();

    Integer askObjectiveCard();

    //this method only shows the 4 Central cards at the beginning of the game
    void print4CentralCards();
    
    void showUpdatedStation();

    //this mehtod only print the hand and is used at the beginning of the game
    void printMyHand();

    //void prin


    Integer[] askCoordinatesOfCards();

    Integer askWhichCardToDraw();

    //ToDo

    // this method print the 4 central cards, the hand , the playingstation, the common and private objectives
    // and the other players name and points;
   void printStartOfMyTurn();

   // printa solo la staion aggiornata più risorse e più punti
   void printAfterCardHasBeenAdded();

   void printOtherPlayersStation(String nickname);

    TokenEnum askToken(ArrayList<TokenEnum> availableTokens);

    void waitingForOtherPlayers();

    void showErrorMessage(String message);

    void printAvailableTokens(ArrayList<TokenEnum> availableTokens);


}
