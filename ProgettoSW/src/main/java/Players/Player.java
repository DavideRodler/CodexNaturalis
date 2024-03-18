package Players;


import cards.CardResource;
import decks.Deck;
import enums.Color;
import cards.CardObjective;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private final String nickname;
    private final Integer playerNumber;
    private Integer numberTurns;
    private Integer points;
    private Color token;
    private PlayingStation station;
    private Integer pointsObjective;
    private List<CardResource> hand;
    private final List<CardObjective> secretObjective;


    public Player(String nickname, Integer playerNumber) { //constructor
        this.nickname = nickname;
        this.playerNumber = playerNumber;
        this.numberTurns = 0;
        this.points = 0;
        this.pointsObjective = 0;
        this.hand = new ArrayList<>(3);
        this.secretObjective = new ArrayList<>(2);
    }


    //--------------------GETTING FASE STARTING----------------------------
    public List<CardResource> getHand() {
        return hand;
    }

    public Color getToken() {
        return token;
    }

    public PlayingStation getStation() {
        return station;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getPointsObjective() {
        return pointsObjective;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getNumberTurns() {
        return numberTurns;
    }
    //--------------------GETTING FASE ENDING----------------------------

    //--------------------SETTING FASE STARTING----------------------------
    public void setNumberTurns(Integer numberTurns) {
        this.numberTurns = numberTurns;
    }

    public void setPointsObjective(Integer pointsObjective) {
        this.pointsObjective = pointsObjective;
    }

    public void setStation(PlayingStation station) {
        this.station = station;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
    //--------------------SETTING FASE ENDING----------------------------


    public void addCardFromDeck(Deck deck) throws DeckEmptyException { //Have to add exception classes
        CardResource card = (CardResource) deck.drawCard();
        if (card == null) {
            throw new DeckEmptyException("Il mazzo è vuoto.");
        } else {
            hand.add(card);
        }
    }

    public void removeCard(Integer id) {
        int i=0;
        while (hand.get(i).getId().equals(id) || i< hand.size()) {
            hand.remove(i);
            i++;
        }

    }

    //FOR NOW I HAVE PUT THE CHOICE OF THE RANDOM PAKERS
    public void chooseToken() {
        Random random = new Random();
        this.token = Color.values()[random.nextInt(Color.values().length)];
    }

    public CardObjective chooseObjective(CardObjective objective) {

        return objective;
    }

    public void addCardFromCentral(CardResource card) {
        hand.add(card);
    }

    public boolean checkForWin() {

    }


}


//!!!!!!!!!BISOGNA AGGIUNGERE IN OGNI METODO CHE MANIPOLA LA MANO EXCEPTION CHE CONTROLLANO CHE NON SI SUPERI MAI IL LIMITE DELLA MAX_LEN DELLA MANO (3)!!!!!