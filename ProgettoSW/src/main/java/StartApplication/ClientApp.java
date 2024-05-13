package StartApplication;

import Network.Client.RmiClient;
import Network.Client.VirtualView;
import Network.Server.RmiServer;
import Network.Server.VirtualServer;
import controller.GameController;

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
        System.out.println("Se preferisci connessione Socket premi 1 else per RMI premi 0");
        Scanner in1 = new Scanner(new InputStreamReader(System.in));
        String input1 = in1.nextLine();
        if (input1.equals("0")) { //crea connessione RMI
            Scanner in = new Scanner(new InputStreamReader(System.in));
            String input;
            do {
                System.out.println("\uD83D\uDD35Insert ipAddress (press enter if using local machine): ");
                input = in.nextLine();
                if (input.isEmpty()) {
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
                RmiClient rmiClient = new RmiClient(server);
                rmiClient.ClientSetup();
            } catch (RemoteException r) {
                System.out.println("Error: " + r);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        } else { //crea connessione Socket
            try {
                System.out.println("\uD83D\uDD35Insert ipAddress (press enter if using local machine): ");
                Scanner in = new Scanner(new InputStreamReader(System.in));
                String input = in.nextLine();
                if (input.isEmpty()) {
                    input = "127.0.0.1";
                } //else???????
                Socket clientSocket = new Socket(input, 1234); // Indirizzo e porta del server
                System.out.println("Connessione al server riuscita");
                // Creazione di buffer per la comunicazione con il server


            } catch (IOException e) {
                e.printStackTrace();
            }
            }

        }

    }

