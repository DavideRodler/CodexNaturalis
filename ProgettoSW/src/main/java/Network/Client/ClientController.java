package Network.Client;

import Socket.Messages.CurrentPlayerInfoMessage;
import View.CLI.Cli2;
import Network.Client.RMI.RmiClient;
import Network.Server.VirtualServer;
import View.GUI.Gui;
import View.UI;
import exception.*;
import model.Player;
import model.PlayingStation;
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
    private UI ui;
    private ClientBoard clientModel;
    private VirtualServer server;
    private RmiClient rmiClient;

    public ClientController(VirtualServer server, RmiClient rmiClient, String uiChoice) {
        this.clientModel = new ClientBoard(null, null, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(), null);
        this.server = server;
        this.rmiClient = rmiClient;
        if(uiChoice.equals("GUI"))
        {
            ui = new Gui();
            ui.launchGui(clientModel, this);
        }
        else
            ui= new Cli2(clientModel, this);

        ui.showGameTitle();
    }

    public ClientBoard getClientModel() {
        return clientModel;
    }

    public void setupOfnicknameAndToken() {
        try {
            String nickname = null;
            TokenEnum token;
            do {
                nickname = ui.askNickname();
                token = ui.askToken(server.getAvailableTokens());
            } while (!server.checkNicknameAvailability(nickname) || !server.checkTokenAvailability(token));

            //adding player to client model
            Player myplayer = new Player(nickname, token, new PlayingStation(new HashMap<>()), 0, new ArrayList<>());
            clientModel.setMyplayer(myplayer);

            //adding player to server
            server.addPlayer(nickname, token, rmiClient);

        } catch (NotValidMoveException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void showFourCentralCards() {
        ui.print4CentralCardsAndDecks();
    }

    public synchronized void setupOfStartingCard() {

        //asking starting card face
        ui.showStartingCard();
        boolean answer = ui.askStartingCardPlayedBack();

        //setting starting card face in server
        try {
            CardStarting cardStarting = (CardStarting) clientModel.getMyplayer().getStation().getCard(40,40);
            server.setStartingCardPlayedBack(answer, clientModel.getMyplayer().getNickname(),cardStarting.getId());
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public void showPlayerHand() {
        ui.printPlayerHand();
    }

    public synchronized void setupOfSecretObjective(){
        //printing selectible objectives
        ui.printSelectableObjectives();

        //asking secret objective
        int answer = ui.askObjectiveCard();

        try {
            server.setSecretObjective(clientModel.getMyplayer().getNickname(), clientModel.getMyplayer().getSelectibleObjectives().get(answer).getId());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * This method is called when the player is the current player
     */
    public synchronized void notifyItIsYourTurn() {

        ui.askMenuAction();

        try {
            server.updatePlayerReadyForNewMenu(1);
            server.startTurn();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotMyTurnException e) {
            throw new RuntimeException(e);
        }
    }

    private void ChatPrinter(String chatChoice) throws RemoteException {
        switch(chatChoice) {
            case "1":
                String Message;
                ui.printGloablChatInfo();
                break;
            case "2":
                ui.printPrivateChatInfo();
                break;
        }
    }


    /**
     * This method is called when the player is not the current player
     * Prints the menu and waits for the player to choose an action while it is not his turn
     * @param currentPlayer
     */
    public synchronized void notifyIsNotYourTurn(String currentPlayer) {

        ui.printMenuNotMyTurn(currentPlayer);
        ui.askNotMyTurnMenuAction(currentPlayer);

        try{
            server.updatePlayerReadyForNewMenu(1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
        //} while(startNotTurn);

    public void updateModel(Message message) throws RemoteException, NonePlayerFoundException {
        switch (message.getType()) {
            case "PRIVATE":
                PrivateChatMessage privateMessage = (PrivateChatMessage) message;
                clientModel.updateChat( "PRIVATE", privateMessage.getNicknameSender(), privateMessage.getNicknameReceiver(), privateMessage.getMessage());
                break;
            case "GLOBAL":
                ChatMessage chatMessage = (ChatMessage) message;
                clientModel.updateChat("GLOBAL", chatMessage.getNickname(), null ,chatMessage.getMessage());
                break;
            case "typeOfChat":
                TypeOfChatMessage typeOfChatMessage = (TypeOfChatMessage) message;
                clientModel.addNewPrivateChat(typeOfChatMessage.getNickname1(), typeOfChatMessage.getNickname2());
                break;
            case "CurrentPlayerInfo":
                CurrentPlayerInfoMessage currentPlayerInfoMessage = (CurrentPlayerInfoMessage) message;
                clientModel.setCurrentPlayer(currentPlayerInfoMessage.getCurrentPlayerName());
                break;
            case "ChangeState":
                ChangeStateMessage changeStateMessage = (ChangeStateMessage) message;
                clientModel.setGameState((changeStateMessage).getGameState());
                break;
            case "PlayersInfo":
                PlayersInfoMessage playerInfoMessage = (PlayersInfoMessage) message;
                HashMap<String, TokenEnum> playersMap = playerInfoMessage.getPlayers();
                for (String nickname : playersMap.keySet()) {
                    if (!nickname.equals(clientModel.getMyplayer().getNickname())) {
                        clientModel.getOtherplayers().add(new ReductPlayer(nickname, playersMap.get(nickname)));
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


                            //ui.printOtherPlayersStation(cardAddedToStationMessage.getNickname());
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

        }

    }

    public void messageToServerhandler(Message message) throws RemoteException, InvalidPlacingCondition, NotMyTurnException {
        switch(message.getType()){
            case "NICKNAME_CHOICE" :
                NicknameMessage nicknameMessage = (NicknameMessage) message;
                break;
            case "TOKEN_CHOICE" :
                TokenMessage tokenMessage = (TokenMessage) message;
                break;
            case "STARTING_CARD_CHOICE" :
                StartingCardMessage startingCardMessage = (StartingCardMessage) message;
                break;
            case "SECRET_OBJ_CHOICE" :
                SecretObjMessage secretObjMessage = (SecretObjMessage) message;
                break;
            case "PLACE_CARD_CHOICE" :
                PlaceCardMessage placeCardMessage = (PlaceCardMessage) message;
                server.addCardToStation(clientModel.getMyplayer().getNickname(), placeCardMessage.getCardID(), placeCardMessage.getPlayedBack(), placeCardMessage.getXcoordinate(), placeCardMessage.getYcoordinate());
                break;
            case "DRAW_CARD_CHOICE" :
                DrawCardMessage drawCardMessage = (DrawCardMessage) message;
                server.addCardFromCentralCardsToPlayerHand(clientModel.getMyplayer().getNickname(), drawCardMessage.getCardID());
                break;
            case "DRAW_DECK_CHOICE" :
                DrawDeckMessage drawDeckMessage = (DrawDeckMessage) message;
                server.addCardFromDeckToPlayerHand(clientModel.getMyplayer().getNickname(), drawDeckMessage.getSelection());
                break;
            case "CHAT_CHOICE" :
                ChatChoiceMessage chatChoiceMessage = (ChatChoiceMessage) message;
                ChatPrinter(chatChoiceMessage.getChatChoice());
                break;
            case "PRIVATE_CHAT_NICKNAME_CHOICE":
                PrivateChatNicknameMessage privateChatNicknameMessage = (PrivateChatNicknameMessage) message;
                ui.askPrivateMessage(privateChatNicknameMessage.getNickname());
                break;
            case "PRIVATE" :
                PrivateChatMessage privateChatMessage = (PrivateChatMessage) message;
                server.sendPrivateMessage(privateChatMessage.getNicknameSender(), privateChatMessage.getNicknameReceiver(), privateChatMessage.getMessage());
                break;
            case "GLOBAL":
                ChatMessage chatMessage = (ChatMessage) message;
                server.sendGlobalMessage(chatMessage.getNickname(), chatMessage.getMessage());
                break;
        }
    }



    public void setupOfPlayersNumber() {
        try {
            server.setPlayerNumber(ui.askPlayerNumber());
        } catch (RemoteException | ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {
        System.out.println("Another is selecting the number of players, do you want to try again?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if(answer.isEmpty()) {
            rmiClient.connectToServer();
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



}
