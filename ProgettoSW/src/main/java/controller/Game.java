package controller;

import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.CardGold;
import model.cards.CardObjective;
import model.cards.CardResource;
import model.cards.CardStarting;
import model.deck.Decktemplates;
import model.enums.GameState;

import java.util.LinkedList;
import java.util.Scanner;

public class Game {
    private PlayingBoard board;

    private GameState gameState;

    //constructor
    public Game() {initGameController();}

    //getter
    public PlayingBoard getBoard() {
        return board;
    }

    public void initGameController() {
        // creating decks
        LinkedList<CardGold> deckGold = Decktemplates.GoldCardDeck();
        LinkedList<CardResource> deckResource = Decktemplates.ResourceCardDeck();
        LinkedList<CardStarting> deckStarting = Decktemplates.StartingCardDeck();
        LinkedList<CardObjective> deckObjective = Decktemplates.ObjectiveCardDeck();

        // popping objective
        CardObjective firstCardObj = deckObjective.pop();
        CardObjective secondCardObj = deckObjective.pop();

        //creating the board
        this.board = new PlayingBoard(firstCardObj, secondCardObj,deckGold, deckObjective, deckResource, deckStarting);
        setGameState(GameState.LOGIN);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public void addPlayer(String nickname) {
        this.board.addPlayer(new Player(nickname));
    }

    //Create the order for the player and set the first player
    public void setPlayerOrder() {
        //shuffleing the Playerlist
        board.shufflePlayer();
        //setting the PlayerNumber
        for (int i = 0; i < board.getPlayers().size(); i++) {
            board.getPlayers().get(i).setPlayerNumber(i + 1);
        }
    }

    //gives the player a playingstation after he selects the playingstation
    private void givePlayerStation(Player p, CardObjective obj) {
        CardStarting startingCard = board.getDeckCardStarting().pop();
        PlayingStation station = new PlayingStation(p, startingCard, obj);
    }

    //for each player i show the two possible objectives that he can choose
    public void createSations(){
        for (int i = 0; i < board.getPlayers().size(); i++) {
            CardObjective firstObjective = board.getDeckCardObjective().pop();
            CardObjective secondObjective = board.getDeckCardObjective().pop();
            System.out.println(firstObjective.getObjective().toString());
            System.out.println(secondObjective.getObjective().toString());
            System.out.println(board.getPlayers().get(i).getNickname() + "seleziona obiettivo 0 o 1");
            Scanner scanner = new Scanner(System.in);
            int selezione = scanner.nextInt();
            if (selezione == 0){
                givePlayerStation(board.getPlayers().get(i), firstObjective);
            }
            else {
                givePlayerStation(board.getPlayers().get(i), secondObjective);
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initGameController();
        game.addPlayer("tommy");
        game.addPlayer("davide");
        game.addPlayer("isa");
        game.addPlayer("eric");
        game.setPlayerOrder();
        game.getBoard().getPlayers().stream().map(x -> x.getNickname()).forEach(System.out::println);
        game.createSations();
    }
}
