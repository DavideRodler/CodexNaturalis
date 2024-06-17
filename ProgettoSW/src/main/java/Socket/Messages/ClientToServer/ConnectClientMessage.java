package Socket.Messages.ClientToServer;

import Socket.Messages.Message;

public class ConnectClientMessage extends Message {
    public ConnectClientMessage() {
        super("ConnectClient");
    }
}
