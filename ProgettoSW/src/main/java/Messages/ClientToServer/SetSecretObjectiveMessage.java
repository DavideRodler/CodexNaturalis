package Messages.ClientToServer;

import Messages.Message;

public class SetSecretObjectiveMessage extends Message {
    private String nickname;
    private int secretObjectiveID;

    public SetSecretObjectiveMessage(String nickname, int secretObjectiveID) {
        super("SetSecretObjective");
        this.nickname = nickname;
        this.secretObjectiveID = secretObjectiveID;
    }

    public int getId() {
        return secretObjectiveID;
    }

    public String getNickname() {
        return nickname;
    }
}
