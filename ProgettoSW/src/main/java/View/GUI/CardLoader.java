package View.GUI;

import javafx.scene.image.Image;
import model.enums.SuitEnum;

import java.io.InputStream;

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

         fileNameFront = type + "/front/" + id + "_"+ suit + ".png";
         fileNameBack = type + "/back/" + id + "_" + suit + ".png";
     }

     public void printPath(int id, SuitEnum suit){
         createImageOfCard(id, suit);
         System.out.println("/resources/cards" + fileNameFront);
     }

    public Image getBack(int id, SuitEnum suit) {
         createImageOfCard(id, suit);
         return new Image("/resources/cards" + fileNameBack);
    }

    public Image getFront(int id, SuitEnum suit) {
         createImageOfCard(id, suit);
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("63_ANIMAL.png");
        if(stream == null){
            System.out.println("Erroreeeee");
        } else{
            return new Image(stream);
        }
         return new Image(this.getClass().getClassLoader().getResourceAsStream("cards/23_ANIMAL.png"));
    }
}
