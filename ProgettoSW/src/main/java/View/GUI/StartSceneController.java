package View.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartSceneController {

    //TODO: idealmente la gui dovrebbe essere strutturata cosi:
    // - creazione del server e del client fatta da terminale
    // - sempre da terminale si sceglie se usare cli oppure gui
    // - se viene scelta al gui, si apre questa scena, dove ci sara schermata iniziale e si sceglierà il nome e pedina
    // - poi scena di attesa che tutti i client si siano collegati
    // - scena per selezione delle carta iniziale, obiettivo etc...
    // - scena di gioco effettivo --> station anche degli altri giocatori
    // - scena della scoreboard
    // - scena del vincitore


    @FXML
    private Button button;

    @FXML
    public ImageView coverImage;

    public void makeImageDisappear(){
        coverImage.setVisible(false);
    }

    @FXML
    void goToSetup(MouseEvent event) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseNicknameAndToken.fxml"));
        stage.setScene(new Scene(root));
    }
}
