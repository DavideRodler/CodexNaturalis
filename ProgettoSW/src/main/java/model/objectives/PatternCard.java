package model.objectives;

import model.enums.SuitEnum;

public class PatternCard {
    private int dx;
    private int dy;
    private SuitEnum suit;

    public PatternCard(int dx, int dy, SuitEnum suit) {
        this.dx = dx;
        this.dy = dy;
        this.suit = suit;
    }


    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public SuitEnum getSuit() {
        return suit;
    }

    // getters...
}