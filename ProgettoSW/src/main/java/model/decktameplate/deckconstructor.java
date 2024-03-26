package model.decktameplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.Suit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// create a static class that has for each deck a static method that returns the List of all model.cards that need to be in that deck
public class deckconstructor {
    // creating the DeckResource Deck
    // each line in the text represent the card:
    // we have in order: suit of the card, the four corner(starting from upright) and the points

    public static ArrayList<CardStarting> StartingCardDeck() throws IOException, ParseException {
        ArrayList<Suit> symbols = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray startingCard = (JSONArray) parser.parse(new FileReader("src/main/resources/StartingDeck.json"));
        for(Object obj : startingCard){
            JSONObject card = (JSONObject) obj;
            JSONArray suite = (JSONArray) card.get("suit");
            for(Object vecObject: suite)
            {
                symbols.add(AssignSuit((String) vecObject));

            }
            String uprightFront = (String) card.get("uprightFront");
            String upleftFront = (String) card.get("upleftFront");
            String downrightFront = (String) card.get("downrightFront");
            String downleftFront = (String) card.get("downleftFront");
            String uprightBack = (String) card.get("uprightBack");
            String upleftBack = (String) card.get("upleftBack");
            String downrightBack = (String) card.get("downrightBack");
            String downleftBack = (String) card.get("downleftBack");
            Face back= new Face(AssignCorner(uprightBack), AssignCorner(upleftBack), AssignCorner(downrightBack), AssignCorner(downleftBack));
            Face front= new Face(AssignCorner(uprightFront), AssignCorner(upleftFront), AssignCorner(downrightFront), AssignCorner(downleftFront));
            CardStarting tmp = new CardStarting(0, front, back, symbols);
        }

    }

    private static Corner AssignCorner(String s){
        return switch (s) {
            case "empty" -> new Corner(Suit.EMPTY);
            case "fungi" -> new Corner(Suit.FUNGI);
            case "plant" -> new Corner(Suit.PLANT);
            case "animal" -> new Corner(Suit.ANIMAL);
            case "insect" -> new Corner(Suit.INSECT);
            case "manuscript" -> new Corner(Suit.MANUSCRIPT);
            case "inkwell" -> new Corner(Suit.INKWELL);
            case "quill" -> new Corner(Suit.QUILL);
            default -> null;
        };
    }

    private static Suit AssignSuit(String s){
        return switch (s) {
            case "fungi" -> Suit.FUNGI;
            case "plant" -> Suit.PLANT;
            case "animal" -> Suit.ANIMAL;
            case "insect" -> Suit.INSECT;
            default -> null;
        };
    }

    //create a private method AssignOvbective that takes a string (null,corenrs or gold resource:manuscript,inkwell,quill) and create an object that is statically type Objective and dinamically type Objective, ObjectiveCountingResources or ObjectiveGoldCorners if the string is respectability null, corners or gold resource and return that objective


    private static Objective AssignObjective(String s){
        Objective obj;
        return switch (s) {
            case "null" -> {
                obj = new Objective();
                yield obj;
            }
            case "corners" -> {
                obj = new ObjectiveGoldCorners();
                yield obj;
            }
            case "manuscript" -> {
                obj = new ObjectiveCountingGold(0,1,0);
                yield obj;
            }
            case "inkwell" -> {
                obj = new ObjectiveCountingGold(1,0,0);
                yield obj;
            }
            case "quill" -> {
                obj = new ObjectiveCountingGold(0,0,1);
                yield obj;
            }
            default -> null;
        };
    }




















}
