package model.cards.face;

import model.enums.SuitEnum;
import java.io.Serializable;

/**
 * This class represents the corner of a card.
 * It can be covered or not, and it has a drawing on it.
 */
public class Corner implements Serializable {
    /**
     * True if the corner is covered, false otherwise.
     */
    private boolean covered;
    /**
     * The drawing on the corner.
     */
    private SuitEnum drawing;


    public Corner(SuitEnum drawing) {
        this.covered = false;
        this.drawing = drawing;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public SuitEnum getDrawing(){
        return drawing;
    }
}
