package View;

import Network.Client.RmiClient;
import Network.Client.VirtualView;
import Network.Server.VirtualServer;
import model.cards.*;
import model.cards.CardStarting;

import model.cards.face.Face;
import model.enums.Suit;
import model.objectives.ObjectiveAssign;
import model.objectives.ObjectivePositioning;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Cli2 implements UI {

    private final RmiClient client;
    private static String red = "\033[0;31m";
    private static String green = "\033[0;32m";
    private static String yellow = "\033[0;33m";;
    private static String reset = "\033[0m";
    private static String purple = "\033[0;35m";
    private static String blue = "\033[0;34m";
    private static String black = "\u001B[30m";
    private static String lightBlue = "\u001B[38;5;39m";
    private static String beige = "\u001B[38;2;245;245;220m";;
    private static String gold = "\u001B[38;2;255;215;0m";
    private static String animal = lightBlue + "A";
    private static String insect = purple + "I";
    private static String plant =  green + "P";
    private static String fungi = red + "F";
    private static String manuscript = gold + "M";
    private static String quill = gold + "Q";
    private static String inkwell = gold + "W";

    /* suit con emoji/unicode
    private static String animal = lightBlue + "\uD81A\uDC98";
    private static String insect = purple + "\uD80C\uDDA6";
    private static String plant =  "\uD83C\uDF3F";
    private static String fungi = "\uD83C\uDF44";
    private static String manuscript = "\uD83D\uDCDC";
    private static String quill = "\uD83E\uDEB6";
    private static String inkwell = "\uD83C\uDF6F";
    */


    public Cli2(RmiClient client) {
        this.client = client;
    }

    public Cli2(VirtualServer server, RmiClient client) {
        this.client = client;
    }

    //Idealmente vorrei avere un metodo che prende in input una carta e a seconda del tipo di carta la stampa
    //metto in input la carta e da li ottengo la face. In questo modo posso differenziare la popolazione della carta
    //a seconda del tipo che ha

    public static void printCard(Card card) {
        if (card instanceof CardStarting) {
            //stampo la carta iniziale. Deve avere il colore beige. Il back ha le risorse in mezzo
            System.out.println("This is the front of your starting card: \n");
            printStartingFront(card);
            System.out.println("This is the back of your starting card: \n");
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
        }
    }

    public static void printStartingFront(Card card) {
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
        startingCard [1][0] = "";
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(startingCard[i][j]);
            }
            System.out.println();
        }
    }

    public static void printStartingBack(Card card) {
        //Stampo la carta iniziale. Deve avere il colore beige.
        String[][] startingCard = new String[3][11];
        String color = beige;
        int rows = 3;
        int cols = 11;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                startingCard[i][j] = color + "█";
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(startingCard[i][j]);
            }
            System.out.println();
        }
    }

    public static void printResourceFront(Card card) {
        String[][] resCard = createFrontResCard(card);
        resCard = hasPoints(card, resCard);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(resCard[i][j]);
            }
            System.out.println();
        }
    }

    public static void printGoldFront(Card card) {
        String[][] goldCard = createFrontResCard(card);
        hasPoints(card, goldCard);
        hasCost(card, goldCard);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(goldCard[i][j]);
            }
            System.out.println();
        }
    }

    public static void printResBack(Card card) {
        String[][] resCard = createBackResCard(card);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(resCard[i][j]);
            }
            System.out.println();
        }
    }

    public static String[][] createFrontResCard(Card card) {
        String[][] resCard = new String[3][11];
        int rows = 3;
        int cols = 11;
        String color = "\033[0;31m";
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                resCard[i][j] = "a";
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
    //un po' overkill forse questo
    public static String[][] createBackResCard(Card card) {
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

    public static boolean isUpLeftCorner(int row, int col, int rows, int cols) {
        return (row == 0 && col == 0);
    }

    public static boolean isDownLeftCorner(int row, int col, int rows, int cols) {
        return (row == rows - 1 && col == 0);
    }

    public static boolean isUpRightCorner(int row, int col, int rows, int cols) {
        return (row == 0 && col == cols - 1);
    }

    public static boolean isDownRightCorner(int row, int col, int rows, int cols) {
        return (row == rows - 1 && col == cols-1);
    }

    //in questo metodo voglio estrarre il costo di ogni carta gold e stamparlo opportunamente.
    public static String[][] hasCost(Card card, String[][] mat) {
        String color = colorResource((CardResource) card);
        int costFungi = ((CardGold) card).getCostFungi();
        int costAnimal = ((CardGold) card).getCostAnimal();
        int costPlant = ((CardGold) card).getCostPlant();
        int costInsect = ((CardGold) card).getCostInsect();
        ArrayList<Suit> costList = new ArrayList<Suit>();
        while(costFungi > 0){
            costList.add(Suit.FUNGI);
            costFungi--;
        }
        while(costAnimal > 0){
            costList.add(Suit.ANIMAL);
            costAnimal--;
        }
        while(costPlant > 0){
            costList.add(Suit.PLANT);
            costPlant--;
        }
        while(costInsect > 0){
            costList.add(Suit.INSECT);
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

        public static void main(String[] args) {
        ArrayList<Suit> listaprova = new ArrayList<Suit>();
        listaprova.add(Suit.ANIMAL);
        listaprova.add(Suit.INSECT);
        listaprova.add(Suit.PLANT);
        CardStarting prova = new CardStarting(0 , new Face(Suit.EMPTY, Suit.EMPTY, Suit.NULL, Suit.NULL),new Face(Suit.FUNGI, Suit.INSECT, Suit.ANIMAL, Suit.PLANT),listaprova);
        CardResource prova2 = new CardResource(0, new Face(Suit.EMPTY, Suit.NULL, Suit.FUNGI, Suit.FUNGI), new Face(Suit.EMPTY, Suit.EMPTY, Suit.EMPTY, Suit.EMPTY), Suit.FUNGI, 0, null);
        CardGold prova3 = new CardGold(0, new Face(Suit.EMPTY, Suit.NULL, Suit.QUILL, Suit.EMPTY), new Face(Suit.EMPTY, Suit.EMPTY, Suit.EMPTY, Suit.EMPTY), Suit.FUNGI, 1, 0, 5 , 0, 0, new ObjectiveAssign());
        printCard(prova);
        printCard(prova2);
        printCard(prova3);
    }

    public static String colorResource(CardResource card) {
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
    public static String cornerScanner(Suit drawing) {
        switch (drawing) {
            case EMPTY -> {
                return "E";
            }
            case NULL -> {
                return "N";
            }
            case PLANT -> {
                return plant;
            }
            case ANIMAL -> {
                return animal;
            }
            case INSECT -> {
                return insect;
            }
            case FUNGI -> {
                return fungi;
            }
            case QUILL -> {
                return quill;
            }
            case INKWELL -> {
                return inkwell;
            }
            case MANUSCRIPT -> {
                return manuscript;
            }
            default -> {
                return "_";
            }
        }
    }

    public static String[][] hasPoints(Card card, String[][] mat) {
        int points = ((CardResource) card).getPoints();
        if(points != 0) {
            mat[0][5] = Integer.toString(points);
            if(((CardGold) card).getObjective() instanceof ObjectivePositioning){
                mat[0][6] = "P";
            } else {
                mat[0][6] = "p";
            }
        }
        return mat;
    }

    @Override
    public void showStartingCard(CardStarting cardStarting) {
        //System.out.println("Your starting card is:" );
        //System.out.println("Front:");
        //printStarting(cardStarting.getFront());
        //System.out.println("Back:");
        //printStarting(cardStarting.getBack());
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
        }while(input < 2 || input > 4);
        return input;
    }

    @Override
    public void init() {
        System.out.println(""+
                "oooooooo8                  oooo                              oooo   oooo            o8                                     o888\n"+
                "o888        ooooooo     ooooo888   ooooooooo8 oooo   oooo       8888o  88   ooooooo o888oo oooo  oooo  oo oooooo   ooooooo    888  oooo   oooooooo8\n"+
                "888        888   888  888    888  888oooooo8    888o888         88 888o88   ooooo888 888    888   888   888        ooooo888   888   888  888ooooooo\n"+
                "888o       888   888  888    888  888           o88 88o         88   8888 888    888 888    888   888   888      888    888   888   888          888\n"+
                "888oooo88   88ooo88     88ooo888o  88oooo888 o88o   o88o      o88o    88  88ooo88 8o 888o   888o88 8o o888o      88ooo88 8o o888o o888o 88oooooo88\n\n");


    }
}
