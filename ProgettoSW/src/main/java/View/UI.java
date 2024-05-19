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

    //void init();
    String askNickname();

    int askPlayerNumber();


    void printErrorMessage(Exception e);
    boolean askStartingCardPlayedBack();

    void showObjectiveCards(ArrayList<CardObjective> cardObjectives);

    Integer askObjectiveCard();
    
    void showUpdatedBoard(PlayingBoard playingBoard);
    
    void showUpdatedStation(Map<ArrayList<Integer>, CardPlaying> playingStation);

    void showUpdatedHand(ArrayList<CardResource> hand);

    void showMyUpdatedBoard(Map<ArrayList<Integer>, CardPlaying> playingStation, String name);

    Integer[] askCoordinatesOfCards();

    Integer askDrawingCard();

    TokenEnum askToken(ArrayList<TokenEnum> availableTokens);

    void waitingForOtherPlayers();

    void showErrorMessage(String message);

    void printWaitingForPlayerToSetPlayerNumber();

    void printAvailableTokens(ArrayList<TokenEnum> availableTokens);
}
