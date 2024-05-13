package StartApplication;

import Network.Server.RmiServer;
import Network.Server.VirtualServer;
import controller.GameController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServerApp implements Remote {

    /**
     * The main method that starts the server application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Se preferisci connessione Socket premi 1 else per RMI premi 0");
        Scanner in1 = new Scanner(new InputStreamReader(System.in));
        String input1 = in1.nextLine();
        if (input1.equals("0")) {
            try {
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

                VirtualServer server = new RmiServer(new GameController());
                try {
                    VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server, 0);
                    Registry registry = LocateRegistry.createRegistry(16000);
                    registry.rebind("MyServer", stub);
                } catch (RemoteException e) {
                    System.out.println("Error: " + e);
                }
                server.initializeBoard();
                System.out.println("➖Server is booting....");
                System.out.println("➖Server created");
            } catch (Exception e) {
                System.out.println("Something went wrong :( " + e);
            }
        } else { //connesione socket
            try {
                ServerSocket serverSocket = new ServerSocket(1234); // Porta del server
                System.out.println("Server in attesa di connessioni su porta 1234...");

                // Accetta una connessione in arrivo
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione accettata da " + clientSocket.getInetAddress());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}