package Network.Client;

import Network.Cli2;
import Network.Server.VirtualServer;

import View.UI;
import model.ReducedBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardStarting;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends UnicastRemoteObject implements VirtualView {

    final VirtualServer server;
    private UI cli;
    private ReducedBoard clientModel;
    private final Object lock;


    public RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
        lock = new Object();
    }

    public void ClientSetup() throws RemoteException{
        this.server.connectClient(this);

        cli = new Cli2(server, this);

        String nickname;
        do {
            nickname = cli.askNickname(); // ask for nickname
        } while (server.nicknameCheck(nickname));


        server.addNewPlayer(nickname);

        if (server.numberOfPlayer() == 1) {
            Integer playerNumber = cli.askPlayerNumber();
            server.inizializeLobby(playerNumber);
        }
        System.out.println("Waiting for other players to connect...");
    }

    public void ClientGameSetup () throws RemoteException {
        CardPlaying startingCard;
        CardObjective[] cardObjective;
        synchronized (this.server){
            String name = server.getClientNickname(this);
            startingCard = server.getStartingCard(name);
            cardObjective = server.getObjectiveCards(name);
        }
        Integer choice = cli.askStartingCardFront();
        synchronized (this.server){
            if (choice == 2) {
                startingCard.setPlayingBack(true);
            }
        }

        Integer choiceObjective = cli.askObjectiveCard();

        synchronized (this.server){
            inizializePlayingStation(this, startingCard, choice, cardObjective[choiceObjective-1]);
        }

    }

    private void inizializePlayingStation(VirtualView rmiClient, CardPlaying startingCard, Integer choice, CardObjective cardObjective) throws RemoteException {
        String name = server.getClientNickname(rmiClient);
        server.inizializePlayingStation(name, startingCard, choice, cardObjective);
        this.clientModel = server.getReducedBoard(rmiClient);
    }









    @Override
    public void showUpdatedBoard() throws RemoteException {

    }

    @Override
    public void showUpdatedHand() {

    }

    @Override
    public synchronized void showStartingCard(CardStarting cardStarting) throws RemoteException {
        cli.showStartingCard(cardStarting);
    }

    @Override
    public void Loginupdate() {
        System.out.println("Lobby is full, starting game...");
        try {
            this.ClientGameSetup();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void showObjectiveCards(CardObjective[] cardObjective) {
        cli.showObjectiveCards(cardObjective);
    }
}
