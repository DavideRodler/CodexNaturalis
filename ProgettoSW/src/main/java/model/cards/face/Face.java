package model.cards.face;

import model.enums.Suit;

import java.io.Serializable;
import java.util.ArrayList;

public class Face implements Serializable {
    private Suit UpRight;
    private Suit UpLeft;
    private Suit DownRight;
    private Suit DownLeft;

    public Face(Suit upRight, Suit upLeft, Suit downRight, Suit downLeft) {
        UpRight = upRight;
        UpLeft = upLeft;
        DownRight = downRight;
        DownLeft = downLeft;
    }
    public ArrayList<Suit>getFaceList(){
        ArrayList<Suit> tmp = new ArrayList<Suit>();
        tmp.add(getUpLeft());
        tmp.add(getUpRight());
        tmp.add(getDownLeft());
        tmp.add(getDownRight());
        return tmp;
    }

    public Suit getUpRight() {
        return UpRight;
    }

    public Suit getUpLeft() {
        return UpLeft;
    }

    public Suit getDownRight() {
        return DownRight;
    }

    public Suit getDownLeft() {
        return DownLeft;
    }


}
