package Socket;

import controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;



public class SocketServer {

    final ServerSocket listenSocket;
    final GameController controller;

    final List<SocketClientHandler> clients = new ArrayList<>();



    public SocketServer(ServerSocket listenSocket, GameController controller) {
        this.controller = controller;
        this.listenSocket = listenSocket;
    }

public void runServer() throws IOException {
    Socket clientSocket = null;
    while ((clientSocket = this.listenSocket.accept()) != null) {
        InputStreamReader reader = new InputStreamReader(clientSocket.getInputStream());
        OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());

        SocketClientHandler handler = new SocketClientHandler(this.controller, this, new BufferedReader(reader), new BufferedWriter(writer));

        synchronized (this.clients) {
            clients.add(handler);
        }

        new Thread(() -> {
            try {
                handler.runVirtualView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}

public void broadcastUpdate(Integer value){
        synchronized (this.clients){
            for(var client: this.clients){
                client.showUpdate(value);
            }
        }
}

    public void sendMessageToClient(BufferedWriter writer) throws IOException {
        Scanner in = new Scanner(new InputStreamReader(System.in)); //DA CORREGGERE
        String message = in.nextLine();;
        writer.write(message + "\n");
        writer.flush(); // Assicura che la risposta sia inviata immediatamente
    }

    public void receiveMessageFromServer(BufferedReader reader) throws IOException {
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            System.out.println("Messaggio ricevuto dal client: " + inputLine);
        }
    }

    public void closeClient(BufferedReader reader, BufferedWriter writer, Socket socket) throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException{
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        ServerSocket listenSocket = new ServerSocket(port);

        new SocketServer(listenSocket, new GameController());
    }
}