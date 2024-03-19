package decktameplate;

import cards.Card;
import cards.CardResource;
import cards.face.corner.Corner;
import cards.face.corner.CornerGold;
import cards.face.corner.CornerResource;
import enums.GoldSuit;
import enums.Suit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// create a static class that has for each deck a static method that returns the List of all cards that need to be in that deck
public class deckconstructor {
    // creating the DeckResource Deck
    // each line in the text represent the card:
    // we have in order: suit of the card, the four corner(starting from upright) and the points
    public static void DeckResource(){
        System.out.println(new File(".").getAbsolutePath());
        File file = new File("src/main/java/decktameplate/DeckResource.txt");
        // try to create a scanner only if the file exists
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String Suit = scanner.next();
                Corner upright = AssignCorner(scanner.next());
                Corner upleft = AssignCorner(scanner.next());
                Corner downright = AssignCorner(scanner.next());
                Corner downleft = AssignCorner(scanner.next());




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
                return new CornerGold(GoldSuit.MANUSCRIPT);
                break;
            case "inkwell":
                return new CornerGold(GoldSuit.INKWELL);
                break;
            case "quill":
                return new CornerGold(GoldSuit.QUILL);
                break;
        }
    }

    public static void main(String[] args) {
        DeckResource();
    }


}
