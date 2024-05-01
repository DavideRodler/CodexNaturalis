package model.cards.face;

import model.enums.SuitEnum;

import java.io.Serializable;
import java.util.ArrayList;

public class Face implements Serializable {
    private SuitEnum UpRight;
    private SuitEnum UpLeft;
    private SuitEnum DownRight;
    private SuitEnum DownLeft;

    public Face(SuitEnum upRight, SuitEnum upLeft, SuitEnum downRight, SuitEnum downLeft) {
        UpRight = upRight;
        UpLeft = upLeft;
        DownRight = downRight;
        DownLeft = downLeft;
    }
    public ArrayList<SuitEnum>getFaceList(){
        ArrayList<SuitEnum> tmp = new ArrayList<SuitEnum>();
        tmp.add(getUpLeft());
        tmp.add(getUpRight());
        tmp.add(getDownLeft());
        tmp.add(getDownRight());
        return tmp;
    }

    public SuitEnum getUpRight() {
        return UpRight;
    }

    public SuitEnum getUpLeft() {
        return UpLeft;
    }

    public SuitEnum getDownRight() {
        return DownRight;
    }

    public SuitEnum getDownLeft() {
        return DownLeft;
    }


}
