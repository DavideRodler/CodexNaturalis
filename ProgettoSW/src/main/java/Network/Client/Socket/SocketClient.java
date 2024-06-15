package Network.Client.Socket;

import Network.Client.ClientController;
import Network.Client.ClientToServerCommunication;
import Network.Client.RMI.VirtualView;
import Network.Server.VirtualServer;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SocketClient implements ClientToServerCommunication {
    final BufferedReader input;
    final VirtualSocketServer server;
    private ClientController clientController;

    public SocketClient(BufferedReader input,PrintWriter output) {
        this.input = input;
        this.server = new VirtualSocketServer(output);
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
    public void run() throws RemoteException{
        new Thread( () -> {
            try {
                runVirtualServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void runVirtualServer() throws IOException {
        String line;
        // Read message type
        while ((line = input.readLine()) != null) {
            // Read message and perform action
            switch (line) {
                case "setupOfPlayersNumber" -> clientController.setupOfPlayersNumber();
                case "setupOfNicknameAndToken" -> clientController.setupOfnicknameAndToken();
                default -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }

    @Override
    public void connectToServer() throws Exception {

    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() {
        return null;
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) {
        return false;
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) {
        return false;
    }



    @Override
    public void addPlayer(String nickname, TokenEnum token) {

    }

    @Override
    public void setStartingCardPlayedBack(boolean playedBack, String nickname, int id) {

    }



    @Override
    public void setSecretObjective(String nickname, int id) {

    }

    @Override
    public void addCardToStation(String nickname, int cardid, boolean playedback, int x, int y) throws InvalidPlacingCondition {

    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int cardid) {

    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardid) {

    }

    @Override
    public void startTurn() {

    }


    @Override
    public void setPlayerNumber(int num) {
        try {
            server.setPlayerNumber(num);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotValidMoveException e) {
            throw new RuntimeException(e);
        } catch (ChangedStateException e) {
            throw new RuntimeException(e);
        }

    }
}
