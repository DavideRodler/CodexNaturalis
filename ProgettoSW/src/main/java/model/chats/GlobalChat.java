package model.chats;

import Socket.Messages.Chat.GlobalChatMessage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a global chat
 */
public class GlobalChat implements Serializable {
    private ArrayList<GlobalChatMessage> message;

    public GlobalChat() {
        message = new ArrayList<>();
    }

    public ArrayList<GlobalChatMessage> getMessage() {
        return message;
    }



    /**
     * Add message to the chat
     * @param chatMessage message to add
     */
    public void addMessage(GlobalChatMessage chatMessage){
        message.add(chatMessage);
    }
}

