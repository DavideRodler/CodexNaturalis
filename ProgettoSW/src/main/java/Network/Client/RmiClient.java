package Network.Client;

import Network.Cli2;
import Network.Server.VirtualServer;

import View.UI;
import exception.NotValidMoveException;
import model.PlayingBoard;
import model.client.ClientBoard;
import model.enums.GameState;
import socket.Messages.ChangeStateMessage;
import socket.Messages.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class RmiClient extends UnicastRemoteObject implements VirtualView {

    final VirtualServer server;
    private UI ui;
    private ClientBoard clientModel;

    public RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
        this.clientModel = new ClientBoard(null, null, null, null, null, null, GameState.SET_PLAYER_NUMBER);
    }

    public ClientBoard getClientModel() {
        return clientModel;
    }

    public void ClientSetup() throws RemoteException, NotValidMoveException {
        this.server.connectClient(this);

        ui = new Cli2(server, this);

        ui.showGameTitle();

        while (true) {
            switch (clientModel.getGameState()) {
                case SET_PLAYER_NUMBER:
                    ui.askPlayerNumber();
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

    @Override
    public void update(Message message) throws RemoteException {
        switch (message.getType()){
            case "ChangeState":
                clientModel.setGameState( ((ChangeStateMessage) message).getGameState() );
                break;
        }

    }
}
