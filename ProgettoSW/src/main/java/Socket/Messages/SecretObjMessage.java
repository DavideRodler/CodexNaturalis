package Socket.Messages;

public class SecretObjMessage extends Message{
    private String secretObj;

    public SecretObjMessage(String secretObj) {
        super("SECRET_OBJ_CHOICE");
        this.secretObj = secretObj;
    }

    public String getSecretObj() {
        return secretObj;
    }
}
