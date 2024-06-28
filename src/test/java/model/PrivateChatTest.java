package model;

import Messages.Chat.PrivateChatMessage;
import model.chats.PrivateChat;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PrivateChatTest {

    PrivateChat chat = new PrivateChat("isa", "tommy");

    @Test
    void getNicknames() {
        assertEquals("isa", chat.getNickname1());
        assertEquals("tommy", chat.getNickname2());
    }

    @Test
    void addMessage() {
        PrivateChatMessage mex = new PrivateChatMessage("ciao", "isa", "tommy");
        chat.addMessage(mex);
        ArrayList<PrivateChatMessage> message = new ArrayList<>();
        message.add(mex);
        assertEquals(message, chat.getMessage());
    }
}