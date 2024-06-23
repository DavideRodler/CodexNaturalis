package Socket.Messages;

public class ChatMessageMessage extends Message{
    private String chatMessage;

    public ChatMessageMessage(String chatMessage) {
        super("CHAT_MESSAGE_CHOICE");
        this.chatMessage = chatMessage;
    }

    public String getChatMessage() {
        return chatMessage;
    }
}
