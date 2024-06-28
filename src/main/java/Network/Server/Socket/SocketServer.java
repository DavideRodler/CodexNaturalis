package Network.Server.Socket;

import Network.Client.RMI.VirtualView;
import Network.Server.Server;
import Network.Server.VirtualServer;
import exception.ChangedStateException;
import exception.InvalidPlacingCondition;
import exception.NotMyTurnException;
import exception.NotValidMoveException;
import model.enums.TokenEnum;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SocketServer {
    private ServerSocket listenSocket;
    private Server server;

    public SocketServer(Server server, ServerSocket serverSocket) throws IOException {
        this.server = server;
        this.listenSocket = serverSocket;
    }

    /**
     * This method is used to run the server
     * @throws IOException if an I/O error occurs
     */
    public void RunServer() throws IOException {
        Socket clientSocket = null;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            OutputStream output = clientSocket.getOutputStream();
            InputStream input = clientSocket.getInputStream();
            SocketClientHandler handler = new SocketClientHandler( new ObjectOutputStream(output),new ObjectInputStream(input), server);

            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

}
