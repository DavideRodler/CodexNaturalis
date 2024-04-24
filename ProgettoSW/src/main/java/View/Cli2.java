package View;

import Network.Client.RmiClient;
import Network.Client.VirtualView;
import Network.Server.VirtualServer;
import model.cards.CardStarting;
import model.cards.CardResource;
import model.cards.CardStarting;

import model.cards.face.Face;
import model.enums.Suit;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Cli2 implements UI {

    private static String red;
    private static String green;
    private static String yellow;
    private static String reset;
    private static String purple;
    private static String blue;
    private final RmiClient client;

    public Cli2(RmiClient client) {
        this.client = client;
    }

    public Cli2(VirtualServer server, RmiClient client) {
        this.client = client;
        blue = "\033[0;34m";
        green = "\033[0;32m";
        yellow = "\033[0;33m";
        reset = "\033[0m";
        purple = "\033[0;35m";
        red = "\033[0;31m";

    }

    public void printFaceofStarting(Face face) {
        char startingCardFaceMatrix[][] = new char[3][9];
        //first line
        startingCardFaceMatrix[0][0] = cornerScanner(face.getUpLeft());
        startingCardFaceMatrix[0][1] = '-';
        startingCardFaceMatrix[0][2] = '-';
        startingCardFaceMatrix[0][3] = '-';
        startingCardFaceMatrix[0][4] = '-';
        startingCardFaceMatrix[0][5] = '-';
        startingCardFaceMatrix[0][6] = '-';
        startingCardFaceMatrix[0][7] = '-';
        startingCardFaceMatrix[0][8] = cornerScanner(face.getUpLeft());


        //second line
        startingCardFaceMatrix[1][0] = '|';
        startingCardFaceMatrix[1][1] = ' ';
        startingCardFaceMatrix[1][2] = ' ';
        startingCardFaceMatrix[1][3] = cornerScanner(face.getFaceList().get(0));
        startingCardFaceMatrix[1][4] = cornerScanner(face.getFaceList().get(1));
        startingCardFaceMatrix[1][5] = cornerScanner(face.getFaceList().get(2));
        startingCardFaceMatrix[1][6] = ' ';
        startingCardFaceMatrix[1][7] = ' ';
        startingCardFaceMatrix[1][8] = '|';


        //third line
        startingCardFaceMatrix[2][0] = cornerScanner(face.getDownLeft());
        startingCardFaceMatrix[2][1] = '-';
        startingCardFaceMatrix[2][2] = '-';
        startingCardFaceMatrix[2][3] = '-';
        startingCardFaceMatrix[2][4] = '-';
        startingCardFaceMatrix[2][5] = '-';
        startingCardFaceMatrix[2][6] = '-';
        startingCardFaceMatrix[2][7] = '-';
        startingCardFaceMatrix[2][8] = cornerScanner(face.getDownRight());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(startingCardFaceMatrix[i][j]);
            }
            System.out.print("\n");
        }
    }


    public static void main(String[] args) {
        ArrayList<Suit> listaprova = new ArrayList<Suit>();
        listaprova.add(Suit.ANIMAL);
        listaprova.add(Suit.ANIMAL);
        listaprova.add(Suit.ANIMAL);
        CardStarting prova = new CardStarting(0 , new Face(Suit.ANIMAL, Suit.PLANT, Suit.FUNGI, Suit.PLANT),new Face(Suit.ANIMAL, Suit.PLANT, Suit.FUNGI, Suit.PLANT),listaprova);

    }


    public char cornerScanner(Suit drawing) {
        switch (drawing) {
            case EMPTY -> {
                return 'E';
            }
            case NULL -> {
                return 'N';
            }
            case PLANT -> {
                return 'P';
            }
            case ANIMAL -> {
                return 'A';
            }
            case INSECT -> {
                return 'I';
            }
            case FUNGI -> {
                return 'F';
            }
            case QUILL -> {
                return 'Q';
            }
            case INKWELL -> {
                return 'i';
            }
            case MANUSCRIPT -> {
                return 'M';
            }
            default -> {
                return '_';
            }
        }
    }

    @Override
    public void showStartingCard(CardStarting cardStarting) {
        System.out.println("Your starting card is:" );
        System.out.println("Front:");
        printFaceofStarting(cardStarting.getFront());
        System.out.println("Back:");
        printFaceofStarting(cardStarting.getBack());
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
