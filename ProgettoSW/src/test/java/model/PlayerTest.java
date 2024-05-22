package model;

import model.cards.CardResource;
import model.cards.face.Corner;
import model.cards.face.Face;
import model.enums.SuitEnum;
import model.enums.TokenEnum;
import model.objectives.Objective;
import model.objectives.ObjectiveAssign;
import model.testsTemplate.PlayingStationTemplate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getHand() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        Face backTmp = new Face(new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.EMPTY));
        Face frontTmp = new Face(new Corner(SuitEnum.ANIMAL), new Corner(SuitEnum.NULL), new Corner(SuitEnum.EMPTY), new Corner(SuitEnum.FUNGI));
        Objective obj = new ObjectiveAssign();
        CardResource cardAnimal1 = new CardResource(0, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
        CardResource cardAnimal2 = new CardResource(1, frontTmp, backTmp, SuitEnum.ANIMAL, 1, obj);
        isa.addCardToHand(cardAnimal1);
        isa.addCardToHand(cardAnimal2);
        assertEquals(hand, isa.getHand());

    }

    @Test
    void getToken() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals(TokenEnum.YELLOW, isa.getToken());
    }

    @Test
    void getNickname() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals("isa", isa.getNickname());
    }

    @Test
    void getPoints() {
        PlayingStation station = PlayingStationTemplate.test_2Cards_0Diagonal_c();
        ArrayList<CardResource> hand = new ArrayList<>();
        Player isa = new Player("isa", TokenEnum.YELLOW, station, 2, hand);
        assertEquals(2, isa.getPoints());
    }

    @Test
    void getSelectibleObjectives() {
    }

    @Test
    void getStation() {
    }

    @Test
    void getSecretObjective() {
    }

    @Test
    void removeCardFromHand() {
    }

    @Test
    void getNumberOfCardInHand() {
    }

    @Test
    void addCardToHand() {
    }
}