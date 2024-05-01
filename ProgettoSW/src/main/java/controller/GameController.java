package controller;

import Network.Client.ClientController;
import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.ReducedBoard;
import model.cards.*;
import model.deck.Decktemplates;
import model.enums.GameState;

import java.io.Serializable;
import java.util.*;

public class GameController extends ClientController implements Serializable {
    private PlayingBoard board;

    private GameState gameState;

    //constructor
    public GameController() {}

    //getter
    public PlayingBoard getBoard() {
        return board;
    }

    public void initGameController() {
        // creating decks
        LinkedList<CardStarting> deckStarting = Decktemplates.StartingCardDeck(); //ID : 0 -> 5
        LinkedList<CardObjective> deckObjective = Decktemplates.ObjectiveCardDeck(); //ID : 6 -> 21
        LinkedList<CardResource> deckResource = Decktemplates.ResourceCardDeck(); //ID : 22 -> 61
        LinkedList<CardGold> deckGold = Decktemplates.GoldCardDeck(); //ID : 62 -> 101



        //shuffleing the decks
        Collections.shuffle(deckGold);
        Collections.shuffle(deckResource);
        Collections.shuffle(deckStarting);
        Collections.shuffle(deckObjective);

        // popping objective
        CardObjective firstCardObj = deckObjective.pop();
        CardObjective secondCardObj = deckObjective.pop();

        //creating the board
        this.board = new PlayingBoard(deckGold, deckObjective, deckResource, deckStarting,firstCardObj, secondCardObj);
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
        board.shufflePlayer();
    }

    //gives the player a playingstation after he selects the playingstation
    private void givePlayerStation(Player p, CardObjective obj) {
        CardStarting startingCard = board.getCardStartingFromDeck();
        PlayingStation station = new PlayingStation(p, startingCard, obj);
        p.setStation(station);
    }

    public boolean takenNickname(String nickname) {
        return board.nicknameChecker(nickname);
    }

    //for each player i show the two possible objectives that he can choose
    public CardStarting getStartingCard(String nickname) {
           CardStarting firstCard = board.getDeckCardStarting().pop();
           return firstCard;
    }

    public ReducedBoard getReducedBoard(String nickname) {
        Player player = this.board.getPlayers().get(nickname);
        if (player == null) {
            throw new IllegalArgumentException("No player with the given nickname");
        }
        return new ReducedBoard(nickname, player.getHand(), player.getStation().getTable() , player.getStation().getSecretObjective());
    }


    public boolean addCard(CardPlaying card,Integer choice, Integer X, Integer Y, String name) {
        return board.getPlayers().get(name).getStation().addCard((CardResource) card,X,Y);
    }

    public CardObjective[] getObjectiveCards(String nickname) {
        CardObjective first = board.getDeckCardObjective().pop();
        CardObjective second = board.getDeckCardObjective().pop();
        return new CardObjective[]{first, second};
    }


    public CardPlaying drawCard(Integer number, String clientNickname) {
        switch(number) {
                case 5:
                    return board.getPlayers().get(clientNickname).addCardFromDeck(board.getDeckCardResource(), board.getDeckCardGold(), "resource");
                case 6:
                    return board.getPlayers().get(clientNickname).addCardFromDeck(board.getDeckCardResource(), board.getDeckCardGold(), "gold");
                case 1:
                    return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "upleft");
                case 2:
                    return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "upright");
                case 3:
                    return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "downleft");
                case 4:
                    return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "downright");
            default:
                throw new IllegalArgumentException("Invalid choice");
        }

    }
    public void inizializePlayingStation(String clientNickname, CardPlaying startingCard, Integer choice, CardObjective cardObjective) {
        Player player = getBoard().getPlayers().get(clientNickname);
        PlayingStation station = new PlayingStation(player, startingCard, cardObjective);
        player.setStation(station);
    }

    public boolean isGamefinished(){
        for (Player player : board.getPlayers().values()){
            if (player.getPoints() > 20){
                return true;
            }
        }
        return false;
    }

    public String winner(){
       int maxpoint = 0;
       String maxpointHolder = "";
        for (Player player : board.getPlayers().values()){
            if (maxpoint < player.getPoints()){
                maxpoint = player.getPoints();
                maxpointHolder = player.getNickname();
            }
        }
        return maxpointHolder;
    }

    public void setPlayerNumber(int playernumber){
        board.setPlayernumber(playernumber);
    }
    public int  getPlayerNumber(){
        return board.getPlayernumber();
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
