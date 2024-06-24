package View.GUI;

import Network.Client.ClientController;
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

    @FXML
    private Label AlreadyTakenNickTok;

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

    private static ClientController clientController;

    public ChooseNickAndTokenController() {
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public HBox getNicknamePane() {
        return nicknamePane;
    }

    @FXML
    public void enterNickname() {
        nick = chooseNickname.getText();
        label.setText("Waiting for other players to set nickname...");
        this.getClientController().setupOfnickname_UI(nick);
        enterNicknameButton.setVisible(false);
        chooseNickname.setVisible(false);
        chooseNicknameLbl.setVisible(false);

    }

    @FXML
    public void enterToken() { //TODO: gestire il fatto che un token potrebbe essere stato scelto da un altro giocatore
        TokenEnum token = chooseToken.getValue();
        label.setText("Waiting for other players...");
        clientController.setupOfToken_CLI(token);
    }


    @FXML
    public void setNickname(){
        label.setText("Choose your nickname!");
        enterNicknameButton.setVisible(true);
        chooseNickname.setVisible(true);
        chooseNicknameLbl.setVisible(true);
    }


    @FXML
    public void setAvailableTokens(ArrayList<TokenEnum> tokens){
        tokenPane.setVisible(true);
        label.setText("Choose your token!");

        ArrayList<TokenEnum> itemsToRemove = new ArrayList<>(chooseToken.getItems());
        for (TokenEnum token: itemsToRemove){
            chooseToken.getItems().remove(token);
        }
        for(TokenEnum token: tokens){
            chooseToken.getItems().add(token);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AlreadyTakenNickTok.setVisible(false);
        tokenPane.setVisible(false);
        enterNicknameButton.setVisible(false);
    }


    @FXML
    public void printNicknameAlreadyTaken() {
        AlreadyTakenNickTok.setVisible(true);
        AlreadyTakenNickTok.setText("Nickname already taken, choose another one!");
    }

    @FXML
    public void printTokenAlreadyTaken() {
        AlreadyTakenNickTok.setVisible(true);
        AlreadyTakenNickTok.setText("Token already taken, choose another one!");
    }
}
