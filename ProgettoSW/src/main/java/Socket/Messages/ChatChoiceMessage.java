package Socket.Messages;

public class ChatChoiceMessage extends Message{
    private String chatChoice;

    public ChatChoiceMessage(String chatChoice) {
        super("CHAT_CHOICE");
        this.chatChoice = chatChoice;
    }

    public String getChatChoice() {
        return chatChoice;
    }
}
