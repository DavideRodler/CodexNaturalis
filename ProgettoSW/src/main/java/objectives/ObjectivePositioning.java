package objectives;
import cards.Card;
import enums.Suit;
import enums.Direction;
import enums.Position;
public class ObjectivePositioning extends Objective{
    public Suit colorOneCard;
    public Suit colorTwoCards;
public Direction horizontalDirection;
public Position verticalDirection;
    public boolean checkObjective(Card[][] table){

    }

    public Suit getColorOneCard() {
        return colorOneCard;
    }

    public Suit getColorTwoCards() {
        return colorTwoCards;
    }

    public Position getVerticalDirection() {
        return verticalDirection;
    }

    public Direction getHorizontalDirection() {
        return horizontalDirection;
    }
}