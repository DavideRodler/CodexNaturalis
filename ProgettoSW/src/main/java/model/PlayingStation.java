package model;

import model.cards.*;
import model.cards.face.Corner;
import model.enums.Suit;


import java.util.*;

public class PlayingStation {
    private HashMap<ArrayList<Integer>, CardPlaying> table;
    private Integer countInsect;
    private Integer countAnimal;
    private Integer countPlant;
    private Integer countFungi;
    private Integer countInkwell;
    private Integer countQuill;
    private Integer countManuscript;
    private CardStarting cardStarting;

    private CardObjective firstSecretObjective;
    private CardObjective secondSecretObjective;


    public void setFirstSecretObjective(CardObjective firstSecretObjective) {
        this.firstSecretObjective = firstSecretObjective;
    }

    public void setSetSecondSecretObjective(CardObjective SecondSecretObjective) {
        this.secondSecretObjective = SecondSecretObjective;
    }

    public CardObjective getFirstSecretObjective() {
        return firstSecretObjective;
    }

    public CardObjective getSecondSecretObjective() {
        return secondSecretObjective;
    }

    public ArrayList<Integer> getCoordinates(Card card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : table.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey();
            }
        }
        return null; // Return null if the card is not found
    }

    public Integer getXCoordinate(Card card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : table.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey().get(0);
            }
        }
        return null; // Return null if the card is not found
    }

    public Integer getYCoordinate(Card card) {
        for (Map.Entry<ArrayList<Integer>, CardPlaying> entry : table.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey().get(1);
            }
        }
        return null; // Return null if the card is not found
    }

    public Card getCard(int x, int y) {
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, x);
        coordinates.add(1, y);
        return table.get(coordinates);
    }



    // Costruttore
    public PlayingStation(CardStarting startCart, CardObjective firstObjective, CardObjective secondObjective) {
        this.table = new HashMap<>();
        for (int i = 0; i < 42; i++) {
            ArrayList<Integer> coordinates = new ArrayList<>();
            coordinates.add(0, -1);
            coordinates.add(1, -1);
            table.put(coordinates, null);
        }
        ArrayList<Integer> coordi = new ArrayList<Integer>();
        coordi.add(41);
        coordi.add(41);
        this.table.put(coordi, startCart);
        this.firstSecretObjective =  firstObjective;
        this.secondSecretObjective =  secondObjective;
        this.countInsect = 0;
        this.countAnimal = 0;
        this.countPlant = 0;
        this.countFungi = 0;
        this.countInkwell = 0;
        this.countQuill = 0;
        this.countManuscript = 0;
    }


    public void addCard(CardResource card, Integer X, Integer Y) {
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, X);
        coordinates.add(1, Y);
        table.put(coordinates, card);
        //richiamo updateCounters passando come parametro il corner che viene coperto
        setCountAnimal(card);
        setCountFungi(card);
        setCountInsect(card);
        setCountPlant(card);
        setCountInkwell(card);
        setCountManuscript(card);
        setCountQuill(card);
    }

    public void addCardStarting(CardStarting card) {
        this.cardStarting = card;
    }


    // Metodi getter e setter
    public CardStarting getCardStarting() {
        return cardStarting;
    }

    public Integer getCountInsect() {
        return countInsect;
    }

    public Integer getCountAnimal() {
        return countAnimal;
    }

    public Integer getCountPlant() {
        return countPlant;
    }

    public Integer getCountFungi() {
        return countFungi;
    }

    public Integer getCountInkwell() {
        return countInkwell;
    }

    public Integer getCountQuill() {
        return countQuill;
    }

    public Integer getCountManuscript() {
        return countManuscript;
    }

    public void setCountInsect(CardPlaying card) {
        countInsect += card.countResource(Suit.INSECT);
    }

    public void setCountAnimal(CardPlaying card) {
        countAnimal += card.countResource(Suit.ANIMAL);
    }

    public void setCountPlant(CardPlaying card) {
        countPlant += card.countResource(Suit.PLANT);
    }

    public void setCountFungi(CardPlaying card) {
        countFungi += card.countResource(Suit.FUNGI);
    }

    public void setCountInkwell(CardResource card) {
        countInkwell += card.countResource(Suit.INKWELL);
    }

    public void setCountQuill(CardResource card) {
        countQuill += card.countResource(Suit.QUILL);
    }

    public void setCountManuscript(CardResource card) {
        countManuscript += card.countResource(Suit.MANUSCRIPT);
    }

    public void setObjective(CardObjective objective) {

    }

    public void updateCounters(Corner corner) {
            switch (corner.getDrawing()) {
                case QUILL -> countQuill--;
                case MANUSCRIPT -> countManuscript--;
                case INKWELL -> countInkwell--;
                case FUNGI -> countFungi--;
                case PLANT -> countPlant--;
                case ANIMAL -> countAnimal--;
                case INSECT -> countInsect--;
              //  case EMPTY -> null;
            }
        }

    public HashMap<ArrayList<Integer>, CardPlaying> getTable(){
        return table;
    }
}
