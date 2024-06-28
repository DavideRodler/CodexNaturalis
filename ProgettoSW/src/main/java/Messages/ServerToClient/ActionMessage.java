package Messages.ServerToClient;

import Messages.Message;

public class ActionMessage extends Message {

    public ActionMessage( String action) {
        super(action);
    }

}
