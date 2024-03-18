package cards.face.corner;

import enums.Suit;

public class CornerResource extends Corner {
    private Suit drawing;

    public CornerResource(boolean covered, Suit drawing) {
        super(covered);
        this.drawing = drawing;
    }

    public Suit getDrawing() {
        return drawing;
    }
}
