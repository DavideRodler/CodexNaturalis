package View;

import model.cards.CardObjective;
import model.cards.CardStarting;

public interface UI {
    void showStartingCard(CardStarting cardStarting);

    void showGameTitle();

    //void init();
    String askNickname();

    Integer askPlayerNumber();

    Integer askStartingCardFront();

    void showObjectiveCards(CardObjective[] cardObjective);

    Integer askObjectiveCard();
    
    void showUpdatedBoard();
    
    void showUpdatedStation(String name);

    void showUpdatedHand(String name);

    void showMyUpdatedBoard(String clientNickname);
}
