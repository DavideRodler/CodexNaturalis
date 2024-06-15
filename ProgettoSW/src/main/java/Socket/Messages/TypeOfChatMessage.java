package Socket.Messages;

import java.util.LinkedHashMap;

public class TypeOfChatMessage extends Message{
    private String TypeOfChat;

    public TypeOfChatMessage(String TypeOfChat){
        super("typeOfChat");
        this.TypeOfChat = TypeOfChat;
    }

    public String getTypeOfChat() {
        return TypeOfChat;
    }
}
