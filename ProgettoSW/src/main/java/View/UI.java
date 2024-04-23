package View;

import model.cards.CardStarting;

public interface UI {
    void showStartingCard(CardStarting cardStarting);

    String askNickname();

    Integer askPlayerNumber();

}
