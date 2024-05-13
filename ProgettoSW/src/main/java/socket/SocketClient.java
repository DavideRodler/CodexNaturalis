package socket;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class SocketClient implements VirtualView {

    final BufferedReader reader;

    final ServerProxy server;


    public SocketClient(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.server = new ServerProxy(writer);

    }

    public void run() throws RemoteException {
        new Thread(() -> { //analogo al clientHandler
            try {
                runVirtualServer();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }).start();
        runCli();
    }

    public void runVirtualServer() throws IOException {
        String line;
        while ((line = reader.readLine()) != null){
            switch(line){
                case "update" -> this.showUpdate(Integer.parseInt(reader.readLine())); //leggo
                case "error" -> this.reportError(reader.readLine());
                //defaul -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }

    public void showUpdate(Integer number){
        System.out.print("\n " + number + "\n");
    }

    public void reportError(String details){
        System.out.print("<\n[ERROR] " + details + "\n>");
    }

    public void runCli() throws RemoteException{
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.print(">");
            int command = scan.nextInt();
            if (command == 0){
                server.reset();
            }
            else{
                server.add(command);
            }
        }
    }
    public void sendMessageToServer(BufferedWriter writer) throws IOException {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        String message = in.nextLine();;
        writer.write(message + "\n");
        writer.flush(); // Assicura che il messaggio sia inviato immediatamente

    }

    public void receiveMessageFromServer(BufferedReader reader) throws IOException {
        String response = reader.readLine();
        System.out.println("Risposta dal server: " + response);
    }

    public void closeClient(BufferedReader reader, BufferedWriter writer, Socket socket) throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException{
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Socket serverSocket = new Socket(host, port);

        InputStreamReader reader = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter writer = new OutputStreamWriter(serverSocket.getOutputStream());

        new SocketClient(new BufferedReader(reader), new BufferedWriter(writer)).run();

    }


}
