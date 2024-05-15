package Network.Client;

import Network.Cli2;
import Network.Client.RMI.RmiClient;
import Network.Server.VirtualServer;
import View.UI;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.Player;
import model.PlayingStation;
import model.cards.CardObjective;
import model.cards.CardStarting;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.TokenEnum;
import socket.Messages.*;
//import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientController {
    private UI ui;
    private ClientBoard clientModel;
    private VirtualServer server;
    private RmiClient rmiClient;

    public ClientController(VirtualServer server, RmiClient rmiClient) {
        this.clientModel = new ClientBoard(null, null, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(), null);
        this.server = server;
        this.rmiClient = rmiClient;
        ui = new Cli2();
    }

    public ClientBoard getClientModel() {
        return clientModel;
    }

    public void StartGame() {
        ui.showGameTitle();
        try {
            if (server.askFirstPlayertoConnect()) {
                server.setPlayerNumber(ui.askPlayerNumber());
                server.checkAllPlayersConnected();
            } else {
                if(server.morePlayersNeeded()) {
                    System.out.println("waiting to start game");
                    server.checkAllPlayersConnected();
                }
                else {
                    System.out.println("can't join game, too many players");
                    server.disconnectClient(rmiClient);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            System.out.println("Not valid move");
        }
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
            server.addPlayer(nickname, token);

        } catch (NotValidMoveException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupOfStartingCard() {
        ArrayList<CardObjective> cardObjectiveList = new ArrayList<>();
        cardObjectiveList.add(clientModel.getFirstObjective());
        cardObjectiveList.add(clientModel.getSecondObjective());
        ui.showObjectiveCards(cardObjectiveList);

        ui.showStartingCard((CardStarting) clientModel.getMyplayer().getStation().getCard(40,40));
        for(ReductPlayer p : clientModel.getOtherplayers()) {
            ui.showStartingCard((CardStarting) p.getStation().getCard(40,40));
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
                for(String nickname : playersMap.keySet()) {
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
                if(clientModel.getMyplayer().getNickname().equals(cardStartingMessage.getNickname())) {
                    clientModel.getMyplayer().getStation().setCardStarting(cardStartingMessage.getCardStarting(),null);
                }
                else {
                    for(ReductPlayer player : clientModel.getOtherplayers()) {
                        if(player.getNickname().equals(cardStartingMessage.getNickname())) {
                            player.getStation().setCardStarting(cardStartingMessage.getCardStarting(),null);
                        }
                    }
                }
        }
    }
}
