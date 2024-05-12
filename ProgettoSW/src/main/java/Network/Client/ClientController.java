package Network.Client;

import Network.Cli2;
import Network.Client.RMI.RmiClient;
import Network.Server.VirtualServer;
import View.UI;
import exception.ChangedStateException;
import exception.NotValidMoveException;
import model.client.ClientBoard;
import model.enums.GameState;
import socket.Messages.ChangeStateMessage;
import socket.Messages.Message;

import java.rmi.RemoteException;

public class ClientController {
    private UI ui;
    private ClientBoard clientModel;
    private VirtualServer server;

    public ClientController(VirtualServer server) {
        this.clientModel = new ClientBoard(null, null, null, null, null, null, GameState.SET_PLAYER_NUMBER);
        this.server = server;
        ui = new Cli2();
    }

    public ClientBoard getClientModel() {
        return clientModel;
    }

    public void StartGame() {
        ui.showGameTitle();

        while (true) {
            switch (clientModel.getGameState()) {
                case SET_PLAYER_NUMBER:
                    setupofPlayerNumber();
                    break;
                case SET_NAME_AND_TOKEN:
                    ui.askNickname();
                    ui.askToken();
                    break;
                default:
                    break;
            }

        }
    }

    public void updateModel(Message message) throws RemoteException {
        switch (message.getType()) {
            case "ChangeState":
                clientModel.setGameState(((ChangeStateMessage) message).getGameState());
                break;
        }
    }

    public void setupofPlayerNumber(){
        try {
            int input = ui.askPlayerNumber();
            server.setPlayerNumber(input);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotValidMoveException e) {
            System.out.println(e.getMessage());
        } catch (ChangedStateException e) {
            ui.alreadySettedPlayerNumber();
            setupofPlayerNumber();
        }
    }
}
