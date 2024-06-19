package Socket.Messages.queqe;

import Socket.Messages.Message;
import Network.Client.RMI.VirtualView;

public class QueueResultOfCardAddedToStationMessage extends Message {
    private final boolean added;
    private final String message ;
    private final VirtualView client;

    public QueueResultOfCardAddedToStationMessage(boolean added, String message, VirtualView client) {
        super("QueueResultOfCardAddedToStation");
        this.added = added;
        this.message = message;
        this.client = client;
    }

    public boolean isAdded() {
        return added;
    }

    public String getMessage() {
        return message;
    }

    public VirtualView getClient() {
        return client;
    }
}
