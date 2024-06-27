package model.cards;
import model.enums.SuitEnum;
import model.cards.face.Face;

import java.io.Serializable;

public abstract  class CardPlaying extends Card implements Serializable {
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

    public int countResource(SuitEnum suit){
        return 0;
    } //base case
}
