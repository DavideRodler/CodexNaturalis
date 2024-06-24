package View;

import Network.Client.ClientController;
import Network.Client.RMI.RmiClientToServer;
import Network.Server.RMI.RmiServer;
import Socket.VirtualServer;
import View.GUI.Gui;
import model.Player;
import model.PlayingStation;
import model.client.ClientBoard;
import model.client.ReductPlayer;
import model.enums.TokenEnum;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestStationGUI {

    @Test
    public void startGUI() throws RemoteException {
        //Gui gui = new Gui();
        ReductPlayer tommy = new ReductPlayer(2, new ArrayList<>(), "tommy", new PlayingStation(new HashMap<>()), TokenEnum.BLUE );
        ReductPlayer eric = new ReductPlayer(4, new ArrayList<>(), "eric", new PlayingStation(new HashMap<>()), TokenEnum.YELLOW );
        ReductPlayer dave = new ReductPlayer(6, new ArrayList<>(), "dave", new PlayingStation(new HashMap<>()), TokenEnum.RED);
        ArrayList<ReductPlayer> redplayers= new ArrayList<>();
        redplayers.add(0, tommy);
        redplayers.add(1, eric);
        redplayers.add(2, dave);
        Player isa = new Player("isa", TokenEnum.GREEN, new PlayingStation(new HashMap<>()), 15, new ArrayList<>());
        //gui.launchGui(new ClientBoard(null, null, redplayers, isa, null, null, null), new ClientController(null, null));
    }
}
