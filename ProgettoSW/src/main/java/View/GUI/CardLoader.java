package View.GUI;

import javafx.scene.image.Image;
import model.enums.SuitEnum;

import java.io.IOException;
import java.io.InputStream;

public class CardLoader {


    private String fileNameFront;
    private String fileNameBack;

    private String type;


    //TODO: fare switch per vedere a seconda dell'id in che directory è --> poi fare come nelle carte


    public Image getBack(int id, SuitEnum suit) {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("");
        if(id > 0 && id < 41){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream("resource_"+ suit +"_back.png");
        }
        else if(id > 40 && id < 81){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream("gold_"+ suit +"_back.png");
        }
        else{
            System.out.println("Erroreeeee");
        }
        assert stream != null;
        return new Image(stream);
    }

    public Image getBack(int id) {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("");
        if(id > 80 && id < 87){ //starting
            stream = this.getClass().getClassLoader().getResourceAsStream(id +"_starting_back.png");
        }
         else if(id > 86 && id < 103){
            stream = this.getClass().getClassLoader().getResourceAsStream("objective_back.png");
        }
        else{
            System.out.println("Erroreeeee");
        }
        assert stream != null;
        return new Image(stream);
    }

    public Image getFront(int id) {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("");
        if(id > 80 && id < 87){ //starting
            stream = this.getClass().getClassLoader().getResourceAsStream(id +"_starting_front.png");
        }
        else if(id > 86 && id < 103){
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_objective_front.png");
        }
        else{
            System.out.println("Erroreeeee");
        }
        assert stream != null;
        return new Image(stream);
    }

    public Image getFront(int id, SuitEnum suit){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(id + "_"+ suit +"_front.png");
        assert stream != null;
        return new Image(stream);
    }

    public Image getTopDeckGold(SuitEnum suit){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("gold_"+ suit +"_back.png");
        assert stream != null;
        return new Image(stream);
    }

    public Image getTopDeckResource(SuitEnum suit){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("resource_"+ suit +"_back.png");
        assert stream != null;
        return new Image(stream);
    }
}
