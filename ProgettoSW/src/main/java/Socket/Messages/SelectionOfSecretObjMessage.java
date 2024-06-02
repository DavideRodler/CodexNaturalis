package Socket.Messages;

public class SelectionOfSecretObjMessage extends Message{
    private int cardId;

   public SelectionOfSecretObjMessage(int positionOfSecretObj){
        super("SelectionOfSecretObj");
        this.cardId = positionOfSecretObj;
    }


    public int getCardId() {
        return cardId;
    }
}
