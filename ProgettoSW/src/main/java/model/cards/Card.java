package model.cards;

public abstract class Card {
    private Integer ID;

    public Integer getId() {
        return ID;
    }
    public Card(int ID) {
        this.ID = ID;
    }
}
