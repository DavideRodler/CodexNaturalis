package View.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CardLoader {
    @FXML
    public ImageView cardone = FXMLLoader.load(getClass().getResource("/cards/gold/front/animal/gold_animal1"));

    @FXML
    public ImageView cardtwo = FXMLLoader.load(getClass().getResource("/cards/gold/front/animal/gold_animal2"));

    public CardLoader() throws IOException {
        //TODO: fare switch per vedere a seconda dell'id in che directory è --> poi fare come nelle carte
    }
}
