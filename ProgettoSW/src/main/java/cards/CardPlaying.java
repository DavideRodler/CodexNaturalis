package cards;

import cards.face.Face;

public class CardPlaying extends Card {
    private Face front;
    private Face back;

    public Face getFront() {
        return front;
    }

    public Face getBack() {
        return back;
    }
}
