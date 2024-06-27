package model;

import Socket.Messages.Chat.GlobalChatMessage;
import model.chats.GlobalChat;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GlobalChatTest {

    @Test
    void getMessage() {
            GlobalChat chat = new GlobalChat();
            GlobalChatMessage mex = new GlobalChatMessage("GLOBAL", "ciao", "isa");
            ArrayList<GlobalChatMessage> messages = new ArrayList<>();
            messages.add(mex);
            chat.addMessage(mex);
            assertEquals(mex.getMessage(), chat.getMessage().getFirst().getMessage());
        }

}