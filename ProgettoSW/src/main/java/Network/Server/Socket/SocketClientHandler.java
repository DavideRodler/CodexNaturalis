package Network.Server.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.ServerToClientCommunication;
import Socket.Messages.Message;
import exception.ChangedStateException;
import exception.NotValidMoveException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SocketClientHandler implements VirtualView {
    final BufferedReader input;
    final PrintWriter output;
    final Server server;

    public SocketClientHandler(BufferedReader input, PrintWriter output, Server server) {
        this.input = input;
        this.output = output;
        this.server = server;
    }

    public void runVirtualView() throws IOException {
        String line;
        while ((line = input.readLine()) != null) {
            switch (line) {
                case "setPlayerNumber" -> {
                    int number = Integer.parseInt(input.readLine());
                    try {
                        server.setPlayerNumber(number);
                    } catch (NotValidMoveException e) {
                        throw new RuntimeException(e);
                    } catch (ChangedStateException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> System.out.println("invalid message");
                // handle the message
            }
        }
    }


    @Override
    public void setupOfnicknameAndToken() throws RemoteException {
        this.output.println("setupOfNicknameAndToken");
        this.output.flush();

    }

    @Override
    public void notifyStartSetupOfStartingCard() throws RemoteException {

    }

    @Override
    public void showFourCentralCards() throws RemoteException {

    }

    @Override
    public void setupOfPlayersNumber() throws RemoteException {
        this.output.println("setupOfPlayersNumber");
        this.output.flush();

    }

    @Override
    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {

    }

    @Override
    public void notifyWaitingForPlayersToJoin() throws RemoteException {

    }

    @Override
    public void notifyAllPlayersConnected() throws RemoteException {

    }

    @Override
    public void notifyGameAlreadyStarted() throws RemoteException {

    }

    @Override
    public void notifyItIsYourTurn() throws RemoteException {

    }

    @Override
    public void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) throws RemoteException {

    }

    @Override
    public void showHandsAndCommonObjectives() throws RemoteException {

    }

    @Override
    public void setupOfSecretObjective() throws RemoteException {

    }

    @Override
    public void update(Message message) throws RemoteException {

    }
}
