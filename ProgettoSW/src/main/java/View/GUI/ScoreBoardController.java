package View.GUI;

import Network.Client.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Player;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.TokenEnum;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.*;

import static java.util.Map.Entry.comparingByValue;


public class ScoreBoardController implements Initializable{

    @FXML
    private Button btngoback;

    @FXML
    public ClientController clientController;

    @FXML
    private ImageView imgScoreBoard;

    @FXML
    private Label lblFirstPlace;

    @FXML
    private Label lblFourthPlace;

    @FXML
    private Label lblSecondPlace;

    @FXML
    private Label lblThirdPlace;

    @FXML
    private ImageView pos0;

    @FXML
    private ImageView pos1;

    @FXML
    private ImageView pos10;

    @FXML
    private ImageView pos11;

    @FXML
    private ImageView pos12;

    @FXML
    private ImageView pos13;

    @FXML
    private ImageView pos14;

    @FXML
    private ImageView pos15;

    @FXML
    private ImageView pos16;

    @FXML
    private ImageView pos17;

    @FXML
    private ImageView pos18;

    @FXML
    private ImageView pos19;

    @FXML
    private ImageView pos2;

    @FXML
    private ImageView pos20;

    @FXML
    private ImageView pos21;

    @FXML
    private ImageView pos22;

    @FXML
    private ImageView pos23;

    @FXML
    private ImageView pos24;

    @FXML
    private ImageView pos25;

    @FXML
    private ImageView pos26;

    @FXML
    private ImageView pos27;

    @FXML
    private ImageView pos28;

    @FXML
    private ImageView pos3;

    @FXML
    private ImageView pos4;

    @FXML
    private ImageView pos5;

    @FXML
    private ImageView pos6;

    @FXML
    private ImageView pos7;

    @FXML
    private ImageView pos8;

    @FXML
    private ImageView pos9;

    private Integer oldpoints;

    private Scene preScene;
    private ClientBoard clientBoard;

    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }

    public void setClientBoard(ClientBoard clientBoard){
        this.clientBoard = clientBoard;
    }

    public ClientBoard getClientModel(){
        return clientBoard;
    }

    public void updateTokens(){
        findPos(clientController.getClientModel().getMyplayer().getPoints()).setImage(this.getImageToken(clientController.getClientModel().getMyplayer().getToken()));
        for(ReductPlayer player: clientController.getClientModel().getOtherplayers() ){
            findPos(player.getPoints()).setImage(this.getImageToken(player.getToken()));
        }
    }


    public Image getImageToken(TokenEnum token){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(token.name()+"_token.png");
        return new Image(stream);
    }

    public ImageView findPos(int points){
        switch(points){
            case 1: return pos1;
            case 2: return pos2;
            case 3: return pos3;
            case 4: return pos4;
            case 5: return pos5;
            case 6: return pos6;
            case 7: return pos7;
            case 8: return pos8;
            case 9: return pos9;
            case 10: return pos10;
            case 11: return pos11;
            case 12: return pos12;
            case 13: return pos13;
            case 14: return pos14;
            case 15: return pos15;
            case 16: return pos16;
            case 17: return pos17;
            case 18: return pos18;
            case 19: return pos19;
            case 20: return pos20;
            case 21: return pos21;
            case 22: return pos22;
            case 23: return pos23;
            case 24: return pos24;
            case 25: return pos25;
            case 26: return pos26;
            case 27: return pos27;
            case 28: return pos28;
            default: return pos0;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btngoback.setOnMouseClicked(this::switchToStation);

    }

    public void switchToStation(MouseEvent mouseEvent) {
        Stage stage = (Stage)btngoback.getScene().getWindow();
        stage.setScene(preScene);
        stage.show();
    }

    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    public Label findLabel(int i){
        switch(i){
            case 0: return lblFirstPlace;
            case 1: return lblSecondPlace;
            case 2: return lblThirdPlace;
            case 3: return lblFourthPlace;
            default: return null;
        }
    }


    public void printScores(LinkedHashMap<String, ArrayList<Integer>> map){

    }



}

