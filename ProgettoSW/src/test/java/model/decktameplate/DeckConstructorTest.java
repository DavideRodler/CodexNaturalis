package model.decktameplate;

import controller.DeckconstructorController;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckConstructorTest {

    @Test
    public void testDeckResource() throws IOException, ParseException {
        List<CardResource> deck = DeckconstructorController.ResourceCardDeck();
        assertFalse(deck.isEmpty(), "DeckResource should not be empty");
    }

    @Test
    public void testStartingCardDeck() throws IOException, ParseException {
        List<CardStarting> deck = DeckconstructorController.StartingCardDeck();
        assertFalse(deck.isEmpty(), "StartingCardDeck should not be empty");
    }

    @Test
    public void testDeckGold() throws IOException, ParseException {
        List<CardGold> deck = DeckconstructorController.GoldCardDeck();
        assertFalse(deck.isEmpty(), "DeckGold should not be empty");
    }

    @Test
    public void testDeckObjective() throws IOException, ParseException {
        List<CardObjective> deck = DeckconstructorController.ObjectiveCardDeck();
        assertFalse(deck.isEmpty(), "DeckObjective should not be empty");
    }
}