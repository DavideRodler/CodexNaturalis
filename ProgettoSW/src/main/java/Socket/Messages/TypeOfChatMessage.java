package Socket.Messages;

import java.util.LinkedHashMap;

public class TypeOfChatMessage extends Message{
    private String nickname1;
    private String nickname2;

    public TypeOfChatMessage(String nickname1, String nickname2){
        super("typeOfChat");
        this.nickname1 = nickname1;
        this.nickname2 = nickname2;
    }

    public String getNickname1() {
        return nickname1;
    }

    public String getNickname2() {
        return nickname2;
    }
}
