package View.CLI;

import Network.Client.ClientController;
import View.UI;
import model.PlayingStation;
import model.client.ClientBoard;
import model.enums.GameState;
import model.enums.TokenEnum;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Cli2 implements UI {

//    private final String black;
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
    private ClientBoard clientBoard;
    private ClientController clientController;


    //constructor with clientboard
    public Cli2(ClientBoard clientBoard, ClientController clientController) {
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

        this.clientBoard = clientBoard;
        this.clientController = clientController;

    }

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

    /**
     * this method prints the front and back of the player's starting card
     *
     */
    @Override
    public void showStartingCard() {
        System.out.println("Your starting card: :");
        StartingCardPrinter startingCardPrinter = new StartingCardPrinter();
        startingCardPrinter.cardStartingPrinter(clientBoard.getMyplayer().getStation().getCardStarting());
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
            System.out.println("You are the first player to join: insert number of players in your Lobby: ");
            input = scanner.nextInt();
        }while (input < 2 || input > 4);
        return input;
    }

    /**
     * this method asks the player if they want to play the starting card in front or in back
     * @return
     */
    @Override
    public boolean askStartingCardPlayedBack() {
        System.out.println("Select the front or the back of your starting card: ");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        int choice;
        System.out.println("Insert 1 for choose the front or insert 2 for choose back");
        choice = in.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid choice, please try again.");
            System.out.println("Insert 1 for choose the front or insert 2 for choose back");
            choice = in.nextInt();
        }
        return choice == 2;
    }

    /**
     * this method prints the common objectives
     */

    @Override
    public void printCommonObjectives() {
        ObjectivePrinter objPrinter = new ObjectivePrinter();
        System.out.println("Common objectives: ");
        objPrinter.printSelectableObjectives(clientBoard.getFirstObjective(), clientBoard.getSecondObjective());
    }

    /**
     * this method prints the secret objective of the player
     */
    @Override
    public void printSecretObjective(){
        ObjectivePrinter objPrinter = new ObjectivePrinter();
        objPrinter.printSecretObjective(clientBoard.getMyplayer().getSecretObjective());
    }

    /**
     * this method prints the objectives to choose from
     */
    @Override
    public void printSelectableObjectives(){
        ObjectivePrinter objPrinter = new ObjectivePrinter();
        System.out.println("These are the objectives you can choose from:");
        objPrinter.printSelectableObjectives(clientBoard.getMyplayer().getSelectibleObjectives().get(0),clientBoard.getMyplayer().getSelectibleObjectives().get(1));
        System.out.println();
    }

    /**
     * this method asks the player which objective card they want to keep
     *
     * @return
     */
    @Override
    public int askObjectiveCard() {
        System.out.println("Select the Objective Card you want to keep:");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do {
            System.out.println("1 for first, 2 for second");
            choice = in.nextInt();
        } while (choice != 1 && choice != 2);
        return choice -1;
    }

    /**
     * this method prints the common cards.
     */
    @Override
    public void print4CentralCards() {
        int pos = 0;
        CentralCardsCreator centralCards = new CentralCardsCreator();
        System.out.println("Central cards are: ");
        for(int i = 0; i < clientBoard.getCentralCardsGold().size(); i++){
            centralCards.addCentralCardGold(clientBoard.getCentralCardsGold().get(i), pos);
            pos++;
        }
        for(int i = 0; i < clientBoard.getCentralCardsResource().size(); i++){
            centralCards.addCentralCardRes(clientBoard.getCentralCardsResource().get(i), pos);
            pos++;
        }
        centralCards.printCentral();
    }


    /**
     * this method prints the station of the player at the beginning of their turn.
     */
    //Adesso nella print updatedstation devo solo chiamare l'oggetto stationmatrix, popolarlo (mettendo la poopolazione dalla mappa alla matrice in stationMatrix, e stamparla

    /**
     * this method prints the station of the player.
     * @param playingStation is the playing station the player.
     */
    @Override
    public void printPlayerStation(PlayingStation playingStation) {
        int fungi = playingStation.getCountFungi();
        int plant = playingStation.getCountPlant();
        int animal = playingStation.getCountAnimal();
        int insect = playingStation.getCountInsect();
        int quill = playingStation.getCountQuill();
        int manuscript = playingStation.getCountManuscript();
        int inkwell = playingStation.getCountInkwell();
        StationMatrix stationMatrix= new StationMatrix();
        stationMatrix.printStation(playingStation.getMap());
        stationMatrix.printResources(fungi, plant, animal, insect, quill, manuscript, inkwell);
        stationMatrix.printPoints(clientBoard);
    }

    /**
     * this method prints the hand of the player at the setup stage of the game
     */
    @Override
    public void printSetupPlayerHand() {
        System.out.println("Here is your hand:");
        HandPrinter playerHand = new HandPrinter();
        playerHand.addCardsToHand(clientBoard.getMyplayer().getHand());
        playerHand.printHandMatrix();
    }

    /**
     * this method prints the hand of the player during the game. It also prints the secreto objective of the player
     */
    @Override
    public void printPlayerHand(){
        System.out.println("Here is your hand and your secret objective:");
        HandPrinter playerHand = new HandPrinter();
        playerHand.addCardsToHand(clientBoard.getMyplayer().getHand());
        playerHand.addObjectiveToHand(clientBoard.getMyplayer().getSecretObjective());
        playerHand.printHandMatrix();
    }


    /**
     * this method asks where the player which card he would like to play, if he wants to play it in front or in back and also where.
     * @return an array containing the choices of the player.
     */
    @Override
    public synchronized Integer[] askCoordinatesOfCards() {
        printPlayerStation(clientBoard.getMyplayer().getStation());
        printPlayerHand();
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int cardChoice;
        do {
            System.out.println("Which card do you want to place? Insert the number of the card (1-3)");
            cardChoice = scanner.nextInt() -1;
        } while (cardChoice < 0 || cardChoice > 2);

        int cardSide;
        do {
            System.out.println("front (1) or back (2)");
            cardSide = scanner.nextInt();
        } while(cardSide < 1 || cardSide > 2);

        //qua il controllo viene fatto da isPlayable.
        System.out.println("Choose x coordinates");
        int x = scanner.nextInt();
        System.out.println("Choose y coordinates");
        int y = scanner.nextInt();
        Integer[] Choice = {cardChoice, cardSide, x, y};
        clientController.setCartToStation(clientBoard.getMyplayer().getHand().get(cardChoice).getId(), cardSide == 2, x, y);
        return Choice;

    }

    /**
     * this method asks the player which card they would like to draw, whether onr of the central cards or from
     * one ot the two decks.
     * @return the choice of the player
     */
    @Override
    public synchronized Integer askWhichCardToDraw() {
        print4CentralCards();
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int choice;
        do {
            System.out.println("Which card do you want to draw? Insert 1 for up left card, 2 for up right card, 3 for down left card, 4 for down right card, 5 for resource Deck, 6 for gold Deck");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 6);
        if (choice < 5) {
            clientController.addCardFromCentralCardsToPlayerHand(choice - 1);
        } else {
            clientController.addCardFromDeckToPlayerHand(choice - 5);
        }
        return choice;
    }

    @Override
    public synchronized void printStartOfMenu() {
        String currentplayer = clientBoard.getCurrentPlayer();
        GameState gameState = clientBoard.getGameState();

        print4CentralCards();
        System.out.println();
        printCommonObjectives();
        printSecretObjective();
        printPlayerStation(clientBoard.getMyplayer().getStation());
        if (clientBoard.getCurrentPlayer().equals(clientBoard.getMyplayer().getNickname())) {
            System.out.println("It's your turn!");
        } else System.out.println("It's " + currentplayer + "'s turn!");
        System.out.println("The game is now in the " + gameState + " state");
        System.out.println("You can do the following actions: ");
        System.out.println("1. to see another player's station");
        System.out.println("2. Draw a card");
        System.out.println("3. Place a card");
    }


    public void getanswerOfmenu() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do {
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);
        switch (choice) {
            case 1 -> {
                synchronized (this) {
                    System.out.println("The players in the game are: " + clientBoard.getOtherplayers().stream().map(p -> p.getNickname()).toList());
                    System.out.println("Insert the number of the player you want to see the station of: ");
                    int choice2 = scanner.nextInt();
                    printOtherPlayersStation(clientBoard.getOtherplayers().get(choice2 - 1).getNickname());
                }
                    new Thread(() -> {
                        printStartOfMenu();
                    }).start();
                    getanswerOfmenu();
            }
            case 2 -> {
                askWhichCardToDraw();
            }
            case 3 -> {
                askCoordinatesOfCards();
            }
        }
    }

    /**
     * this method prints everything needed for the start of the player's turn: the central cards, the
     * common objectives, the secret objective, the hand and the station.
     */
    @Override
    public void printStartOfPlayerTurn() {
        new Thread(() -> {
            printStartOfMenu();
        }).start();
        getanswerOfmenu();
//        print4CentralCards();
//        System.out.println();
//        printCommonObjectives();
//        printSecretObjective();
//        printSetupPlayerHand();
//        printPlayerStation(clientBoard.getMyplayer().getStation());
    }

    /**
     * this method prints the station of the player after they place a card
     */
    @Override
    public void printStationAfterCardHasBeenAdded() {
        printPlayerStation(clientBoard.getMyplayer().getStation());
    }

    /**
     * this method prints the station of the other player after their turn ended.
     * @param nickname is the nickname of the player of which the station is going to be printed
     */
    @Override
    public synchronized void printOtherPlayersStation(String nickname) {
        System.out.println(nickname + "'s station is: ");
        printPlayerStation(clientBoard.getOtherPlayer(nickname).getStation());
    }


    @Override
    public void printFinalPoints(LinkedHashMap<String, ArrayList<Integer>> map) {
        System.out.println("The game is over! Here are the final points ");
        System.out.println("Drumroll please..............");
        for (String key : map.keySet()) {
            if (map.get(key).get(2) == 1) {
                System.out.println("Congratulations to " + key + " , you are the WINNER!");
                System.out.println(key + " has totaled the beauty of " + map.get(key).get(0) + " points and has completed" + map.get(key).get(1) + " objectives");
            } else {
                System.out.println(key + " arrived " + map.get(key).get(2) + " and has totaled " + map.get(key).get(0) + " points and has completed " + map.get(key).get(1) + " objectives");
            }
        }
    }

    /**
     * this method asks the player to choose a token
     * @param availableTokens is the list of available tokens
     * @return the token chosen
     */
    @Override
    public TokenEnum askToken(ArrayList<TokenEnum> availableTokens) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        Integer choice;
        do {
            System.out.println("Use numbers to select one of the available tokens: " + availableTokens);
            choice = scanner.nextInt();
        }
        while (choice < 0 && choice >= availableTokens.size());
        return availableTokens.get(choice - 1);
    }

    @Override
    public void waitingForOtherPlayers() {

    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    /**
     * this method shows the available tokens to the player
     * @param availableTokens
     */
    @Override
    public void printAvailableTokens(ArrayList<TokenEnum> availableTokens) {
        Scanner input = new Scanner(System.in);
        System.out.println("Available tokens are: " + availableTokens);
        System.out.println("Choose one of the available tokens");


    }

    @Override
    public void printCardAddedSuccessfully() {
        System.out.println("Card added successfully");
    }

    @Override
    public void printCardNotAdded(String message) {
        System.out.println(message);

    }

    @Override
    public void printPlayerToken(){
        System.out.println("Your token is: " + clientBoard.getMyplayer().getToken());
    }

    private void printMatrix(String[][] mat){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }
}
