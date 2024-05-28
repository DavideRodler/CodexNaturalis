package View.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.enums.TokenEnum;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseNickAndTokenController implements Initializable {
//TODO: qua schermata con label + textBox per inserire il proprio nickname + tendina per scegliere il token
// infine label che dice di attendere gli altri giocatori
@FXML
private TextField ChooseNickname;

    @FXML
    private ChoiceBox<TokenEnum> ChooseToken;

    @FXML
    private Button EnterNickname;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //fare qualcosa prima che parta questa scena
       // ArrayList<TokenEnum> tokens = ask;
        ChooseToken.getItems().addAll(TokenEnum.YELLOW, TokenEnum.BLUE, TokenEnum.RED, TokenEnum.GREEN); //DA AGGIORNARE


    }
}
