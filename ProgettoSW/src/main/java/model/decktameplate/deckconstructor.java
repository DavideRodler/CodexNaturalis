package model.decktameplate;

import model.cards.Card;
import model.cards.CardGold;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.cards.face.Face;
import model.cards.face.Corner;
import model.cards.face.CornerGold;
import model.cards.face.CornerResource;
import model.enums.Suit;
import model.objectives.Objective;
import model.objectives.ObjectiveCountingGold;
import model.objectives.ObjectiveGoldCorners;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// create a static class that has for each deck a static method that returns the List of all model.cards that need to be in that deck
public class deckconstructor {
    // creating the DeckResource Deck
    // each line in the text represent the card:
    // we have in order: suit of the card, the four corner(starting from upright) and the points
    public static <Int> void DeckResource(){
        System.out.println(new File(".").getAbsolutePath());
        File file = new File("src/main/java/model.decktameplate/DeckResource.txt");
        // try to create a scanner only if the file exists
        try {
            Scanner scanner = new Scanner(file);
            int IDcont = 0;
            while (scanner.hasNext()) {
                Suit suit = AssignSuit(scanner.next());
                Corner upright = AssignCorner(scanner.next());
                Corner upleft = AssignCorner(scanner.next());
                Corner downright = AssignCorner(scanner.next());
                Corner downleft = AssignCorner(scanner.next());
                Face front = new Face(upright,upleft,downright,downleft);
                Face back = new Face(new Corner(),new Corner(),new Corner(), new Corner());
                int cost = Integer.parseInt(scanner.next());
                Card tmp = new CardResource(IDcont,front,back,suit,cost);
                IDcont++;

            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("file not found " + e.getMessage());

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
    public static void DeckGold(){
        File file = new File("src/main/java/model.decktameplate/GoldDeck.txt");
        // try to create a scanner only if the file exists
        try {
            Scanner scanner = new Scanner(file);
            int IDcont = 0;
            while (scanner.hasNext()) {
                Suit suit = AssignSuit(scanner.next());
                Corner upright = AssignCorner(scanner.next());
                Corner upleft = AssignCorner(scanner.next());
                Corner downright = AssignCorner(scanner.next());
                Corner downleft = AssignCorner(scanner.next());
                int points = Integer.parseInt(scanner.next());
                int costAnimal = Integer.parseInt(scanner.next());
                int costInsect = Integer.parseInt(scanner.next());
                int costFungi = Integer.parseInt(scanner.next());
                int costPlant = Integer.parseInt(scanner.next());
                Face front = new Face(upright,upleft,downright,downleft);
                Face back = new Face(new Corner(),new Corner(),new Corner(), new Corner());
                Objective objective = AssignObjective(scanner.next());
                Card tmp = new CardGold(IDcont,front,back,suit,points,costAnimal,costInsect,costFungi,costPlant, objective);
                IDcont++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("file not found " + e.getMessage());
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
    public static <Objective> void StartingCardDeck(){
        File file = new File("src/main/java/model.decktameplate/StartingCardDeck.json");
        // try to create a scanner only if the file exists
        try {
            Scanner scanner = new Scanner(file);
            int IDcont = 0;
            while (scanner.hasNext()) {
                Suit suit = AssignSuit(scanner.next());
                Corner upright = AssignCorner(scanner.next());
                Corner upleft = AssignCorner(scanner.next());
                Corner downright = AssignCorner(scanner.next());
                Corner downleft = AssignCorner(scanner.next());
                Face front = new Face(upright,upleft,downright,downleft);
                Face back = new Face(InitialBackAssignCorner("plant"),InitialBackAssignCorner("fungi"),InitialBackAssignCorner("animal"), InitialBackAssignCorner("insect"));
                Objective objective = AssignObjective(scanner.next());
                Card tmp = new CardStarting(IDcont,front,back,suit);
                IDcont++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("file not found " + e.getMessage());
        }
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
