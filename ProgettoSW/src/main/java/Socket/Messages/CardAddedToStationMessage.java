package Socket.Messages;

import model.cards.CardResource;

public class CardAddedToStationMessage extends Message{
    private String nickname;
    private int x;
    private int y;
    private boolean playedback;
    private CardResource card;

    public CardAddedToStationMessage( CardResource card, String nickname, int x , int y , boolean playedback){
        super("CardAddedToStation");
        this.card  = card;
        this.nickname = nickname;
        this.x = x;
        this.y = y;
        this.playedback = playedback;
    }

    public String getNickname() {
        return nickname;
    }
    public CardResource getCard() {
        return card;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean getPlayedBack() {
        return playedback;
    }
}

