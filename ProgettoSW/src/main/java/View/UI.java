package View;

import model.PlayingBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardStarting;

import java.util.ArrayList;
import java.util.Map;

public interface UI {
    void showStartingCard(CardStarting cardStarting);

    void showGameTitle();

    //void init();
    String askNickname();

    Integer askPlayerNumber();

    Integer askStartingCardFront();

    void showObjectiveCards(ArrayList<CardObjective> cardObjectives);

    Integer askObjectiveCard();
    
    void showUpdatedBoard(PlayingBoard playingBoard);
    
    void showUpdatedStation(Map<ArrayList<Integer>, CardPlaying> playingStation);

    void showUpdatedHand(ArrayList<CardPlaying> hand);

    void showMyUpdatedBoard(Map<ArrayList<Integer>, CardPlaying> playingStation, String name);

    Integer[] askCoordinatesOfCards();

    Integer askDrawingCard();
}
