package Socket.Messages;

public class DrawDeckMessage extends Message{
    private int selection;

    public DrawDeckMessage(int selection) {
        super("ENEMY_STATION_CHOICE");
        this.selection = selection;
    }

    public int getSelection() {
        return selection;
    }
}
