package model.decktameplate;

import model.cards.Card;
import model.cards.CardResource;
import model.cards.face.Face;
import model.cards.face.Corner;
import model.cards.face.CornerGold;
import model.cards.face.CornerResource;
import model.enums.Suit;

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
            Integer IDcont = 0;
            while (scanner.hasNext()) {
                Suit suit = AssignSuit(scanner.next());
                Corner upright = AssignCorner(scanner.next());
                Corner upleft = AssignCorner(scanner.next());
                Corner downright = AssignCorner(scanner.next());
                Corner downleft = AssignCorner(scanner.next());
                Face front = new Face(upright,upleft,downright,downright);
                Face back = new Face(new Corner(),new Corner(),new Corner(), new Corner());
                int cost = Integer.parseInt(scanner.next());
                Card tmp = new CardResource(IDcont,front,back,suit,cost);
                IDcont = IDcont ++ ;

            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("file not found " + e.getMessage());

        }
    }
    private static Corner AssignCorner(String s){
        switch (s){
            case "null":
                return null;
            case "empty":
                return new Corner();
            case "fungi":
                return new CornerResource(model.enums.Suit.FUNGI);
            case "plant":
                return new CornerResource(model.enums.Suit.PLANT);
            case "animal":
                return new CornerResource(model.enums.Suit.ANIMAL);
            case "insect":
                return new CornerResource(model.enums.Suit.INSECT);
            case "manuscript":
                return new CornerGold(model.enums.GoldSuit.MANUSCRIPT);
            case "inkwell":
                return new CornerGold(model.enums.GoldSuit.INKWELL);
            case "quill":
                return new CornerGold(model.enums.GoldSuit.QUILL);
        }
        return null;
    }
    private static Suit AssignSuit(String s){
        switch (s){
            case "fungi":
                return model.enums.Suit.FUNGI;
            case "plant":
                return model.enums.Suit.PLANT;
            case "animal":
                return model.enums.Suit.ANIMAL;
            case "insect":
                return model.enums.Suit.INSECT;
        }
        return null;
    }

    public static void main(String[] args) {
        DeckResource();
    }


}
