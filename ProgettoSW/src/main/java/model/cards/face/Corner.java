package model.cards.face;
import model.enums.SuitEnum;

import java.io.Serializable;

public class Corner implements Serializable {
    private boolean covered;
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
