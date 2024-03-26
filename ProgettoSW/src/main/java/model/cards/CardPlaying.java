package model.cards;
import model.cards.face.CornerResource;
import model.enums.Suit;
import model.cards.face.Face;

public class CardPlaying extends Card {
    private Face front;
    private Face back;
    private Boolean playingBack;

    public CardPlaying(int ID, Face front, Face back) {
        super(ID);
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
        this.playingBack=playingBack;
    }
    public boolean getPlayingBack(){
        return playingBack;
    }

    public int countResource(Suit suit){
        int count = 0;
        return count;
    }
}
