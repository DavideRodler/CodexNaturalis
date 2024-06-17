package Network.Server.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.VirtualServer;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SocketServer implements VirtualServer {
    private ServerSocket listenSocket;
    private Server server;

    public SocketServer(Server server, ServerSocket serverSocket) throws IOException {
        this.server = server;
        this.listenSocket = serverSocket;
    }

    public void RunServer() throws IOException {
        Socket clientSocket = null;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            OutputStream output = clientSocket.getOutputStream();
            InputStream input = clientSocket.getInputStream();
            SocketClientHandler handler = new SocketClientHandler( new ObjectOutputStream(output),new ObjectInputStream(input), server);

            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Override
    public void connectClient(VirtualView client) throws RemoteException {

    }

    @Override
    public void setPlayerNumber(int playerNumber) throws RemoteException, NotValidMoveException, ChangedStateException {

    }

    @Override
    public ArrayList<TokenEnum> getAvailableTokens() throws RemoteException {
        return null;
    }

    @Override
    public boolean checkNicknameAvailability(String nickname) throws RemoteException {
        return false;
    }

    @Override
    public boolean checkTokenAvailability(TokenEnum token) throws RemoteException {
        return false;
    }

    @Override
    public void addPlayer(String nickname, TokenEnum token, VirtualView Myclient) throws RemoteException, ChangedStateException, NotValidMoveException {

    }

    @Override
    public void setStartingCardPlayedBack(boolean playedback, String nickname, int ID) throws ChangedStateException, NotValidMoveException, RemoteException {

    }

    @Override
    public void setSecretObjective(String nickname, Integer id) throws RemoteException, ChangedStateException, NotValidMoveException {

    }

    @Override
    public void addCardToStation(String nickname, int cardId, boolean playedBack, int x, int y) throws RemoteException, InvalidPlacingCondition {

    }

    @Override
    public void addCardFromDeckToPlayerHand(String nickname, int cardId) throws RemoteException, InvalidPlacingCondition {

    }

    @Override
    public void addCardFromCentralCardsToPlayerHand(String nickname, int id) throws RemoteException, NotMyTurnException {

    }

    @Override
    public void startTurn() throws RemoteException, NotMyTurnException {

    }
}
