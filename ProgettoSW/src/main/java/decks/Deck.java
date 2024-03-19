package decks;

import cards.Card;

public class Deck {
    private Card[] deck;
    private int counter;

    public Deck(Card[] deck, int counter, boolean isEmpty) {
        this.deck = deck;
        this.counter = counter;
        this.isEmpty = isEmpty;
    }

    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {this.counter = counter;}
//    public boolean isEmpty{
//    }
//
//    public  Card drawCard(){
//
//    }
//
//    public  Card addCardBottom(){
//    }

}
