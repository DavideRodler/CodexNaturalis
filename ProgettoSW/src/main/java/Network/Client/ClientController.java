package Network.Client;

import Network.Cli2;
import Network.Client.RMI.RmiClient;
import Network.Server.VirtualServer;
import View.UI;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotValidMoveException;
import model.Player;
import model.PlayingStation;
import model.cards.CardObjective;
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
            Player myplayer = new Player(nickname, token, new PlayingStation(new HashMap<>()), 0, null);
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
        CardStarting cardStarting = (CardStarting) clientModel.getMyplayer().getStation().getCard(40,40);

        //asking starting card face
        ui.showStartingCard();
        boolean answer = ui.askStartingCardPlayedBack();

        //setting starting card face in local model
        clientModel.getMyplayer().getStation().setCardStartingPlayedBack(null,answer);

        //Printing the board
        ui.printPlayerStation(clientModel.getMyplayer().getStation().getMap());

        //notify the server
        try {
            server.setStartingCardPlayedBack(answer, clientModel.getMyplayer().getNickname(),cardStarting.getId());
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void reciveMyFirstHand() {

        //setting My Hand in the client model
        try {
            clientModel.getMyplayer().setHand(server.getMyHand(clientModel.getMyplayer().getNickname()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        //Printing My hand
        ui.printMyHand();

    }

    public synchronized void setupOfSecretObjective(){
        //printing common objectives
        ui.printCommonObjectives();

        //getting selectable objectives from server and setting it on local model
        try {
            clientModel.getMyplayer().setSelectibleObjectives(server.getSelectableObjectives(clientModel.getMyplayer().getNickname()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        //printing selectible objectives
        ui.printSelectableObjectives();

        //asking secret objective
        int answer = ui.askObjectiveCard();

        //setting secret objective in local model
        clientModel.getMyplayer().setSecretObjective(clientModel.getMyplayer().getSelectibleObjectives().get(answer));

        //sending secret objective to server
        try {
            server.setSecretObjective(clientModel.getMyplayer().getNickname(),clientModel.getMyplayer().getSecretObjective().getId());
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        System.out.println("ciao");
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
                clientModel.getMyplayer().getStation().isPlayable(cardchoosen, answer[2], answer[3]);
                cardId = cardchoosen.getId();
                break;
            }
            catch (InvalidPlacingCondition e) {
                ui.showErrorMessage(e.getMessage());
            }
        }
        //updating the local model

        //adding card to the station
        clientModel.getMyplayer().getStation().addCard(cardchoosen,answer[2],answer[3],answer[1] == 0,clientModel.getMyplayer().getNickname());
        try {
            //removing the card from the hand
            clientModel.getMyplayer().removeCardFromHand(cardchoosen.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // updating player points

        ui.showUpdatedStation();

        //sending the move to the server
        try {
            server.addCardToStation(clientModel.getMyplayer().getNickname(),cardId, answer[1] == 0, answer[2], answer[3]);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        ui.print4CentralCards();
        int cardToDraw = ui.askWhichCardToDraw();
//        try {
//            server.drawCard(clientModel.getMyplayer().getNickname(), cardToDraw);
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        }
//
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
                } else {
                    for (ReductPlayer player : clientModel.getOtherplayers()) {
                        if (player.getNickname().equals(cardStartingMessage.getNickname())) {
                            player.getStation().setCardStarting(cardStartingMessage.getCardStarting(), null);
                        }
                    }
                }
                break;
            case "CardStartingPlayedBack":
                CardStartingPlayedBackMessage cardStartingPlayedBackMessage = (CardStartingPlayedBackMessage) message;
                for (ReductPlayer player : clientModel.getOtherplayers()) {
                    if (player.getNickname().equals(cardStartingPlayedBackMessage.getNickname())) {
                        player.getStation().setCardStartingPlayedBack(null, cardStartingPlayedBackMessage.isPlayedBack());
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
                for (ReductPlayer player : clientModel.getOtherplayers()) {
                    if (player.getNickname().equals(cardAddedToStationMessage.getNickname())) {
                        player.getStation().addCard(cardAddedToStationMessage.getCard(), cardAddedToStationMessage.getX(), cardAddedToStationMessage.getY(), cardAddedToStationMessage.getPlayedBack(), cardAddedToStationMessage.getNickname());
                        ui.printOtherPlayersStation(cardAddedToStationMessage.getNickname());
                    }
                }
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
}
