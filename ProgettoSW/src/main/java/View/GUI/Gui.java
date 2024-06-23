package View.GUI;

import Network.Client.ClientController;
import View.UI;
import javafx.application.Application;
import javafx.application.Platform;
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
    private ClientController clientController;

    private ScoreBoardController scoreBoardController;



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

    public void launchGui(ClientBoard clientBoard, ClientController clientController){
        this.clientBoard = clientBoard;
        this.clientController = clientController;
        this.startSceneController = new StartSceneController(this.clientController);
        this.stationController = new StationController(this.clientController);
        this.chooseNickAndTokenController = new ChooseNickAndTokenController();
        this.scoreBoardController = new ScoreBoardController();
        this.chooseNickAndTokenController.setClientController(this.clientController);
        this.scoreBoardController.setClientController(this.clientController);
        launch();
    }


    @Override
    public void showStartingCard() {
        Platform.runLater(() -> stationController.showStartingCard());
    }

    @Override
    public void showGameTitle() {
    }

    @Override
    public void printIsNotMyTurnMenu() {

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
        //stationController.show2CommonObjectives(clientBoard.getFirstObjective(), clientBoard.getSecondObjective());
        Platform.runLater(() -> stationController.showCommonObjectives());
    }

    @Override
    public void printSecretObjective() {
        Platform.runLater(() -> stationController.showStartingCard());
    }

    @Override
    public void printSelectableObjectives() {
        //stationController.showSelectableObjectives(clientBoard.getMyplayer().getSelectibleObjectives().get(0), clientBoard.getMyplayer().getSelectibleObjectives().get(1));
        Platform.runLater(() -> stationController.showSelectableObjectives());
    }



    @Override
    public void askObjectiveCard() {

    }

    @Override
    public void print4CentralCards() {
        //stationController.show4CentralCards(clientBoard.getCentralCardsGold().get(0), clientBoard.getCentralCardsGold().get(1),clientBoard.getCentralCardsResource().get(0), clientBoard.getCentralCardsResource().get(1));
        Platform.runLater(() -> stationController.showCentralCardsAndDecks());
    }

    @Override
    public void printPlayerStation(PlayingStation playingStation) {

    }

    @Override
    public void printSetupPlayerHand() {

    }

    @Override
    public void printPlayerHand() {
        //stationController.showPlayerHand(clientBoard.getMyplayer().getHand().get(0),clientBoard.getMyplayer().getHand().get(1), clientBoard.getMyplayer().getHand().get(2),clientBoard.getMyplayer().getSecretObjective());
        Platform.runLater(() -> stationController.showPlayerHand());
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
        System.out.println("ciaooo");
        Platform.runLater(() -> chooseNickAndTokenController.setAvailableTokens(availableTokens));

    }


    @Override
    public void printCardAddedSuccessfully() {

    }

    @Override
    public void printCardNotAdded(String message) {

    }

    @Override
    public void printMenu() {

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