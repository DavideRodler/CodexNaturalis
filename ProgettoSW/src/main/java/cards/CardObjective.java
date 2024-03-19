package cards;

public class CardObjective extends Card{
    private int points;

    public CardObjective(int ID, int points) {
        super(ID);
        this.points = points;
    }

    public int getPoints() {
        return points;
    } //getter
}
