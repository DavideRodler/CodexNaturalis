package controller;


import model.Player;
import model.PlayingBoard;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.deck.Decktemplates;

import java.util.LinkedList;

public class Game {
    private PlayingBoard board;

    //constructor
    public Game() {
    }

    //getter
    public PlayingBoard getBoard() {
        return board;
    }

    public void startGame()  {
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
        this.board.addPlayer(new Player(nickname));
    }

    //Create the order for the player and set the first player
    public void setPlayerOrder(){
        //shuffleing the Playerlist
        board.shufflePlayer();
        //setting the PlayerNumber
        for (int i = 0; i < board.getPlayers().size(); i++) {
            board.getPlayers().get(i).setPlayerNumber(i+1);
        }
    }
    public void endGame(){
        //se sono all'ultimo turno devo verificare che tutti abbiano fatto un numero di turni uguale'
    }
}
