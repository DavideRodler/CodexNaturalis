package Network.Client;

import Network.Cli2;
import Network.Server.VirtualServer;
import model.cards.CardStarting;

import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView{

    final VirtualServer server;
    private Cli2 cli;
    private String nickname;

    public RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void run()  throws RemoteException {
        this.server.connectClient(this);
        cli = new Cli2(server, this);
        cli.init();
        String nickname;
        do {
            nickname = cli.askNickname(); // ask for nickname
        } while (server.nicknameCheck(nickname));

        server.addNewPlayer(nickname);
        setNickname(nickname);

        if (server.numberOfPlayer() == 1) {
            Integer playerNumber = cli.askPlayerNumber();
            server.inizializeLobby(playerNumber);
            System.out.println("Waiting for other players to join...");
        } else if (server.numberOfPlayer() < server.getPlayerNumber()) {
            System.out.println("Waiting for other players to join...");
        }else{
            System.out.println("Lobby is full, starting game...");
        }

        while(true)
        {
            if(server.startTurn()) {
                server.addStartingCard(this.nickname);
                break;
            }
        }

    }

    @Override
    public void showUpdatedBoard() {

    }

    @Override
    public void showUpdatedHand() {

    }

    @Override
    public void showStartingCard(CardStarting cardStarting) throws RemoteException {
        cli.showStartingCard(cardStarting);
    }
}
