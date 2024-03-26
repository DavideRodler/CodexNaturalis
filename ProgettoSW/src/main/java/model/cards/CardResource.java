package model.cards;

import model.cards.face.CornerGold;
import model.cards.face.CornerResource;
import model.cards.face.Face;
import model.enums.GoldSuit;
import model.enums.Suit;

public class CardResource extends CardPlaying{
    private Suit symbol;
    private Integer points;

    public CardResource(int ID, Face front, Face back, Suit symbol, Integer points) {
        super(ID, front, back);
        this.symbol = symbol;
        this.points = points;
    }

    public Suit getSymbol() {
        return symbol;
    }

    public int getPoints() {
        return points;
    }

    public int countGoldResource(GoldSuit suit){
        int count = 0;
        if (!getPlayingBack()){
            if(getFront().getDownLeft() instanceof CornerGold) {
                CornerGold corner = (CornerGold) getFront().getDownLeft();
                if (corner.getDrawing().equals(suit)) {
                    count++;
                }
            }
            if(getFront().getDownRight() instanceof CornerGold) {
                CornerGold corner = (CornerGold) getFront().getDownRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(getFront().getUpLeft() instanceof CornerGold) {
                CornerGold corner = (CornerGold) getFront().getUpLeft();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(getFront().getUpRight() instanceof CornerGold) {
                CornerGold corner = (CornerGold) getFront().getUpRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
        }
        //only front has gold resources
        return count;
    }

    public int countResource(Suit suit){
        int count = 0;
        if (!getPlayingBack()){
            if(getFront().getDownLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getDownLeft();
                if (corner.getDrawing().equals(suit)) {
                    count++;
                }
            }
            if(getFront().getDownRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getDownRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(getFront().getUpLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getUpLeft();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(getFront().getUpRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) getFront().getUpRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
        }
        else {
            if(getSymbol().equals(suit)){ //in back there is only one symbol in the middle
                count++;
            }
        }
        //only front
        return count;
    }

}
