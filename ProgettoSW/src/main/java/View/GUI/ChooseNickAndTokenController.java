package View.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.enums.SuitEnum;
import model.enums.TokenEnum;


import java.net.URL;
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
    public String enterNickname() {
        nick = chooseNickname.getText();
        label.setText("Choose your token!");
        enterTokenButton.setVisible(true);
        chooseToken.setVisible(true);
        return nick;
    }

    @FXML
    public TokenEnum enterToken() { //TODO: gestire il fatto che un token potrebbe essere stato scelto da un altro giocatore
        TokenEnum token = chooseToken.getValue();
        label.setText("Waiting for other players...");
        return token;
    }



    @FXML
    public void setAvailableTokens(ArrayList<TokenEnum> tokens){
        for(TokenEnum token: tokens){
            chooseToken.getItems().add(token);
        }
    }

    public void enterNicknameAndToken(MouseEvent mouseEvent) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("Enter your nickname!");
        enterTokenButton.setVisible(false);
        chooseToken.setVisible(false);

    }
}
