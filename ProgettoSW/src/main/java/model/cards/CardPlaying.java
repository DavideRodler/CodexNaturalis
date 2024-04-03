package model.cards;
import model.enums.Suit;
import model.cards.face.Face;

public abstract  class CardPlaying extends Card {
    private Face front;
    private Face back;
    private Boolean playedBack;

    public CardPlaying(int ID, Face front, Face back) {
        super(ID);
        this.playedBack = false;
        this.front = front;
        this.back = back;
    }

    public Face getFront() {
        return front;
    }

    public Face getBack() {
        return back;
    }
    public void setPlayingBack(boolean playingBack){
        this.playedBack=playingBack;
    }
    public boolean getPlayingBack(){
        return playedBack;
    }

    public int countResource(Suit suit){
        int count = 0;
        return count;
    }
}
