package soket.server;

import soket.Messages.Message;

public interface VirtualServerSocket {
    void manageAnswer(Message m);
}
