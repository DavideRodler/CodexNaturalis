package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class startGui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseNickAndToken.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Codex");
        //stage.setFullScreen(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
