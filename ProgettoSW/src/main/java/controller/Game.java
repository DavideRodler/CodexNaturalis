package controller;


import model.PlayingBoard;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.deck.Decktemplates;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.LinkedList;

public class Game {
    private PlayingBoard;

    public void startGame() throws IOException, ParseException {
        LinkedList<CardGold> deckGold = Decktemplates.GoldCardDeck();
        LinkedList<CardResource> deckResource = Decktemplates.ResourceCardDeck();
        LinkedList<CardStarting> deckStarting = Decktemplates.StartingCardDeck();
        LinkedList<CardObjective> deckObjective = Decktemplates.ObjectiveCardDeck();








    }
}
