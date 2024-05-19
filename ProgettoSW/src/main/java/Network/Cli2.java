package Network;

import Network.Client.RmiClient;
import Network.Server.VirtualServer;
import View.BoardMatrix;
import View.HandMatrix;
import View.StationMatrix;
import View.UI;
import model.PlayingBoard;
import model.cards.*;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.ObjectiveCountingGold;
import model.objectives.ObjectiveCountingResource;
import model.objectives.ObjectiveDiagonal;
import model.objectives.ObjectivePositioning;
import model.objectives.*;
import model.enums.Suit;
import model.objectives.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static View.CardMatrixCreator.*;

public class Cli2 implements UI {

    private final VirtualServer server;
    private final RmiClient client;
    private final String black;
    private final String red;
    private final String green;
    private final String yellow;
    private final String reset;
    private final String purple;
    private final String blue;
    private final String beige;
    private final String lightBlue;
    private final String gold;
    private final String quill;
    private final String manuscript;
    private final String inkwell;


    public Cli2() {
        blue = "\033[0;34m";
        green = "\033[0;32m";
        yellow = "\033[0;33m";
        reset = "\033[0m";
        purple = "\033[0;35m";
        red = "\033[0;31m";
        beige = "\u001B[38;2;245;245;220m";
        lightBlue = "\u001B[38;5;39m";
        gold = "\u001B[38;2;255;215;0m";
        inkwell = gold + "W";
        manuscript = gold + "M";
        quill = gold + "Q";
    }


    private String cornerScanner(SuitEnum suit) {
        return switch (suit) {
            case ANIMAL -> lightBlue + "A" + reset;
            case INSECT -> purple + "I" + reset;
            case PLANT -> green + "P" + reset;
            case FUNGI -> red + "F" + reset;
            case QUILL -> gold + "Q" + reset;
            case INKWELL -> gold + "W" + reset;
            case MANUSCRIPT -> gold + "M" + reset;
            case EMPTY -> "E";
            default -> "N";
        };
    }


    @Override
    public void showStartingCard(CardStarting cardStarting) {
        printCard(cardStarting);
    }
   @Override
    public void showGameTitle(){

        System.out.println("" + red +
                "oooooooo8                  oooo                              oooo   oooo            o8                                     o888\n" +
                "o888        ooooooo     ooooo888   ooooooooo8 oooo   oooo       8888o  88   ooooooo o888oo oooo  oooo  oo oooooo   ooooooo    888  oooo   oooooooo8\n" +
                "888        888   888  888    888  888oooooo8    888o888         88 888o88   ooooo888 888    888   888   888        ooooo888   888   888  888ooooooo\n" +
                "888o       888   888  888    888  888           o88 88o         88   8888 888    888 888    888   888   888      888    888   888   888          888\n" +
                "888oooo88   88ooo88     88ooo888o  88oooo888 o88o   o88o      o88o    88  88ooo88 8o 888o   888o88 8o o888o      88ooo88 8o o888o o888o 88oooooo88\n\n" + reset);

    }

    @Override
    public String askNickname() {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        String input;
        System.out.println("Insert your nickname: ");
        input = in.nextLine();
        return input;
    }

