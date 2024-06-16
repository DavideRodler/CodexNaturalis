package View;

import exception.NonePlayerFoundException;
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
    void print4CentralCardsAndDecks();

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

    void printMenu();

    String askTypeOfChat(int numberOfOtherPlayers, String[] NamesOfOtherPlayers);

    // printa solo la staion aggiornata più risorse e più punti
   void printStationAfterCardHasBeenAdded();

   void printOtherPlayersStation(String nickname) throws NonePlayerFoundException;

    void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map);

    TokenEnum askToken(ArrayList<TokenEnum> availableTokens);

    void printPlayerToken();

    void waitingForOtherPlayers();

    void showErrorMessage(String message);

    void printAvailableTokens(ArrayList<TokenEnum> availableTokens);


    Integer askMenuAction();

    String askWichStationToPrint();

    void printSpace();

    void printMenu2and3();

    void printMenuNotMyTurn(String currentPlayer);

    void printMenu2and3NotMyTurn(String currentPlayer);

    String askMessage();

    void printChat();

    void printChatInfo();

    String printPrivateChatInfo();

    void printPrivateChat(String nickname, String nickname1);
}
