package model.cards;

import model.cards.face.Face;

public class CardPlaying extends Card {
    private Face front;
    private Face back;
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
}
