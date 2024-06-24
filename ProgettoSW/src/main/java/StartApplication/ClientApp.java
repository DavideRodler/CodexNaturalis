package StartApplication;

import Network.Client.ClientController;
import Network.Client.RMI.RmiClientToServer;
import Network.Client.Socket.SocketClient;
import Network.Server.VirtualServer;
import View.CLI.Cli2;
import View.GUI.Gui;
import View.UI;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.net.Socket;

public class ClientApp implements Remote {

    public static void main(String[] args) throws IOException {
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



        while (true) {
            System.out.println("Select Communication Type: 1. RMI 2. Socket");
            int selection = in.nextInt();
            if (selection == 1) {
                VirtualServer server;
                try {
                    //locating the registry
                    Registry registry = LocateRegistry.getRegistry(ip, 16000);
                    server = (VirtualServer) registry.lookup("MyServer");

                    //creating my client with RMI and a client controller
                    RmiClientToServer client = new RmiClientToServer(server);
                    ClientController clientController = new ClientController(client);
                    client.setClientController(clientController);
                    client.connectToServer();

                    System.out.println("Do you want to play with 1.GUI or 2.CLI?");
                    Scanner scanner = new Scanner(System.in);
                    String answer;
                    UI ui ;
                    do {
                        answer = scanner.nextLine();
                    } while (!answer.equals("1") && !answer.equals("2"));
                    if (answer.equals("1")) {
                        ui = new Gui();
                        new Thread(() -> {

                            ((Gui) ui).launchGui(clientController);

                        }).start();
                    }
                    else {
                        ui = new Cli2(clientController);
                    }

                } catch (RemoteException r) {
                    System.out.println("Error: " + r);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            } else if (selection ==2) {
                //creating the soket communication
                Socket serverSocket = new Socket(ip, 16001);

                InputStream socketRx = serverSocket.getInputStream();
                OutputStream socketTx = serverSocket.getOutputStream();

                SocketClient client = new  SocketClient(new ObjectInputStream(socketRx), new ObjectOutputStream(socketTx));

                //creating the client controller
                ClientController clientController = new ClientController(client);
                client.setClientController(clientController);
                client.run();
                try {
                    client.connectToServer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Do you want to play with 1.GUI or 2.CLI?");
                Scanner scanner = new Scanner(System.in);
                String answer;
                UI ui ;
                do {
                    answer = scanner.nextLine();
                } while (!answer.equals("1") && !answer.equals("2"));
                if (answer.equals("1")) {
                    ui = new Gui();
                    new Thread(() -> {

                        ((Gui) ui).launchGui(clientController);

                    }).start();
                }
                else {
                    ui = new Cli2(clientController);
                }

                break;
            } else {
                System.out.println("Invalid Input");
            }
        }


    }
}