    @Override
    public int askPlayerNumber() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        Integer input;
        do {
            System.out.println("Insert number of players in your Lobby: ");
            input = scanner.nextInt();
        }while (input < 2 || input > 4);
        return input;
    }

    @Override
    public void printErrorMessage(Exception e) {
        System.out.println("Error: " + e.getMessage());

    }


    @Override
    public Integer askStartingCardFront() {
        System.out.println("Select the front of your starting card: ");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do {
            System.out.println("1 for front 1, 2 for back 2");
            choice = in.nextInt();
        } while (choice != 1 && choice != 2);
        return choice;
    }

    @Override
    public void showObjectiveCards(CardObjective[] cardObjective) {
        System.out.println();
        printCard(cardObjective[0]);
        System.out.println();
        printCard(cardObjective[1]);
    }

    @Override
    public Integer askObjectiveCard() {
        System.out.println("Select the Objective Card you want to keep:");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do {
            System.out.println("1 for first, 2 for second");
            choice = in.nextInt();
        } while (choice != 1 && choice != 2);
        return choice;
    }

    @Override
    public void showUpdatedBoard(PlayingBoard playingBoard) {
        BoardMatrix board = new BoardMatrix();
        System.out.println("Common objectives: ");
        board.printCommonObjectives(playingBoard.getFirstObjective(), playingBoard.getSecondObjective());
        System.out.println("Central cards are: ");
        for(int i = 0; i < playingBoard.getCentralCards().length; i++) { //fare in modo che non stampi il back.
            board.printCentralCard(playingBoard.getCentralCards()[i], i);
        }
        board.printCentral();
    }

    @Override
    public void showUpdatedStation(Map<ArrayList<Integer>, CardPlaying> playingStation) {
        int x, y, maxX, maxY, distanceX, distanceY, max;
        maxX = 0;
        maxY = 0;
        max = 0;
        System.out.println("Station ");//name);
        CardPlaying[][] station = new CardPlaying[80][80];
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : playingStation.entrySet()) {
            ArrayList<Integer> coordinates = entry.getKey();
            CardPlaying card = entry.getValue();
            x = coordinates.get(0);
            y = coordinates.get(1);
            station[x][y] = card;
            distanceX = Math.abs(x - 40);
            distanceY = Math.abs(y - 40);
            if(distanceX > maxX || distanceY > maxY){
                maxX = distanceX;
                maxY = distanceY;
                max = Math.max(distanceX, distanceY);
            }
        }
        //ho popolato le carte della station, la passo come argomento alla boardmatrix
        StationMatrix stationMatrix = new StationMatrix();
        stationMatrix.addCardsToStation(station, max);
        stationMatrix.printStation(max);
    }

    @Override
    public void showUpdatedHand(ArrayList<CardPlaying> hand) { //TODO: cambaire cardPlaying in resource
        System.out.println("Here is your hand:");
        HandMatrix playerHand = new HandMatrix();
        playerHand.addCardsToHand(hand);
        playerHand.printHandMatrix();
    }

    @Override
    public void showMyUpdatedBoard(Map<ArrayList<Integer>, CardPlaying> playingStation, String clientNickname) {
        System.out.println("\n Updated Station of " + clientNickname + "\n");
    }

    @Override
    public Integer[] askCoordinatesOfCards() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Which card do you want to place? Insert the number of the card (1-3)");
        Integer cardChoice = scanner.nextInt();
        System.out.println("front (1) or back (2)");
        Integer cardSide = scanner.nextInt();
        System.out.println("Choose x coordinates");
        Integer x = scanner.nextInt();
        System.out.println("Choose y coordinates");
        Integer y = scanner.nextInt();
        Integer[] Choice = {cardChoice, cardSide, x, y};
        return Choice;
    }

    @Override
    public Integer askDrawingCard() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Which card do you want to draw? Insert 1 for up left card, 2 for up right card, 3 for down left card, 4 for down right card, 5 for resource Deck, 6 for gold Deck");
        Integer choice = scanner.nextInt();
        return choice;
    }

