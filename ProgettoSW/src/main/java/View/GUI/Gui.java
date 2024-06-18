package View.GUI;

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



    public Gui(){
        this.startSceneController = new StartSceneController();
        this.stationController = new StationController();
        this.chooseNickAndTokenController = new ChooseNickAndTokenController();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseNicknameAndToken.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Codex");
        //stage.setFullScreen(true);
        stage.show();
    }

    @Override
    public void launchGui(ClientBoard clientBoard){
        this.clientBoard = clientBoard;
        launch();
    }


    @Override
    public void showStartingCard() {
        Platform.runLater(() -> stationController.showStartingCard(clientBoard.getMyplayer().getStation().getCardStarting()));
    }

    @Override
    public void showGameTitle() {
    }

    @Override
    public String askNickname() {
        String a ="";
        //Platform.runLater(() -> a = chooseNickAndTokenController.enterNickname());
        return a;
    }

    @Override
    public int askPlayerNumber() {
        return 0;
    }

    @Override
    public boolean askStartingCardPlayedBack() {
        return true;
    }

    @Override
    public void printCommonObjectives() {
        //stationController.show2CommonObjectives(clientBoard.getFirstObjective(), clientBoard.getSecondObjective());
    }

    @Override
    public void printSecretObjective() {
    }

    @Override
    public void printSelectableObjectives() {
        //stationController.showSelectableObjectives(clientBoard.getMyplayer().getSelectibleObjectives().get(0), clientBoard.getMyplayer().getSelectibleObjectives().get(1));
    }

    @Override
    public int askObjectiveCard() {
        return 0;
    }

    @Override
    public void print4CentralCardsAndDecks() {
        //stationController.show4CentralCards(clientBoard.getCentralCardsGold().get(0), clientBoard.getCentralCardsGold().get(1),clientBoard.getCentralCardsResource().get(0), clientBoard.getCentralCardsResource().get(1));
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
    public void printMenu() {

    }

    @Override
    public void privateChatTitlePrinter() {

    }

    @Override
    public String askTypeOfChat(int numberOfOtherPlayers, String[] NamesOfOtherPlayers) {
        return "";
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
    public TokenEnum askToken(ArrayList<TokenEnum> availableTokens) { //TODO: passare per al controller l'arraylist
        chooseNickAndTokenController.setAvailableTokens(availableTokens);
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

    @Override
    public Integer askMenuAction() {
        return 0;
    }

    @Override
    public Integer askNotMyTurnMenuAction() {
        return 0;
    }

    @Override
    public String askWichStationToPrint() {
        return "";
    }

    @Override
    public void printSpace() {

    }

    @Override
    public void printMenu2and3() {

    }

    @Override
    public void printMenuNotMyTurn(String currentPlayer) {

    }

    @Override
    public void printMenu2and3NotMyTurn(String currentPlayer) {

    }

    @Override
    public String askMessage() {
        return "";
    }

    @Override
    public void printChat() {

    }

    @Override
    public void printChatInfo() {

    }

    @Override
    public String printPrivateChatInfo() {
        return "";
    }

    @Override
    public void printPrivateChat(String nickname, String nickname1) {

    }

    @Override
    public void chatTitlePrinter() {

    }

    @Override
    public void printPoints() {

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