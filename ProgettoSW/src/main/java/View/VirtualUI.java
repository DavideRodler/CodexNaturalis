package View;

import model.cards.CardStarting;

public class VirtualUI implements UI {
    public void update(String message) {

    }

    @Override
    public void showStartingCard(CardStarting cardStarting) {

    }

    @Override
    public void init() {

    }

    @Override
    public String askNickname() {
        return "";
    }

    @Override
    public Integer askPlayerNumber() {
        return 0;
    }
}
