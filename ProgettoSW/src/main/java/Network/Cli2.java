package Network;

import Network.Client.RmiClient;
import Network.Server.VirtualServer;
import View.UI;
import model.PlayingBoard;
import model.cards.*;
import model.enums.SuitEnum;
import model.objectives.ObjectiveCountingGold;
import model.objectives.ObjectiveCountingResource;
import model.objectives.ObjectiveDiagonal;
import model.objectives.ObjectivePositioning;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
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
    private final String beige;
    private final String lightBlue;
    private final String gold;
    private final String quill;
    private final String manuscript;
    private final String inkwell;


    public Cli2(VirtualServer server, RmiClient client) {
        this.server = server;
        this.client = client;
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
            /*System.out.println(
                    cornerScanner(cardStarting.getFront().getUpLeft())
                            + "  " + cornerScanner(cardStarting.getFront().getUpRight())
                            + "\n" + cornerScanner(cardStarting.getFront().getDownLeft())
                            + "  " + cornerScanner(cardStarting.getFront().getDownRight()));*/
        //System.out.println("Back:");
            /*System.out.println(
                    cornerScanner(cardStarting.getBack().getUpLeft())
                            + "  " + cornerScanner(cardStarting.getBack().getUpRight())
                            + "\n" + cornerScanner(cardStarting.getBack().getDownLeft())
                            + "  " + cornerScanner(cardStarting.getBack().getDownRight()));
        System.out.println("Symbols in the front middle:"); //non so quanto utile in quando si vede nella carta.
        for (var c : cardStarting.getSymbols()) {
            System.out.print(" " + cornerScanner(c));
        }*/

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
    public Integer askPlayerNumber() {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer input;
        do {
            System.out.println("Insert number of players in your Lobby: ");
            input = in.nextInt();
        } while (input < 2 || input > 4);
        return input;
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
        //System.out.println("Your first Objective Card is:");
        printCard(cardObjective[0]);
            /*switch (cardObjective[0].getObjective()) {
                case ObjectiveDiagonal objectiveDiagonal -> diagonalObjectivePrinter(objectiveDiagonal);
                case ObjectiveCountingGold objectiveCountingGold -> countingGoldPrinter(objectiveCountingGold);
                case ObjectivePositioning objectivePositioning -> positioningObjectivePrinter(objectivePositioning);
                case null, default ->
                        countingResourcePrinter((ObjectiveCountingResource) cardObjective[0].getObjective());
            }*/
        //System.out.println("\nYour second Objective Card is:");
        printCard(cardObjective[1]);
            /*switch (cardObjective[1].getObjective()) {
                case ObjectiveDiagonal objectiveDiagonal -> diagonalObjectivePrinter(objectiveDiagonal);
                case ObjectiveCountingGold objectiveCountingGold -> countingGoldPrinter(objectiveCountingGold);
                case ObjectivePositioning objectivePositioning -> positioningObjectivePrinter(objectivePositioning);
                case null, default ->
                        countingResourcePrinter((ObjectiveCountingResource) cardObjective[1].getObjective());
            }*/
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
    public void showUpdatedBoard(PlayingBoard board) {
        System.out.println("Board");
    }

    @Override
    public void showUpdatedStation(Map<ArrayList<Integer>, CardPlaying> playingStation) {
        System.out.println("Station:");
    }

    @Override
    public void showUpdatedHand(ArrayList<CardPlaying> hand) {
        System.out.println("Here is your hand:");
        for(CardPlaying card : hand){
            printCard(card);
        }
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

    private void printMatrix(String[][] mat){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }

    private void printCard(Card card) {
        if (card instanceof CardStarting) {
            System.out.println("This is the front of your starting card: \n");
            printStartingFront(card);
            System.out.println("\nThis is the back of your starting card: \n");
            printStartingBack(card);
        } else if (card instanceof CardResource) { //tutti hanno il back uguale! un metodo unico
            if (card instanceof CardGold) {
                //Stampo la carta oro. Deve mostrare i punti e il costo. colore variabile
                //per differenziare metto un quadrato oro nel centro
                System.out.println("This is the front of your gold card: \n");
                printGoldFront(card);
                System.out.println("This is the back of your gold card: \n");
                printResBack(card);
            } else{
                //Stampo la carta risorsa. può avere punti e non ha costo. colore variabile
                System.out.println("This is the front of your resource card: \n");
                printResourceFront(card);
                System.out.println("This is the back of your resource card: \n");
                printResBack(card);
            }
        } else if (card instanceof CardObjective) {
            System.out.println("This is the your objective card: \n");
            if(((CardObjective) card).getObjective() instanceof ObjectivePositioning){
                System.out.println("Your type of objective is: positioning");
                if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == SuitEnum.FUNGI){
                    printFungiPositioning();
                } else if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == SuitEnum.PLANT) {
                    printPlantPositioning();
                } else if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == SuitEnum.ANIMAL){
                    printAnimalPositioning();
                } else if(((ObjectivePositioning) ((CardObjective) card).getObjective()).getColorTwoCards() == SuitEnum.INSECT){
                    printInsectPositioning();
                }
            } else if (((CardObjective) card).getObjective() instanceof ObjectiveCountingGold) {
                System.out.println("Your type of objective is: counting gold");
                int countInkwell = ((ObjectiveCountingGold) ((CardObjective) card).getObjective()).getCountInkwell();
                int countManuscript = ((ObjectiveCountingGold) ((CardObjective) card).getObjective()).getCountManuscript();
                int countQuill = ((ObjectiveCountingGold) ((CardObjective) card).getObjective()).getCountQuill();
                if((countInkwell == countManuscript)&&(countInkwell == countQuill)&&(countInkwell == 1)){
                    printCountAll();
                }
                else if((countQuill != 0)&&(countInkwell == 0)&&(countManuscript==0)){
                    printCountGold(SuitEnum.QUILL);
                }
                else if ((countQuill == 0)&&(countInkwell != 0)&&(countManuscript==0)) {
                    printCountGold(SuitEnum.INKWELL);
                }
                else if ((countQuill == 0)&&(countInkwell == 0)&&(countManuscript!=0)){
                    printCountGold(SuitEnum.MANUSCRIPT);
                }
            } else if (((CardObjective) card).getObjective() instanceof ObjectiveCountingResource) {
                System.out.println("Your type of objective is: counting resourses");
                SuitEnum suit = ((ObjectiveCountingResource) ((CardObjective) card).getObjective()).getSymbol();
                printCountRes(suit);
            } else if (((CardObjective) card).getObjective() instanceof ObjectiveDiagonal) {
                System.out.println("Your type of objective is: ");
                SuitEnum suit = ((ObjectiveDiagonal) ((CardObjective) card).getObjective()).getColor();
                printDiagonal(suit);
            }
        }
    }
    private void printStartingFront(Card card) {
        //Stampo la carta iniziale. Deve avere il colore beige. Il front ha le risorse in mezzo!
        String[][] startingCard = new String[3][11];
        int rows = 3;
        int cols = 11;
        //setto i corner
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getFront().getUpLeft());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getFront().getDownLeft());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getFront().getUpRight());
                    startingCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getFront().getDownRight()) + reset;
                    startingCard[i][j-1] = " ";
                } else {
                    startingCard[i][j] = beige + "█" + "\u001B[0m";
                }
            }
        }
        //setto le risorse in mezzo alla carta.
        int numOfCentralResources = ((CardStarting) card).getSymbols().size();
        //startingCard [1][0] = "";
