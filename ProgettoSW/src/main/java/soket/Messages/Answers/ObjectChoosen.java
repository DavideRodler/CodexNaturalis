package soket.Messages.Answers;

import soket.Messages.Message;

public class ObjectChoosen extends Message {
    private int num;

    public ObjectChoosen(int num) {
        super("ObjectChoosen");
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
