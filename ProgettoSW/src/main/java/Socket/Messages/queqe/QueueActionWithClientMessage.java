package Socket.Messages.queqe;

import Network.Client.RMI.VirtualView;
import Socket.Messages.Message;

public class QueueActionWithClientMessage extends Message {
    private VirtualView client;

    public QueueActionWithClientMessage(String type, VirtualView client) {
        super(type);
        this.client = client;
    }

    public VirtualView getClient() {
        return client;
    }
}
