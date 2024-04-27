package Network.Client;

import Network.Cli2;
import Network.Server.VirtualServer;

import View.UI;
import model.ReducedBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardStarting;

import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;


public class RmiClient extends UnicastRemoteObject implements VirtualView {

    final VirtualServer server;
    private UI cli;
    private ReducedBoard clientModel;
    private final Object lock;
    int i=0;
    CountDownLatch latch;

    public RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
        lock = new Object();
    }

    public ReducedBoard getClientModel() {
        return clientModel;
    }

    public void ClientSetup() throws RemoteException{
        this.server.connectClient(this);

        cli = new Cli2(server, this);

        cli.showGameTitle();

        if (server.numberOfPlayer() == 0) {
            Integer playerNumber = cli.askPlayerNumber();
            server.inizializeLobby(playerNumber);
        }

        String nickname;
        do {
            nickname = cli.askNickname(); // ask for nickname
        } while (server.nicknameCheck(nickname));

        server.addNewPlayer(nickname);

        System.out.println("Waiting for other players to connect...");
    }

    public void ClientGameSetup () throws RemoteException, InterruptedException{
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

        System.out.println("\nGame is starting, please wait for your turn\n");
        server.allPlayerReady();
    }

    @Override
    public void StartGameTurns() throws RemoteException, InterruptedException {

        System.out.println("\nIs Your Turn");

        this.resetLatch();

        server.startTurnNotify();

        latch.await();

        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Do you want to place a card? (y/n)");
        String choice = scanner.nextLine();


        //VA IMPLEMENTATA LA PARTE DI PIAZZAMENTO DELLA CARTA SULLA BOARD!


        server.notifyMyUpdatedBoard(this);

        if(i<20) { //MODO TEMPORANEO PER IMPOSTARE UN FINE TURNO, DA IMPLEMENTARE METODO PER FINE PARTITA
            server.nextTurn();
            i++;
        }
    }

    @Override
    public void gameSituationUpdate() throws RemoteException {
        cli.showUpdatedBoard();
        cli.showUpdatedStation(server.getClientNickname(this));
        cli.showUpdatedHand(server.getClientNickname(this));
        server.showedBoardNotify();
    }

    @Override
    public void showMyUpdatedBoard(String name) throws RemoteException {
        cli.showMyUpdatedBoard(name);
        server.showedMyBoardNotify();
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void showObjectiveCards(CardObjective[] cardObjective) {
        cli.showObjectiveCards(cardObjective);
    }

    @Override
    public void decrementLatch() {
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
