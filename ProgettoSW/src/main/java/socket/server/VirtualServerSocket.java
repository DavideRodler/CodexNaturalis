package socket.server;

import socket.Messages.Message;

public interface VirtualServerSocket {
    void manageAnswer(Message m);
}
