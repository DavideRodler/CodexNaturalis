package Network.Server.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.ServerToClientCommunication;
import Socket.Messages.Message;
import Socket.Messages.ServerToClient.ActionMessage;
import exception.ChangedStateException;
import exception.NotValidMoveException;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SocketClientHandler implements VirtualView {
    final ObjectInputStream input;
    final ObjectOutputStream output;
    final Server server;

    public SocketClientHandler(ObjectInputStream input, ObjectOutputStream output, Server server) {
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
        Message message = new ActionMessage("setupOfNicknameAndToken");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void notifyStartSetupOfStartingCard() throws RemoteException {
//        this.output.println("notifyStartSetupOfStartingCard");
//        this.output.flush();

    }

    @Override
    public void showFourCentralCards() throws RemoteException {
//        this.output.println("showFourCentralCards");
//        this.output.flush();

    }

    @Override
    public void setupOfPlayersNumber() throws RemoteException {
//        this.output.println("setupOfPlayersNumber");
//        this.output.flush();

    }

    @Override
    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {
//        this.output.println("notifyAnotherPlayerSettingNumOfPlayers");
//        this.output.flush();
    }

    @Override
    public void notifyWaitingForPlayersToJoin() throws RemoteException {
        Message message = new ActionMessage("notifyWaitingForPlayersToJoin");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyAllPlayersConnected() throws RemoteException {
        Message message = new ActionMessage("notifyAllPlayersConnected");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyGameAlreadyStarted() throws RemoteException {
        Message message = new ActionMessage("notifyGameAlreadyStarted");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyItIsYourTurn() throws RemoteException {
        Message message = new ActionMessage("notifyItIsYourTurn");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyGameFinished(LinkedHashMap<String, ArrayList<Integer>> scoreBoard) throws RemoteException {
        //TODO
    }

    @Override
    public void showHandsAndCommonObjectives() throws RemoteException {
        Message message = new ActionMessage("showHandsAndCommonObjectives");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setupOfSecretObjective() throws RemoteException {
        Message message = new ActionMessage("setupOfSecretObjective");
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Message message) throws RemoteException {
        try {
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
