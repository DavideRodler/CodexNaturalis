package model.cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private Integer ID;

    public Integer getId() {
        return ID;
    }
    public Card(int ID) {
        this.ID = ID;
    }
}
