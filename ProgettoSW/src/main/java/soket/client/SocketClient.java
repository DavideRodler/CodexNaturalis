package soket.client;

import View.UI;
import soket.Messages.ChooseObjectives;
import soket.Messages.Message;
import soket.server.VirtualServerSocket;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient implements VirtualViewSocket{
    private UI visualinterface;
    final VirtualServerSocket server;

    public SocketClient(VirtualServerSocket server) {
        this.server = server;
    }

    @Override
   public void sendMessageToServer(Message m) throws RemoteException {
//     //   Message answer = visualinterface.handleMessage(m);
//        try {
//            server.manageAnswer(answer);
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }
   }
    // function to handle messages and callig write methods for the UI.
    public void handleMessage(Message m) {
        switch (m.getType()) {
            case "ChooseObjective" :
                handleChooseObjective(m);
        }
    }

    private void handleChooseObjective(Message m){
        Scanner scanner = new Scanner(System.in);
        //selecting the cardobjective
        System.out.println(  ((ChooseObjectives) m).getFirstobject().getObjective().toString());
        System.out.println( ((ChooseObjectives) m).getSecondobject().getObjective().toString());
        System.out.println(" select the objective with 0 or 1");

        int input = scanner.nextInt();
        // this.servermessage = new ObjectChoosen(input);
    }
}
