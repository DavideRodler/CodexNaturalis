package model.cards.face;

import java.util.ArrayList;

public class Face {
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
    public ArrayList<Corner>getFaceList(){
        ArrayList<Corner> tmp = new ArrayList<Corner>();
        tmp.add(getUpLeft());
        tmp.add(getUpRight());
        tmp.add(getDownLeft());
        tmp.add(getDownRight());
        return tmp;
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
