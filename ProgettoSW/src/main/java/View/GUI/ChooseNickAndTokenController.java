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
    private ChoiceBox<TokenEnum> ChooseToken;

    @FXML
    private Button enter;

    @FXML
    private Label label;

    @FXML
    public void enterNicknameAndToken(MouseEvent event) {
        do{
            changeLabelText("Please enter a nickname!");
        } while(chooseNickname == null);

        do{
            changeLabelText("Please choose a token!");
        } while((ChooseToken.getValue() == null));

        changeLabelText("Waiting for other players...");

//        if(chooseNickname != null){
//            if((ChooseToken.getValue() == null)){
//                changeLabelText("Please choose a token!");
//            } else{
//                changeLabelText("Waiting for other players...");
//            }
//        } else{
//            changeLabelText("Please enter a nickname!");
//        }
    }


    @FXML
    private void changeLabelText(String text){
        label.setText(text);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //fare qualcosa prima che parta questa scena
       // ArrayList<TokenEnum> tokens = ask;
        ChooseToken.getItems().addAll(TokenEnum.YELLOW, TokenEnum.BLUE, TokenEnum.RED, TokenEnum.GREEN); //DA AGGIORNARE


    }

}
