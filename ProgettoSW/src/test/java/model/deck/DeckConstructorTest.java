package model.deck;

import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckConstructorTest {

  @Test
  public void testDeckResource() throws IOException, ParseException {
    List<CardResource> deck = Decktemplates.ResourceCardDeck();
    assertFalse(deck.isEmpty(), "DeckResource should not be empty");
  }

  @Test
  public void testStartingCardDeck() throws IOException, ParseException {
    List<CardStarting> deck = Decktemplates.StartingCardDeck();
    assertFalse(deck.isEmpty(), "StartingCardDeck should not be empty");
  }

  @Test
  public void testDeckGold() throws IOException, ParseException {
    LinkedList<CardGold> deck = Decktemplates.GoldCardDeck();
    assertFalse(deck.isEmpty(), "DeckGold should not be empty");
  }

  @Test
  public void testDeckObjective() throws IOException, ParseException {
    List<CardObjective> deck = Decktemplates.ObjectiveCardDeck();
    assertFalse(deck.isEmpty(), "DeckObjective should not be empty");
  }

  @Test
  public void printcards() {

    LinkedList<CardGold> deckGold = Decktemplates.GoldCardDeck();
    LinkedList<CardResource> deckResource = Decktemplates.ResourceCardDeck();
    LinkedList<CardStarting> deckStarting = Decktemplates.StartingCardDeck();
    LinkedList<CardObjective> deckObjective = Decktemplates.ObjectiveCardDeck();

    for (CardStarting c : deckStarting) {
      System.out.println(c.getFront().getUpRight().getDrawing());
      System.out.println(c.getFront().getUpLeft().getDrawing());
      System.out.println(c.getFront().getDownRight().getDrawing());
      System.out.println(c.getFront().getDownLeft().getDrawing());
      System.out.println(c.getBack().getUpRight().getDrawing());
      System.out.println(c.getBack().getUpLeft().getDrawing());
      System.out.println(c.getBack().getDownRight().getDrawing());
      System.out.println(c.getBack().getDownLeft().getDrawing());
      System.out.println(c.getSymbols());
      System.out.println("------------------------------------------------------");

    }
    System.out.println("*******************************************************");
    for (CardResource c : deckResource) {
      System.out.println(c.getFront().getUpRight().getDrawing());
      System.out.println(c.getFront().getUpLeft().getDrawing());
      System.out.println(c.getFront().getDownRight().getDrawing());
      System.out.println(c.getFront().getDownLeft().getDrawing());
      System.out.println(c.getBack().getUpRight().getDrawing());
      System.out.println(c.getBack().getUpLeft().getDrawing());
      System.out.println(c.getBack().getDownRight().getDrawing());
      System.out.println(c.getBack().getDownLeft().getDrawing());
      System.out.println(c.getSymbol());
      System.out.println(c.getPoints());
      System.out.println("------------------------------------------------------");
    }
    System.out.println("*******************************************************");
    for (CardGold c : deckGold) {
      System.out.println(c.getFront().getUpRight().getDrawing());
      System.out.println(c.getFront().getUpLeft().getDrawing());
      System.out.println(c.getFront().getDownRight().getDrawing());
      System.out.println(c.getFront().getDownLeft().getDrawing());
      System.out.println(c.getBack().getUpRight().getDrawing());
      System.out.println(c.getBack().getUpLeft().getDrawing());
      System.out.println(c.getBack().getDownRight().getDrawing());
      System.out.println(c.getBack().getDownLeft().getDrawing());
      System.out.println(c.getSymbol());
      System.out.println(c.getPoints());
      System.out.println(c.getCostAnimal());
      System.out.println(c.getCostFungi());
      System.out.println(c.getCostInsect());
      System.out.println(c.getCostPlant());
      System.out.println("------------------------------------------------------");
    }
    System.out.println("*******************************************************");
    for (CardObjective c : deckObjective) {
      System.out.println(c.getPoints());
      System.out.println(c.getObjective());
      System.out.println("------------------------------------------------------");
    }
  }
}
