package View.GUI;

import Network.Client.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.cards.CardPlaying;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class OtherPlayerStationController implements Initializable {

    private ClientController clientController;

    private int playerChosen;

    private CardLoader cardLoader;

    private boolean playedBack;


    @FXML
    private Button goBackButton;

    @FXML
    private Label label;

    @FXML
    private Pane stationPane;

    private Scene preScene;

    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    public void setPlayerChosen(int playerChosen){
        this.playerChosen = playerChosen;
    }

    public void switchToStation(MouseEvent mouseEvent) {
        Stage stage = (Stage)goBackButton.getScene().getWindow();
        stage.setScene(preScene);
        stage.show();
    }

    public void showStation(){
        String nickname = clientController.getClientModel().getOtherplayers().get(playerChosen).getNickname();
        label.setText(nickname + "'s station");
        cardLoader = new CardLoader();
        int firstCoordinate, secondCoordinate, firstCoordinateDistanceFromStarting, secondCoordinateDistanceFromStarting;
        double first ,second;
        for(Map.Entry<ArrayList<Integer>, CardPlaying> entry : clientController.getClientModel().getOtherplayers().get(playerChosen).getStation().getMap().entrySet()){
            secondCoordinate = entry.getKey().getFirst();
            firstCoordinate = entry.getKey().getLast();
            playedBack = entry.getValue().getPlayingBack();
            firstCoordinateDistanceFromStarting = firstCoordinate - 40;
            secondCoordinateDistanceFromStarting = secondCoordinate - 40;
            ImageView card = new ImageView();
            if(playedBack){
                card.setImage(cardLoader.getBack(entry.getValue().getId()));
            } else{
                card.setImage(cardLoader.getFront(entry.getValue().getId()));
            }
            card.setFitHeight(65);
            card.setFitWidth(110);
            first = 1500 + firstCoordinateDistanceFromStarting*80;
            second = 1125 + secondCoordinateDistanceFromStarting*40;
            card.setLayoutX(first);
            card.setLayoutY(second);
            stationPane.getChildren().add(card);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBackButton.setOnMouseClicked(this::switchToStation);

    }
}
