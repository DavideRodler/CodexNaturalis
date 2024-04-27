package socket.client;

import View.UI;
import socket.Messages.ChooseObjectives;
import socket.Messages.Message;
import socket.server.VirtualServerSocket;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class SocketClient implements VirtualViewSocket{
        public static void main(String[] args) {
            try {
                Socket socket = new Socket("localhost", 1234); // Indirizzo e porta del server
                System.out.println("Connessione al server riuscita.");

                // Creazione di buffer per la comunicazione con il server
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // Invio di un messaggio al server
                String message = "Ciao, server!";
                writer.write(message + "\n");
                writer.flush(); // Assicura che il messaggio sia inviato immediatamente
                System.out.println("Messaggio inviato al server: " + message);

                // Ricezione della risposta dal server
                String response = reader.readLine();
                System.out.println("Risposta dal server: " + response);

                // Chiudi le risorse
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    private UI visualinterface;
    final VirtualServerSocket server;

    public SocketClient(VirtualServerSocket server) {
        this.server = server;
    }

    @Override
   public void sendMessageToServer(Message m) throws RemoteException {
//     //   Message answer = visualinterface.handleMessage(m);
//        try {
//            server.manageAnswer(answer);
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }
   }
    // function to handle messages and callig write methods for the UI.
    public void handleMessage(Message m) {
        switch (m.getType()) {
            case "ChooseObjective" :
                handleChooseObjective(m);
        }
    }

   private void handleChooseObjective(Message m){
        Scanner scanner = new Scanner(System.in);
        //selecting the cardobjective
        System.out.println(  ((ChooseObjectives) m).getFirstobject().getObjective().toString());
        System.out.println( ((ChooseObjectives) m).getSecondobject().getObjective().toString());
        System.out.println(" select the objective with 0 or 1");

        int input = scanner.nextInt();
        // this.servermessage = new ObjectChoosen(input);
    }
}
