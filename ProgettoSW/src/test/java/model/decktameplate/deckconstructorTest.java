package model.decktameplate;

import model.cards.CardGold;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.decktameplate.deckconstructor;
import model.objectives.Objective;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckConstructorTest {

    @Test
    public void testDeckResource() {
        List<CardResource> deck = deckconstructor.DeckResource();
        assertFalse(deck.isEmpty(), "DeckResource should not be empty");
    }

    @Test
    public void testStartingCardDeck() {
        List<CardStarting> deck = deckconstructor.StartingCardDeck();
        assertFalse(deck.isEmpty(), "StartingCardDeck should not be empty");
    }

    @Test
    public void testDeckGold() {
        List<CardGold> deck = deckconstructor.DeckGold();
        assertFalse(deck.isEmpty(), "DeckGold should not be empty");
    }

    //@Test
    //public void testDeckObjective() {
      //  List<Objective> deck = deckconstructor.deckObjective();
        //assertFalse(deck.isEmpty(), "DeckObjective should not be empty");
    //}
}