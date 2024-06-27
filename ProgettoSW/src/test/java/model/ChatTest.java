package model;

import Socket.Messages.ChatMessage;
import model.chats.Chat;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    Chat chat = new Chat();
    @Test
    void addMessage() {
        ChatMessage mex = new ChatMessage("GLOBAL", "ciao", "isa");
        chat.addMessage(mex);
        ArrayList<ChatMessage> chattest = new ArrayList<>();
        chattest.add(mex);
        assertEquals(chattest, chat.getMessage());
    }


}