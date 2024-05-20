//package socket.server;
//
//import controller.GameController;
//import socket.Messages.Message;
//import socket.client.VirtualViewSocket;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//import java.io.*;
//
//public class SocketServer implements VirtualServerSocket {
//    private GameController controller;
//    final List<VirtualViewSocket> clients = new ArrayList<>(); // list of players
//
//        public static void main(String[] args) {
//            try {
//                ServerSocket serverSocket = new ServerSocket(8888); // Porta del server
//                System.out.println("Server in attesa di connessioni...");
//
//                // Accetta una connessione in arrivo
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("Connessione accettata da " + clientSocket.getInetAddress());
//
//                // Gestisci la comunicazione con il client
//                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//
//                // Leggi dati inviati dal client
//                String inputLine;
//                while ((inputLine = reader.readLine()) != null) {
//                    System.out.println("Messaggio ricevuto dal client: " + inputLine);
//
//                    // Invia una risposta al client
//                    writer.write("Messaggio ricevuto: " + inputLine + "\n");
//                    writer.flush(); // Assicura che la risposta sia inviata immediatamente
//                }
//
//                // Chiudi le risorse
//                reader.close();
//                writer.close();
//                clientSocket.close();
//                serverSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    @Override
//    public void manageAnswer(Message m) {
//
//    }
//}