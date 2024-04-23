package Network;

import Network.Client.RmiClient;
import Network.Server.VirtualServer;
import View.UI;
import model.cards.CardStarting;
import model.enums.Suit;

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
        System.out.println("Your starting card is:" );
        System.out.println("Front:");
        System.out.println(
                cornerScanner(cardStarting.getFront().getUpLeft())
                +"  "+cornerScanner(cardStarting.getFront().getUpRight())
                +"\n"+cornerScanner(cardStarting.getFront().getDownLeft())
                +"  "+cornerScanner(cardStarting.getFront().getDownRight()));
        System.out.println("Back:");
        System.out.println(
                cornerScanner(cardStarting.getBack().getUpLeft())
                +"  "+cornerScanner(cardStarting.getBack().getUpRight())
                +"\n"+cornerScanner(cardStarting.getBack().getDownLeft())
                +"  "+cornerScanner(cardStarting.getBack().getDownRight()));
        System.out.println("Symbols in the front middle:");
        for(var c : cardStarting.getSymbols())
        {
            System.out.print(" "+cornerScanner(c));
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

}
