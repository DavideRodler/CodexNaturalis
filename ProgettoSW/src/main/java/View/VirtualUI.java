package View;

import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardStarting;

import java.util.ArrayList;
import java.util.Map;

public class VirtualUI implements UI {
    public void update(String message) {

    }

    @Override
    public void showStartingCard(CardStarting cardStarting) {

    }

    @Override
    public void showGameTitle() {

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
    public void showMyUpdatedBoard(Map<ArrayList<Integer>, CardPlaying> playingStation, String name) {

    }

    @Override
    public Integer[] askCoordinatesOfCards() {

        return new Integer[0];
    }

    @Override
    public Integer askDrawingCard() {
        return 0;
    }


}
