package model;

import model.cards.*;

import java.lang.reflect.Array;
import java.util.*;

public class PlayingStation {
    private HashMap<ArrayList<Integer>, Card> table;


    public ArrayList<Integer> getCoordinates(Card card) {
        for (Map.Entry<ArrayList<Integer>, Card> entry : table.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey();
            }
        }
        return null; // Return null if the card is not found
    }

    public Integer getXCoordinate(Card card) {
        for (Map.Entry<ArrayList<Integer>, Card> entry : table.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey().get(0);
            }
        }
        return null; // Return null if the card is not found
    }

    public Integer getYCoordinate(Card card) {
        for (Map.Entry<ArrayList<Integer>, Card> entry : table.entrySet()) {
            if (entry.getValue().equals(card)) {
                return entry.getKey().get(1);
            }
        }
        return null; // Return null if the card is not found
    }

    public Card ArraygetCard(int x, int y) {
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, x);
        coordinates.add(1, y);
        return table.get(coordinates);
    }

    private Integer countInsect;
    private Integer countAnimal;
    private Integer countPlant;
    private Integer countFungi;
    private Integer countInkwell;
    private Integer countQuill;
    private Integer countManuscript;
    private CardStarting cardStarting;

    private final List<CardObjective> secretObjective;

    // Costruttore
    public PlayingStation() {
        this.table = new HashMap<>();
        for (int i = 0; i < 40; i++) {
            ArrayList<Integer> coordinates = new ArrayList<>();
            coordinates.add(0, -1);
            coordinates.add(1, -1);
            table.put(coordinates, null);

        }
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
        updateCounters(card);
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
        card.
    }

    public void setCountAnimal(Integer countAnimal) {
        this.countAnimal = countAnimal;
    }

    public void setCountPlant(Integer countPlant) {
        this.countPlant = countPlant;
    }

    public void setCountFungi(Integer countFungi) {
        this.countFungi = countFungi;
    }

    public void setCountInkwell(Integer countInkwell) {
        this.countInkwell = countInkwell;
    }

    public void setCountQuill(Integer countQuill) {
        this.countQuill = countQuill;
    }

    public void setCountManuscript(Integer countManuscript) {
        this.countManuscript = countManuscript;
    }
    public void setObjective(CardObjective objective) {

    }



}
