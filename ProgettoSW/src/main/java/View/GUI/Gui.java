package View.GUI;

import View.UI;
import model.PlayingStation;
import model.enums.TokenEnum;

import java.util.ArrayList;

public class Gui implements UI {

    @Override
    public void showStartingCard() {

    }

    @Override
    public void showGameTitle() {

    }

    @Override
    public String askNickname() {
        return null;
    }

    @Override
    public int askPlayerNumber() {
        return 0;
    }

    @Override
    public boolean askStartingCardPlayedBack() {
        return false;
    }

    @Override
    public void printCommonObjectives() {

    }

    @Override
    public void printSecretObjective() {

    }

    @Override
    public void printSelectableObjectives() {

    }

    @Override
    public int askObjectiveCard() {
        return 0;
    }

    @Override
    public void print4CentralCards() {

    }

    @Override
    public void printPlayerStation(PlayingStation playingStation) {

    }

    @Override
    public void printSetupPlayerHand() {

    }

    @Override
    public void printPlayerHand() {

    }

    @Override
    public Integer[] askCoordinatesOfCards() {
        return new Integer[0];
    }

    @Override
    public Integer askWhichCardToDraw() {
        return null;
    }

    @Override
    public void printStartOfPlayerTurn() {

    }

    @Override
    public void printStationAfterCardHasBeenAdded() {

    }

    @Override
    public void printOtherPlayersStation(String nickname) {

    }

    @Override
    public TokenEnum askToken(ArrayList<TokenEnum> availableTokens) {
        return null;
    }

    @Override
    public void printPlayerToken() {

    }

    @Override
    public void waitingForOtherPlayers() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void printAvailableTokens(ArrayList<TokenEnum> availableTokens) {

    }
}
