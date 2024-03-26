package model.cards.face;
import model.enums.Suit;
public class Corner {
    private boolean covered;
    private Suit drawing;

    public Corner(Suit drawing) {

        this.covered = false;
        this.drawing = drawing;
    }

    public boolean isCovered() {
        return covered;
    }

    public Suit getDrawing(){
        return drawing;
    }
}
