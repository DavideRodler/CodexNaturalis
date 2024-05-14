package Network.Client;

import Network.Cli2;
import Network.Server.VirtualServer;
import View.UI;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.client.ClientBoard;
import model.enums.TokenEnum;
import socket.Messages.ChangeStateMessage;
import socket.Messages.Message;
import socket.Messages.PlayersInfoMessage;

import java.rmi.RemoteException;

public class ClientController {
    private UI ui;
    private ClientBoard clientModel;
    private VirtualServer server;

    public ClientController(VirtualServer server) {
        this.clientModel = new ClientBoard(null, null, null, null, null, null, null);
        this.server = server;
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
                server.startSetupOfNicknameAndToken();
            } else {
                    System.out.println("waiting to start game");
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

                    server.addPlayer(nickname, token);

                    } catch (NotValidMoveException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

    public void gameLoop(){
        while (true) {
            switch (clientModel.getGameState()) {
                case SET_PLAYER_NUMBER:
                    break;
                case SET_NAME_AND_TOKEN:
                    setupOfnicknameAndToken();
                    break;
                default:
                    break;
            }

        }
    }

    public void updateModel(Message message) throws RemoteException {
        switch (message.getType()) {
            case "ChangeState":
                ChangeStateMessage changeStateMessage = (ChangeStateMessage) message;
                clientModel.setGameState((changeStateMessage).getGameState());
                break;
            case "PlayersInfoMessage":
                PlayersInfoMessage playersInfoMessage = (PlayersInfoMessage) message;
                break;
        }
    }

    }
