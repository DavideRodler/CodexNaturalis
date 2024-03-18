package Players;

import cards.Card;
import cards.CardObjective;
import cards.CardResource;
import cards.CardStarting;

public class PlayingStation {
    private Card[][] table;
    private Integer countInsect;
    private Integer countAnimal;
    private Integer countPlant;
    private Integer countFungi;
    private Integer countInkwell;
    private Integer countQuill;
    private Integer countManuscript;
    private CardStarting cardStarting;

    // Costruttore
    public PlayingStation() {
        this.table = new Card[80][80];
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                table[i][j] = null;
            }
        }
        this.countInsect = 0;
        this.countAnimal = 0;
        this.countPlant = 0;
        this.countFungi = 0;
        this.countInkwell = 0;
        this.countQuill = 0;
        this.countManuscript = 0;
    }



    public void addCard(CardResource card, int i, int j) {
        if (i >= 0 && i < 80 && j >= 0 && j < 80) {
            table[i][j] = card;
            updateCounters(card);
        }
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

    public void setCountInsect(Integer countInsect) {
        this.countInsect = countInsect;
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

    private void updateCounters(CardResource card) {
        switch (card.getSymbol()) {
            case INSECT:
                countInsect++;
                break;
            case ANIMAL:
                countAnimal++;
                break;
            case PLANT:
                countPlant++;
                break;
            case FUNGI:
                countFungi++;
                break;
        }
    }

}
