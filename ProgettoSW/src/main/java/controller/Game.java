package controller;


import model.Player;
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
    private PlayingBoard board;

    public void startGame() throws IOException, ParseException {
        // creating decks
        LinkedList<CardGold> deckGold = Decktemplates.GoldCardDeck();
        LinkedList<CardResource> deckResource = Decktemplates.ResourceCardDeck();
        LinkedList<CardStarting> deckStarting = Decktemplates.StartingCardDeck();
        LinkedList<CardObjective> deckObjective = Decktemplates.ObjectiveCardDeck();

        // popping objective
        CardObjective firstCardObj = deckObjective.pop();
        CardObjective secondCardObj = deckObjective.pop();

        //creating the board
        this.board = new PlayingBoard(firstCardObj, secondCardObj);
    }

    public void addPlayer(String nickname){
        this.board.addPlayer(new Player(nickname,0));
    }
    public void setPlayerOrder(){


    }
    public void endGame(){
        //se sono all'ultimo turno devo verificare che tutti abbiano fatto un numero di turni uguale'
    }
}
