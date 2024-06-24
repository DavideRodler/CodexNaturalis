package View.GUI;

import Network.Client.ClientController;
import View.UI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PlayingStation;
import model.client.ClientBoard;
import model.enums.TokenEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Gui extends Application implements UI {

    private static StartSceneController startSceneController;
    private static StationController stationController;
    private static ChooseNickAndTokenController chooseNickAndTokenController;
    private static ClientController clientController;

    private ScoreBoardController scoreBoardController;

    public ClientController getClientController() {
        return clientController;
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseNicknameAndToken.fxml"));
        Parent root = loader.load();
        chooseNickAndTokenController = loader.getController();
        chooseNickAndTokenController.setClientController(clientController);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Codex");
        stage.show();
    }

    public void launchGui(ClientController ClientController){
        clientController = ClientController;
        launch();
    }


    @Override
    public void showStartingCard() {
        Platform.runLater(() -> stationController.showStartingCard());
    }


    @Override
    public void printIsNotMyTurnMenu() {

    }

    @Override
    public void printIsMyTurnMenu() {

    }

    @Override
    public void askNickname() {
        Platform.runLater(() -> chooseNickAndTokenController.setNickname());
    }

    @Override
    public void askStartingCardPlayedBack() {
        Platform.runLater(() -> stationController.showStartingCard());
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
        Platform.runLater(() -> {
            try {
                // Carica il file FXML per la nuova scena
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Station.fxml"));
                Parent root = loader.load();

                // Ottieni il controller per la nuova scena e imposta il ClientController
                stationController = loader.getController();
                stationController.setClientController(clientController);

                // Crea la nuova scena
                Scene scene = new Scene(root);

                // Ottieni lo Stage corrente e imposta la nuova scena
                Stage currentStage = (Stage) chooseNickAndTokenController.getNicknamePane().getScene().getWindow();
                currentStage.setScene(scene);

                // Mostra la nuova scena
                currentStage.show();

                // Aggiorna la visualizzazione delle carte centrali
                stationController.showCentralCardsAndDecks();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

    @Override
    public void printNicknameAlreadyTaken() {
        Platform.runLater(() -> chooseNickAndTokenController.printNicknameAlreadyTaken());
    }

    @Override
    public void printTokenAlreadyTaken() {
        Platform.runLater(() -> chooseNickAndTokenController.printTokenAlreadyTaken());
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