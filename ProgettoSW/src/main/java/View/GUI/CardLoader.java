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


    public Image getBack(int id) {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("");
        if(id > 0 && id < 11){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream("resource_FUNGI_back.png");
        }
        else if(id > 10 && id < 21){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream("resource_PLANT_back.png");
        }
        else if(id > 20 && id < 31){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream("resource_ANIMAL_back.png");
        }
        else if(id > 30 && id < 41){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream("resource_INSECT_back.png");
        }
        else if(id > 40 && id < 51){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream("gold_FUNGI_back.png");
        }
        else if(id > 50 && id < 61){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream("gold_PLANT_back.png");
        }
        else if(id > 60 && id < 71){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream("gold_ANIMAL_back.png");
        }
        else if(id > 70 && id < 81){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream("gold_INSECT_back.png");
        }
        else if(id > 80 && id < 87){ //starting
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
        if(id > 0 && id < 11){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_FUNGI_front.png");
        }
        else if(id > 10 && id < 21){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_PLANT_front.png");
        }
        else if(id > 20 && id < 31){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_ANIMAL_front.png");
        }
        else if(id > 30 && id < 41){ //resource
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_INSECT_front.png");
        }
        else if(id > 40 && id < 51){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_FUNGI_front.png");
        }
        else if(id > 50 && id < 61){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_PLANT_front.png");
        }
        else if(id > 60 && id < 71){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_ANIMAL_front.png");
        }
        else if(id > 70 && id < 81){ //gold
            stream = this.getClass().getClassLoader().getResourceAsStream(id + "_INSECT_front.png");
        }
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

    public Image getTopDeckObjectives(){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("objective_back.png");
        return new Image(stream);
    }
}
