package Network.Client;

import Network.Client.RMI.RmiClientToServer;
import Network.Client.Socket.SocketClient;
import Network.Server.VirtualServer;
import Socket.Messages.Chat.AddPrivateChatMessage;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import View.UI;
import exception.InvalidPlacingCondition;
import model.Player;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.TokenEnum;
import Socket.Messages.Message;
import Socket.Messages.*;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class is the controller of the client
 */
public class ClientController {

    private UI ui;
    private final ClientBoard clientModel;
    private ClientToServerCommunication clientToServerCommunication;
    private final String ip;

    /**
     * This method is used to get the client model
     * @return the client model
     */
    public ClientBoard getClientModel() {
        return clientModel;
    }

    public void setClientToServerCommunication(ClientToServerCommunication clientToServerCommunication) {
        this.clientToServerCommunication = clientToServerCommunication;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public ClientController(String ip){
        this.clientModel = new ClientBoard(null, null, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(), null);
        this.ip = ip;
    }



    /**
     * This method is used to set the nickname of the player
     */
    public void setupOfnickname(){
        ui.askNickname();
    }


    /**
     * This method is used to set the nickname of the player in the server
     * @param nickname  is the name of the player to add
     */
    public void setupOfnickname_UI(String nickname){
        //adding player to client model
        Player myplayer = new Player(nickname, new PlayingStation(new HashMap<>()), 0, new ArrayList<>());
        clientModel.setMyplayer(myplayer);

        //adding player to server
        clientToServerCommunication.addPlayer(nickname);
    }

    /**
     * This method is used to display the central cards after the sever has distribuited
     */
    public synchronized void showFourCentralCards() {
        ui.print4CentralCards();
    }

    /**
     * This method is start the setup of the starting card
     */
    public synchronized void setupOfStartingCard() {
        ui.askStartingCardPlayedBack();
    }

    /**
     * This method is used to notify the server to set the starting card
     * @param answer the answer of the player
     */
    public synchronized void setupOfStartingCard_UI(boolean answer){
        //setting starting card face in server
        CardStarting cardStarting = (CardStarting) clientModel.getMyplayer().getStation().getCard(40,40);
        clientToServerCommunication.setStartingCardPlayedBack(answer, clientModel.getMyplayer().getNickname(),cardStarting.getId());
    }


    /**
     * This method is start the setup of the private objective card
     */
    public synchronized void setupOfSecretObjective() {
        ui.askObjectiveCard();
    }


    /**
     * This method is used to notify the server to set the objective card
     * @param answer the answer of the player
     */
    public synchronized void setupOfSecretObjective_UI(int answer){
        clientToServerCommunication.setSecretObjective(clientModel.getMyplayer().getNickname(), clientModel.getMyplayer().getSelectibleObjectives().get(answer).getId());
    }


    /**
     * This method is used to start the game loop
     */
    public void startGameLoop() {
        ui.startGame();
    }


    /**
     * notify the server to add a card
     * @param playedbakck
     * @param x
     * @param y
     * @param cardId
     */
    public void playCardOnPlayngStation_UI(boolean playedbakck, int x, int y, int cardId) {

        clientToServerCommunication.addCardToStation(clientModel.getMyplayer().getNickname(), cardId, playedbakck, x, y);    //try to add the card to local model
    }

    /**
     * this method is used to handle the result of the card added to the station
     * after the server has tried to add it
     *
     * @param result
     * @param message
     */
    public void handleResultOfCardAdded(boolean result, String message) {
        if(result) {
            ui.printCardAddedSuccessfully();
        }
        else {
            ui.printCardNotAdded(message);
        }
    }


    /**
     * this method is used to Send to server which card the player has choosen to draw
     * @param choice
     */
    public void startAfterCardHasBeenAddedToStation_UI(Integer choice) {

        int selection = choice;
        CardResource card;
        if(selection>=1 && selection<=4){
            if(selection>=3) {
                ArrayList<CardResource> centralCardsResource = clientModel.getCentralCardsResource();
                card = centralCardsResource.get(selection - 3);
            }
            else {
                ArrayList<CardGold> centralCardsGold = clientModel.getCentralCardsGold();
                card = centralCardsGold.get(selection - 1);
            }
                clientToServerCommunication.addCardFromCentralCardsToPlayerHand(clientModel.getMyplayer().getNickname(), card.getId());
        }
        else {
                clientToServerCommunication.addCardFromDeckToPlayerHand(clientModel.getMyplayer().getNickname(), selection);
        }
    }


    /**
     * This method is used to update the model of a client
     *
     * @param message contains the update
     */
    public void updateModel(Message message) {
        switch (message.getType()) {
            case "CurrentPlayer":
                CurrentPlayerMessage currentPlayerMessage = (CurrentPlayerMessage) message;
                clientModel.setCurrentPlayer(currentPlayerMessage.getCurrentPlayer());
                ui.updateCurrentPlayer();
                break;
            case "PRIVATE":
                PrivateChatMessage privateMessage = (PrivateChatMessage) message;
                clientModel.updatePrivateChat( "PRIVATE", privateMessage.getNicknameSender(), privateMessage.getNicknameReceiver(), privateMessage.getMessage());
                ui.updatePrivateChat();
                break;
            case "GLOBAL":
                GlobalChatMessage chatMessage = (GlobalChatMessage) message;
                clientModel.updateGlobalChat("GLOBAL", chatMessage.getNickname(),chatMessage.getMessage());
                ui.updateGlobalChat();
                break;
            case "ADD_PRIVATE_CHAT":
                AddPrivateChatMessage typeOfChatMessage = (AddPrivateChatMessage) message;
                clientModel.addNewPrivateChat(typeOfChatMessage.getNickname1(), typeOfChatMessage.getNickname2());
                break;
            case "ChangeState":
                ChangeStateMessage changeStateMessage = (ChangeStateMessage) message;
                clientModel.setGameState((changeStateMessage).getGameState());
                break;
            case "PlayersInfo":
                PlayersInfoMessage playerInfoMessage = (PlayersInfoMessage) message;
                ArrayList<String> playes = playerInfoMessage.getPlayers();
                for (String nickname : playes) {
                    if (!nickname.equals(clientModel.getMyplayer().getNickname())) {
                        clientModel.getOtherplayers().add(new ReductPlayer(nickname));
                    }
                }
                break;
            case "TokenOfPlayer":
                TokenOfPlayerMessage tokenOfPlayerMessage = (TokenOfPlayerMessage) message;
                if (clientModel.getMyplayer().getNickname().equals(tokenOfPlayerMessage.getNickname())) {
                    clientModel.getMyplayer().setToken(tokenOfPlayerMessage.getToken());
                }
                else {
                    for (ReductPlayer player : clientModel.getOtherplayers()) {
                        if (player.getNickname().equals(tokenOfPlayerMessage.getNickname())) {
                            player.setToken(tokenOfPlayerMessage.getToken());
                        }
                    }
                }
                break;
            case "CommonObjectives":
                CommonObjectivesMessage commonObjectivesMessage = (CommonObjectivesMessage) message;
                clientModel.setFirstObjective(commonObjectivesMessage.getFirstobjective());
                clientModel.setSecondObjective(commonObjectivesMessage.getSecondobjective());
                break;
            case "CardStarting":
                CardStartingMessage cardStartingMessage = (CardStartingMessage) message;
                if (clientModel.getMyplayer().getNickname().equals(cardStartingMessage.getNickname())) {
                    clientModel.getMyplayer().getStation().setCardStarting(cardStartingMessage.getCardStarting(), null);
                }
                else {
                    for (ReductPlayer player : clientModel.getOtherplayers()) {
                        if (player.getNickname().equals(cardStartingMessage.getNickname())) {
                            player.getStation().setCardStarting(cardStartingMessage.getCardStarting(), null);
                        }
                    }
                }
                break;

            case "CardStartingPlayedBack":
                CardStartingPlayedBackMessage cardStartingPlayedBackMessage = (CardStartingPlayedBackMessage) message;
                if (clientModel.getMyplayer().getNickname().equals(cardStartingPlayedBackMessage.getNickname())) {
                    clientModel.getMyplayer().getStation().setCardStartingPlayedBack(null, cardStartingPlayedBackMessage.isPlayedBack());
                }
                else {
                    for (ReductPlayer player : clientModel.getOtherplayers()) {
                        if (player.getNickname().equals(cardStartingPlayedBackMessage.getNickname())) {
                            player.getStation().setCardStartingPlayedBack(null, cardStartingPlayedBackMessage.isPlayedBack());
                        }
                    }
                }
                break;
            case "CentralCardResource":
                CentralCardResourceMessage centralCardResourceMessage = (CentralCardResourceMessage) message;
                clientModel.getCentralCardsResource().add(centralCardResourceMessage.getCardResource());
                break;

            case "CentralCardGold":
                CentralCardGoldMessage centralCardGoldMessage = (CentralCardGoldMessage) message;
                clientModel.getCentralCardsGold().add(centralCardGoldMessage.getCardGold());
                break;

            case "CardAddedToStation":
                CardAddedToStationMessage cardAddedToStationMessage = (CardAddedToStationMessage) message;
                if (clientModel.getMyplayer().getNickname().equals(cardAddedToStationMessage.getNickname())){
                    try {
                        int points = clientModel.getMyplayer().getStation().addCard(cardAddedToStationMessage.getCard(),cardAddedToStationMessage.getX(), cardAddedToStationMessage.getY(), cardAddedToStationMessage.getPlayedBack(), null);
                        clientModel.getMyplayer().setPoints(clientModel.getMyplayer().getPoints() + points);
                        ui.updateViewAfterCardAddedToStation(cardAddedToStationMessage.getCard(), cardAddedToStationMessage.getX(), cardAddedToStationMessage.getY(), cardAddedToStationMessage.getPlayedBack());
                    } catch (InvalidPlacingCondition e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    for (ReductPlayer player : clientModel.getOtherplayers()) {
                        if (player.getNickname().equals(cardAddedToStationMessage.getNickname())) {
                            try {
                                int points = player.getStation().addCard(cardAddedToStationMessage.getCard(), cardAddedToStationMessage.getX(), cardAddedToStationMessage.getY(), cardAddedToStationMessage.getPlayedBack(), cardAddedToStationMessage.getNickname());
                                player.setPoints(player.getPoints() + points);

                            } catch (InvalidPlacingCondition e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                break;

            case "CardAddedToHand":
               CardAddedToHandMessage cardAddedToHandMessage = (CardAddedToHandMessage) message;
               clientModel.getMyplayer().getHand().add(cardAddedToHandMessage.getCardAdded());
               ui.updateHand();
               break;

            case "CardRemovedFromHand":
                CardRemovedFromHandMessage cardRemovedFromHandMessage = (CardRemovedFromHandMessage) message;
                try {
                    clientModel.getMyplayer().removeCardFromHand(cardRemovedFromHandMessage.getID());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;

            case "SelectableObj":
                SelectableObjMessage selectableObjMessage = (SelectableObjMessage) message;
                clientModel.getMyplayer().setSelectibleObjectives(selectableObjMessage.getSelectableObjectives());
                break;
            case "SelectionOfSecretObj":
                SelectionOfSecretObjMessage selectionOfSecretObjMessage = (SelectionOfSecretObjMessage) message;
                clientModel.getMyplayer().setSecretObjective(clientModel.getMyplayer().getSelectibleObjectives().stream()
                        .filter(card -> card.getId() == selectionOfSecretObjMessage.getCardId())
                        .findFirst().get());
                break;
            case "CentralCardResourceRemoved":
                CentralCardResourceRemovedMessage centralCardResourceRemovedMessage = (CentralCardResourceRemovedMessage) message;
                clientModel.getCentralCardsResource().removeIf(c -> c.getId() == centralCardResourceRemovedMessage.getCardID());
                clientModel.getCentralCardsGold().removeIf(c -> c.getId() == centralCardResourceRemovedMessage.getCardID());
                break;
            case "BackOfFirstCardOfDeck":
                BackOfFirstCardOfDeckMessage backOfFirstCardOfDeckMessage = (BackOfFirstCardOfDeckMessage) message;
                if (backOfFirstCardOfDeckMessage.isDeckGold()){
                    clientModel.setBackOfGoldDeck(backOfFirstCardOfDeckMessage.getBackOfCard());
                }
                else {
                    clientModel.setBackOfResourceDeck(backOfFirstCardOfDeckMessage.getBackOfCard());
                }
                break;
        }
    }

    /**
     * Used to ask the number of player of a game
     */
    public void setupOfPlayersNumber() {
        System.out.println("You are the first player, select the number of players of the game");
        Scanner scanner = new Scanner(System.in);
        String number;
        do {
            number = scanner.nextLine();
        } while (!number.equals("2") && !number.equals("3") && !number.equals("4"));
        setupOfPlayersNumber_CLI(Integer.parseInt(number));
    }

    /**
     * Sends the number of player to the server
     * @param number
     */
    public void setupOfPlayersNumber_CLI(int number){
        clientToServerCommunication.setPlayerNumber(number);
    }


    /**
     * notify another player is selecting the number of players and tries again the connection
     */
    public void notifyAnotherPlayerSettingNumOfPlayers() {
        System.out.println("Another is selecting the number of players, do you want to try again?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if(answer.isEmpty()) {
            try {
                clientToServerCommunication.connectToServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyAllPlayersConnected() {
        System.out.println("All players are connected, the game is starting");
    }

    public void notifyWaitingForPlayersToJoin() {
        System.out.println("You have joined the game!");
        System.out.println("Waiting for other players to join");
    }

    public void notifyGameAlreadyStarted() {
        System.out.println("The game has already started, you can't join now");
    }

    public void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) {
        ui.printFinalPoints(scoreBoard);
    }

    public synchronized void showHandsAndCommonObjectives() {
        ui.printCommonObjectives();
    }

    public void notifyNicknameAlreadyTaken() {
        ui.printNicknameAlreadyTaken();
        setupOfnickname();
    }

    public void notifyTokenAlreadyTaken() {
        ui.printTokenAlreadyTaken();
        setupOfToken();
    }

    public void setupOfToken() {
        ui.askToken(clientModel.getAvailableTokens());
    }

    public void setupOfToken_CLI(TokenEnum token){
        clientToServerCommunication.setToken(clientModel.getMyplayer().getNickname(), token);
    }


    public void sendGlobalMessage(GlobalChatMessage global) {
        clientToServerCommunication.sendGlobalMessage(global);
    }

    public void sendPrivateMessage(PrivateChatMessage privateMessage) {
        clientToServerCommunication.sendPrivateMessage(privateMessage);
    }


    /**
     * Method used after the server goes down
     * It tries to reconnect to the server
     * If there is not a sever available it waits for the user to press enter
     */
    public synchronized void tryToReconnect() {
        System.out.println("Press enter to try to reconnect");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        if (clientToServerCommunication instanceof RmiClientToServer) {

            VirtualServer server;
            Registry registry = null;
            while (true) {
                try {
                    registry = LocateRegistry.getRegistry(ip, 16000);
                    server = (VirtualServer) registry.lookup("MyServer");
                    break;
                } catch (RemoteException | NotBoundException e) {
                    System.out.println("Server is not available, press enter to try again");
                    scanner.nextLine();
                }
            }

            RmiClientToServer Newclient = null;
            try {
                Newclient = new RmiClientToServer(server);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Newclient.setClientController(this);

            this.clientToServerCommunication = Newclient;
            clientToServerCommunication.reconnectToServer(clientModel.getMyplayer().getNickname());

            System.out.println("Reconnected successfully");
        } else if (clientToServerCommunication instanceof SocketClient) {
            SocketClient client;
            //creating the soket communication
            while (true) {

                try {

                    Socket serverSocket = new Socket(ip, 16001);

                    InputStream socketRx = serverSocket.getInputStream();
                    OutputStream socketTx = serverSocket.getOutputStream();

                    client = new SocketClient(new ObjectInputStream(socketRx), new ObjectOutputStream(socketTx));
                    break;
                } catch (IOException e) {
                    System.out.println("Server is not available, press enter to try again");
                    scanner.nextLine();
                }
            }

            client.setClientController(this);
            client.run();
            this.clientToServerCommunication = client;
            client.reconnectToServer(clientModel.getMyplayer().getNickname());

            System.out.println("Reconnected successfully");
        }

    }
}
