package Socket.Messages.ServerToClient;

import Socket.Messages.Message;

public class ActionMessage extends Message {
    private final String action;

    public ActionMessage( String action) {
        super("Action");
        this.action = action;
    }
    public String getAction() {
        return action;
    }
}
