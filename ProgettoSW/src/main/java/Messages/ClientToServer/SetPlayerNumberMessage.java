package Messages.ClientToServer;

import Messages.Message;

public class SetPlayerNumberMessage extends Message {
    private final int number;

    public SetPlayerNumberMessage(int number) {
        super("SetPlayerNumber");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
