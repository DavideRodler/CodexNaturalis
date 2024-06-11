package Network.Client;

import View.CLI.Cli2;
import Network.Client.RMI.RmiClient;
import Network.Server.VirtualServer;
import View.UI;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
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
    private UI ui;
    private ClientBoard clientModel;
    private VirtualServer server;
    private RmiClient rmiClient;

    public ClientController(VirtualServer server, RmiClient rmiClient) {
        this.clientModel = new ClientBoard(null, null, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(), null);
        this.server = server;
        this.rmiClient = rmiClient;
        ui = new Cli2(clientModel);
        ui.showGameTitle();
    }

    public ClientBoard getClientModel() {
        return clientModel;
    }

    public void setupOfnicknameAndToken() {
        try {
            String nickname;
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
        ui.print4CentralCards();
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

        //Printing the board
        ui.printPlayerStation(clientModel.getMyplayer().getStation());


        //Print Player hand
        ui.printSetupPlayerHand();


        setupOfSecretObjective();
    }



    public synchronized void setupOfSecretObjective(){
        //printing common objectives
        ui.printCommonObjectives();

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

    public void notifyItIsYourTurn() {
        //showing the board
        ui.printStartOfPlayerTurn();
        //the answer to where to put the card
        Integer[] answer;

        //the card i want to put in the station
        CardResource cardchoosen;
        int cardId;

        while (true) {
            try {
                answer = ui.askCoordinatesOfCards();
                cardchoosen = clientModel.getMyplayer().getHand().get(answer[0]);
                cardId = cardchoosen.getId();

                server.addCardToStation(clientModel.getMyplayer().getNickname(),cardId, answer[1] == 2 , answer[2], answer[3]);    //try to add the card to local model
                break;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            catch (InvalidPlacingCondition e) {
                ui.showErrorMessage(e.getMessage());
            }
        }

        ui.printStationAfterCardHasBeenAdded();

        ui.print4CentralCards();
        int selection = ui.askWhichCardToDraw();

        CardResource card = null;
        if(selection>=1 && selection<=4){
            if(selection>=3) {
                ArrayList<CardResource> centralCardsResource = clientModel.getCentralCardsResource();
                card = centralCardsResource.get(selection - 3);
            }
            else {
                ArrayList<CardGold> centralCardsGold = clientModel.getCentralCardsGold();
                card = centralCardsGold.get(selection - 1);
            }
            try {
                server.addCardFromCentralCardsToPlayerHand(clientModel.getMyplayer().getNickname(), card.getId());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotMyTurnException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                server.addCardFromDeckToPlayerHand(clientModel.getMyplayer().getNickname(), selection);
            } catch (RemoteException | InvalidPlacingCondition e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateModel(Message message) throws RemoteException {
        switch (message.getType()) {
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

        }

    }

    public void setupOfPlayersNumber() {
        try {
            server.setPlayerNumber(ui.askPlayerNumber());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
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
}
