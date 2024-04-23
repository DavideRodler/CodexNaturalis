package soket.server;

import controller.GameController;
import soket.Messages.Message;
import soket.client.VirtualViewSocket;

import java.util.ArrayList;
import java.util.List;

public class SocketServer implements VirtualServerSocket {
    private GameController controller;
    final List<VirtualViewSocket> clients = new ArrayList<>(); // list of players

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
