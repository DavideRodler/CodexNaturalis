package model;

import Socket.Messages.Chat.GlobalChatMessage;
import Socket.Messages.Chat.PrivateChatMessage;

import java.io.Serializable;
import java.util.ArrayList;

public class PrivateChat implements Serializable {
    private ArrayList<PrivateChatMessage> message;
    private String nickname1;
    private String nickname2;

    public PrivateChat(String nickname1, String nickname2) {
        message = new ArrayList<>();
        this.nickname1 = nickname1;
        this.nickname2 = nickname2;
    }

    public ArrayList<PrivateChatMessage> getMessage() {
        return message;
    }

    public String getNickname1() {
        return nickname1;
    }

    public String getNickname2() {
        return nickname2;
    }

    public void addMessage(PrivateChatMessage chatMessage){
        message.add(chatMessage);
    }
}