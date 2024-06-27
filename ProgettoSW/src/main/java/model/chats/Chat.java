package model.chats;

import Socket.Messages.ChatMessage;

import java.util.ArrayList;

public class Chat {
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
