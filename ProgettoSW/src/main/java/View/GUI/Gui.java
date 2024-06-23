package View.GUI;

import View.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PlayingStation;
import model.client.ClientBoard;
import model.enums.TokenEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static javafx.application.Application.launch;

public class Gui extends Application implements UI {

    private StartSceneController startSceneController;
    private StationController stationController;
    private ChooseNickAndTokenController chooseNickAndTokenController;
    private ClientBoard clientBoard;



    public Gui(StartSceneController startSceneController, StationController stationController, ChooseNickAndTokenController chooseNickAndTokenController, ClientBoard clientBoard){
        this.startSceneController = startSceneController;
        this.stationController = stationController;
        this.chooseNickAndTokenController = chooseNickAndTokenController;
        this.clientBoard = clientBoard;
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
        stationController.showStartingCard(clientBoard.getMyplayer().getStation().getCardStarting());
    }

    @Override
    public void showGameTitle() {
    }

    @Override
    public void printIsMyTurnMenu() {

    }

    @Override
    public void askNickname() {
    }

    @Override
    public void askPlayerNumber() {

    }

    @Override
    public void askStartingCardPlayedBack() {

    }

    @Override
    public void printCommonObjectives() {

    }

    @Override
    public void printSecretObjective() {
    }

    @Override
    public void printSelectableObjectives() {
        stationController.showSelectableObjectives(clientBoard.getMyplayer().getSelectibleObjectives().get(0), clientBoard.getMyplayer().getSelectibleObjectives().get(1));
    }

    @Override
    public void askObjectiveCard() {

    }

    @Override
    public void print4CentralCards() {
        stationController.show4CentralCards(clientBoard.getCentralCardsGold().get(0), clientBoard.getCentralCardsGold().get(1),clientBoard.getCentralCardsResource().get(0), clientBoard.getCentralCardsResource().get(1));
    }

    @Override
    public void printPlayerStation(PlayingStation playingStation) {

    }

    @Override
    public void printSetupPlayerHand() {

    }

    @Override
    public void printPlayerHand() {
        stationController.showPlayerHand(clientBoard.getMyplayer().getHand().get(0),clientBoard.getMyplayer().getHand().get(1), clientBoard.getMyplayer().getHand().get(2),clientBoard.getMyplayer().getSecretObjective());
    }

    @Override
    public Integer[] askCoordinatesOfCards() {
        return new Integer[0];
    }

    @Override
    public void askWhichCardToDraw() {

    }


    @Override
    public void printStationAfterCardHasBeenAdded() {

    }

    @Override
    public void printOtherPlayersStation(String nickname) {

    }

    @Override
    public void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map) {

    }

    @Override
    public void askToken(ArrayList<TokenEnum> availableTokens) { //TODO: passare per al controller l'arraylist

    }


    @Override
    public void printCardAddedSuccessfully() {

    }

    @Override
    public void printCardNotAdded(String message) {

    }
}

//TODO:
// - caricare carte -> seguo ID (da ogni front posso risalire al back - trovo pattern)
// - passare da una scena all'altra tramite button
// - salvare scelte di un giocatore a seconda del button
// - COME PIAZZARE CARTE
// - come passare da salvataggio giocatori a start game
// - caricare bene immagini nelle ImageView
// - label nel salvataggio nickname e token