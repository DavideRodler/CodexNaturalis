package View.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.enums.SuitEnum;

import java.io.IOException;

public class CardLoader {


    private String fileNameFront;
    private String fileNameBack;

    private String type;


    //TODO: fare switch per vedere a seconda dell'id in che directory è --> poi fare come nelle carte
     private void createImageOfCard(int id, SuitEnum suit){
         if(id > 0 && id < 41){ // la carta è risorsa
            type = "/resource";
         } else if(id >= 41 && id < 81){
             type = "/gold";
         } else if(id >= 81 && id < 87){
             type = "/starting";
         } else if(id >= 87 && id < 103){
             type = "/objective";
         }

         fileNameFront = type + "/front" + id + suit + ".png";
         fileNameBack = type + "/back" + id + suit + ".png";
     }

    public Image getBack() {
        return new Image("/resources/cards/" + fileNameBack);
    }

    public Image getFront() {
         return new Image("/resources/cards/" + fileNameFront);
    }
}
