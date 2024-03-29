package controller;

import model.PlayingBoard;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.LinkedList;

import static controller.DeckconstructorController.GoldCardDeck;
import static controller.DeckconstructorController.ResourceCardDeck;
import static controller.DeckconstructorController.ObjectiveCardDeck;
import static controller.DeckconstructorController.StartingCardDeck;
import static java.util.Collections.shuffle;

public class GameController {
    public void startGame() throws IOException, ParseException {
        LinkedList<CardResource> deckCardGold = GoldCardDeck();
        LinkedList<CardResource> deckCardResource = ResourceCardDeck();
        LinkedList<CardObjective> deckCardObjective = ObjectiveCardDeck();
        LinkedList<CardStarting> deckCardStarting = StartingCardDeck();
        shuffle(deckCardGold);
        shuffle(deckCardResource);
        shuffle(deckCardObjective);
        shuffle(deckCardStarting);
        //PlayingBoard board = new PlayingBoard(qua devo mettere metodi per pescare le carte);

    }
}
