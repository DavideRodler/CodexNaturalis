package View.CLI;

import Network.Client.ClientController;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import View.UI;
import model.PlayingStation;
import model.cards.CardResource;
import model.client.ClientBoard;
import model.enums.CliState;
import model.enums.GameState;
import model.enums.TokenEnum;
import model.client.ReductPlayer;
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

    private final Object menulock = new Object();
    private final Object chatlock = new Object();

    private CliState cliState = CliState.SETUP;
    private String playeNameCurrentChat;


    //constructor with clientboard
    public Cli2(ClientController clientController) {
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

        this.clientController = clientController;
        this.clientBoard = clientController.getClientModel();

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

    public void printIsNotMyTurnMenu() {

    }


    private void privateChatTitlePrinter() {
        for(int i = 0; i < 50; i++) {
            System.out.println();
        }

        System.out.println(purple+"    _____                    _           _____ _            _"+reset);
        System.out.println(purple+"   |  __ |    (_)           | |         / ____| |          | |"+reset);
        System.out.println(purple+"   | |__) | __ ___    ____ _| |_ ___   | |    | |___   __ _| |_"+reset);
        System.out.println(purple+"   |  ___/ '__| || | / / _` | __/ _ |  | |    | '_  | / _` | __|"+reset);
        System.out.println(purple+"   | |   | |  | | | V / (_| | ||  __/  | |____| | | || (_| | |_"+reset);
        System.out.println(purple+"   |_|   |_|  |_|  |_/ |__,_|__|_____| |______|_| |_||___,_|___|"+reset);


    }

    private void globalChatTitlePrinter() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

        System.out.println(purple + "            _____ _       _           _    _____ _            _" + reset);
        System.out.println(purple + "           / ____| |     | |         | |  / ____| |          | |" + reset);
        System.out.println(purple + "          | |  __| | ___ | |__   __ _| | | |    | |___   __ _| |_" + reset);
        System.out.println(purple + "          | | |_ | |/ _ || '_ | / _` | | | |    | '_  | / _` | __|" + reset);
        System.out.println(purple + "          | |__| | | (_) | |_) | (_| | | | |____| | | || (_| | |_" + reset);
        System.out.println(purple + "          |______|_||___/|_.__/|___,_|_| |______|_| |_||___,_|___|" + reset);

    }

    public void printMenu(){
        for(int i = 0; i < 50; i++) {
            System.out.println();
        }
        if (clientBoard.getCurrentPlayer().equals(clientBoard.getMyplayer().getNickname())) {
            System.out.println("It's your turn");
        } else {
            System.out.println("It's " + clientBoard.getCurrentPlayer() + "'s turn");
        }
        System.out.println("---------------------------------------------");
        System.out.println("/    1. Play a card                         /");
        System.out.println("/    2. Show my playing station             /");
        System.out.println("/    3. Show other playing station          /");
        System.out.println("/    4. Show central cards and decks        /");
        System.out.println("/    5. Show hand and secret objectives     /");
        System.out.println("/    6. Show Points                         /");
        System.out.println("/    7. Open Chat                           /");
        System.out.println("/    8. Draw a Card                         /");
        System.out.println("---------------------------------------------");
        for(int i = 0; i < 17; i++) {
            System.out.println();
        }
        System.out.println("Choose an option: ");
    }

    @Override
    public void printNicknameAlreadyTaken() {
        System.out.println("Nickname already taken, please choose another one.");
    }

    @Override
    public void printTokenAlreadyTaken() {
        System.out.println("Token already taken, please choose another one.");
    }

    @Override
    public void startGame() {
        new Thread(() -> {
                System.out.println("Game is starting");
                printMenu();
                cliState = CliState.MAIN_MENU;
                while (!clientBoard.getGameState().equals(GameState.FINISHED)){
                    //System in
                    printIsMyTurnMenu();
                }
            }).start();
        }

    @Override
    public void updateCurrentPlayer() {
        if (cliState.equals(CliState.MAIN_MENU)) {
            new Thread(() -> {
                synchronized (menulock) {
                    printMenu();
                }
            }).start();
        }
    }

    @Override
    public void updateGlobalChat() {
        if(cliState.equals(CliState.GLOBAL_CHAT)) {
            new Thread(() -> {
                globalChatTitlePrinter();
                printChatInfo();
                for (int i = 0; i < 10; i++) {
                    System.out.println();
                }
                printGlobalChat();
            }).start();
        }
    }
    @Override
    public void updatePrivateChat(){
        if(cliState.equals(CliState.PRIVATE_CHAT)) {
            new Thread(() -> {
                privateChatTitlePrinter();
                printChatInfo();
                for (int i = 0; i < 10; i++) {
                    System.out.println();
                }
                printPrivateChat(clientBoard.getMyplayer().getNickname(), playeNameCurrentChat);
            }).start();
        }
    }

    @Override
    public void updateHand() {

    }

    @Override
    public void updateViewAfterCardAddedToStation(CardResource card, int x, int y, boolean playedBack) {

    }


    /**
     * this method prints the hand of the player
     */




    private void printIsMyTurnMenu() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String choice;
        do {
            choice = scanner.nextLine();
        } while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5") && !choice.equals("6") && !choice.equals("7") && !choice.equals("8"));

        synchronized (menulock) {
            switch (choice) {
                case "1":
                    printMenu();
                    if(clientBoard.getCurrentPlayer().equals(clientBoard.getMyplayer().getNickname())) {
                        if(clientBoard.getGameState() == GameState.PLACING_CARD) {
                            this.printPlayerStation(clientBoard.getMyplayer().getStation());
                            this.printPlayerHand();
                            askCoordinatesOfCards();
                            try {
                                menulock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            printMenu();
                            System.out.println("You can't play a card now");
                        }
                    }
                    else {
                        printMenu();
                        System.out.println("It's not your turn");
                    }
                    break;
                case "2":
                    printMenu();
                    printPlayerStation(clientBoard.getMyplayer().getStation());
                    break;
                case "3":
                    System.out.println("Insert the nickname of the player you want to see the station of: ");
                    String nickname = getValidNickname();
                    printMenu();
                    printOtherPlayersStation(nickname);
                    break;
                case "4":
                    printMenu();
                    print4CentralCards();
                    break;
                case "5":
                    printMenu();
                    printPlayerHand();
                    break;
                case "6":
                    printMenu();
                    printAllPlayersPoints();
                    break;
                case "7":
                    printMenu();
                    int typeOfChat = askTypeOfChat(clientBoard.getOtherplayers().size(), clientBoard.getOtherplayers().stream().map(ReductPlayer::getNickname).toArray(String[]::new));
                    startChat(typeOfChat);
                    cliState = CliState.MAIN_MENU;
                    printMenu();
                    break;
                case "8":
                    if (clientBoard.getCurrentPlayer().equals(clientBoard.getMyplayer().getNickname()) ) {
                        if(clientBoard.getGameState() == GameState.ADDING_CARD_TO_HAND){
                            printMenu();
                            askWhichCardToDraw();
                        }
                        else {
                            printMenu();
                            System.out.println("You can't takke a card now");
                        }
                    } else {
                        printMenu();
                        System.out.println("It's not your turn");
                    }
                    break;
            }
        }
    }


    private void startChat(int typeOfChat) {
        switch (typeOfChat) {
            case 1:
                cliState = CliState.GLOBAL_CHAT;
                printGloablChatInfo();
                break;
            case 2:
                cliState = CliState.PRIVATE_CHAT;
                printPrivateChatInfo();
                break;
        }
    }

    public void printChatInfo() {
        System.out.println();
        System.out.println("Insert Message to send: ");
        System.out.println("Insert EXIT to exit the chat");
        System.out.println("Press enter to update the chat");
        System.out.println();
    }

    public void printPrivateChat(String nickname, String nickname1) {
        if(!clientBoard.getPrivateChat(nickname, nickname1).isEmpty()){
            for(PrivateChatMessage message : clientBoard.getPrivateChat(nickname, nickname1)){
                System.out.println(message.getNicknameReceiver() + ": " + message.getMessage());
            }
        }
    }

    public void printGlobalChat() {
        if(!clientBoard.getGlobalChat().getMessage().isEmpty()){
            for(GlobalChatMessage message : clientBoard.getGlobalChat().getMessage()){
                System.out.println(message.getNickname() + ": " + message.getMessage());
            }
        }
    }


    private void printGloablChatInfo() {
        String message;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        synchronized (this) {
            globalChatTitlePrinter();
            printChatInfo();
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            printGlobalChat();
        }
        message = scanner.nextLine();
        while (!message.equals("EXIT")){
            clientController.sendGlobalMessage(new GlobalChatMessage("GLOBAL", message, clientBoard.getMyplayer().getNickname()));
            message = scanner.nextLine();
        }
    }

    public void printPrivateChatInfo() {
        String message;
        String nickname = getValidNickname();
        playeNameCurrentChat = nickname;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        synchronized (this) {
            privateChatTitlePrinter();
            printChatInfo();
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            printPrivateChat(clientBoard.getMyplayer().getNickname(), nickname);
        }

        message = scanner.nextLine();
        while (!message.equals("EXIT")) {
            clientController.sendPrivateMessage(new PrivateChatMessage(message, clientBoard.getMyplayer().getNickname(), nickname));
            message = scanner.nextLine();
        }
        playeNameCurrentChat = "none";
    }

    private int askTypeOfChat(int numberOfOtherPlayers, String[] NamesOfOtherPlayers) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Insert the type of chat you want to open: ");
        System.out.println("1. Global chat");
        System.out.println("2. Private chat");
        int choice;
        do {
            choice = scanner.nextInt();
        } while (choice != 1 && choice != 2);
        return choice;
    }

    private void printAllPlayersPoints() {
        System.out.println(getColorFromEnum(clientBoard.getMyplayer().getToken()) + "Your points are: " + reset + clientBoard.getMyplayer().getPoints());
        for (int i = 0; i < clientBoard.getOtherplayers().size(); i++) {
            System.out.println(getColorFromEnum(clientBoard.getOtherplayers().get(i).getToken()) + clientBoard.getOtherplayers().get(i).getNickname() + reset+ " points are: " + clientBoard.getOtherplayers().get(i).getPoints());
        }

    }

   private String getColorFromEnum(TokenEnum token){
       return switch (token) {
           case YELLOW -> yellow;
           case GREEN -> green;
           case RED -> red;
           case BLUE -> blue;
       };
    }

    private String getValidNickname() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String nickname="";
        boolean valid = false;
        do {
            for (var c : clientBoard.getOtherplayers()) {
                System.out.print(lightBlue + c.getNickname() + reset + " ");
            }
            System.out.println();
            System.out.println();
            try{
                nickname = scanner.nextLine();
                if (clientBoard.getOtherPlayer(nickname) == null){
                    throw new Exception();
                }
                valid = true;
            } catch (Exception e) {
                printMenu();
                System.out.println("Invalid nickname, please try again.");
            }
        } while (!valid);
        return nickname;
    }

   

    @Override
    public void askNickname() {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        String input;
        System.out.println("Insert your nickname: ");
        input = in.nextLine();
        this.clientController.setupOfnickname_UI(input);
    }


    /**
     * this method asks the player if they want to play the starting card in front or in back
     * @return
     */
    @Override
    public void askStartingCardPlayedBack() {
        this.showStartingCard();
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
        this.clientController.setupOfStartingCard_UI( choice == 2);
    }

    /**
     * this method prints the common objectives
     */

    @Override
    public void printCommonObjectives() {
        printSetupPlayerHand();
        ObjectivePrinter objPrinter = new ObjectivePrinter();
        System.out.println("Common objectives: ");
        objPrinter.printSelectableObjectives(clientBoard.getFirstObjective(), clientBoard.getSecondObjective());
    }

    /**
     * this method prints the secret objective of the player
     */
    public void printSecretObjective(){
        ObjectivePrinter objPrinter = new ObjectivePrinter();
        objPrinter.printSecretObjective(clientBoard.getMyplayer().getSecretObjective());
    }

    /**
     * this method prints the objectives to choose from
     */
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
    public void askObjectiveCard() {
        this.printSelectableObjectives();
        System.out.println("Select the Objective Card you want to keep:");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do {
            System.out.println("1 for first, 2 for second");
            choice = in.nextInt();
        } while (choice != 1 && choice != 2);
        this.clientController.setupOfSecretObjective_UI(choice -1);
    }

    /**
     * this method prints the common cards.
     */
    @Override
    public void print4CentralCards() {
        int pos = 0;
        CentralCardsCreator centralCards = new CentralCardsCreator();
        centralCards.initializeMatrix();
        System.out.println("Central cards and decks: ");
        for(int i = 0; i < clientBoard.getCentralCardsGold().size(); i++){
            centralCards.addCentralCardGold(clientBoard.getCentralCardsGold().get(i), pos);
            pos++;
        }
        for(int i = 0; i < clientBoard.getCentralCardsResource().size(); i++){
            centralCards.addCentralCardRes(clientBoard.getCentralCardsResource().get(i), pos);
            pos++;
        }
        centralCards.addDeckResToBoard(clientBoard.getBackOfResourceDeck());
        centralCards.addDeckGoldToBoard(clientBoard.getBackOfGoldDeck());
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
    private void printPlayerStation(PlayingStation playingStation) {
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
        //stationMatrix.printPoints(clientBoard);
    }

    /**
     * this method prints the hand of the player at the setup stage of the game
     */
    private void printSetupPlayerHand() {
        System.out.println("Here is your hand:");
        HandPrinter playerHand = new HandPrinter();
        playerHand.addCardsToHand(clientBoard.getMyplayer().getHand());
        playerHand.printHandMatrix();
    }

    /**
     * this method prints the hand of the player during the game. It also prints the secreto objective of the player
     */
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
    private void askCoordinatesOfCards() {
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
        clientController.playCardOnPlayngStation_UI(cardSide == 2,x,y,clientBoard.getMyplayer().getHand().get(cardChoice).getId());
    }


    /**
     * this method asks the player which card they would like to draw, whether onr of the central cards or from
     * one ot the two decks.
     * @return the choice of the player
     */
    private void askWhichCardToDraw() {
        synchronized (menulock) {
            this.print4CentralCards();

            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            Integer choice;
            do {
                System.out.println("Which card do you want to draw? Insert 1 for up left card, 2 for up right card, 3 for down left card, 4 for down right card, 5 for resource Deck, 6 for gold Deck");
                choice = scanner.nextInt();
            } while (choice < 1 || choice > 6);
            cliState = CliState.MAIN_MENU;
            clientController.startAfterCardHasBeenAddedToStation_UI(choice);
        }
    }

    /**
     * this method prints the station of the other player after their turn ended.
     * @param nickname is the nickname of the player of which the station is going to be printed
     */
    private void printOtherPlayersStation(String nickname) {
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
    public void askToken(ArrayList<TokenEnum> availableTokens) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        Integer choice;
        do {
            for (TokenEnum token : availableTokens) {
                System.out.println(availableTokens.indexOf(token) + 1 + ". " + getColorFromEnum(token) + token + reset);
            }
            choice = scanner.nextInt();
        }
        while (choice < 0 || choice > availableTokens.size());
        this.clientController.setupOfToken_CLI(availableTokens.get(choice-1));
    }


    @Override
    public void printCardAddedSuccessfully() {
        synchronized (menulock) {
            printMenu();
            System.out.println("Card added successfully");
            menulock.notify();
        }
    }

    @Override
    public void printCardNotAdded(String message) {
        synchronized (menulock) {
            cliState = CliState.MAIN_MENU;
            printMenu();
            System.out.println(message);
            menulock.notify();
        }
    }
}
