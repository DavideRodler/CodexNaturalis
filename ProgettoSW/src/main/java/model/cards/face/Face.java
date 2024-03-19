package model.cards.face;

public class Face {
    private Corner Upright;
    private Corner UpLeft;
    private Corner DownRight;
    private Corner DownLeft;

    public Corner GetUpRight() {
        return DownLeft;
    }

    public Corner getUpLeft() {
        return UpLeft;
    }

    public Corner getDownRight(Corner downRight) {
        return DownRight;
    }

    public Corner getDownLeft() {
        return DownLeft;
    }
}
