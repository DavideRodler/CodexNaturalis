package Socket.Messages;

public class CliGuiChoiceMessage extends Message{
    private String choice;

    public CliGuiChoiceMessage(String choice){
        super("CLI_GUI_CHOICE");
        this.choice = choice;
    }

    public String getChoice(){
        return this.choice;
    }
}
