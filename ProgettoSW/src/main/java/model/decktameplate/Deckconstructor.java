package model.decktameplate;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.Direction;
import model.enums.Position;
import model.enums.Suit;
import model.objectives.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// create a static class that has for each deck a static method that returns the List of all model.cards that need to be in that deck
public class Deckconstructor {
    // creating the DeckResource Deck
    // each line in the text represent the card:
    // we have in order: suit of the card, the four corner(starting from upright) and the points

    public static LinkedList<CardResource> ResourceCardDeck() throws IOException, ParseException {
        LinkedList<CardResource> deck = new LinkedList<>();
        JSONParser parser = new JSONParser();
        JSONArray resourceCard = (JSONArray) parser.parse(new FileReader("src/main/java/model/decktameplate/resourceDeck.json"));
        for(Object obj : resourceCard){
            JSONObject card = (JSONObject) obj;
            String suite = (String) card.get("type");
            String upright = (String) card.get("upright");
            String upleft = (String) card.get("upleft");
            String downright = (String) card.get("downright");
            String downleft = (String) card.get("downleft");
            int points = ((Long) card.get("points")).intValue();
            Face back= new Face(AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"));
            Face front= new Face(AssignCorner(upright), AssignCorner(upleft), AssignCorner(downright), AssignCorner(downleft));
            CardResource tmp = new CardResource(0, front, back, AssignSuit(suite), points);
            deck.add(tmp);

        }
        return deck;
    }
    public static LinkedList<CardGold> GoldCardDeck() throws IOException, ParseException {
        LinkedList<CardGold> deck = new LinkedList<>();
        JSONParser parser = new JSONParser();
        JSONArray goldCard = (JSONArray) parser.parse(new FileReader("src/main/java/model/decktameplate/GoldDeck.json"));
        for(Object obj : goldCard){
            JSONObject card = (JSONObject) obj;
            String suite = (String) card.get("type");
            String upright = (String) card.get("upright");
            String upleft = (String) card.get("upleft");
            String downright = (String) card.get("downright");
            String downleft = (String) card.get("downleft");
            String center = (String) card.get("center");
            int points = ((Long) card.get("points")).intValue();
            int costAnimal = ((Long) card.get("costAnimal")).intValue();
            int costInsect = ((Long) card.get("costInsect")).intValue();
            int costFungi = ((Long) card.get("costFungi")).intValue();
            int costPlant = ((Long) card.get("costPlant")).intValue();
            Face back= new Face(AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"), AssignCorner("empty"));
            Face front= new Face(AssignCorner(upright), AssignCorner(upleft), AssignCorner(downright), AssignCorner(downleft));
            CardGold tmp = new CardGold(0, front, back, AssignSuit(suite), points, costAnimal, costInsect, costFungi, costPlant, AssignObjective(center));
            deck.add(tmp);

        }
        return deck;
    }
    public static LinkedList<CardStarting> StartingCardDeck() throws IOException, ParseException {
        LinkedList<CardStarting> deck = new LinkedList<>();
        JSONParser parser = new JSONParser();
        JSONArray startingCard = (JSONArray) parser.parse(new FileReader("src/main/java/model/decktameplate/StartingDeck.json"));
        for(Object obj : startingCard){
            ArrayList<Suit> symbols = new ArrayList<>();
            JSONObject card = (JSONObject) obj;
            JSONArray suite = (JSONArray) card.get("suite");
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
            deck.add(tmp);

        }
        return deck;
    }
    public static LinkedList<CardObjective> ObjectiveCardDeck() throws IOException, ParseException {
        LinkedList<CardObjective> deck = new LinkedList<>();
        JSONParser parser = new JSONParser();
        JSONArray objectiveCard = (JSONArray) parser.parse(new FileReader("src/main/java/model/decktameplate/ObjectiveCard.json"));
        for(Object obj : objectiveCard){
            JSONObject card = (JSONObject) obj;
            String suite = (String) card.get("type");
            switch (suite) {
                case "diagonal" -> {
                    String resource = (String) card.get("resource");
                    String diagonal = (String) card.get("diagonal");
                    int points = ((Long) card.get("points")).intValue();
                    Objective objectiveDiagonal = new ObjectiveDiagonal(AssignDirection(diagonal), AssignSuit(resource));
                    CardObjective tmp= new CardObjective(0, points, objectiveDiagonal);
                    deck.add(tmp);
                }
                case "positioning" -> {
                    String twoCards = (String) card.get("twoCards");
                    String oneCard = (String) card.get("oneCard");
                    String Horizontal = (String) card.get("horizontal");
                    String Vertical = (String) card.get("vertical");
                    int points = ((Long) card.get("points")).intValue();
                    Objective objectivePositioning = new ObjectivePositioning( AssignSuit(oneCard),AssignSuit(twoCards),AssignDirection(Horizontal), AssignPosition(Vertical));
                    CardObjective tmp= new CardObjective(0, points, objectivePositioning);
                    deck.add(tmp);
                }
                case "resources" -> {
                    String resource = (String) card.get("resource");
                    int points = ((Long) card.get("points")).intValue();
                    Objective objectiveCountingResource = new ObjectiveCountingResource(AssignSuit(resource));
                    CardObjective tmp= new CardObjective(0, points, objectiveCountingResource);
                    deck.add(tmp);
                }
                case "gold" -> {
                    String goldType = (String) card.get("goldType");
                    int points = ((Long) card.get("points")).intValue();
                    Objective objectiveCountingGold = AssignObjective(goldType);
                    CardObjective tmp= new CardObjective(0, points, objectiveCountingGold);
                    deck.add(tmp);
                }
                default -> throw new IllegalStateException("Unexpected value: " + suite);
            }
        }
        return deck;
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
            default -> new Corner(Suit.NULL);
        };
    }

    private static Suit AssignSuit(String s){
        return switch (s) {
            case "fungi" -> Suit.FUNGI;
            case "plant" -> Suit.PLANT;
            case "animal" -> Suit.ANIMAL;
            case "insect" -> Suit.INSECT;
            case "empty" -> Suit.EMPTY;
            default -> Suit.NULL;
        };
    }

    private static Direction AssignDirection (String s){
        return switch (s) {
            case "left" -> Direction.LEFT;
            default -> Direction.RIGHT;
        };
    }

    private static Position AssignPosition(String s){
        return switch (s) {
            case "top" -> Position.TOP;
            default -> Position.BOTTOM;
        };
    }

    //create a private method AssignOvbective that takes a string (null,corenrs or gold resource:manuscript,inkwell,quill) and create an object that is statically type Objective and dinamically type Objective, ObjectiveCountingResources or ObjectiveGoldCorners if the string is respectability null, corners or gold resource and return that objective


    private static Objective AssignObjective(String s){
        Objective obj;
        switch (s) {
            case "points":
                obj = new Objective();
                return obj;
            case "corners":
                obj = new ObjectiveGoldCorners();
                return obj;
            case "manuscript":
                obj = new ObjectiveCountingGold(0, 1, 0);
                return obj;
            case "inkwell":
                obj = new ObjectiveCountingGold(1, 0, 0);
                return obj;
            case "quill":
                obj = new ObjectiveCountingGold(0, 0, 1);
                return obj;
            case "all":
                obj = new ObjectiveCountingGold(1, 1, 1);
                return obj;
            default:
                throw new IllegalStateException("Unexpected value: " + s);
        }
    }




















}
