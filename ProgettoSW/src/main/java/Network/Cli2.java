package Network;

import Network.Client.RmiClient;
import Network.Server.VirtualServer;
import View.UI;
import model.cards.CardObjective;
import model.cards.CardStarting;
import model.enums.Direction;
import model.enums.Suit;
import model.objectives.ObjectiveCountingGold;
import model.objectives.ObjectiveCountingResource;
import model.objectives.ObjectiveDiagonal;
import model.objectives.ObjectivePositioning;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Cli2 implements UI {

    private final VirtualServer server;
    private final RmiClient client;
    private final String red;
    private final String green;
    private final String yellow;
    private final String reset;
    private final String purple;
    private final String blue;


    public Cli2(VirtualServer server, RmiClient client) {
        this.server = server;
        this.client = client;
        blue = "\033[0;34m";
        green = "\033[0;32m";
        yellow = "\033[0;33m";
        reset = "\033[0m";
        purple = "\033[0;35m";
        red = "\033[0;31m";

    }


    private String cornerScanner(Suit suit){
        return switch (suit) {
            case ANIMAL -> blue + "A" + reset;
            case INSECT -> purple + "I" + reset;
            case PLANT -> green + "P" + reset;
            case FUNGI -> yellow + "F" + reset;
            case EMPTY -> "E";
            default -> "N";
        };
    }


    @Override
    public void showStartingCard(CardStarting cardStarting) {
            System.out.println("Your starting card is:");
            System.out.println("Front:");
            System.out.println(
                    cornerScanner(cardStarting.getFront().getUpLeft())
                            + "  " + cornerScanner(cardStarting.getFront().getUpRight())
                            + "\n" + cornerScanner(cardStarting.getFront().getDownLeft())
                            + "  " + cornerScanner(cardStarting.getFront().getDownRight()));
            System.out.println("Back:");
            System.out.println(
                    cornerScanner(cardStarting.getBack().getUpLeft())
                            + "  " + cornerScanner(cardStarting.getBack().getUpRight())
                            + "\n" + cornerScanner(cardStarting.getBack().getDownLeft())
                            + "  " + cornerScanner(cardStarting.getBack().getDownRight()));
            System.out.println("Symbols in the front middle:");
            for (var c : cardStarting.getSymbols()) {
                System.out.print(" " + cornerScanner(c));
            }

    }

    @Override
    public String askNickname() {

        System.out.println(""+ red +
                "oooooooo8                  oooo                              oooo   oooo            o8                                     o888\n"+
                "o888        ooooooo     ooooo888   ooooooooo8 oooo   oooo       8888o  88   ooooooo o888oo oooo  oooo  oo oooooo   ooooooo    888  oooo   oooooooo8\n"+
                "888        888   888  888    888  888oooooo8    888o888         88 888o88   ooooo888 888    888   888   888        ooooo888   888   888  888ooooooo\n"+
                "888o       888   888  888    888  888           o88 88o         88   8888 888    888 888    888   888   888      888    888   888   888          888\n"+
                "888oooo88   88ooo88     88ooo888o  88oooo888 o88o   o88o      o88o    88  88ooo88 8o 888o   888o88 8o o888o      88ooo88 8o o888o o888o 88oooooo88\n\n" + reset);

        Scanner in = new Scanner(new InputStreamReader(System.in));
        String input;
        System.out.println("Insert your nickname: ");
        input = in.nextLine();
        return input;
    }

    @Override
    public Integer askPlayerNumber() {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer input;
        do {
            System.out.println("Insert number of players in your Lobby: ");
            input = in.nextInt();
        }while(input < 2 || input > 4);
        return input;
    }

    @Override
    public Integer askStartingCardFront() {
        System.out.println("Select the front of your starting card: ");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do{
            System.out.println("1 for front 1, 2 for front 2");
            choice = in.nextInt();
        }while( choice != 1 && choice != 2);
        return choice;
    }

    @Override
    public void showObjectiveCards(CardObjective[] cardObjective) {
            System.out.println("Your first Objective Card is:");
            switch (cardObjective[0].getObjective()) {
                case ObjectiveDiagonal objectiveDiagonal -> diagonalObjectivePrinter(objectiveDiagonal);
                case ObjectiveCountingGold objectiveCountingGold -> countingGoldPrinter(objectiveCountingGold);
                case ObjectivePositioning objectivePositioning -> positioningObjectivePrinter(objectivePositioning);
                case null, default ->
                        countingResourcePrinter((ObjectiveCountingResource) cardObjective[0].getObjective());
            }
            System.out.println("\nYour second Objective Card is:");
            switch (cardObjective[1].getObjective()) {
                case ObjectiveDiagonal objectiveDiagonal -> diagonalObjectivePrinter(objectiveDiagonal);
                case ObjectiveCountingGold objectiveCountingGold -> countingGoldPrinter(objectiveCountingGold);
                case ObjectivePositioning objectivePositioning -> positioningObjectivePrinter(objectivePositioning);
                case null, default ->
                        countingResourcePrinter((ObjectiveCountingResource) cardObjective[1].getObjective());
            }


    }

    @Override
    public Integer askObjectiveCard() {
        System.out.println("Select the Objective Card you want to keep:");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do{
            System.out.println("1 for first, 2 for second");
            choice = in.nextInt();
        }while( choice != 1 && choice != 2);
        return choice;
    }

    private void diagonalObjectivePrinter(ObjectiveDiagonal objectiveDiagonal){
        String color;
        switch(objectiveDiagonal.getColor()){
            case ANIMAL:
                color = blue + "*" + reset;
                break;
            case INSECT:
                color = purple + "*" + reset;
                break;
            case PLANT:
                color = green + "*" + reset;
                break;
            case FUNGI:
                color = yellow + "*" + reset;
                break;
            default:
                color = "N";
                break;
        }

        if(objectiveDiagonal.getDirection() == Direction.LEFT){
            System.out.println("  " + color + "  ");
            System.out.println(" " + color + " " + color + " ");
            System.out.println(color + " " + color + " " + color);
        } else {
            System.out.println(color + " " + color + " " + color);
            System.out.println(" " + color + " " + color + " ");
            System.out.println("  " + color + "  ");
        }
    }

    private void countingGoldPrinter(ObjectiveCountingGold objectiveCountingGold){
            String inkwell = "I";
            String manuscript = "M";
            String quill = "Q";

            int inkwellCount = objectiveCountingGold.getCountInkwell();
            int manuscriptCount = objectiveCountingGold.getCountManuscript();
            int quillCount = objectiveCountingGold.getCountQuill();

            String[][] grid = new String[3][3];

            // Initialize the grid with empty strings
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    grid[i][j] = " ";
                }
            }

            // Set the gold resources count at (0,0)
            grid[0][0] = String.valueOf(inkwellCount + manuscriptCount + quillCount);

            // Set the gold resources types at (0,2), (2,0) and (2,2) if they are different
            if (inkwellCount > 0) grid[0][2] = inkwell;
            if (manuscriptCount > 0) grid[2][0] = manuscript;
            if (quillCount > 0) grid[2][2] = quill;

            // Set the points at the center
            if(inkwellCount > 0 && manuscriptCount > 0 && quillCount > 0){
                grid[1][1] = "3";
            }
            else{
                grid[1][1] = "2";
            }

            // Print the grid
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(grid[i][j]);
                }
                System.out.println();
            }

    }

    private void positioningObjectivePrinter(ObjectivePositioning objectivePositioning){
        String colorOneCard;
        String colorTwoCards;
        colorOneCard = switch (objectivePositioning.getColorOneCard()) {
            case ANIMAL -> "\033[0;34m*\033[0m"; // Blu
            case INSECT -> "\033[0;35m*\033[0m"; // Viola
            case PLANT -> "\033[0;32m*\033[0m"; // Verde
            case FUNGI -> "\033[0;33m*\033[0m"; // Arancione
            default -> "*";
        };
        colorTwoCards = switch (objectivePositioning.getColorTwoCards()) {
            case ANIMAL -> "\033[0;34m*\033[0m"; // Blu
            case INSECT -> "\033[0;35m*\033[0m"; // Viola
            case PLANT -> "\033[0;32m*\033[0m"; // Verde
            case FUNGI -> "\033[0;33m*\033[0m"; // Arancione
            default -> "*";
        };
        String horizontalDirection = objectivePositioning.getHorizontalDirection().toString().substring(0, 1);
        String verticalDirection = objectivePositioning.getVerticalDirection().toString().substring(0, 1);

        String[][] grid = new String[3][3];

        // Initialize the grid with empty strings
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = " ";
            }
        }

        if (verticalDirection.equals("T")) {
            if (horizontalDirection.equals("L")) {
                grid[2][0] = colorOneCard;
                grid[0][1] = colorTwoCards;
                grid[1][1] = colorTwoCards;
                grid[2][1] = "3";
            } else {
                grid[2][2] = colorOneCard;
                grid[0][1] = colorTwoCards;
                grid[1][1] = colorTwoCards;
                grid[2][1] = "3";
            }
        }

        // Set the colorOneCard at (0,0) or (0,2) and colorTwoCards at (2,0) and (2,1) or (2,1) and (2,2) if verticalDirection is "bottom"
        if (verticalDirection.equals("B")) {
            if (horizontalDirection.equals("L")) {
                grid[0][0] = colorOneCard;
                grid[1][1] = colorTwoCards;
                grid[2][1] = colorTwoCards;
                grid[0][1] = "3";
            } else {
                grid[0][2] = colorOneCard;
                grid[1][1] = colorTwoCards;
                grid[2][1] = colorTwoCards;
                grid[0][1] = "3";
            }
        }

        // Print the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    private void countingResourcePrinter(ObjectiveCountingResource objectiveCountingResource){
        String suit;
        switch(objectiveCountingResource.getSymbol()){
            case ANIMAL:
                suit = "\033[0;34m*\033[0m"; // Blu
                break;
            case INSECT:
                suit = "\033[0;35m*\033[0m"; // Viola
                break;
            case PLANT:
                suit = "\033[0;32m*\033[0m"; // Verde
                break;
            case FUNGI:
                suit = "\033[0;33m*\033[0m"; // Arancione
                break;
            default:
                suit = "*";
                break;
        }

        String[][] grid = new String[3][3];

        // Initialize the grid with empty strings
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = " ";
            }
        }

        // Set the suit at (0,1), (2,0) and (2,2)
        grid[0][1] = suit;
        grid[2][0] = suit;
        grid[2][2] = suit;

        // Set the points at the center
        grid[1][1] = "2";

        // Print the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

}
