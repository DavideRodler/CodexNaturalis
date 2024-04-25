package View;

import model.cards.CardObjective;
import model.cards.CardStarting;

public interface UI {
    void showStartingCard(CardStarting cardStarting);

    String askNickname();

    Integer askPlayerNumber();

    Integer askStartingCardFront();

    void showObjectiveCards(CardObjective[] cardObjective);

    Integer askObjectiveCard();
}
