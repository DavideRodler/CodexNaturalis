package model.decktameplate;

import model.cards.Card;
import model.cards.CardGold;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Corner;
import model.cards.face.CornerGold;
import model.cards.face.CornerResource;
import model.cards.face.Face;
import model.enums.Suit;
import model.objectives.Objective;
import model.objectives.ObjectiveCountingGold;
import model.objectives.ObjectiveGoldCorners;
import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// create a static class that has for each deck a static method that returns the List of all model.cards that need to be in that deck
public class deckconstructor {
    // creating the DeckResource Deck
    // each line in the text represent the card:
    // we have in order: suit of the card, the four corner(starting from upright) and the points
    public static void DeckResource() {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get("src/main/java/model/decktameplate/DeckResource.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Suit suit = AssignSuit(jsonObject.getString("suit"));
                Corner upright = AssignCorner(jsonObject.getString("upright"));
                Corner upleft = AssignCorner(jsonObject.getString("upleft"));
                Corner downright = AssignCorner(jsonObject.getString("downright"));
                Corner downleft = AssignCorner(jsonObject.getString("downleft"));
                Face front = new Face(upright, upleft, downright, downleft);
                Face back = new Face(new Corner(), new Corner(), new Corner(), new Corner());
                int point = jsonObject.getInt("points");
                Card tmp = new CardResource(i, front, back, suit, point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Corner AssignCorner(String s){
        return switch (s) {
            case "empty" -> new Corner();
            case "fungi" -> new CornerResource(Suit.FUNGI);
            case "plant" -> new CornerResource(Suit.PLANT);
            case "animal" -> new CornerResource(Suit.ANIMAL);
            case "insect" -> new CornerResource(Suit.INSECT);
            case "manuscript" -> new CornerGold(model.enums.GoldSuit.MANUSCRIPT);
            case "inkwell" -> new CornerGold(model.enums.GoldSuit.INKWELL);
            case "quill" -> new CornerGold(model.enums.GoldSuit.QUILL);
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

    public static void main(String[] args) {
        DeckResource();
    }

    /* create the constructor of the gold deck using cards from GoldDeck.json, card are made with this field: suit symbol, the four corner(starting from upright), the the way he scores points (normal point written on the card, objectiveCountingResources or objectiveGoldCorners) and the cost of the card in the four resources */
    public static void DeckGold() {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get("src/main/java/model/decktameplate/GoldDeck.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Suit suit = AssignSuit(jsonObject.getString("suit"));
                Corner upright = AssignCorner(jsonObject.getString("upright"));
                Corner upleft = AssignCorner(jsonObject.getString("upleft"));
                Corner downright = AssignCorner(jsonObject.getString("downright"));
                Corner downleft = AssignCorner(jsonObject.getString("downleft"));
                int points = jsonObject.getInt("points");
                int costAnimal = jsonObject.getInt("costAnimal");
                int costInsect = jsonObject.getInt("costInsect");
                int costFungi = jsonObject.getInt("costFungi");
                int costPlant = jsonObject.getInt("costPlant");
                Face front = new Face(upright, upleft, downright, downleft);
                Face back = new Face(new Corner(), new Corner(), new Corner(), new Corner());
                Objective objective = AssignObjective(jsonObject.getString("objective"));
                Card tmp = new CardGold(i, front, back, suit, points, costAnimal, costInsect, costFungi, costPlant, objective);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            case "manuscript", "inkwell", "quill" -> {
                obj = new ObjectiveCountingGold();
                yield obj;
            }
            default -> null;
        };
    }

    //Create
    public static List<Card> StartingCardDeck(){
        List<Card> deck = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CardData> cards = objectMapper.readValue(new File("src/main/java/model/decktameplate/StartingDeck.json"), new TypeReference<List<CardData>>(){});

            for (CardData cardData : cards) {
                List<Suit> suits = cardData.getSuite().stream().map(AssignSuit::new).collect(Collectors.toList());
                Corner upright = AssignCorner(cardData.getUpright());
                Corner upleft = AssignCorner(cardData.getUpleft());
                Corner downright = AssignCorner(cardData.getDownright());
                Corner downleft = AssignCorner(cardData.getDownleft());
                Face front = new Face(upright,upleft,downright,downleft);
                Face back = new Face(InitialBackAssignCorner("plant"),InitialBackAssignCorner("fungi"),InitialBackAssignCorner("animal"), InitialBackAssignCorner("insect"));
                // Assuming the first suit in the list is the main suit for the card
                Card tmp = new CardStarting(i,front,back,suits.get(0));
                deck.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deck;
    }

    private static Corner InitialBackAssignCorner( String s){
        return switch (s) {
            case "fungi" -> new CornerResource(Suit.FUNGI);
            case "plant" -> new CornerResource(Suit.PLANT);
            case "animal" -> new CornerResource(Suit.ANIMAL);
            case "insect" -> new CornerResource(Suit.INSECT);
            default -> null;
        };
    }





















}
