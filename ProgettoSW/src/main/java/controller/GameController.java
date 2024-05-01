package controller;

import Network.Client.ClientController;
import model.Player;
import model.PlayingBoard;
import model.PlayingStation;
import model.cards.*;
import model.deck.Decktemplates;
import model.enums.TokenEnum;
import model.enums.GameState;
import Exception.*;


import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class GameController extends ClientController implements Serializable {
    private PlayingBoard board;
    private GameState gameState;

    //costructor
    public GameController(PlayingBoard board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    //constructor
    public void addCardToPlayerHand(String nickname, int cardId) throws InterruptedException {
        CardResource card = board.getCardResource(cardId)
                .orElse(board.getCardGold(cardId)
                        .orElseThrow(() -> new IllegalStateException("Central card " + cardId + " not found")));

        Player player = board.getPlayer(nickname);

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
        this.board = new PlayingBoard(firstCardObj, secondCardObj, 0, new ArrayList<Player>(), deckStarting, deckResource, deckObjective, deckGold, null, new ArrayList<CardResource>(), new ArrayList<CardGold>());

        board.getCentralCardsGold().add(board.getDeckCardGold().pop());
        board.getCentralCardsGold().add(board.getDeckCardGold().pop());

        board.getCentralCardsResource().add(board.getDeckCardResource().pop());
        board.getCentralCardsResource().add(board.getDeckCardResource().pop());

        gameState = GameState.WAITING_FOR_PLAYERS_TO_CONNECT;
    }


    public List<TokenEnum> getAvailableToken() {
        return Arrays.stream(TokenEnum.values())
                .filter(t -> board.getPlayers()
                        .stream()
                        .noneMatch(p -> p.getToken().equals(t)))
                .toList();
    }


    public boolean checkNicknameAvailability(String nickname) {
        return board.getPlayers()
                .stream()
                .filter(p -> p.getNickname().equals(nickname))
                .toList()
                .isEmpty();
    }

    /**
     * This method adds a card to the playing station
     * and retrurns the points that generates that card
     *
     * @param card the card to add
     * @param X    the x coordinate
     * @param Y    the y coordinate
     */
    public int addCard(String nickname, CardResource card, Integer X, Integer Y) throws InvalidPlacingCondition {

        Player player = board.getPlayer(nickname);
        try {
            if (player.getStation().isPlayable(card, X, Y)) {
                // Check if the card can be placed
                throw new InvalidPlacingCondition("Non puoi piazzare la carta qua!");
            }
        } catch (InvalidPlacingCondition e) {
            System.out.println(e.getMessage());
            throw new InvalidPlacingCondition("the card cant't be blaced at the coordinates " + X + " " + Y);
        }

        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0, X);
        coordinates.add(1, Y);
        player.getStation().getMap().put(coordinates, card);

        // Update the counters
//        updateCounters(card);

        //calculating the points that the card generates
        if (!(card.getPlayingBack())) {
            return card.getObjective().countObjectivePoints(player.getStation(), card, X, Y);
        } else return 0;
    }

    public void setCentralCardForPlayers() {
        ArrayList<Integer> coordinates = new ArrayList<Integer>();
        coordinates.add(40);
        coordinates.add(40);
        board.getPlayers().stream()
                .map(p -> p.getStation().getMap().put(coordinates, board.getDeckCardStarting().pop()));
    }

    public void setCentralCardPlayedBack(boolean playedback, String nickname) {
        Player player = board.getPlayers().stream()
                .filter(x -> x.getNickname().equals(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("player " + nickname + " not found"));
    }


    public void addPlayer(String nickname, TokenEnum token) {
        this.board.addPlayer(new Player(nickname, token, new PlayingStation(null, new HashMap<ArrayList<Integer>, CardPlaying>()), 0, new ArrayList<CardResource>()));
    }

    //Create the order for the player and set the first player
    public void setPlayerOrder() {
        board.shufflePlayer();
    }

    //for each player i show the two possible objectives that he can choose
    public CardStarting getStartingCard(String nickname) {
        CardStarting firstCard = board.getDeckCardStarting().pop();
        return firstCard;
    }

//public ReducedBoard getReducedBoard(String nickname) {
//    Player player = this.board.getPlayers().get(nickname);
//    if (player == null) {
//        throw new IllegalArgumentException("No player with the given nickname");
//    }
//    return new ReducedBoard(nickname, player.getHand(), player.getStation().getMap(), player.getStation().getSecretObjective());
//}


    public CardObjective[] getObjectiveCards(String nickname) {
        CardObjective first = board.getDeckCardObjective().pop();
        CardObjective second = board.getDeckCardObjective().pop();
        return new CardObjective[]{first, second};
    }


//public CardPlaying drawCard(Integer number, String clientNickname) {
//    switch (number) {
//        case 5:
//            return board.getPlayers().get(clientNickname).addCardFromDeck(board.getDeckCardResource(), board.getDeckCardGold(), "resource");
//        case 6:
//            return board.getPlayers().get(clientNickname).addCardFromDeck(board.getDeckCardResource(), board.getDeckCardGold(), "gold");
//        case 1:
//            return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "upleft");
//        case 2:
//            return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "upright");
//        case 3:
//            return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "downleft");
//        case 4:
//            return board.getPlayers().get(clientNickname).addCardFromCentral(board.getCentralCards(), board.getDeckCardResource(), board.getDeckCardGold(), "downright");
//        default:
//            throw new IllegalArgumentException("Invalid choice");
//    }
//
//}


    public boolean isGamefinished() {
        for (Player player : board.getPlayers()) {
            if (player.getPoints() > 20) {
                return true;
            }
        }
        return false;
    }

    public String winner() {
        int maxpoint = 0;
        String maxpointHolder = "";
        for (Player player : board.getPlayers()) {
            if (maxpoint < player.getPoints()) {
                maxpoint = player.getPoints();
                maxpointHolder = player.getNickname();
            }
        }
        return maxpointHolder;
    }

    public void setPlayerNumber(int playernumber) {
        board.setPlayernumber(playernumber);
    }

    public int getPlayerNumber() {
        return board.getPlayernumber();
    }

    public void assertGameState(GameState gameState) throws NotValidMove {
        if (this.gameState != gameState) {
            throw new NotValidMove();
        }
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

