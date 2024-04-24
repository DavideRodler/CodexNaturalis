package Network.Client;

import View.Cli2;
import Network.Server.VirtualServer;
import View.UI;
import model.PlayingBoard;
import model.cards.CardObjective;
import model.cards.CardPlaying;
import model.cards.CardStarting;

import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView{

    final VirtualServer server;
    private UI cli;
    private String nickname;
    private ClientModel clientModel;


    public RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void run()  throws RemoteException {
        this.server.connectClient(this);

        cli = new Cli2( this);

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

        clientModel = new ClientModel();


        while(true)
        {
            if(server.allPlayerConnected()) {
                CardPlaying cardPlaying = server.getStartingCard(this.nickname);
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
    public void showObjectivetochoose(CardObjective firsrobj, CardObjective secondobj) throws RemoteException {

    }

    @Override
    public void showStartingCard(CardStarting cardStarting) throws RemoteException {
        cli.showStartingCard(cardStarting);
    }
}
