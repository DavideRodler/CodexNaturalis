package StartApplication;

import Network.Client.ClientCommunication;
import Network.Client.ClientController;
import Network.Client.RMI.RmiClient;
import Network.Server.VirtualServer;
import exception.NotValidMoveException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientApp implements Remote {

    public static void main(String[] args) {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        String ip;
        do {
            System.out.println("\uD83D\uDD35Insert ipAddress (press enter if using local machine): ");
            ip = in.nextLine();
            if (ip.equals("")) {
                ip = "127.0.0.1";
                System.out.println("Local IP 127.0.0.1 accepted ✔️");
                break;
            } else if (ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
                System.out.println("Valid IP ✔️");
                break;
            } else {
                System.out.println("Invalid IP ❌");
            }
        } while (true);

        System.out.println("Select Communication Type: 1. RMI 2. Socket");
        int selection = in.nextInt();
        while (true) {
            if (selection == 1) {
                VirtualServer server;
                try {
                    Registry registry = LocateRegistry.getRegistry(ip, 16000);
                    server = (VirtualServer) registry.lookup("MyServer");
                    RmiClient client = new RmiClient(server);
                    ClientController clientController = new ClientController(client);
                    client.setClientController(clientController);
                    client.connectToServer();
                } catch (RemoteException r) {
                    System.out.println("Error: " + r);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            } else if (selection ==2) {
                System.out.println("non abbiamo implementato la comunicazione via socket");
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }
}
