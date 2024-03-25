package model.decktameplate;

import model.cards.CardResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class deckconstructorTest {

    @Test
    void deckResource() {
        ArrayList<CardResource> deckResource = deckconstructor.DeckResource();
        for (CardResource c : deckResource) {
            System.out.println("fronte");
            System.out.println(c.getFront().getFaceList());

        }

    }

    @Test
    void startingCardDeck() {
    }

    @Test
    void deckGold() {
    }

    @Test
    void deckObjective() {
    }
}