package Network.Server.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.ServerToClientCommunication;
import Socket.Messages.ClientToServer.AddPlayerMessage;
import Socket.Messages.ClientToServer.SetPlayerNumberMessage;
import Socket.Messages.ClientToServer.SetSecretObjectiveMessage;
import Socket.Messages.ClientToServer.SetStartingCardPlayedBackMessage;
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

    public SocketClientHandler( ObjectOutputStream output,ObjectInputStream input, Server server) {
        this.input = input;
        this.output = output;
        this.server = server;
    }

    public void runVirtualView() throws IOException {
        Message message;
        while (true) {
            try {
                if (((message = (Message) input.readObject()) == null)) break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            switch (message.getType()) {
                case "SetPlayerNumber" -> {
                    SetPlayerNumberMessage setPlayerNumberMessage = (SetPlayerNumberMessage) message;
                    try {
                        server.setPlayerNumber(setPlayerNumberMessage.getNumber());
                    } catch (NotValidMoveException | ChangedStateException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "AddPlayer" -> {
                    AddPlayerMessage addPlayerMessage = (AddPlayerMessage) message;
                    try {
                        server.addPlayer(addPlayerMessage.getNickname(), addPlayerMessage.getToken(), this);
                    } catch (ChangedStateException | NotValidMoveException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "SetStartingCardPlayedBack" -> {
                    SetStartingCardPlayedBackMessage setStartingCardPlayedBackMessage = (SetStartingCardPlayedBackMessage) message;
                    try {
                        server.setStartingCardPlayedBack(setStartingCardPlayedBackMessage.isPlayedBack(), setStartingCardPlayedBackMessage.getNickname(), setStartingCardPlayedBackMessage.getId());
                    } catch (ChangedStateException | NotValidMoveException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "SetSecretObjective" -> {
                    SetSecretObjectiveMessage setSecretObjectiveMessage = (SetSecretObjectiveMessage) message;
                    try {
                        server.setSecretObjective(setSecretObjectiveMessage.getNickname(), setSecretObjectiveMessage.getId());
                    } catch (ChangedStateException | NotValidMoveException e) {
                        throw new RuntimeException(e);
                    }
                }


                default -> System.out.println("invalid message");
                // handle the message
            }
        }
    }




    @Override
    public void setupOfPlayersNumber() throws RemoteException {
        Message message = new ActionMessage("setupOfPlayersNumber");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void notifyAnotherPlayerSettingNumOfPlayers() throws RemoteException {
        Message message = new ActionMessage("notifyAnotherPlayerSettingNumOfPlayers");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyWaitingForPlayersToJoin() throws RemoteException {
        Message message = new ActionMessage("notifyWaitingForPlayersToJoin");
        try {
            this.output.writeObject(message);
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
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        Message message = new ActionMessage("notifyStartSetupOfStartingCard");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showFourCentralCards() throws RemoteException {
        Message message = new ActionMessage("showFourCentralCards");
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showHandsAndCommonObjectives() throws RemoteException {
        Message message = new ActionMessage("showHandsAndCommonObjectives");
        try {
            this.output.writeObject(message);
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
    public void update(Message message) throws RemoteException {
        try {
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
