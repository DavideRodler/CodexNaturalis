package socket.client;

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class SocketClient implements VirtualClientSocket {


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

}
