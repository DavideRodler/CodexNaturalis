package View.GUI;

import View.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PlayingStation;
import model.enums.TokenEnum;

import java.util.ArrayList;

import static javafx.application.Application.launch;

public class Gui extends Application implements UI {

    private StartSceneController startSceneController;
    private GameSetupController gameSetupController;
    private StationController stationController;
    private ChooseNickAndTokenController chooseNickAndTokenController;

    public Gui(StartSceneController startSceneController, GameSetupController gameSetupController, StationController stationController, ChooseNickAndTokenController chooseNickAndTokenController){
        this.startSceneController = startSceneController;
        this.gameSetupController = gameSetupController;
        this.stationController = stationController;
        this.chooseNickAndTokenController = chooseNickAndTokenController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Codex");
        //stage.setFullScreen(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }


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
