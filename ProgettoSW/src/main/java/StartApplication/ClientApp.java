package StartApplication;

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
        String input;
        do {
            System.out.println("\uD83D\uDD35Insert ipAddress (press enter if using local machine): ");
            input = in.nextLine();
            if (input.equals("")) {
                input = "127.0.0.1";
                System.out.println("Local IP 127.0.0.1 accepted ✔️");
                break;
            } else if (input.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
                System.out.println("Valid IP ✔️");
                break;
            } else {
                System.out.println("Invalid IP ❌");
            }
        } while (true);


        VirtualServer server;
        try {
            Registry registry = LocateRegistry.getRegistry(input, 16000);
            server = (VirtualServer) registry.lookup("MyServer");
            RmiClient client = new RmiClient(server);
            client.connectToServer();
        } catch (RemoteException r) {
            System.out.println("Error: " + r);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }


    }
}
