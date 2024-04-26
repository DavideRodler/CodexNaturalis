package View;

import model.cards.CardObjective;
import model.cards.CardStarting;

public class VirtualUI implements UI {
    public void update(String message) {

    }

    @Override
    public void showStartingCard(CardStarting cardStarting) {

    }

    @Override
    public String askNickname() {
        return "";
    }

    @Override
    public Integer askPlayerNumber() {
        return 0;
    }

    @Override
    public Integer askStartingCardFront() {
        return 0;
    }

    @Override
    public void showObjectiveCards(CardObjective[] cardObjective) {

    }

    @Override
    public Integer askObjectiveCard() {
        return 0;
    }

    @Override
    public void showUpdatedBoard() {

    }

    @Override
    public void showUpdatedStation(String name) {

    }

    @Override
    public void showUpdatedHand(String name) {

    }

    @Override
    public void showMyUpdatedBoard(String clientNickname) {

    }


}
