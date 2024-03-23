package model.cards.face;

import model.enums.Suit;

public class CornerResource extends Corner {
    private Suit drawing;

    public Suit getDrawing(){
        return drawing;
    }

    public CornerResource(Suit drawing) {
        super();
        this.drawing = drawing;
    }

}
