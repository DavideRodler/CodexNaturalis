package model.chats;

import Socket.Messages.Chat.GlobalChatMessage;

import java.util.ArrayList;

public class GlobalChat {
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

