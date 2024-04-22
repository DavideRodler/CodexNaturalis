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

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class GameController {
    private PlayingBoard board;

    private GameState gameState;
    private Player activeplayer;

    //constructor
    public GameController() {}
    //setter
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

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

        //shuffleing the decks
        Collections.shuffle(deckGold);
        Collections.shuffle(deckResource);
        Collections.shuffle(deckStarting);
        Collections.shuffle(deckObjective);

        // popping objective
        CardObjective firstCardObj = deckObjective.pop();
        CardObjective secondCardObj = deckObjective.pop();

        //creating the board
        this.board = new PlayingBoard(firstCardObj, secondCardObj,deckGold, deckObjective, deckResource, deckStarting);

        //putting the first card on the central board
        CardResource firstCardRes = board.drawCardResource();
        CardResource secondCardRes = board.drawCardResource();

        CardGold firstCardGodl = board.drawCardGold();
        CardGold secondtCardGold = board.drawCardGold();

        board.setCentralFirsResourceCard(firstCardRes);
        board.setCentralSecondResourceCard(secondCardRes);

        board.setCentralFristGoldCard(firstCardGodl);
        board.setCentralSecondGoldCard(secondtCardGold);

        setGameState(GameState.LOGIN);
    }

    public void addPlayer(String nickname) {
        this.board.addPlayer(new Player(nickname));
    }

    //Create the order for the player and set the first player
    public void setPlayerOrder() {
        if (gameState.equals(GameState.INIT)) {
            //shuffleing the Playerlist
            board.shufflePlayer();
            //setting the PlayerNumber
            for (int i = 0; i < board.getPlayers().size(); i++) {
                board.getPlayers().get(i).setPlayerNumber(i + 1);
            }
            //the first player to play is the first in the list
            this.activeplayer = board.getPlayers().get(0);

        }
    }

    //gives the player a playingstation after he selects the playingstation
    private void givePlayerStation(Player p, CardObjective obj) {
        CardStarting startingCard = board.getDeckCardStarting().pop();
        PlayingStation station = new PlayingStation(p, startingCard, obj);
        p.setStation(station);
    }

    public boolean takenNickname(String nickname) {
        return board.getPlayers().containsKey(nickname);
    }

    public Integer numberOfPlayer() {
        return board.getPlayers().size();
    }
    //for each player i show the two possible objectives that he can choose
    public void createSations(){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < board.getPlayers().size(); i++) {
            // popping two cards from the deck
            CardObjective firstObjectiveToChoose = board.getDeckCardObjective().pop();
            CardObjective secondObjectiveToChoose = board.getDeckCardObjective().pop();
            //selecting the cardobjective
            System.out.println(firstObjectiveToChoose.getObjective().toString());
            System.out.println(secondObjectiveToChoose.getObjective().toString());
            System.out.println(board.getPlayers().get(i).getNickname() + " select the objective with 0 or 1");
            int selezione = scanner.nextInt();
            System.out.println(selezione);
            //putting the cardobjective in the player attribute and then putting the other card at the bottom of the deck
            switch (selezione){
                case 0:
                    System.out.println(board.getPlayers().get(i).getNickname() + " has selected the objective" + firstObjectiveToChoose.getObjective().toString());
                    givePlayerStation(board.getPlayers().get(i), firstObjectiveToChoose);
                    board.getDeckCardObjective().addLast(secondObjectiveToChoose);
                    break;
                case 1:
                    System.out.println(board.getPlayers().get(i).getNickname() + " has selected the objective" + secondObjectiveToChoose.getObjective().toString());
                    givePlayerStation(board.getPlayers().get(i), secondObjectiveToChoose);
                    board.getDeckCardObjective().addLast(firstObjectiveToChoose);
                    break;
                default:
                    System.out.println("number out of bound");

            }

        }
    }

    public void addStartingCard(String nickname) {
            getBoard().getPlayers().get(nickname).setStartingCard(board.getDeckCardStarting().pop());
    }

   /* public static void main(String[] args) {
        Game game = new Game();
        game.initGameController();
        game.addPlayer("tommy");
        game.addPlayer("davide");
        game.addPlayer("isa");
        game.addPlayer("eric");
        game.setPlayerOrder();
        game.getBoard().getPlayers().stream().map(x -> x.getNickname()).forEach(System.out::println);
        game.createSations();
    }*/
}
