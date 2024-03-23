package model.cards;
import model.cards.face.CornerResource;
import model.enums.Suit;
import model.cards.face.Face;

public class CardPlaying extends Card {
    private Face front;
    private Face back;
    private Boolean playingBack;

    public CardPlaying(int ID, Face front, Face back) {
        super(ID);
        this.front = front;
        this.back = back;
    }

    public Face getFront() {
        return front;
    }

    public Face getBack() {
        return back;
    }
    public void setPlayingBack(boolean playingBack){
        this.playingBack=playingBack;
    }
    public boolean getPlayingBack(){
        return playingBack;
    }

    public int countResource(Suit suit){
        int count = 0;
        if (!playingBack){
            if(front.getDownLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) front.getDownLeft();
                if (corner.getDrawing().equals(suit)) {
                    count++;
                }
            }
            if(front.getDownRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) front.getDownRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(front.getUpLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) front.getUpLeft();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(front.getUpRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) front.getUpRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
        }
        else{
            if(back.getDownLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) back.getDownLeft();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(back.getDownRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) back.getDownRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(back.getUpLeft() instanceof CornerResource) {
                CornerResource corner = (CornerResource) back.getUpLeft();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
            if(back.getUpRight() instanceof CornerResource) {
                CornerResource corner = (CornerResource) back.getUpRight();
                if(corner.getDrawing().equals(suit)){
                    count++;
                }
            }
        }
        return count;
    }
}
