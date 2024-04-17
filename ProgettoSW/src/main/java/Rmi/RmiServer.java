package Rmi;

import controller.Game;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RmiServer implements VirtualServer{
    private Game controller;
    final List<VirtualView> clients = new ArrayList<>(); // list of players
    public RmiServer(Game controller) {
        this.controller = controller;
    }
    public void connect(VirtualView client) throws RemoteException { // method to add players to the list.
        System.err.println("New client connected!");
        this.clients.add(client);
    }

    public static void main(String[] args) throws RemoteException {
        String name = "VirtualServer";
        VirtualServer engine = new RmiServer(new Game());
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(name, stub);
        System.out.println("Server started!");
    }

}
