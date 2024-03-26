package model.cards.face;
import model.enums.Suit;
public class Corner {
    private boolean covered;
    private Suit drawing;

    public Corner(boolean covered, Suit drawing) {

        this.covered = covered;
        this.drawing = drawing;
    }

    public boolean isCovered() {
        return covered;
    }

    public Corner() {
        this.covered = false;
    }

    public Suit getDrawing(){
        return drawing;
    }
}
