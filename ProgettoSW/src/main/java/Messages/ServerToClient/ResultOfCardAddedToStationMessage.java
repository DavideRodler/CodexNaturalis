package Messages.ServerToClient;

import Messages.Message;

public class ResultOfCardAddedToStationMessage extends Message {
    private final boolean added;
    private final String message ;

    public ResultOfCardAddedToStationMessage(boolean added, String message) {
        super("ResultOfCardAddedToStation");
        this.added = added;
        this.message = message;
    }

    public boolean isAdded() {
        return added;
    }

    public String getMessage() {
        return message;
    }
}
