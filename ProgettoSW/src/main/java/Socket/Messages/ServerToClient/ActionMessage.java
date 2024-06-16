package Socket.Messages.ServerToClient;

import Socket.Messages.Message;

public class ActionMessage extends Message {

    public ActionMessage( String action) {
        super(action);
    }

}
