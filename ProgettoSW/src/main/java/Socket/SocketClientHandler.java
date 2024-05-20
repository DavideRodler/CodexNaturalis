package Socket;

import controller.GameController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SocketClientHandler implements VirtualView {
    final GameController controller;
    final SocketServer server;
    final BufferedReader reader;
    final VirtualView view;

    public SocketClientHandler(GameController controller, SocketServer server, BufferedReader reader, BufferedWriter output){
        this.controller = controller;
        this.server = server;
        this.reader = reader;
        this.view = new ClientProxy(output);
    }


    public void runVirtualView() throws IOException{
        String line;
        while ((line = reader.readLine()) != null){
            switch(line){
                case "connect" -> {}
                case "add" -> { //deserializzo messaggi
                    // Formato.deserialize<AddMessage>();
                    //this.controller.add(Integer.parseInt(reader.readLine())); //continuo a leggere
                    //this.server.broadcastUpdate(this.controller.getState());
                }
                case "reset" -> {
                    // Formato.deserialize<Reset>();
                    //this.controller.reset();
                    //this.server.broadcastUpdate(this.controller.getState());
                }
                default -> System.err.println("[INVALID MESSAGE]");
            }

        }
    }

    public void showUpdate(Integer number){
        synchronized (this.view) {
            this.view.showUpdate(number);
        }
    }

    public void reportError(String details) {
        synchronized (this.view) {
            this.view.reportError(details);
        }
    }

}