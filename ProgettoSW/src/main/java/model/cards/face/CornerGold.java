package model.cards.face;

import model.enums.GoldSuit;

public class CornerGold extends Corner {
    private GoldSuit drawing;

    public CornerGold(GoldSuit drawing) {
        super();
        this.drawing = drawing;
    }

    public GoldSuit getDrawing() {
        return drawing;
    } //getter
}
