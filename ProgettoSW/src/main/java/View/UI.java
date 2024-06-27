package View;

import model.PlayingStation;
import model.cards.CardResource;
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

    void printCommonObjectives();

    void print4CentralCards();

    void printPlayerHand();

    void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map);

    void printCardAddedSuccessfully();

    void printCardNotAdded(String message);

    void printNicknameAlreadyTaken();

    void printTokenAlreadyTaken();

    void startGame();

    void updateCurrentPlayer();

    void updateGlobalChat();

    void updatePrivateChat();

    void updateHand();
}
