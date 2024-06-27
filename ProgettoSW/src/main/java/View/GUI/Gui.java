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
import model.cards.CardResource;
import model.client.ClientBoard;
import model.enums.*;

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
        scene.getStylesheets().add(getClass().getResource("/css/background.css").toExternalForm());
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
    public void askNickname() {
        Platform.runLater(() -> chooseNickAndTokenController.setNickname());
    }

    @Override
    public void askStartingCardPlayedBack() {
        Platform.runLater(() -> stationController.showStartingCard());
    }

    @Override
    public void printCommonObjectives() {
        Platform.runLater(() -> stationController.showCommonObjectives());
    }



    @Override
    public void askObjectiveCard() {
        Platform.runLater(() -> stationController.showSelectableObjectives());
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
    public void printPlayerHand() {
        Platform.runLater(() -> stationController.showPlayerHand());
    }



    @Override
    public void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map) {
        Platform.runLater(() -> {
            try {
                // Carica il file FXML per la nuova scena
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScoreBoard.fxml"));
                Parent root = loader.load();

                // Ottieni il controller per la nuova scena e imposta il ClientController
                scoreBoardController = loader.getController();
                scoreBoardController.setClientController(clientController);

                // Crea la nuova scena
                Scene scene = new Scene(root);

                // Ottieni lo Stage corrente e imposta la nuova scena

                Stage currentStage = (Stage) stationController.getStationPane().getScene().getWindow();
                currentStage.setScene(scene);

                // Mostra la nuova scena
                currentStage.show();

                // Aggiorna la visualizzazione delle carte centrali
                stationController.gameFinished(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void askToken(ArrayList<TokenEnum> availableTokens) {
        Platform.runLater(() -> chooseNickAndTokenController.setAvailableTokens(availableTokens));

    }


    @Override
    public void printCardAddedSuccessfully() {
        Platform.runLater(() -> stationController.cardPlacedCorrectly());
    }

    @Override
    public void printCardNotAdded(String message) {
        Platform.runLater(() -> stationController.cardPlaceIncorrectly(message));
    }


    @Override
    public void printNicknameAlreadyTaken() {
        Platform.runLater(() -> chooseNickAndTokenController.printNicknameAlreadyTaken());
    }

    @Override
    public void printTokenAlreadyTaken() {
        Platform.runLater(() -> chooseNickAndTokenController.printTokenAlreadyTaken());
    }

    @Override
    public void startGame() {
        Platform.runLater(() -> stationController.updateCurrentPlayerView());
    }

    @Override
    public void updateCurrentPlayer() {
        if(!clientController.getClientModel().getGameState().equals(GameState.SELECT_TOKEN) &&
                !clientController.getClientModel().getGameState().equals(GameState.ADD_PLAYERS) &&
                !clientController.getClientModel().getGameState().equals(GameState.INITIALIZE_GAME)) {
                Platform.runLater(() -> stationController.updateCurrentPlayerView());

        }
    }

    @Override
    public void updateGlobalChat() {
        Platform.runLater(() -> stationController.printGlobalChat());
    }

    @Override
    public void updatePrivateChat() {
        Platform.runLater(() -> stationController.printPrivateChat());
    }

    @Override
    public void updateHand() {
        if (!clientController.getClientModel().getGameState().equals(GameState.SELECT_TOKEN) &&
                !clientController.getClientModel().getGameState().equals(GameState.ADD_PLAYERS) &&
                !clientController.getClientModel().getGameState().equals(GameState.INITIALIZE_GAME)) {
            Platform.runLater(() -> stationController.updateHand());
        }
    }

    @Override
    public void updateViewAfterCardAddedToStation(CardResource card, int x, int y, boolean playedBack) {
        if (!clientController.getClientModel().getGameState().equals(GameState.SELECT_TOKEN) &&
                !clientController.getClientModel().getGameState().equals(GameState.SELECT_STARTINGCARDFACE) &&
                !clientController.getClientModel().getGameState().equals(GameState.SELECT_OBJECTIVE) &&
                !clientController.getClientModel().getGameState().equals(GameState.INITIALIZE_GAME))
        Platform.runLater(() -> stationController.updateViewAfterCardAddedToStation(card, x, y, playedBack));
    }
}
