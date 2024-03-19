package model.cards.face;

public class Corner {
    private boolean covered;

    public Corner(boolean covered) {
        this.covered = covered;
    }

    public boolean isCovered() {
        return covered;
    }

    public Corner() {
        this.covered = false;
    }
}
