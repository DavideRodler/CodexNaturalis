package socket.server;

import controller.GameController;
import socket.Messages.Message;
import socket.client.VirtualClientSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class SocketServer implements VirtualServerSocket {

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

}