//        startingCard [1][10] = "";
        if(numOfCentralResources == 1){
            startingCard[1][5] = cornerScanner(((CardStarting) card).getSymbols().getFirst());
        } else if (numOfCentralResources == 2) {
            startingCard[0][5] = cornerScanner(((CardStarting) card).getSymbols().getFirst());
            startingCard[1][5] = cornerScanner(((CardStarting) card).getSymbols().get(1));
        } else if (numOfCentralResources == 3) {
            //startingCard[1][0] = " ";
            startingCard[0][5] = cornerScanner(((CardStarting) card).getSymbols().getFirst());
            startingCard[1][6] = cornerScanner(((CardStarting) card).getSymbols().get(1)) + beige + "█";
            startingCard[2][5] = cornerScanner(((CardStarting) card).getSymbols().get(2));
        }
        printMatrix(startingCard);
    }
    private void printStartingBack(Card card) {
        //Stampo la carta iniziale. Deve avere il colore beige.
        String[][] startingCard = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                startingCard[i][j] = beige + "█";
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getBack().getUpLeft());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getBack().getDownLeft());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getBack().getUpRight());
                    startingCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(((CardStarting) card).getBack().getDownRight()) + reset;
                    startingCard[i][j-1] = " ";
                } else {
                    startingCard[i][j] = beige + "█" + "\u001B[0m";
                }
            }
        }
        printMatrix(startingCard);
    }
    private void printGoldFront(Card card) {
        String[][] goldCard = createFrontResCard(card);
        goldCard = hasPoints(card, goldCard);
        goldCard = hasCost(card, goldCard);
        printMatrix(goldCard);
    }
    private void printResourceFront(Card card) {
        String[][] resCard = createFrontResCard(card);
        resCard = hasPoints(card, resCard);
        printMatrix(resCard);
    }
    private void printResBack(Card card) {
        String[][] resCard = createBackResCard(card);
        printMatrix(resCard);
    }
    private String[][] createFrontResCard(Card card) {
        String[][] resCard = new String[3][11];
        int rows = 3;
        int cols = 11;
        String color = colorResource((CardResource) card);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                resCard[i][j] = color + "█";
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getFront().getUpLeft());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getFront().getDownLeft());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getFront().getUpRight());
                    resCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getFront().getDownRight()) + reset;
                    resCard[i][j-1] = " ";
                } else {
                    resCard[i][j] = color + "█" + "\u001B[0m";
                }
            }
        }
        return resCard;
    }
    //metodo per avere il back delle carte oro e risorsa.
    private String[][] createBackResCard(Card card) {
        String[][] resCard = new String[3][11];
        int rows = 3;
        int cols = 11;
        String color = colorResource((CardResource) card);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                resCard[i][j] = color + "█";
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getBack().getUpLeft());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getBack().getDownLeft());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getBack().getUpRight());
                    resCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(((CardResource) card).getBack().getDownRight()) + reset;
                    resCard[i][j-1] = " ";
                } else {
                    resCard[i][j] = color + "█" + "\u001B[0m";
                }
            }
        }
        return resCard;
    }
    private boolean isUpLeftCorner(int row, int col, int rows, int cols) {
        return (row == 0 && col == 0);
    }
    private boolean isDownLeftCorner(int row, int col, int rows, int cols) {
        return (row == rows - 1 && col == 0);
    }
    private boolean isUpRightCorner(int row, int col, int rows, int cols) {
        return (row == 0 && col == cols - 1);
    }
    private boolean isDownRightCorner(int row, int col, int rows, int cols) {
        return (row == rows - 1 && col == cols-1);
    }
    private String[][] hasCost(Card card, String[][] mat) {
        String color = colorResource((CardResource) card);
        int costFungi = ((CardGold) card).getCostFungi();
        int costAnimal = ((CardGold) card).getCostAnimal();
        int costPlant = ((CardGold) card).getCostPlant();
        int costInsect = ((CardGold) card).getCostInsect();
        ArrayList<SuitEnum> costList = new ArrayList<SuitEnum>();
        while(costFungi > 0){
            costList.add(SuitEnum.FUNGI);
            costFungi--;
        }
        while(costAnimal > 0){
            costList.add(SuitEnum.ANIMAL);
            costAnimal--;
        }
        while(costPlant > 0){
            costList.add(SuitEnum.PLANT);
            costPlant--;
        }
        while(costInsect > 0){
            costList.add(SuitEnum.INSECT);
            costInsect--;
        }
        if(costList.size() == 3){
            int i = 0;
            for(int j = 4; j < 7; j++){
                mat[2][j] = cornerScanner(costList.get(i));
                i++;
            }
        }
        if(costList.size() == 4){
            int i = 0;
            for(int j = 4; j < 8; j++){
                mat[2][j] = cornerScanner(costList.get(i));
                i++;
            }
        }
        if(costList.size() == 5){
            int i = 0;
            for(int j = 3; j < 8; j++){
                mat[2][j] = cornerScanner(costList.get(i));
                i++;
            }
        }
        return mat;
    }
    private String[][] hasPoints(Card card, String[][] mat) {
        int points = ((CardResource) card).getPoints();
        if(points != 0) {
            mat[0][5] = Integer.toString(points);
            if(card instanceof CardGold && ((CardGold) card).getObjective() instanceof ObjectivePositioning){
                mat[0][6] = "P";
            } else {
                mat[0][6] = "p";
            }
        }
        return mat;
    }
    private void printFungiPositioning(){
        String[][] fungiPos = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                fungiPos[i][j] = beige + "█" + reset;
            }
        }
        fungiPos[0][5] = "2";
        fungiPos[0][3] = red + "█" + reset;
        fungiPos[0][4] = red + "█" + reset;
        fungiPos[1][3] = red + "█" + reset;
        fungiPos[1][4] = red + "█" + reset;
        fungiPos[2][4] = green + "█" + reset;
        fungiPos[2][5] = green + "█" + reset;
        printMatrix(fungiPos);
    }
    private void printPlantPositioning(){
        String[][] plantPos = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                plantPos[i][j] = beige + "█" + reset;
            }
        }
        plantPos[0][5] = "2";
        plantPos[0][6] = green + "█" + reset;
        plantPos[0][7] = green + "█" + reset;
        plantPos[1][6] = green + "█" + reset;
        plantPos[1][7] = green + "█" + reset;
        plantPos[2][4] = purple + "█" + reset;
        plantPos[2][5] = purple + "█" + reset;
        printMatrix(plantPos);
    }
    private void printAnimalPositioning(){
        String[][] animalPos = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                animalPos[i][j] = beige + "█" + reset;
            }
        }
        animalPos[2][5] = "2";
        animalPos[2][3] = lightBlue + "█" + reset;
        animalPos[2][4] = lightBlue + "█" + reset;
        animalPos[1][3] = lightBlue + "█" + reset;
        animalPos[1][4] = lightBlue + "█" + reset;
        animalPos[0][5] = red + "█" + reset;
        animalPos[0][6] = red + "█" + reset;
        printMatrix(animalPos);
    }
    private void printInsectPositioning(){
        String[][] insPos = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                insPos[i][j] = beige + "█" + reset;
            }
        }
        insPos[2][5] = "2";
        insPos[2][6] = purple + "█" + reset;
        insPos[2][7] = purple + "█" + reset;
        insPos[1][6] = purple + "█" + reset;
        insPos[1][7] = purple + "█" + reset;
        insPos[0][4] = lightBlue + "█" + reset;
        insPos[0][5] = lightBlue + "█" + reset;
        printMatrix(insPos);
    }
    private void printCountGold(SuitEnum suit){
        String[][] countGold = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                countGold[i][j] = beige + "█" + reset;
            }
        }
        countGold[0][5] = "2";
        countGold[1][6] = cornerScanner(suit);
        countGold[1][7] = cornerScanner(suit);
        printMatrix(countGold);
    }
    private void printCountAll(){
        String[][] countGold = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                countGold[i][j] = beige + "█" + reset;
            }
        }
        countGold[0][5] = "3";
        countGold[1][6] = quill;
        countGold[1][7] = inkwell;
        countGold[1][8] = manuscript;
        printMatrix(countGold);
    }
    private void printCountRes(SuitEnum suitEnum){
        String[][] countRes = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                countRes[i][j] = beige + "█" + reset;
            }
        }
        countRes[0][5] = "2";
        countRes[1][5] = cornerScanner(suitEnum); //rivedere ciclo for così non va bene devo farnre due
        countRes[2][4] = cornerScanner(suitEnum);
        countRes[2][6] = cornerScanner(suitEnum);
        printMatrix(countRes);
    }
    private void printDiagonal(SuitEnum suitEnum){
        String[][] diag = new String[3][11];
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                diag[i][j] = beige + "█" + reset;
            }
        }
        diag[0][5] = "2";
        if(suitEnum == SuitEnum.FUNGI || suitEnum == SuitEnum.ANIMAL){
            if(suitEnum == SuitEnum.FUNGI){
                diag[2][3] = red + "█";
                diag[2][4] = red + "█";
                diag[1][5] = red + "█";
                diag[1][6] = red + "█";
                diag[0][7] = red + "█";
                diag[0][8] = red + "█" + reset;
            } else {
                diag[2][3] = lightBlue + "█";
                diag[2][4] = lightBlue + "█";
                diag[1][5] = lightBlue + "█";
                diag[1][6] = lightBlue + "█";
                diag[0][7] = lightBlue + "█";
                diag[0][8] = lightBlue + "█" + reset;
            }
        } else if (suitEnum == SuitEnum.PLANT || suitEnum == SuitEnum.INSECT) {
            if(suitEnum == SuitEnum.PLANT){
                diag[0][3] = green + "█";
                diag[0][4] = green + "█";
                diag[1][5] = green + "█";
                diag[1][6] = green + "█";
                diag[2][7] = green + "█";
                diag[2][8] = green + "█" + reset;
            } else {
                diag[0][3] = purple + "█";
                diag[0][4] = purple + "█";
                diag[1][5] = purple + "█";
                diag[1][6] = purple + "█";
                diag[2][7] = purple + "█";
                diag[2][8] = purple + "█" + reset;
            }
        }
        printMatrix(diag);
    }
    private String colorResource(CardResource card) {
        switch (card.getSymbol()) {
            case INSECT -> {
                return purple;
            }
            case ANIMAL -> {
                return lightBlue;
            }
            case FUNGI -> {
                return red;
            }
            case PLANT -> {
                return green;
            }
            default -> {
                return "_";
            }
        }
    }
}
