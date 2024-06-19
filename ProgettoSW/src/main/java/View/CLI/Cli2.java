package View.CLI;

import Network.Client.ClientController;
import Socket.Messages.*;
import View.UI;
import exception.InvalidPlacingCondition;
import exception.NonePlayerFoundException;
import exception.NotMyTurnException;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardResource;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.TokenEnum;

import java.io.InputStreamReader;
import java.rmi.RemoteException;
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

    @Override
    public void launchGui(ClientBoard clientModel, ClientController clientController){

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
    public void print4CentralCardsAndDecks() {
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

    private Integer[] askCoordinatesOfCards() {
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
        System.out.println("Choose first coordinates");
        int x = scanner.nextInt();
        System.out.println("Choose second coordinates");
        int y = scanner.nextInt();
        Integer[] Choice = {cardChoice, cardSide, x, y};
        return Choice;
    }

    /**
     * this method asks the player which card they would like to draw, whether onr of the central cards or from
     * one ot the two decks.
     * @return the choice of the player
     */
    private Integer askWhichCardToDraw() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        Integer choice;
        do {
            System.out.println("Which card do you want to draw? Insert 1 for up left card, 2 for up right card, 3 for down left card, 4 for down right card, 5 for resource Deck, 6 for gold Deck");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 6);
        return choice;
    }

    /**
     * this method prints everything needed for the start of the player's turn: the central cards, the
     * common objectives, the secret objective, the hand and the station.
     */
    @Override
    public void printStartOfPlayerTurn() {
        printMenu();
    }


    @Override
    public void printMenu(){
        for(int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("It's your turn");
        System.out.println("---------------------------------------------");
        System.out.println("/    1. Play a card                         /");
        System.out.println("/    2. Show my playing station             /");
        System.out.println("/    3. Show other playing station          /");
        System.out.println("/    4. Show central cards and decks        /");
        System.out.println("/    5. Show hand and secret objectives     /");
        System.out.println("/    6. Show Points                         /");
        System.out.println("/    7. Open Chat                           /");
        System.out.println("/    8. End turn                            /");
        System.out.println("---------------------------------------------");
        for(int i = 0; i < 17; i++) {
            System.out.println();
        }
    }

    @Override
    public void printMenu2and3() {
        for(int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("It's your turn");
        System.out.println("---------------------------------------------");
        System.out.println("/    1. Play a card                         /");
        System.out.println("/    2. Show my playing station             /");
        System.out.println("/    3. Show other playing station          /");
        System.out.println("/    4. Show central cards and decks        /");
        System.out.println("/    5. Show hand and secret objectives     /");
        System.out.println("/    6. Show Points                         /");
        System.out.println("/    7. Open Chat                           /");
        System.out.println("/    8. End turn                            /");
        System.out.println("---------------------------------------------");
        for(int i = 0; i < 6; i++) {
            System.out.println();
        }
    }

    @Override
    public void printMenuNotMyTurn(String currentPlayer) {
        for(int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("It's " + currentPlayer + "'s turn");
        System.out.println("---------------------------------------------");
        System.out.println("/    1. Show my playing station             /");
        System.out.println("/    2. Show other playing station          /");
        System.out.println("/    3. Show central cards and decks        /");
        System.out.println("/    4. Show hand and secret objectives     /");
        System.out.println("/    5. Show Points                         /");
        System.out.println("/    6. Open Chat                           /");
        System.out.println("/    7. Ready for next turn                 /");
        System.out.println("---------------------------------------------");
        for(int i = 0; i < 17; i++) {
            System.out.println();
        }
    }

    @Override
    public void printMenu2and3NotMyTurn(String currentPlayer) {
        for(int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("It's " + currentPlayer + "'s turn");
        System.out.println("---------------------------------------------");
        System.out.println("/    1. Show my playing station             /");
        System.out.println("/    2. Show other playing station          /");
        System.out.println("/    3. Show central cards and decks        /");
        System.out.println("/    4. Show hand and secret objectives     /");
        System.out.println("/    5. Show Points                         /");
        System.out.println("/    6. Open Chat                           /");
        System.out.println("/    7. Ready for next turn                 /");
        System.out.println("---------------------------------------------");
        for(int i = 0; i < 6; i++) {
            System.out.println();
        }
    }

    @Override
    public void askPrivateMessage(String nickname) {
        String Message2;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        do{
            privateChatTitlePrinter();
            printChatInfo();
            printSpace();
            printSpace();
            printSpace();
            printPrivateChat(clientBoard.getMyplayer().getNickname(), nickname);
            Message2 = scanner.nextLine();
            if(!Message2.isEmpty() && !Message2.equals("EXIT")) {
                try {
                    clientController.messageToServerhandler(new PrivateChatMessage(Message2, clientBoard.getMyplayer().getNickname(), nickname));
                }catch (RemoteException | InvalidPlacingCondition | NotMyTurnException e){
                    e.printStackTrace();
                }
            }
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
        }while(!Message2.equals("EXIT"));
    }

    @Override
    public void printChat() {

        if(!clientBoard.getGlobalChat().getMessage().isEmpty()){
            for(ChatMessage message : clientBoard.getGlobalChat().getMessage()){
                System.out.println(message.getNickname() + ": " + message.getMessage());
            }
        }
    }

    @Override
    public void printChatInfo() {
        System.out.println();
        System.out.println("Insert Message to send: ");
        System.out.println("Insert EXIT to exit the chat");
        System.out.println("Press enter to update the chat");
        System.out.println();
    }

    @Override
    public void printPrivateChatInfo() {
        System.out.println();
        System.out.println("Insert the nickname of the player you want to chat with: ");
        for (var c : clientBoard.getOtherplayers()) {
            System.out.print(lightBlue + c.getNickname() + reset + " ");
        }
        printSpace();
        String nickname = "";
        boolean validNickname = false;
        do {
            try {
                Scanner scanner = new Scanner(new InputStreamReader(System.in));
                nickname = scanner.nextLine();
                clientBoard.getOtherPlayer(nickname); // This will throw an exception if the player does not exist
                validNickname = true;
            } catch (NonePlayerFoundException e) {
                System.out.println("Invalid nickname. Please try again.");
            }
        } while (!validNickname);
        try{
            this.clientController.messageToServerhandler(new PrivateChatNicknameMessage(nickname));
        }catch(RemoteException | InvalidPlacingCondition | NotMyTurnException e){
            e.printStackTrace();
        }

    }

    @Override
    public void printPrivateChat(String nickname, String nickname1) {
        if(!clientBoard.getPrivateChat(nickname, nickname1).isEmpty()){
            for(PrivateChatMessage message : clientBoard.getPrivateChat(nickname, nickname1)){
                System.out.println(message.getNicknameSender() + ": " + message.getMessage());
            }
        }
    }

    @Override
    public void chatTitlePrinter() {
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();

System.out.println(purple+"            _____ _       _           _    _____ _            _"+reset);
System.out.println(purple+"           / ____| |     | |         | |  / ____| |          | |"+reset);
System.out.println(purple+"          | |  __| | ___ | |__   __ _| | | |    | |___   __ _| |_"+reset);
System.out.println(purple+"          | | |_ | |/ _ || '_ | / _` | | | |    | '_  | / _` | __|"+reset);
System.out.println(purple+"          | |__| | | (_) | |_) | (_| | | | |____| | | || (_| | |_"+reset);
System.out.println(purple+"          |______|_||___/|_.__/|___,_|_| |______|_| |_||___,_|___|"+reset);


    }

    @Override
    public void printPoints() {
        StationMatrix stationMatrix= new StationMatrix();
        stationMatrix.printPoints(clientBoard);
    }

    @Override
    public void printGloablChatInfo() {

        String Message;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        do{
            privateChatTitlePrinter();
            printChatInfo();
            printSpace();
            printSpace();
            printSpace();
            printChat();
            Message = scanner.nextLine();
            if(!Message.isEmpty() && !Message.equals("EXIT")) {
                try {
                    clientController.messageToServerhandler(new ChatMessage("GLOBAL", Message, clientBoard.getMyplayer().getNickname()));
                }catch (RemoteException | InvalidPlacingCondition | NotMyTurnException e){
                    e.printStackTrace();
                }
            }
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
            printSpace();
        }while(!Message.equals("EXIT"));
    }



    @Override
    public void privateChatTitlePrinter() {
        printSpace();
        printSpace();
        printSpace();
        printSpace();
        printSpace();
        printSpace();

        System.out.println(purple+"    _____                    _           _____ _            _"+reset);
        System.out.println(purple+"   |  __ |    (_)           | |         / ____| |          | |"+reset);
        System.out.println(purple+"   | |__) | __ ___    ____ _| |_ ___   | |    | |___   __ _| |_"+reset);
        System.out.println(purple+"   |  ___/ '__| || | / / _` | __/ _ |  | |    | '_  | / _` | __|"+reset);
        System.out.println(purple+"   | |   | |  | | | V / (_| | ||  __/  | |____| | | || (_| | |_"+reset);
        System.out.println(purple+"   |_|   |_|  |_|  |_/ |__,_|__|_____| |______|_| |_||___,_|___|"+reset);


    }




    @Override
    public void askTypeOfChat(int numberOfOtherPlayers, String[] NamesOfOtherPlayers) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Insert the type of chat you want to open: ");
        System.out.println("1. Global chat");
        System.out.println("2. Private chat");
        try {
            this.clientController.messageToServerhandler(new ChatChoiceMessage(scanner.nextLine()));
        }catch (RemoteException | InvalidPlacingCondition | NotMyTurnException e){
            e.printStackTrace();
        }
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
    public void printOtherPlayersStation(String nickname) throws NonePlayerFoundException {
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
    public void askMenuAction() {

        //showing the board
        //the menuAnswer to where to put the card
        Integer[] inputAnswer;
        boolean endTurn = false;
        boolean cardPlaced = false;
        printMenu();
        //the card i want to put in the station
        CardResource cardchoosen;
        int cardId;
        int menuAnswer;

        do{
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            System.out.println("Choose an action: ");
            menuAnswer = scanner.nextInt();
            while (menuAnswer < 1 || menuAnswer > 8) {
                System.out.println("Invalid choice, please try again.");
                System.out.println("Choose an action: ");
                menuAnswer = scanner.nextInt();
            }

            try {
                switch(menuAnswer) {
                    case 1:
                    {
                        if(!cardPlaced) {
                            printPlayerStation(clientBoard.getMyplayer().getStation());
                            printPlayerHand();
                            inputAnswer = askCoordinatesOfCards();
                            cardchoosen = clientBoard.getMyplayer().getHand().get(inputAnswer[0]);
                            cardId = cardchoosen.getId();
                            this.clientController.messageToServerhandler(new PlaceCardMessage(cardId, inputAnswer[1] == 2, inputAnswer[2], inputAnswer[3]));
                            cardPlaced = true;
                            printSpace();
                            print4CentralCardsAndDecks();
                            int selection = askWhichCardToDraw();

                            CardResource card;
                            if (selection >= 1 && selection <= 4) {
                                if (selection >= 3) {
                                    ArrayList<CardResource> centralCardsResource = clientBoard.getCentralCardsResource();
                                    card = centralCardsResource.get(selection - 3);
                                } else {
                                    ArrayList<CardGold> centralCardsGold = clientBoard.getCentralCardsGold();
                                    card = centralCardsGold.get(selection - 1);
                                }
                                try {
                                    this.clientController.messageToServerhandler(new DrawCardMessage(card.getId()));
                                } catch (RemoteException | NotMyTurnException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                try {
                                    this.clientController.messageToServerhandler(new DrawDeckMessage(selection));
                                } catch (RemoteException | InvalidPlacingCondition | NotMyTurnException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            endTurn = true;
                            printMenu();
                        }
                        else {
                            printMenu2and3();
                            printSpace();
                            printSpace();
                            showErrorMessage("You have already placed a card in your station");
                            printSpace();
                            printSpace();
                        }
                        break;
                    }
                    case 2:
                        printMenu2and3();
                        printPlayerStation(clientBoard.getMyplayer().getStation());
                        printSpace();
                        break;
                    case 3:
                        String playerStationName = askWichStationToPrint();
                        printMenu2and3();
                        printOtherPlayersStation(playerStationName);
                        printSpace();
                        break;
                    case 4:
                        printMenu2and3();
                        print4CentralCardsAndDecks();
                        printCommonObjectives();
                        printSpace();
                        break;
                    case 5:
                        printMenu2and3();
                        printPlayerHand();
                        printSpace();
                        break;
                    case 6:
                        printMenu2and3();
                        printSpace();
                        printSpace();
                        printPoints();
                        printSpace();
                        printSpace();
                        break;
                    case 7:
                        askTypeOfChat(clientBoard.getOtherplayers().size(), clientBoard.getOtherplayers().stream().map(ReductPlayer::getNickname).toArray(String[]::new));
                        printMenu();
                        break;
                    case 8:
                        if(!endTurn)
                            System.out.println("You can not end your turn before had placed a card in your station");

                        break;
                }
            } catch (InvalidPlacingCondition | NonePlayerFoundException e) {
                showErrorMessage(e.getMessage());
            } catch (RemoteException | NotMyTurnException e) {
                throw new RuntimeException(e);
            }

        }while (menuAnswer != 8 || !endTurn);
    }

    @Override
    public void askNotMyTurnMenuAction(String currentPlayer) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int menuAnswer = 0;
        boolean ready = false;
        while ((!clientBoard.getCurrentPlayer().equals(clientBoard.getMyplayer().getNickname())) && (!ready || menuAnswer != 7)) {
            System.out.println("Choose an action: ");
            menuAnswer = scanner.nextInt();
            while (menuAnswer < 1 || menuAnswer > 7) {
                System.out.println("Invalid choice, please try again.");
                System.out.println("Choose an action: ");
                menuAnswer = scanner.nextInt();
            }
            try {
                switch (menuAnswer) {
                    case 1:
                        printMenu2and3NotMyTurn(currentPlayer);
                        printPlayerStation(clientBoard.getMyplayer().getStation());
                        printSpace();
                        break;
                    case 2:
                        String playerStationName = askWichStationToPrint();
                        printMenu2and3NotMyTurn(currentPlayer);
                        printOtherPlayersStation(playerStationName);
                        printSpace();
                        break;
                    case 3:
                        printMenu2and3NotMyTurn(currentPlayer);
                        print4CentralCardsAndDecks();
                        printCommonObjectives();
                        printSpace();
                        break;
                    case 4:
                        printMenu2and3NotMyTurn(currentPlayer);
                        printPlayerHand();
                        printSpace();
                        break;
                    case 5:
                        printMenu2and3NotMyTurn(currentPlayer);
                        printSpace();
                        printSpace();
                        printPoints();
                        printSpace();
                        printSpace();
                        break;
                    case 6:
                        askTypeOfChat(clientBoard.getOtherplayers().size(), clientBoard.getOtherplayers().stream().map(ReductPlayer::getNickname).toArray(String[]::new));
                        printMenuNotMyTurn(currentPlayer);
                        break;
                    case 7:
                        ready = true;
                        break;
                }
            } catch (NonePlayerFoundException e) {
                showErrorMessage(e.getMessage());
            }
        }
    }

    private String askWichStationToPrint() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Insert the nickname of the player of which you want to see the station: ");
        for (var c : clientBoard.getOtherplayers()) {
            System.out.print(lightBlue + c.getNickname() + reset + " ");
        }
        printSpace();
        return scanner.nextLine();
    }

    @Override
    public void printSpace() {
        for (int i = 0; i < 4; i++) {
            System.out.println();
        }
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
