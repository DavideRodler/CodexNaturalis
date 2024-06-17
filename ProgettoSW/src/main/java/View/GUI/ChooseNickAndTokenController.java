package View.GUI;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.enums.TokenEnum;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseNickAndTokenController{
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

    private String nick;


    @FXML
    public String enterNickname() {
        nick = chooseNickname.getText();
        System.out.println(nick);
        return nick;
    }

    @FXML
    public TokenEnum enterToken() { //TODO: per inizializzarlo possiamo al posto del meotodo initialize, dalla gui ci facciamo passare i token disponibili e diamo solo quelli come scelta
        TokenEnum token = chooseToken.getValue();
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
}
