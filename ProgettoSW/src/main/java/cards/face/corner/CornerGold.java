package cards.face.corner;

import enums.GoldSuit;

public class CornerGold extends Corner{
    private GoldSuit drawing;

    public CornerGold(boolean covered, GoldSuit drawing) {
        super(covered);
        this.drawing = drawing;
    }

    public GoldSuit getDrawing() {
        return drawing;
    } //getter
}