//    private void printCard(Card card) {
//        if (card instanceof CardStarting) {
//            System.out.println("This is the front of your starting card: \n");
//            printStartingFront(card);
//            System.out.println("\nThis is the back of your starting card: \n");
//            printStartingBack(card);
//        } else if (card instanceof CardResource) { //tutti hanno il back uguale! un metodo unico
//            if (card instanceof CardGold) {
//                //Stampo la carta oro. Deve mostrare i punti e il costo. colore variabile
//                //per differenziare metto un quadrato oro nel centro
//                System.out.println("This is the front of your gold card: \n");
//                printGoldFront(card);
//                System.out.println("This is the back of your gold card: \n");
//                printResBack(card);
//            } else{
//                //Stampo la carta risorsa. può avere punti e non ha costo. colore variabile
//                System.out.println("This is the front of your resource card: \n");
//                printResourceFront(card);
//                System.out.println("This is the back of your resource card: \n");
//                printResBack(card);
//            }
//        } else if (card instanceof CardObjective) {
//            System.out.println("This is the your objective card: \n");
//            if(((CardObjective) card).getObjective() instanceof ObjectivePositioning){
//                System.out.println("Your type of objective is: positioning");
//                if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == Suit.FUNGI){
//                    printFungiPositioning();
//                } else if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == Suit.PLANT) {
//                    printPlantPositioning();
//                } else if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == Suit.ANIMAL){
//                    printAnimalPositioning();
//                } else if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == Suit.INSECT){
//                    printInsectPositioning();
//                }
//            } else if (((CardObjective) card).getObjective() instanceof ObjectiveCountingGold) {
//                System.out.println("Your type of objective is: counting gold");
//                int countInkwell = ((ObjectiveCountingGold) ((CardObjective) card).getObjective()).getCountInkwell();
//                int countManuscript = ((ObjectiveCountingGold) ((CardObjective) card).getObjective()).getCountManuscript();
//                int countQuill = ((ObjectiveCountingGold) ((CardObjective) card).getObjective()).getCountQuill();
//                if((countInkwell == countManuscript)&&(countInkwell == countQuill)&&(countInkwell == 1)){
//                    printCountAll();
//                }
//                else if((countQuill != 0)&&(countInkwell == 0)&&(countManuscript==0)){
//                    printCountGold(Suit.QUILL);
//                }
//                else if ((countQuill == 0)&&(countInkwell != 0)&&(countManuscript==0)) {
//                    printCountGold(Suit.INKWELL);
//                }
//                else if ((countQuill == 0)&&(countInkwell == 0)&&(countManuscript!=0)){
//                    printCountGold(Suit.MANUSCRIPT);
//                }
//            } else if (((CardObjective) card).getObjective() instanceof ObjectiveCountingResource) {
//                System.out.println("Your type of objective is: counting resourses");
//                Suit suit = ((ObjectiveCountingResource) ((CardObjective) card).getObjective()).getSymbol();
//                printCountRes(suit);
//            } else if (((CardObjective) card).getObjective() instanceof ObjectiveDiagonal) {
//                System.out.println("Your type of objective is: ");
//                Suit suit = ((ObjectiveDiagonal) ((CardObjective) card).getObjective()).getColor();
//                printDiagonal(suit);
//            }
//        }
//    }
    @Override
    public TokenEnum askToken(ArrayList<TokenEnum> availableTokens) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Use number to select one of the available tokens: " + availableTokens);
        while (true) {
            Integer choice = scanner.nextInt();
            if (choice > 0 && choice <= availableTokens.size()) {
                return availableTokens.get(choice - 1);
            }
        }
    }

    @Override
    public void waitingForOtherPlayers() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void printWaitingForPlayerToSetPlayerNumber() {
        System.out.println("Waiting for other player to set the number of players in the lobby");

    }

    @Override
    public void printAvailableTokens(ArrayList<TokenEnum> availableTokens) {
        Scanner input = new Scanner(System.in);
        System.out.println("Available tokens are: " + availableTokens);
        System.out.println("Choose one of the available tokens");


    }

    private void printMatrix(String[][] mat){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }

    private void printCard(Card card){
    }

    private void printCard(CardStarting cardStarting){
        System.out.println("This is the front of your starting card: \n");
        printStartingFront(cardStarting);
        System.out.println("\nThis is the back of your starting card: \n");
        printStartingBack(cardStarting);
    }

    private void printCard(CardResource cardRes){
        System.out.println("This is the front of your resource card: \n");
        printResourceFront(cardRes);
        System.out.println("This is the back of your resource card: \n");
        printResBack(cardRes);
    }

    private void printCard(CardGold cardGold){
        System.out.println("This is the front of your gold card: \n");
        printGoldFront(cardGold);
        System.out.println("This is the back of your gold card: \n");
        printResBack(cardGold);
    }

    private void printCard(CardObjective cardObjective){
        Objective obj = cardObjective.getObjective();
        //String[][] objective = createObjective(cardObjective.getObjective());
//        printMatrix(createObjective(cardObjective.getObjective()));
        if(obj instanceof ObjectiveDiagonal){
            printMatrix(createObjective((ObjectiveDiagonal) obj));
        } else if (obj instanceof ObjectivePositioning){
            printMatrix(createObjective((ObjectivePositioning) obj));
        } else if (obj instanceof ObjectiveCountingGold) {
            printMatrix(createObjective((ObjectiveCountingGold) obj));
        } else if (obj instanceof ObjectiveCountingResource) {
            printMatrix(createObjective((ObjectiveCountingResource) obj));
        }
    }

    private void printStartingFront(CardStarting card) {
        String[][] startingCard = createFrontPlayingCard(card);
        printMatrix(startingCard);
    }
    private void printStartingBack(CardStarting card) {
        //Stampo la carta iniziale. Deve avere il colore beige.
        String[][] startingCard = createBackPlayingCard(card);
        printMatrix(startingCard);
    }
    private void printGoldFront(CardGold card) {
        String[][] goldCard = createFrontPlayingCard(card);
        printMatrix(goldCard);
    }
    private void printResourceFront(CardResource card) {
        String[][] resCard = createFrontPlayingCard(card);
        hasPoints(card, resCard);
        printMatrix(resCard);
    }
    private void printResBack(CardResource card) {
        String[][] resCard = createBackPlayingCard(card);
        printMatrix(resCard);
    }
}
