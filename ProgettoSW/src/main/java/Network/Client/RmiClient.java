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
    public void StartGameTurns() throws RemoteException{
        int i=0;
        while(/*!server.isGameFinished()*/ i<1000){


                if(server.isMyTurn(this))
                {
                    server.startTurnNotify();
                    System.out.println("Is your turn");
                    //parte di aggiunta delle carte
                    server.notifyMyUpdatedBoard(this);
                    server.nextTurn();
                }
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
}
