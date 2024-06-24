package View;

import model.PlayingStation;
import model.client.ClientBoard;
import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface UI {


    void askNickname();

    void askToken(ArrayList<TokenEnum> availableTokens);

    void askStartingCardPlayedBack();

    void askObjectiveCard();

    void showStartingCard();

    void printIsNotMyTurnMenu();

    void printIsMyTurnMenu();

    void askWhichCardToDraw();

    void printCommonObjectives();

    void printSecretObjective();

    void printSelectableObjectives();

    void print4CentralCards();
    
    void printPlayerStation(PlayingStation playingStation);

    void printSetupPlayerHand();

    void printPlayerHand();

    Integer[] askCoordinatesOfCards();

   void printStationAfterCardHasBeenAdded();

   void printOtherPlayersStation(String nickname);

    void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map);

    void printCardAddedSuccessfully();

    void printCardNotAdded(String message);


    void printMenu();

    void printNicknameAlreadyTaken();

    void printTokenAlreadyTaken();
}
