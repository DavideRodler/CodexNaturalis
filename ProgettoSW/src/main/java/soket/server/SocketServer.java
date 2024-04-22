package soket.server;

import Rmi.client.VirtualView;
import controller.GameController;
import soket.Messages.Message;

import java.util.ArrayList;
import java.util.List;

public class SocketServer implements VirtualServerSocket {
    private GameController controller;
    final List<VirtualView> clients = new ArrayList<>(); // list of players

    @Override
    public void manageAnswer(Message m){
        switch (m.getType()){
            case "ObjectCardChoosen":
    //                controller.setObjective()
                //non mi serve aggiornare gli altri client(l'obiettivo e' segreto
            case "StartingGame":
                controller.initGameController();
    }
}
}
