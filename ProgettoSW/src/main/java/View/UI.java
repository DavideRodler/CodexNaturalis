package View;

import model.cards.CardStarting;

public interface UI {
    void showStartingCard(CardStarting cardStarting);
    void init();
    String askNickname();

    Integer askPlayerNumber();

}
