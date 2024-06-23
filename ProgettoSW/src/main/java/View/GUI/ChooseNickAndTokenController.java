package View.GUI;

import Network.Client.ClientController;
import Socket.Messages.NicknameMessage;
import Socket.Messages.TokenMessage;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.enums.SuitEnum;
import model.enums.TokenEnum;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseNickAndTokenController implements Initializable{
//TODO: qua schermata con label + textBox per inserire il proprio nickname + tendina per scegliere il token
// infine label che dice di attendere gli altri giocatori
    @FXML
    private TextField chooseNickname;

    @FXML
    private ChoiceBox<TokenEnum> chooseToken;

    @FXML
    private Button enterNicknameButton;

    @FXML
    private Button enterTokenButton;

    @FXML
    private Label label;

    @FXML
    private Label chooseTokenLbl;

    @FXML
    private Label chooseNicknameLbl;

    private String nick;

    @FXML
    private ImageView testCardLoaderFront;

    @FXML
    private ImageView testCardLoaderBack;

    @FXML
    private ImageView imgtest;

    @FXML
    private HBox nicknamePane;

    @FXML
    private HBox tokenPane;

    private ClientController clientController;

    public ChooseNickAndTokenController(){

    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @FXML
    public void enterNickname() {
        nick = chooseNickname.getText();
        tokenPane.setVisible(true);
        label.setText("Choose your token!");
        this.clientController.setupOfnickname_UI(nick);
    }

    @FXML
    public void enterToken() { //TODO: gestire il fatto che un token potrebbe essere stato scelto da un altro giocatore
        TokenEnum token = chooseToken.getValue();
        //clientController.messageToServerhandler(new TokenMessage(clientController.getClientModel().getMyplayer().getNickname(), token));
        label.setText("Waiting for other players...");
        //cambio di scena quanto tutti i players sono connessi --> dall'esterno
    }


    @FXML
    public void setAvailableTokens(ArrayList<TokenEnum> tokens){
        for(TokenEnum token: tokens){
            chooseToken.getItems().add(token);
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("Enter your nickname!");
        tokenPane.setVisible(false);
    }


}
