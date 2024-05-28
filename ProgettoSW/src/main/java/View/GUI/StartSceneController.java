package View.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private ImageView coverImage;

    //metto button --> più semplice

    @FXML
    void start(KeyEvent event) {
        if(event.getCode() ==  KeyCode.ENTER){ //TODO: quando premuto enter in realtà si cambierà scena
            coverImage.setVisible(false);
            System.out.println("Enter pressed!");
        }
    }
}
