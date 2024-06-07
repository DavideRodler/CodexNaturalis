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

public class ChooseNickAndTokenController implements Initializable {
//TODO: qua schermata con label + textBox per inserire il proprio nickname + tendina per scegliere il token
// infine label che dice di attendere gli altri giocatori
    @FXML
    private TextField chooseNickname;

    @FXML
    private ChoiceBox<TokenEnum> chooseToken;

    @FXML
    private Button btnNickname;

    @FXML
    private Button btnToken;

    @FXML
    private Label label;


    @FXML
    public String enterNickname() {
        String nickname = chooseNickname.getText();
        return nickname;
    }

    @FXML
    public TokenEnum enterToken() { //TODO: per inizializzarlo possiamo al posto del meotodo initialize, dalla gui ci facciamo passare i token disponibili e diamo solo quelli come scelta
        TokenEnum token = chooseToken.getValue();
        return token;
    }

    @FXML
    public void enterNicknameAndToken(MouseEvent event) {

    }


    @FXML
    private void changeLabelText(String text){
        label.setText(text);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //fare qualcosa prima che parta questa scena
       // ArrayList<TokenEnum> tokens = ask;
        chooseToken.getItems().addAll(TokenEnum.YELLOW, TokenEnum.BLUE, TokenEnum.RED, TokenEnum.GREEN); //DA AGGIORNARE


    }

}
