package Network.Client;

import Socket.Messages.Chat.AddPrivateChatMessage;
import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;
import View.CLI.Cli2;
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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ClientController {
    private final UI ui;
    private final ClientBoard clientModel;
    private final ClientToServerCommunication clientToServerCommunication;
    private boolean readyForNextTurn = false;

    public ClientController(ClientToServerCommunication clientToServerCommunication){
        this.clientModel = new ClientBoard(null, null, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(), null);
        this.clientToServerCommunication = clientToServerCommunication;
        ui = new Cli2(clientModel, this);
        ui.showGameTitle();
    }

    public void imReadyForNextTurn() {
        readyForNextTurn = true;
    }

    public void imNotReadyForNextTurn() {
        readyForNextTurn = false;
    }

    public void setupOfnickname(){
        ui.askNickname();
    }

    public void setupOfnickname_UI(String nickname){
        //adding player to client model
        Player myplayer = new Player(nickname, new PlayingStation(new HashMap<>()), 0, new ArrayList<>());
        clientModel.setMyplayer(myplayer);

        //adding player to server
        clientToServerCommunication.addPlayer(nickname);
    }

    public synchronized void showFourCentralCards() {
        ui.print4CentralCards();
    }

    public synchronized void setupOfStartingCard() {
        ui.askStartingCardPlayedBack();
    }

    public synchronized void setupOfStartingCard_UI(boolean answer){
        //setting starting card face in server
        CardStarting cardStarting = (CardStarting) clientModel.getMyplayer().getStation().getCard(40,40);
        clientToServerCommunication.setStartingCardPlayedBack(answer, clientModel.getMyplayer().getNickname(),cardStarting.getId());
    }



    public synchronized void setupOfSecretObjective() {
        ui.askObjectiveCard();
    }

    public synchronized void setupOfSecretObjective_UI(int answer){

        clientToServerCommunication.setSecretObjective(clientModel.getMyplayer().getNickname(), clientModel.getMyplayer().getSelectibleObjectives().get(answer).getId());

    }

    public void notifyItIsYourTurn() {
        imNotReadyForNextTurn();
        ui.printIsMyTurnMenu();
    }

    public void playCardOnPS_UI(Integer[] answer , CardResource cardchoosen , int cardId) {

        clientToServerCommunication.addCardToStation(clientModel.getMyplayer().getNickname(), cardId, answer[1] == 2, answer[2], answer[3]);    //try to add the card to local model
    }

    public void handleResultOfCardAdded(boolean result, String message) {
        if(result) {
            ui.printCardAddedSuccessfully();
            startAfterCardHasBeenAddedToStation();
        }
        else {
            ui.printCardNotAdded(message);
        }
    }


    public void startAfterCardHasBeenAddedToStation(){

        ui.askWhichCardToDraw();
    }


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


    public void updateModel(Message message) throws RemoteException {
        switch (message.getType()) {
            case "PRIVATE":
                PrivateChatMessage privateMessage = (PrivateChatMessage) message;
                clientModel.updatePrivateChat( "PRIVATE", privateMessage.getNicknameSender(), privateMessage.getNicknameReceiver(), privateMessage.getMessage());
                break;
            case "GLOBAL":
                GlobalChatMessage chatMessage = (GlobalChatMessage) message;
                clientModel.updateGlobalChat("GLOBAL", chatMessage.getNickname(),chatMessage.getMessage());
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
                    } catch (InvalidPlacingCondition e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    for (ReductPlayer player : clientModel.getOtherplayers()) {
                        if (player.getNickname().equals(cardAddedToStationMessage.getNickname())) {

                            //the card is added to the station of other player
                            //it is always possible to add it
                            try {
                                int points = player.getStation().addCard(cardAddedToStationMessage.getCard(), cardAddedToStationMessage.getX(), cardAddedToStationMessage.getY(), cardAddedToStationMessage.getPlayedBack(), cardAddedToStationMessage.getNickname());
                                player.setPoints(player.getPoints() + points);

                            } catch (InvalidPlacingCondition e) {
                                throw new RuntimeException(e);
                            }


                            ui.printOtherPlayersStation(cardAddedToStationMessage.getNickname());
                        }
                    }
                }
                break;

            case "CardAddedToHand":
               CardAddedToHandMessage cardAddedToHandMessage = (CardAddedToHandMessage) message;
               clientModel.getMyplayer().getHand().add(cardAddedToHandMessage.getCardAdded());
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
            default: System.err.println("Invalid message type");
        }

    }

    public void setupOfPlayersNumber() {
        ui.askPlayerNumber();
    }

    public void setupOfPlayersNumber_CLI(int number){
        clientToServerCommunication.setPlayerNumber(number);
    }

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
        ui.printSetupPlayerHand();
        ui.printCommonObjectives();
    }

    public void notifyNicknameAlreadyTaken() {
        System.out.println("The nickname is already taken, please choose another one");
        setupOfnickname();
    }

    public void notifyTokenAlreadyTaken() {
        System.out.println("The token is already taken, please choose another one");
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
}
