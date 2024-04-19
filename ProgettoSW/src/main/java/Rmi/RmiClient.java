package Rmi;

import View.Cli;
import View.VirtualView;
import model.PlayingBoard;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    protected RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }
    private void run() throws RemoteException {
        this.server.connect(this);
        new Cli(server).init();
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(args[0], 1234);
        VirtualServer server = (VirtualServer) registry.lookup("RmiServer");

        new RmiClient(server).run();
    }

    @Override
    public void update(String Message) {
        switch (Message) {
            case "start":
                System.out.println("Game is starting");
                break;
            default:
                System.out.println(Message);
                break;
        }
    }
}
