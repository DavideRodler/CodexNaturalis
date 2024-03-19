package decktameplate;

import cards.Card;
import cards.CardResource;
import cards.face.Face;
import cards.face.corner.Corner;
import cards.face.corner.CornerGold;
import cards.face.corner.CornerResource;
import enums.Suit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// create a static class that has for each deck a static method that returns the List of all cards that need to be in that deck
public class deckconstructor {
    // creating the DeckResource Deck
    // each line in the text represent the card:
    // we have in order: suit of the card, the four corner(starting from upright) and the points
    public static <Int> void DeckResource(){
        System.out.println(new File(".").getAbsolutePath());
        File file = new File("src/main/java/decktameplate/DeckResource.txt");
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
                break;
            case "empty":
                return new Corner();
            case "fungi":
                return new CornerResource(enums.Suit.FUNGI);
                break;
            case "plant":
                return new CornerResource(enums.Suit.PLANT);
                break;
            case "animal":
                return new CornerResource(enums.Suit.ANIMAL);
                break;
            case "insect":
                return new CornerResource(enums.Suit.INSECT);
                break;
            case "manuscript":
                return new CornerGold(enums.GoldSuit.MANUSCRIPT);
                break;
            case "inkwell":
                return new CornerGold(enums.GoldSuit.INKWELL);
                break;
            case "quill":
                return new CornerGold(enums.GoldSuit.QUILL);
                break;
        }
    }
    private static Suit AssignSuit(String s){
        switch (s){
            case "fungi":
                return enums.Suit.FUNGI;
                break;
            case "plant":
                return enums.Suit.PLANT;
                break;
            case "animal":
                return enums.Suit.ANIMAL;
                break;
            case "insect":
                return enums.Suit.INSECT;
                break;
        }
    }

    public static void main(String[] args) {
        DeckResource();
    }


}
