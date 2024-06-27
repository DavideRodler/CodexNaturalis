package model.cards.face;

import java.io.Serializable;

public class Face implements Serializable{
    private Corner UpRight;
    private Corner UpLeft;
    private Corner DownRight;
    private Corner DownLeft;

    public Face(Corner upRight, Corner upLeft, Corner downRight, Corner downLeft) {
        UpRight = upRight;
        UpLeft = upLeft;
        DownRight = downRight;
        DownLeft = downLeft;
    }

    public Corner getUpRight() {
        return UpRight;
    }

    public Corner getUpLeft() {
        return UpLeft;
    }

    public Corner getDownRight() {
        return DownRight;
    }

    public Corner getDownLeft() {
        return DownLeft;
    }


}
