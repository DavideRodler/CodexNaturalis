package Socket.Messages.ServerToClient;

import Socket.Messages.Message;

public class AddCardResultMessage extends Message {
    private boolean result;
    private String message;
    public AddCardResultMessage(boolean result, String message) {
        super("AddCardResultMessage");
        this.result = result;
        this.message = message;
    }

    public boolean getResult() {
        return result;
    }
    public String getMessage() {
        return message;
    }
}
