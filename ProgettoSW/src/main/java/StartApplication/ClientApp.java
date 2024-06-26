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

        System.out.println("" + "\033[0;31m" +
                "oooooooo8                  oooo                                oooo   oooo            o8                                     o888\n" +
                "o888        ooooooo     ooooo888   ooooooooo8 oooo   oooo       8888o  88   ooooooo o888oo oooo  oooo  oo oooooo   ooooooo    888  oooo   oooooooo8\n" +
                "888        888   888  888    888  888oooooo8    888o888         88 888o88   ooooo888 888    888   888   888        ooooo888   888   888  888ooooooo\n" +
                "888o       888   888  888    888  888           o88 88o         88   8888 888    888 888    888   888   888      888    888   888   888          888\n" +
                "888oooo88   88ooo88     88ooo888o  88oooo888 o88o   o88o      o88o    88  88ooo88 8o 888o   888o88 8o o888o      88ooo88 8o o888o o888o 88oooooo88\n\n" + "\033[0m");

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


        System.out.println("Do you want to play with 1.GUI or 2.CLI?");
        Scanner scanner = new Scanner(System.in);
        String answer;
        ClientController clientController = new ClientController();
        UI ui ;
        do {
            answer = scanner.nextLine();
        } while (!answer.equals("1") && !answer.equals("2"));
        if (answer.equals("1")) {
            ui = new Gui();
            clientController.setUi(ui);
            new Thread(() -> {

                ((Gui) ui).launchGui(clientController);

            }).start();
        }
        else {
            ui = new Cli2(clientController);
            clientController.setUi(ui);
        }


        while (true) {
            System.out.println("Select Communication Type: 1. RMI 2. Socket");
            int selection = in.nextInt();
            if (selection == 1) {
                VirtualServer server;
                //locating the registry
                Registry registry = LocateRegistry.getRegistry(ip, 16000);
                try {
                    server = (VirtualServer) registry.lookup("MyServer");
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);

                }

                //creating my client with RMI and a client controller
                RmiClientToServer client = new RmiClientToServer(server);
                client.setClientController(clientController);
                clientController.setClientToServerCommunication(client);
                client.connectToServer();
                break;

            } else if (selection ==2) {
                //creating the soket communication
                Socket serverSocket = new Socket(ip, 16001);

                InputStream socketRx = serverSocket.getInputStream();
                OutputStream socketTx = serverSocket.getOutputStream();

                SocketClient client = new  SocketClient(new ObjectInputStream(socketRx), new ObjectOutputStream(socketTx));

                //creating the client controller
                client.setClientController(clientController);
                clientController.setClientToServerCommunication(client);
                client.run();
                client.connectToServer();

                break;
            } else {
                System.out.println("Invalid Input");
            }
        }


    }
}
