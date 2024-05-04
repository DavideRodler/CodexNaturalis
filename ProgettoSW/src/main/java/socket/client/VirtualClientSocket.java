package socket.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public interface VirtualClientSocket {
    void sendMessageToServer(BufferedWriter writer) throws IOException;
    public void receiveMessageFromServer(BufferedReader reader) throws IOException;
    public void closeClient(BufferedReader reader, BufferedWriter writer, Socket socket) throws IOException;
}
