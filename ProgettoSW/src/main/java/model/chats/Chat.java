package model.chats;

import Messages.ModelUpdates.ChatMessage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a chat
 */
public class Chat implements Serializable {
    private ArrayList<ChatMessage> message;

    public Chat() {
        message = new ArrayList<>();
    }

    public ArrayList<ChatMessage> getMessage() {
        return message;
    }



    /**
     * Add message to the chat
     * @param chatMessage message to add
     */
    public void addMessage(ChatMessage chatMessage){
        message.add(chatMessage);
    }
}
